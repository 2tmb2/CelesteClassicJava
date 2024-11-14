package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A ColoredRectangle is a Rectangle object that can also stores a color
 */
public class ColoredRectangle extends Rectangle {

	private static final long serialVersionUID = 1L;
	private static final Color BLUE_COLOR = new Color(63, 73, 204);
	private static final Color GREEN_COLOR = new Color(14,209, 69);
	private Color color;
	
	public ColoredRectangle(Point p, Dimension d, Color color) {
		super(p, d);
		this.color = color;
	}
	
	public ColoredRectangle(ColoredRectangle r) {
		super(r.getLocation(), r.getSize());
		this.color = r.getColor();
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public String getIsIce()
	{
		if (color.equals(GREEN_COLOR))
			return "";
		else if (color.equals(BLUE_COLOR))
			return "I";
		else
			return "";
	}
}
