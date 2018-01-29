/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerUtils;

import Controllers.GetPrincipal;
import Pojos.Asistencia;
import Pojos.Huellas;
import Pojos.PagoService;
import Pojos.persona;
import Views.Modulo1;
import Views.Modales.AlertaAtraso;
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
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import ds.desktop.notify.DesktopNotify;
import java.awt.Image;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import static java.lang.Math.abs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mauricio Herrera
 */
public final class ReadFinger {

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private final DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    private final DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    private final DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    public static String TEMPLATE_PROPERTY = "template";
    //Inicio variables para la extraccion de caracteristicas huella
    public DPFPFeatureSet featuresverificacion;
//    private final Modulo1 pr;
    private Asistencia asistencias;
    private PagoService pagoservice;
    private Huellas huella;
    private persona objpersona;
//    private PrincipalController prc;
    IniciarAlerta iniAlerta;
    AnimarLbl animar;
    SimpleDateFormat hh = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
    AlertaAtraso alertas;
    private final int TIEMPO = 9500;
    String TipoService = "";
    String[] horadesde;
    String[] horaHasta;
    String[] horaActual;
    String nombre;
//    AudioClip sonido;

    public ReadFinger() {
//        prc = GetPrincipalController.getPrincipalController();
//        pr = GetPrincipal.getPrincipal();
        Iniciar();
    }

    private class IniciarAlerta extends Thread {

        @Override
        public void run() {
            try {
                alertas.setVisible(true);
                alertas.hacerVisible();
                Thread.sleep(TIEMPO);
                alertas.desvanecer();
                alertas.dispose();
                System.out.println("\nTipoService = " + TipoService + "hora actual: " + horaActual[0] + "\n");
                if (TipoService.equalsIgnoreCase("Hora Feliz")) {
                    if (Integer.parseInt(horaActual[0]) > Integer.parseInt(horadesde[0])
                            && Integer.parseInt(horaActual[0]) <= Integer.parseInt(horaHasta[0])) {
//                        System.out.println("estas en el horario");
                    } else {
                        alertas = new AlertaAtraso();
                        alertas.setLocationRelativeTo(null);
                        alertas.lblAlertaTitle.setText("¡Aviso, Servicio Hora Feliz...!");
                        alertas.lblDias.setText("Este No Es Un Horario Valido");
                        alertas.lblAlertaNombre.setText(nombre);
                        iniAlerta = new IniciarAlerta();
                        animar = new AnimarLbl();
                        iniAlerta.start();
                        animar.start();
                    }
                }
                TipoService = "";
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    private class AnimarLbl extends Thread {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 11; i++) {
                    if (i % 2 == 0) {
                        alertas.lblAlertaTitle.setVisible(true);
                        alertas.lblDias.setVisible(true);
                        alertas.lblAlertaNombre.setVisible(true);
                    } else {
                        alertas.lblAlertaTitle.setVisible(false);
                        alertas.lblDias.setVisible(false);
                        alertas.lblAlertaNombre.setVisible(false);
                    }
                    Thread.sleep(1100);
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    //Inicio metodo Start
    protected void Iniciar() {
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            ProcesarCaptura(e.getSample());
                            identificarHuella();
                            Reclutador.clear();
                        } catch (IOException | InterruptedException ex) {
                            System.out.println("error " + ex);
                        }
                    }
                });
            }
        });

        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("El Sensor de Huella Digital esta Activado o Conectado");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("El Sensor de Huella Digital esta Desactivado o no Conectado");
                        DesktopNotify.showDesktopMessage("Aviso..!", "El Sensor de Huella Digital esta Desactivado o no Conectado", DesktopNotify.WARNING, 4000L);
                    }
                });
            }
        });

        Lector.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("El dedo ha sido colocado sobre el Lector de Huella");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El dedo ha sido quitado del Lector de Huella");
                    }
                });
            }
        });

        Lector.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("Error: " + e.getError());
                    }
                });
            }
        });
    }
