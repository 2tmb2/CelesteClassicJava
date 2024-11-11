package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mainApp.Madeline;
import mainApp.MainApp;

public class RotatableSpike extends CollisionObject {

	private char rotation;
	private static final Color SPIKE_WHITE = new Color(255, 241, 232);
	private static final Color SPIKE_GREY = new Color(194, 195, 199);
	private static final Color SPIKE_BROWN = new Color(95, 87, 79);

	private Madeline m;
	
	public RotatableSpike(int x, int y, int width, int height, char rotation, Madeline m)
	{
		super(x, y, width, height, false, false);
		this.m = m;
		this.rotation = rotation;
		if (rotation == 'l')
		{
			setX(getX() + 6*MainApp.PIXEL_DIM);
			setY(getY() + MainApp.PIXEL_DIM);
			
		}
		else if (rotation == 'r')
		{
			setY(getY() + MainApp.PIXEL_DIM);
			
		}
		else if (rotation == 'u')
		{
			setY(getY() + 6*MainApp.PIXEL_DIM);
			setX(getX() + MainApp.PIXEL_DIM);
		}
		else if (rotation == 'd')
		{
			setX(getX() + MainApp.PIXEL_DIM);
			
		}
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		g2.translate(getX(), getY());
		
		if (rotation == 'l')
		{
			g2.translate(-4*MainApp.PIXEL_DIM, 6*MainApp.PIXEL_DIM);
			g2.rotate(-Math.PI/2);
			
		}
		else if (rotation == 'r')
		{
			g2.translate(6*MainApp.PIXEL_DIM, 0);
			g2.rotate(Math.PI/2);
			
		}
		else if (rotation == 'u')
		{
			g2.translate(0, -4*MainApp.PIXEL_DIM);
			
		}
		else if (rotation == 'd')
		{
			g2.translate(6*MainApp.PIXEL_DIM, 6*MainApp.PIXEL_DIM);
			g2.rotate(Math.PI);
		}

		for (int i = 0; i < 2; i++)
		{
			g2.setColor(SPIKE_GREY);
			g2.fillRect(0, 3*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM);

			g2.setColor(SPIKE_WHITE);
			g2.fillRect(MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM);
			g2.fillRect(2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);

			g2.setColor(SPIKE_BROWN);
			g2.fillRect(-MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM);

			g2.translate(4*MainApp.PIXEL_DIM, 0);
		}
	}
	
	
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY) && (rotation == 'u' || rotation == 'd'))
		{
			m.death();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) && (rotation == 'l' || rotation == 'r'))
		{
			m.death();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (super.isCollidingCeiling(madelineX, madelineY) && (rotation == 'u' || rotation == 'd'))
		{
			m.death();
			return true;
		}
		return false;
	}
}
