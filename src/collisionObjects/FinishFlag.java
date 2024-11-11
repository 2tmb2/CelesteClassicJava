package collisionObjects;

import java.awt.Graphics2D;

import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

public class FinishFlag extends CollisionObject {	
	
	private Madeline m;
	private int flagLocationX;
	private static final int FLAG_LOCATION_X_FRAME_1 = 6*8*MainApp.PIXEL_DIM + Constants.GAME_WIDTH;
	private static final int FLAG_LOCATION_X_FRAME_2 = 7*8*MainApp.PIXEL_DIM + Constants.GAME_WIDTH;
	private static final int FLAG_LOCATION_X_FRAME_3 = 8*8*MainApp.PIXEL_DIM + Constants.GAME_WIDTH;
	private static final int FLAG_LOCATION_Y = 7*8*MainApp.PIXEL_DIM;
	private int animationFrame;
	
	public FinishFlag(int x, int y, Madeline m)
	{
		super(x, y, 48, 48, false, false);
		this.m = m;
		animationFrame = 0;
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) || super.isCollidingFloor(madelineX, madelineY) || super.isCollidingCeiling(madelineX, madelineY))
		{
			m.displayFinalText();
		}
		return false;
	}
	
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		return false;
	}
	
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		return false;
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		animationFrame++;
		if (animationFrame == 0)
		{
			flagLocationX = FLAG_LOCATION_X_FRAME_1;
		}
		else if (animationFrame == 12)
		{
			flagLocationX = FLAG_LOCATION_X_FRAME_2;
		}
		else if (animationFrame == 24)
		{
			flagLocationX = FLAG_LOCATION_X_FRAME_3;
		}
		else if (animationFrame == 36)
		{
			animationFrame = 0;
			flagLocationX = FLAG_LOCATION_X_FRAME_1;
		}
		g2 = (Graphics2D)g2.create();
		g2.drawImage(MainApp.SCALED_MAP, getX(), getY(), getX() + Constants.SPRITE_WIDTH, getY() + Constants.SPRITE_HEIGHT, (flagLocationX - Constants.GAME_WIDTH), FLAG_LOCATION_Y + 1, ((flagLocationX - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH, FLAG_LOCATION_Y + Constants.SPRITE_HEIGHT, null);
	}
}
