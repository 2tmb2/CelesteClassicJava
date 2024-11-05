package collisionObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mainApp.Madeline;
import mainApp.MainApp;

public class FinishFlag extends CollisionObject {	
	
	private BufferedImage scaledMap;
	private Madeline m;
	private static final int GAME_WIDTH = 768;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private int flagLocationX;
	private static final int FLAG_LOCATION_X_FRAME_1 = 6*8*MainApp.PIXEL_DIM + GAME_WIDTH;
	private static final int FLAG_LOCATION_X_FRAME_2 = 7*8*MainApp.PIXEL_DIM + GAME_WIDTH;
	private static final int FLAG_LOCATION_X_FRAME_3 = 8*8*MainApp.PIXEL_DIM + GAME_WIDTH;
	private static final int FLAG_LOCATION_Y = 7*8*MainApp.PIXEL_DIM;
	private int animationFrame;
	
	public FinishFlag(int x, int y, Madeline m)
	{
		super(x, y, 48, 48, false, false);
		this.m = m;
		try {
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		g2.drawImage(scaledMap, getX(), getY(), getX() + SPRITE_WIDTH, getY() + SPRITE_HEIGHT, (flagLocationX - GAME_WIDTH), FLAG_LOCATION_Y + 1, ((flagLocationX - GAME_WIDTH)) + SPRITE_WIDTH, FLAG_LOCATION_Y + SPRITE_HEIGHT, null);
	}
}