//    //Fin metodo Start

    //Inicio metodo procesar captura
    public void ProcesarCaptura(DPFPSample sample) throws IOException {
        featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        if (featuresverificacion != null) {
            try {
                System.out.println("Las Caracteristicas de la Huella han sido creada");
                Reclutador.addFeatures(featuresverificacion);// Agregar las caracteristicas de la huella a la plantilla a crear
            } catch (DPFPImageQualityException ex) {
                System.err.println("Error: " + ex.getMessage());
            } finally {
                //EstadoHuellas();
                // Comprueba si la plantilla se ha creado.
                switch (Reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:	// informe de éxito y detiene  la captura de huellas                       
                        System.out.println("plantilla ok");
                        break;
                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                        Reclutador.clear();
                        stop();
                        setTemplate(null);
                        start();
                        break;
                }
            }
        }
    }
//    //Fin metodo procesar captura

    //    //Inicio metodo extraer caracteristicas huella
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

    public void EnviarTexto(String string) {
        DesktopNotify.showDesktopMessage("Aviso..!", string, DesktopNotify.SUCCESS, 8000L);
    }

    public void start() {
        Lector.startCapture();
        DesktopNotify.showDesktopMessage("Aviso..!", "Iniciando Verificación de Asistencias", DesktopNotify.SUCCESS, 4000L);
    }

    public void stop() {
        Lector.stopCapture();
        DesktopNotify.showDesktopMessage("Aviso..!", "Se ha detenido la verificación de asistencias", DesktopNotify.WARNING, 4000L);
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        changes.firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    //metodo identificar
    public void identificarHuella() throws IOException, InterruptedException {
//        SimpleDateFormat Año = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendario = Calendar.getInstance();
//        int hora, minutos, segundos;
//        hora = calendario.get(Calendar.HOUR_OF_DAY);
//        minutos = calendario.get(Calendar.MINUTE);
//        segundos = calendario.get(Calendar.SECOND);
//        String Horacompleta = hora + ":" + minutos + ":" + segundos;
//        AudioClip sonido;
//        String sql2 = "";     
//        String opc = HuellaDedos.getSelection().getActionCommand();        
        try {
            Huellas h = getHuella();
            horaActual = hh.format(new Date().getTime()).split(":");
            ResultSet rs = h.verificFinger();
            while (rs.next()) {
                int idUsuario = rs.getInt(2);
                String usuario = rs.getString(3);
                int idSede = rs.getInt(4);
                int idempresa = rs.getInt(5);
                int idPersona = rs.getInt(6);
                byte templateBuffer[] = rs.getBytes(7);
                TipoService = rs.getString(9);

                if (TipoService.equalsIgnoreCase("Hora Feliz")) {
                    horadesde = rs.getTime(10).toString().split(":");
                    horaHasta = rs.getTime(11).toString().split(":");
                }
                nombre = getObjpersona().getNombreCompletoByIdPersona(idPersona);
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                setTemplate(referenceTemplate);
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
                if (result.isVerified()) {

//                    System.out.println("horadesde " + horadesde[0] + " horaHasta " + horaHasta[0] + " TipoService " + TipoService);

                    int dias = getPagoservice().ValidacionPagosVencidos(idUsuario);
                    if (dias == 0) {
                        if (!TipoService.equalsIgnoreCase("Dia")) {
                            alertas = new AlertaAtraso();
                            alertas.setLocationRelativeTo(null);
                            alertas.lblAlertaTitle.setText("¡Aviso, Hoy se vence tu servicio..!");
                            alertas.lblDias.setText("No hay dias de Atraso");
                            alertas.lblAlertaNombre.setText(nombre);
//                        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sounds/Alert.wav"));
//                        sonido.play();
                            iniAlerta = new IniciarAlerta();
                            animar = new AnimarLbl();
                            iniAlerta.start();
                            animar.start();
                        }
                    } else if (dias == 2) {
                        alertas = new AlertaAtraso();
                        alertas.setLocationRelativeTo(null);
                        alertas.lblAlertaTitle.setText("¡Aviso, En 2 dias se vence tu servicio..!");
                        alertas.lblDias.setText("No hay dias de Atraso");
                        alertas.lblAlertaNombre.setText(nombre);
//                        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sounds/Alert.wav"));
//                        sonido.play();
                        iniAlerta = new IniciarAlerta();
                        animar = new AnimarLbl();
                        iniAlerta.start();
                        animar.start();
                    } else if (dias < 0) {
                        alertas = new AlertaAtraso();
                        alertas.setLocationRelativeTo(null);
                        alertas.lblAlertaTitle.setText("¡Alerta, Servicio Vencido...!");
                        alertas.lblAlertaNombre.setText(nombre);
                        alertas.lblDias.setText("Dias de Atraso " + abs(dias));
//                        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sounds/Alert.wav"));
//                        sonido.play();
                        iniAlerta = new IniciarAlerta();
                        animar = new AnimarLbl();
                        iniAlerta.start();
                        animar.start();
                    }

                    Asistencia a = getAsistencias();
                    if (!a.ExistAsistencia(new Date(), idUsuario, idPersona)) {
                        a.getObjAsistenciaID().setIdUsuario(idUsuario);
                        a.getObjAsistenciaID().setUsuario(usuario);
                        a.getObjAsistenciaID().setIdempresa(idempresa);
                        a.getObjAsistenciaID().setIdSede(idSede);
                        a.getObjAsistenciaID().setIdPersona(idPersona);
                        a.setFechaMarcacion(new Date());
                        a.setHoraMarcacion(new Date());

                        if (a.create() > 0) {
//                            prc.cargarTblAsistencias(null, null);//pendiente arreglar esta consulta
                            DesktopNotify.showDesktopMessage("Aviso..!", "Usuario verificado: " + nombre
                                    + "", DesktopNotify.SUCCESS, 8000L);
                        } else {
                            DesktopNotify.showDesktopMessage("Aviso..!", "Usuario verificado: "
                                    + nombre + " No se pudo ingresar la asistencia", DesktopNotify.WARNING, 8000L);
                        }
                        h.getConecion().con.close();
                        setAsistencias(null);
                        a = null;
                    } else {
//                        prc.cargarTblAsistencias(null, null);//pendiente arreglar esta consulta
                        DesktopNotify.showDesktopMessage("Aviso..!", "Ya has asistido hoy, no es necesario volver a registrar la asistencia..!", DesktopNotify.WARNING, 8000L);
                    }

                    setPagoservice(null);
                    return;
                }
            }
            DesktopNotify.showDesktopMessage("Aviso..!", "No hay coincidencias", DesktopNotify.FAIL, 8000L);
        } catch (SQLException e) {
            System.err.println("Error al identificar huella dactilar." + e.getMessage());
        }

    }
    ////     //fin identificar

    public Asistencia getAsistencias() {
        if (asistencias == null) {
            asistencias = new Asistencia();
        }
        return asistencias;
    }

    public void setAsistencias(Asistencia asistencias) {
        this.asistencias = asistencias;
    }

    public Huellas getHuella() {
        if (huella == null) {
            huella = new Huellas();
        }
        return huella;
    }

    public void setHuella(Huellas huella) {
        this.huella = huella;
    }

    public persona getObjpersona() {
        if (objpersona == null) {
            objpersona = new persona();
        }
        return objpersona;
    }

    public void setObjpersona(persona objpersona) {
        this.objpersona = objpersona;
    }

    public PagoService getPagoservice() {
        if (pagoservice == null) {
            pagoservice = new PagoService();
        }
        return pagoservice;
    }

    public void setPagoservice(PagoService pagoservice) {
        this.pagoservice = pagoservice;
    }
}
