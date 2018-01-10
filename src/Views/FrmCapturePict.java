package Views;

import Controllers.GetPrincipal;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;
import ds.desktop.notify.DesktopNotify;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Proof of concept of how to handle webcam video stream from Java
 *
 * @author Bartosz Firyn (SarXos)
 */
public class FrmCapturePict extends JFrame implements ActionListener, Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {

    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private WebcamPicker picker = null;
    private final JButton btn = new JButton("Capturar Foto");
    private final Modulo1 pr = GetPrincipal.getPrincipal();
    ImageIcon ii = null;
    ImageIcon iin = null;
    String ruta = System.getProperty("java.io.tmpdir") + "\\default.png";

    @SuppressWarnings("unchecked")
    @Override
    public void run() {

        Webcam.addDiscoveryListener(this);
        setIconImage(new ImageIcon(getClass().getResource("/icons/favicon_2.png")).getImage());
        setTitle("Capturar Imagen");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        addWindowListener(this);
        picker = new WebcamPicker();
        picker.addItemListener(this);
        webcam = picker.getSelectedWebcam();

        if (webcam == null) {
            DesktopNotify.showDesktopMessage("Informacion..!", "No hay camaras conectadas en el equipo..!", DesktopNotify.ERROR, 6000L);
            this.dispose();
        } else {
            webcam.setViewSize(WebcamResolution.QVGA.getSize());
            webcam.addWebcamListener(FrmCapturePict.this);
            panel = new WebcamPanel(webcam, false);
            panel.setFPSDisplayed(true);
            add(picker, BorderLayout.NORTH);
            add(panel, BorderLayout.CENTER);
            add(btn, BorderLayout.SOUTH);
            btn.addActionListener(this);
            pack();
            setVisible(true);
            Thread t = new Thread() {
                @Override
                public void run() {
                    panel.start();
                }
            };
            t.setName("example-starter");
            t.setDaemon(true);
            t.setUncaughtExceptionHandler(this);
            t.start();
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FrmCapturePict());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void webcamOpen(WebcamEvent we) {
        System.out.println("webcam open");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void webcamClosed(WebcamEvent we) {
        System.out.println("webcam closed");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void webcamDisposed(WebcamEvent we) {
        System.out.println("webcam disposed");
        panel.stop();
        remove(panel);
        webcam.removeWebcamListener(this);
        webcam.close();
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void webcamImageObtained(WebcamEvent we) {
        // do nothing
    }

    @SuppressWarnings("unchecked")
    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        webcam.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("webcam viewer resumed");
        panel.resume();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("webcam viewer paused");
        panel.pause();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() != webcam) {
            if (webcam != null) {
                panel.stop();
                remove(panel);
                webcam.removeWebcamListener(this);
                webcam.close();

                webcam = (Webcam) e.getItem();
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                webcam.addWebcamListener(this);

                System.out.println("selected " + webcam.getName());

                panel = new WebcamPanel(webcam, false);
                panel.setFPSDisplayed(true);

                add(panel, BorderLayout.CENTER);
                pack();

                Thread t = new Thread() {

                    @Override
                    public void run() {
                        panel.start();
                    }
                };
                t.setName("example-stoper");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler(this);
                t.start();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void webcamFound(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.addItem(event.getWebcam());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void webcamGone(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.removeItem(event.getWebcam());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            BufferedImage image = webcam.getImage();
            try {
                String formato = "PNG";
                File fichero = new File(ruta);
                ImageIO.write(image, formato, fichero);
                DesktopNotify.showDesktopMessage("Informacion..!", "Foto capturada correctamente..!", DesktopNotify.SUCCESS, 6000L);
                FileInputStream fis;
                fis = new FileInputStream(fichero);
                ajustaImagen(fis, 200, 185);
                ii = new ImageIcon(ruta);
                Image conver = ii.getImage();
                Image tam = conver.getScaledInstance(pr.cLabel1.getWidth(), pr.cLabel1.getHeight(), Image.SCALE_SMOOTH);
                iin = new ImageIcon(tam);
                pr.cLabel1.setIcon(iin);
                this.dispose();
            } catch (IOException | HeadlessException ex) {
                System.out.println("error " + ex);
            } catch (Exception ex) {
                System.out.println("error " + ex);
            }
        }
    }

    private byte[] ajustaImagen(InputStream imagen, int IMG_WIDTH, int IMG_HEIGHT) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage originalImage = ImageIO.read(imagen);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            File fichero = new File(ruta);
            ImageIO.write(resizedImage, "PNG", fichero);
        } catch (Throwable ex) {
            throw new Exception("Error proceso Tamaño Imagen " + ex.toString(), ex);
        }
//                return baos.toByteArray();
        System.out.println("aqui");
        return null;
    }

}
