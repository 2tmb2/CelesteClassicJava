package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class Cloud {
	private int x;
	private int y;
	private int speed;
	private int width;
	private Color cloudColor;
	
	/**
	 * Creates a Cloud, which is one element of the background group of clouds, with a randomized y position, speed, and width
	 * @param c represents the color to make the clouds
	 */
	public Cloud(Color c)
	{
		this.x = 0;
		this.y = Madeline.roundPos((int)(Math.random() * 120*MainApp.PIXEL_DIM) + 4*MainApp.PIXEL_DIM);
		this.speed = MainApp.PIXEL_DIM + (int)(Math.random() * 4);
		this.width = 24*MainApp.PIXEL_DIM + (int)(Math.random()*32*MainApp.PIXEL_DIM);
		cloudColor = c;
	}
	
	/**
	 * Draws the cloud onto g2
	 */
	public void drawOn(Graphics2D g2)
	{
		x += speed;
		g2.setColor(cloudColor);
		g2.fillRect(x, y, width, 4*MainApp.PIXEL_DIM + (4*MainApp.PIXEL_DIM-(width/(64*MainApp.PIXEL_DIM))));
		if (x > 128*MainApp.PIXEL_DIM)
		{
			x = -width;
			y = Madeline.roundPos((int)(Math.random() * 120*MainApp.PIXEL_DIM) + 4*MainApp.PIXEL_DIM);
		}
		
	}
	
	/**
	 * Sets the color of the cloud
	 * @param c representing the color to make the cloud
	 */
	public void setColor(Color c)
	{
		cloudColor = c;
	}
}
