package collectables;

import java.awt.Color;
import java.awt.Graphics2D;

import collisionObjects.CollisionObject;
import mainApp.Madeline;
import mainApp.MainApp;

public class Key extends CollisionObject {
	private static final Color BRIGHT_YELLOW = new Color(255, 236, 39);
	private Chest chest;
	private Madeline m;
	private boolean keyHasBeenCollected;
	public Key(int x, int y, Chest chest, Madeline m) {
		super(x, y, MainApp.PIXEL_DIM*5, MainApp.PIXEL_DIM*8, false, false);
		this.chest = chest;
		this.m = m;
		keyHasBeenCollected = false;
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (!keyHasBeenCollected)
		{
			g2 = (Graphics2D)g2.create();
			g2.translate(getX(), getY());
			g2.setColor(BRIGHT_YELLOW);
			g2.fillRect(0,0,getWidth(), getHeight());
		}
	}
	
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
