package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	private String placeholder = "";

	public PlaceholderTextField() {
	}

	public PlaceholderTextField(String placeholder) {
		this.placeholder = placeholder != null ? placeholder : "";
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder != null ? placeholder : "";
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getText().isEmpty() && !placeholder.isEmpty() && !isFocusOwner()) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.GRAY);
			g2.setFont(getFont().deriveFont(Font.ITALIC));
			int x = getInsets().left + 2;
			int y = (getHeight() - g2.getFontMetrics().getHeight()) / 2 + g2.getFontMetrics().getAscent();
			g2.drawString(placeholder, x, y);
			g2.dispose();
		}
	}
}
