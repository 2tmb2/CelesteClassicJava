package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class ColoredRectangle extends Rectangle {
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
}
