/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerUtils;

import Controllers.GetPrincipal;
import Pojos.persona;
import Views.Modulo1;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import ds.desktop.notify.DesktopNotify;
import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mauricio Herrera
 */
public final class CaptureFinger {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private final DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    private final DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    //Inicio variables para la extraccion de caracteristicas huella
    public DPFPFeatureSet featuresinscripcion;
    private Modulo1 pr;
    private persona persona;
    private int idUsuario;
    private String usuario;
    private int idSede;
    private int idempresa;
    private int idPersona;

    public CaptureFinger() {
//        pr = GetPrincipal.getPrincipal();
        Iniciar();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    //Inicio metodo Iniciar
    protected void Iniciar() {

        Lector.addDataListener(
                new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("La Huella Digital ha sido Capturada");
                        try {
                            ProcesarCaptura(e.getSample());
                        } catch (IOException ex) {
                            System.out.println("error " + ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CaptureFinger.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }
        );

        Lector.addReaderStatusListener(
                new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e
            ) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e
            ) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
                    }
                });
            }
        }
        );

        Lector.addSensorListener(
                new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e
            ) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e
            ) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El dedo ha sido quitado del Lector de Huella");
                    }
                });
            }
        }
        );

        Lector.addErrorListener(new DPFPErrorAdapter() {

            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("Error: " + e.getError());
                    }
                });
            }
        }
        );
    }
    //Fin metodo Iniciar

    //Inicio metodo procesar captura
    public void ProcesarCaptura(DPFPSample sample) throws IOException, InterruptedException {
        // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
        featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
        if (featuresinscripcion != null) {
            try {
                System.out.println("Las Caracteristicas de la Huella han sido creada");
                Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear

                // Dibuja la huella dactilar capturada.
                Image image = CrearImagenHuella(sample);
                DibujarHuella(image);

            } catch (DPFPImageQualityException ex) {
                System.err.println("Error: " + ex.getMessage());
            } finally {
                EstadoHuellas();
                // Comprueba si la plantilla se ha creado.
                switch (Reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:	// informe de éxito y detiene  la captura de huellas
                        stop();
                        setTemplate(Reclutador.getTemplate());
//                        EnviarTexto("La Plantilla de la Huella ha Sido Creada, ya puede Verificarla o Identificarla");
                         {
                            try {
                                guardarHuella();
                            } catch (SQLException ex) {
                                System.out.println("error " + ex);
                            }
                        }
                        break;

                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                        Reclutador.clear();
                        stop();
                        EstadoHuellas();
                        setTemplate(null);
                        JOptionPane.showMessageDialog(null, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                }
            }
        }
    }
    //Fin metodo procesar captura

    //Inicio metodo extraer caracteristicas huella
    public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }
    //fin metodo extraer caracteristicas huella

    //Inicio metodo CrearImagenHuella
    public Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    //fin metodo CrearImagenHuella

    //inicio metodo dibujar huella en el label
    public void DibujarHuella(Image image) {
//        pr.lblImagenHuella.setIcon(new ImageIcon(
//                image.getScaledInstance(pr.lblImagenHuella.getWidth(), pr.lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
//        pr.lblImagenHuella.repaint();
    }
    //Fin metodo dibujar huella en el label

    //inicio metodos abstractos
    public void EstadoHuellas() {
        EnviarTexto2("Muestra de Huellas Necesarias para Guardar " + Reclutador.getFeaturesNeeded());
    }

    public void EnviarTexto(String string) {
//        pr.txtArea.append(string + "\n");
    }

    public void EnviarTexto2(String string) {
//        pr.lblEstadohuellas.setText(string);
    }

    public void start() {
        Lector.startCapture();
        EnviarTexto("Utilizando el Lector de Huella Dactilar ");
    }

    public void stop() {
        Lector.stopCapture();
        EnviarTexto("No se está usando el Lector de Huella Dactilar");
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        changes.firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    private void guardarHuella() throws SQLException, InterruptedException {
        if (getPersona().asocFinger(getIidUsuario(), getUsuario(), getIdSede(), getIdempresa(), getIdPersona(), new ByteArrayInputStream(template.serialize()), template.serialize().length)) {
            DesktopNotify.showDesktopMessage("Aviso..!", "Se ha asociado la huella correctamente", DesktopNotify.SUCCESS, 8000L);
            Thread.sleep(100);
//            pr.btnBack.doClick();
        } else {
            DesktopNotify.showDesktopMessage("Aviso..!", "Ocurrio un error al asociar la huella", DesktopNotify.FAIL, 6000L);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIidUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int id_user) {
        this.idUsuario = id_user;
    }

    public persona getPersona() {
        if (persona == null) {
            persona = new persona();
        }
        return persona;
    }

    public void setPersona(persona persona) {
        this.persona = persona;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

}
