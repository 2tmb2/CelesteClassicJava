package collectables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import collisionObjects.CollisionObject;
import mainApp.Madeline;
import mainApp.MainApp;

public class Gem extends CollisionObject {
	
	private Madeline m;
	private boolean hasBeenCollected;
	private BufferedImage scaledMap;
	private static final int GAME_WIDTH = 768;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private static final int GEM_LOCATION_X = 1056;
	private static final int GEM_LOCATION_Y = 288;
	
	public Gem(int x, int y, Madeline m)
	{
		super(x,y,8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, false, false);
		hasBeenCollected = false;
		try {
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.m = m;
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (!hasBeenCollected)
		{
			g2 = (Graphics2D)g2.create();
			g2.drawImage(scaledMap, getX(), getY(), getX() + SPRITE_WIDTH, getY() + SPRITE_HEIGHT, (GEM_LOCATION_X - GAME_WIDTH), GEM_LOCATION_Y + 1, ((GEM_LOCATION_X - GAME_WIDTH)) + SPRITE_WIDTH, GEM_LOCATION_Y + SPRITE_HEIGHT, null);
		}
	}
	
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