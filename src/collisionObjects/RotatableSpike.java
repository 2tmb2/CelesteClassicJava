package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

public class RotatableSpike extends CollisionObject {

	private char rotation;
	private static final Color SPIKE_WHITE = new Color(255, 241, 232);
	private static final Color SPIKE_GREY = new Color(194, 195, 199);
	private static final Color SPIKE_BROWN = new Color(95, 87, 79);

	private Madeline m;
	
	/**
	 * Creates a rotatable spike
	 * @param x the top left x location of the spike
	 * @param y the top left y location of the spike
	 * @param width the width of the spike's collision box
	 * @param height the height of the spike's collision box
	 * @param rotation Character representing the rotation. 'u' = up, 'd' = down, 'l' = left, 'r' = right
	 * @param m the current Madeline that the spikes will interact with
	 */
	public RotatableSpike(int x, int y, int width, int height, char rotation, Madeline m)
	{
		super(x, y, width, height, false, false);
		this.m = m;
		this.rotation = rotation;
		if (rotation == 'l')
		{
			setX(getX() + 6*Constants.PIXEL_DIM);
			setY(getY() + Constants.PIXEL_DIM);
		}
		else if (rotation == 'r')
		{
			setY(getY() + Constants.PIXEL_DIM);
		}
		else if (rotation == 'u')
		{
			setY(getY() + 6*Constants.PIXEL_DIM);
			setX(getX() + Constants.PIXEL_DIM);
		}
		else if (rotation == 'd')
		{
			setX(getX() + Constants.PIXEL_DIM);
		}
	}
	
	/**
	 * Draws the spike onto g2 with the correct rotation
	 */
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		g2.translate(getX(), getY());
		
		if (rotation == 'l')
		{
			g2.translate(-4*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);
			g2.rotate(-Math.PI/2);
			
		}
		else if (rotation == 'r')
		{
			g2.translate(6*Constants.PIXEL_DIM, 0);
			g2.rotate(Math.PI/2);
			
		}
		else if (rotation == 'u')
		{
			g2.translate(0, -4*Constants.PIXEL_DIM);
			
		}
		else if (rotation == 'd')
		{
			g2.translate(6*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);
			g2.rotate(Math.PI);
		}

		// draws both halves of the spike. Since they are identical, it repeats twice.
		for (int i = 0; i < 2; i++)
		{
			g2.setColor(SPIKE_GREY);
			g2.fillRect(0, 3*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM);

			g2.setColor(SPIKE_WHITE);
			g2.fillRect(Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM);
			g2.fillRect(2*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

			g2.setColor(SPIKE_BROWN);
			g2.fillRect(-Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);

			g2.translate(4*Constants.PIXEL_DIM, 0);
		}
	}
	
	/**
	 * Checks if Madeline is colliding with the top of the spike. If she is, it kills her.
	 */
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
	
	/**
	 * Checks if Madeline is colliding with the side of the spike. If she is, it kills her.
	 */
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
	
	/**
	 * Checks if Madeline is colliding with the bottom of the spike. If she is, it kills her.
	 */
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
