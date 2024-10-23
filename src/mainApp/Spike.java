package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class Spike extends CollisionObject{
	private boolean isHorizontal;
	private static final Color SPIKE_WHITE = new Color(255,241,232);
	private static final Color SPIKE_GREY = new Color(194,195,199);
	private int numberOfSpikes;
	private boolean isDeathZone;
	private Madeline m;
	public Spike(int x, int y, int numOfSpikes, boolean isHorizontal, boolean isDeathZone, Madeline m)
	{
		super(x,y,numOfSpikes*24, 36);
		this.isDeathZone = isDeathZone;
		numberOfSpikes = numOfSpikes;
		this.isHorizontal = isHorizontal;
		this.m = m;
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (!isDeathZone)
		{
			g2 = (Graphics2D) g2.create();
			g2.translate(getX() + 6, getY());
			if (isHorizontal)
			{
				for (int i = 0; i < numberOfSpikes; i++)
				{
					g2.setColor(SPIKE_GREY);
					g2.fillRect(0, 12, 18, 24);
					
					g2.setColor(SPIKE_WHITE);
					g2.fillRect(6, 0, 6, 18);
					g2.fillRect(12, 12, 6, 6);
					
					g2.translate(24, 0);
				}
			}
		}
	}
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
	public String toString()
	{
		return this.getClass().toString().substring(6) + ", " + this.getX() + ", " + this.getY() + ", " + numberOfSpikes + ", " + isHorizontal + ", " + isDeathZone;
	}
	@Override
	public void updateAnimation() {}
	
}
