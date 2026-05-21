package presentacion.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Cursor;

import javax.swing.JWindow;
import javax.swing.JPanel;
import javax.swing.Timer;

public class VentanaProgreso extends JWindow {

    private static final long serialVersionUID = 1L;
    private String mensaje;
    private Timer spinnerTimer;
    private float dashPhase = 0f;

    private static final int ARC = 20;

    public VentanaProgreso(String mensaje) {
        this.mensaje = mensaje;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setSize(300, 130);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));

        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(190, 215, 240), 0, h, new Color(235, 245, 255));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w, h, ARC, ARC);

                g2d.setStroke(new BasicStroke(2f));
                g2d.setColor(new Color(60, 130, 200));
                g2d.drawRoundRect(1, 1, w - 3, h - 3, ARC - 2, ARC - 2);

                g2d.setColor(new Color(60, 130, 200));
                g2d.setFont(new Font("Cambria", Font.BOLD, 18));
                String msg = mensaje;
                int tw = g2d.getFontMetrics().stringWidth(msg);
                g2d.drawString(msg, (w - tw) / 2, 45);

                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g2d.setColor(new Color(100, 190, 255));
                int cx = w / 2, cy = 80, r = 12;
                float circumference = (float) (2 * Math.PI * r);
                g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1f,
                        new float[]{circumference * 0.75f, circumference * 0.25f}, dashPhase));
                g2d.drawOval(cx - r, cy - r, 2 * r, 2 * r);

                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        setContentPane(panel);
    }

    public void mostrar() {
        setVisible(true);
        toFront();
        try {
            setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC, ARC));
        } catch (Exception ignored) {}

        float circumference = (float)(2 * Math.PI * 12);
        spinnerTimer = new Timer(50, ev -> {
            dashPhase -= 3f;
            if (dashPhase < 0) dashPhase += circumference;
            repaint();
        });
        spinnerTimer.start();
    }

    public void cerrar() {
        if (spinnerTimer != null) spinnerTimer.stop();
        dispose();
    }
}