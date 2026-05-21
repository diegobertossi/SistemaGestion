package presentacion.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Cursor;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SplashWindow extends JWindow {

    private static final long serialVersionUID = 1L;
    private float alpha = 0f;
    private Timer fadeTimer;
    private Timer spinnerTimer;
    private float dashPhase = 0f;
    private java.awt.Image fondoImg;
    private BufferedImage logoImg;

    public SplashWindow() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setSize(340, 220);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setBackground(new Color(6, 70, 101));

        try {
            setOpacity(0f);
        } catch (Exception ignored) {}

        try {
            fondoImg = java.awt.Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/Fondo.png"));
            logoImg = ImageIO.read(getClass().getResourceAsStream("/Iconosoft.png"));
        } catch (Exception ignored) {}
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        if (fondoImg != null) {
            g2d.drawImage(fondoImg, 0, 0, w, h, this);
        } else {
            g2d.setColor(new Color(6, 70, 101));
            g2d.fillRect(0, 0, w, h);
        }

        if (logoImg != null) {
            int imgX = (w - 64) / 2;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(logoImg, imgX, 30, 64, 64, this);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Cambria", Font.BOLD, 18));
        String text = "INICIANDO SISTEMA REPARSOFT";
        int tw = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, (w - tw) / 2, 130);

        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.setColor(new Color(100, 190, 255));
        int cx = w / 2, cy = 168, r = 14;
        float circumference = (float) (2 * Math.PI * r);
        g2d.setStroke(new BasicStroke(7f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1f,
                new float[]{circumference * 0.75f, circumference * 0.25f}, dashPhase));
        g2d.drawOval(cx - r, cy - r, 2 * r, 2 * r);

        g2d.setColor(new Color(255, 255, 255, 60));
        g2d.fillRoundRect(w / 2 - 80, h - 5, 160, 3, 2, 2);

        g2d.dispose();
    }
    public void mostrar() {
        setVisible(true);
        toFront();

        fadeTimer = new Timer(30, e -> {
            alpha = Math.min(1f, alpha + 0.1f);
            try {
                setOpacity(alpha);
            } catch (Exception ignored) {}
            if (alpha >= 1f) {
                fadeTimer.stop();
                float circumference = (float)(2 * Math.PI * 14);
                spinnerTimer = new Timer(50, ev -> {
                    dashPhase -= 3f;
                    if (dashPhase < 0) dashPhase += circumference;
                    repaint();
                });
                spinnerTimer.start();
            }
        });
        fadeTimer.start();
    }

    public void disposeConTransicion() {
        if (spinnerTimer != null) spinnerTimer.stop();

        new Thread(() -> {
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            while (alpha > 0) {
                alpha = Math.max(0, alpha - 0.05f);
                try { setOpacity(alpha); } catch (Exception ignored) {}
                try { Thread.sleep(20); } catch (InterruptedException ignored) {}
            }
            SwingUtilities.invokeLater(() -> dispose());
        }).start();
    }
}