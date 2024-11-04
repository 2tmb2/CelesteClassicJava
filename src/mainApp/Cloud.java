package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class Cloud {
	private int x;
	private int y;
	private int speed;
	private int width;
	private Color cloudColor;
	public Cloud(int x, int y, int speed, int width, Color c)
	{
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		cloudColor = c;
	}
	public void drawOn(Graphics2D g2)
	{
		x += speed;
		g2.setColor(cloudColor);
		g2.fillRect(x, y, width, 4*MainApp.PIXEL_DIM + (4*MainApp.PIXEL_DIM-(width/(64*MainApp.PIXEL_DIM))));
		if (x > 128*MainApp.PIXEL_DIM)
		{
			x = -width;
			y = (int)(Math.random() * 120*MainApp.PIXEL_DIM) + 4*MainApp.PIXEL_DIM;
		}
		
	}
	public void setColor(Color c)
	{
		cloudColor = c;
	}
}
