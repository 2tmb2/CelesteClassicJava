package mainApp;

import java.awt.Graphics2D;

public class LevelFinishZone extends CollisionObject{
	
	private Madeline m;
	
	public LevelFinishZone(int x, int y, int width, int height, Madeline m)
	{
		super(x, y, width, height);
		this.m = m;
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
	}
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		return false;
	}
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		return false;
	}
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int isFacing)
	{
		if (super.isCollidingWall(madelineX, madelineY, isFacing))
		{
			m.nextLevel();
		}
		return false;
	}
	@Override
	public void updateAnimation() {}
}
