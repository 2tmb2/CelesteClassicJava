package collisionObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mainApp.Madeline;
import mainApp.MainApp;

public class CloudPlatform extends CollisionObject {

	private int translatedPosition;
	private int travelingDirection;
	private boolean isColliding;
	private Madeline m;
	private static final int GAME_WIDTH = 768;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private static final int CLOUD_LOCATION_X = 1296;
	private static final int CLOUD_LOCATION_Y = 0;
	
	/**
	 * Creates a new CloudPlatform
	 * @param x representing the starting x position of the platform
	 * @param y representing the starting y position of the platform
	 * @param m representing the current level's Madeline 
	 * @param travelingDirection 1 if the platform is traveling to the right, -1 if it is traveling to the left
	 */
	public CloudPlatform(int x, int y, Madeline m, int travelingDirection)
	{
		super(x, y, 96, 4*MainApp.PIXEL_DIM, false, false);
		translatedPosition = x;
		this.travelingDirection = travelingDirection;
		this.m = m;
		isColliding = false;
	}
	
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		translatedPosition += travelingDirection*MainApp.PIXEL_DIM/3;
		if (isColliding)
			m.moveWithCloud(travelingDirection*MainApp.PIXEL_DIM/3);
		if (translatedPosition >= GAME_WIDTH && travelingDirection == 1)
		{
			translatedPosition = -getWidth();
		}
		else if (translatedPosition + getWidth() <= 0)
		{
			translatedPosition = GAME_WIDTH + getWidth();
		}
		super.setX(translatedPosition);
		g2.drawImage(MainApp.SCALED_MAP, Madeline.roundPos(getX()), getY(), Madeline.roundPos(getX() + 2*SPRITE_WIDTH), getY() + SPRITE_HEIGHT, (CLOUD_LOCATION_X - GAME_WIDTH), CLOUD_LOCATION_Y + 1, ((CLOUD_LOCATION_X - GAME_WIDTH)) + 2*SPRITE_WIDTH, CLOUD_LOCATION_Y + SPRITE_HEIGHT, null);
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		return false;
	}
	
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		return false;
	}
	
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (m.getYVelocity() >= 0)
		{
			if (super.isCollidingFloor(madelineX, madelineY))
			{
				isColliding = true;
				return true;
			}
		}
		isColliding = false;
		return false;
	}
	
	
}
