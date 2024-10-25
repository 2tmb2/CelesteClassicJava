package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class Strawberry extends CollisionObject{
	
	private static final Color STRAWBERRY_RED = new Color(255, 0, 77);
	private static final Color STRAWBERRY_DARK_GREEN = new Color(0,135,81);
	private static final Color STRAWBERRY_LIGHT_GREEN = new Color(0, 228, 54);
	private static final Color STRAWBERRY_DARK_RED = new Color(126,37,83);
	private static final Color STRAWBERRY_YELLOW = new Color(255,163,0);
	private int numOfAnimationFrames;
	private int translateBy;
	private int currentFrame;
	private Madeline m;
	public Strawberry(int x, int y, int width, int height, Madeline m)
	{
		super(x, y, width, height);
		this.m = m;
		currentFrame = 0;
		numOfAnimationFrames = 60;
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		g2.translate(getX(), getY());
		updateTranslateBy();
		g2.translate(0, translateBy);
		g2.setColor(STRAWBERRY_RED);
		g2.fillRect(0, 6, 18, 6);
		g2.fillRect(-18, 6, 12, 6);
		g2.fillRect(-12,12,24,6);
		g2.fillRect(-6, 18, 12, 6);
		g2.fillRect(-18,0,24,6);
		g2.fillRect(12, 0, 6, 6);
		g2.fillRect(-18, -6, 6, 6);
		g2.fillRect(-6, -6, 24, 6);
		g2.fillRect(-12, -12, 24, 6);
		
		g2.setColor(STRAWBERRY_DARK_RED);
		g2.fillRect(-18, -12, 6, 6);
		g2.fillRect(12, -12, 6, 6);
		g2.fillRect(-18, 12, 6, 6);
		g2.fillRect(12, 12, 6, 6);
		g2.fillRect(-12, 18, 6, 6);
		g2.fillRect(6, 18, 6, 6);
		
		g2.setColor(STRAWBERRY_YELLOW);
		g2.fillRect(-6, 6, 6, 6);
		g2.fillRect(6, 0, 6, 6);
		g2.fillRect(-12, -6, 6, 6);
		
		g2.setColor(STRAWBERRY_LIGHT_GREEN);
		g2.fillRect(-6, -18, 6, 6);
		g2.fillRect(0, -24, 6, 6);
		g2.fillRect(12, -24, 6, 6);
		
		g2.setColor(STRAWBERRY_DARK_GREEN);
		g2.fillRect(-18, -24, 6, 6);
		g2.fillRect(-12, -18, 6, 6);
		g2.fillRect(0, -18, 12, 6);
	}
	private void updateTranslateBy()
	{
		if (currentFrame > numOfAnimationFrames/4 && currentFrame < 3*numOfAnimationFrames/4)
		{
			translateBy--;;
		}
		else
		{
			translateBy++;
		}
	}
	@Override
	public void updateAnimation()
	{
		if (currentFrame >= numOfAnimationFrames - 2)
		{
			translateBy--;
			currentFrame = 0;
		}
		else
		{
			currentFrame++;
		}
	}
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY))
		{
			m.collectStrawberry();
			return false;
		}
		return false;
	}
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (super.isCollidingCeiling(madelineX, madelineY))
		{
			m.collectStrawberry();
			return false;
		}
		return false;
	}
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int isFacing)
	{
		if (super.isCollidingWall(madelineX, madelineY, isFacing))
		{
			m.collectStrawberry();
			return true;
		}
		return false;
	}
}
