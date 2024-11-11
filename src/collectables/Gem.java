package collectables;

import java.awt.Graphics2D;

import collisionObjects.CollisionObject;
import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

public class Gem extends CollisionObject {
	
	private Madeline m;
	private boolean hasBeenCollected;
	private static final int GEM_LOCATION_X = 1056;
	private static final int GEM_LOCATION_Y = 288;
	
	/**
	 * Creates a Gem object that, when collected, gives Madeline her 2nd dash
	 * @param x representing the x value of where to spawn the Gem
	 * @param y representing the Y value of where to spawn the gem
	 * @param m representing the current Madeline object
	 */
	public Gem(int x, int y, Madeline m)
	{
		super(x,y,8*Constants.PIXEL_DIM, 8*Constants.PIXEL_DIM, false, false);
		hasBeenCollected = false;
		this.m = m;
	}
	
	/**
	 * if it hasn't already been collected, draws the gem onto g2
	 */
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (!hasBeenCollected)
		{
			g2 = (Graphics2D)g2.create();
			g2.drawImage(MainApp.SCALED_MAP, getX(), getY(), getX() + Constants.SPRITE_WIDTH, getY() + Constants.SPRITE_HEIGHT, (GEM_LOCATION_X - Constants.GAME_WIDTH), GEM_LOCATION_Y + 1, ((GEM_LOCATION_X - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH, GEM_LOCATION_Y + Constants.SPRITE_HEIGHT, null);
		}
	}
	
	/**
	 * Ensures collision with the wall if the gem hasn't already been collected
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) && !hasBeenCollected)
		{
			m.setTotalDashes(2);
			m.resetDashes();
			hasBeenCollected = true;
		}
		return false;
	}
	
	/**
	 * Ensures collision with the floor if the gem hasn't already been collected
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY) && !hasBeenCollected)
		{
			m.setTotalDashes(2);
			m.resetDashes();
			hasBeenCollected = true;
		}
		return false;
	}
	
	/**
	 * Ensures collision with the ceiling if the gem hasn't already been collected
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (super.isCollidingCeiling(madelineX, madelineY) && !hasBeenCollected)
		{
			m.setTotalDashes(2);
			m.resetDashes();
			hasBeenCollected = true;
		}
		return false;
	}
}