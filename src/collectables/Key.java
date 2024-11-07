package collectables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import collisionObjects.CollisionObject;
import mainApp.Madeline;
import mainApp.MainApp;

public class Key extends CollisionObject {
	
	private Chest chest;
	private Madeline m;
	private boolean keyHasBeenCollected;
	private static final int GAME_WIDTH = 768;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private static final int KEY_FRAME_1_X = 1152;
	private static final int KEY_LOCATION_Y = 0;
	private static final int KEY_FRAME_2_X = 1200;
	private static final int KEY_FRAME_3_X = 1248;
	private int lifetime;
	private int drawX;
	private BufferedImage scaledMap;
	
	/**
	 * Creates a key object which, when collected, opens the associated chest
	 * @param x representing the x location of the key
	 * @param y representing the y location of the key
	 * @param chest representing the associated Chest object
	 * @param m representing the current Madeline object
	 */
	public Key(int x, int y, Chest chest, Madeline m) {
		super(x, y, MainApp.PIXEL_DIM*5, MainApp.PIXEL_DIM*8, false, false);
		this.chest = chest;
		this.m = m;
		keyHasBeenCollected = false;
		lifetime = 0;
		drawX = KEY_FRAME_1_X;
		try {
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			g2.drawImage(scaledMap, getX(), getY(), getX() + SPRITE_WIDTH, getY() + SPRITE_HEIGHT, (drawX - GAME_WIDTH), KEY_LOCATION_Y + 1, ((drawX - GAME_WIDTH)) + SPRITE_WIDTH, KEY_LOCATION_Y + SPRITE_HEIGHT, null);
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
