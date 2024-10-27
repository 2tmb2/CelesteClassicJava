package mainApp;

import java.awt.Graphics2D;

/**
 * Class: Spike
 * Creates a set of Spikes, which kill the player upon contact
 */
public abstract class Spike extends CollisionObject{
	
	private Madeline m;
	
	public Spike(int x, int y, int width, int height, Madeline m)
	{
		super(x, y, width, height);
		this.m = m;
	}
	@Override
	public abstract void drawOn(Graphics2D g2);
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing))
		{
			m.death();
			return true;
		}
		return false;
	}
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (super.isCollidingCeiling(madelineX, madelineY))
		{
			m.death();
			return true;
		}
		return false;
	}
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY))
		{
			m.death();
			return true;
		}
		return false;
	}
	@Override
	public void updateAnimation() {}
}
