package collectables;

import java.awt.Graphics2D;

import collisionObjects.CollisionObject;
import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

public class Key extends CollisionObject {
	
	private Chest chest;
	private Madeline m;
	private boolean keyHasBeenCollected;
	private static final int KEY_FRAME_1_X = 1152;
	private static final int KEY_LOCATION_Y = 0;
	private static final int KEY_FRAME_2_X = 1200;
	private static final int KEY_FRAME_3_X = 1248;
	private int lifetime;
	private int drawX;
	
	/**
	 * Creates a key object which, when collected, opens the associated chest
	 * @param x representing the x location of the key
	 * @param y representing the y location of the key
	 * @param chest representing the associated Chest object
	 * @param m representing the current Madeline object
	 */
	public Key(int x, int y, Chest chest, Madeline m) {
		super(x, y, Constants.PIXEL_DIM*5, Constants.PIXEL_DIM*8, false, false);
		this.chest = chest;
		this.m = m;
		keyHasBeenCollected = false;
		lifetime = 0;
		drawX = KEY_FRAME_1_X;
	}
	
	/**
	 * Draws the Key onto g2 from it's sprite based on it's current animation frame
	 */
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (!keyHasBeenCollected)
		{
			lifetime++;
			switch (lifetime)
			{
				case 20:
					drawX = KEY_FRAME_1_X;
					break;
				case 30:
					drawX = KEY_FRAME_2_X;
					break;
				case 50:
					drawX = KEY_FRAME_3_X;
					break;
				case 60:
					drawX = KEY_FRAME_2_X;
					lifetime = 0;
					break;
			}
			g2 = (Graphics2D)g2.create();
			g2.drawImage(MainApp.SCALED_MAP, getX(), getY(), getX() + Constants.SPRITE_WIDTH, getY() + Constants.SPRITE_HEIGHT, (drawX - Constants.GAME_WIDTH), KEY_LOCATION_Y + 1, ((drawX - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH, KEY_LOCATION_Y + Constants.SPRITE_HEIGHT, null);
		}
	}
	
	/**
	 * Checks for collision between Madeline and the side of the key
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) && !keyHasBeenCollected)
		{
			keyHasBeenCollected = true;
			chest.collect(m);
		}
		return false;
	}
	
	/**
	 * Checks for collision between Madeline and the top of the key
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY) && !keyHasBeenCollected)
		{
			keyHasBeenCollected = true;
			chest.collect(m);
		}
		return false;
	}
	
	/**
	 * Checks for collision between Madeline and the bottom of the key
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (super.isCollidingCeiling(madelineX, madelineY) && !keyHasBeenCollected)
		{
			keyHasBeenCollected = true;
			chest.collect(m);
		}
		return false;
	}
}
