package collectables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import collisionObjects.CollisionObject;
import mainApp.Madeline;

public class Balloon extends CollisionObject {
	
	private static final Color BALLOON_PINK = new Color(255, 0, 77);
	private static final Color BALLOON_WHITE = new Color(255, 241, 232);
	private static final Color BALLOON_GREY = new Color(194, 195, 199);
	private int currentFrame;
	private int increment;
	private int originalY;
	private Madeline m;
	private boolean isCollected;
	private Timer animationTimer;
	private Timer respawnTimer;
	private double timerTriggerNumber;
	public Balloon(int x, int y, Madeline m)
	{
		super(x,y,42,48);
		originalY = y;
		currentFrame = 1;
		timerTriggerNumber = 0;
		isCollected = false;
		this.m = m;
		animationTimer = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (currentFrame == 1) {
					currentFrame = 2;
				}
				else if (currentFrame == 2)
				{
					currentFrame = 3;
				}
				else
				{
					currentFrame = 1;
				}
				timerTriggerNumber += 1.5;
				if (timerTriggerNumber % 1 == 0)
				{
					if (getY() + increment >= originalY + 24)
					{
						increment = increment * -1;
					}
					else if (getY() + increment < originalY)
					{
						increment = increment * -1;
					}
					setY(getY() + increment);
				}
			}
		});
		increment = 6;
		animationTimer.start();
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (!isCollected)
		{
			g2 = (Graphics2D)g2.create();
			g2.translate(getX(), getY());
			
			g2.setColor(BALLOON_PINK);
			g2.fillRect(6,0,24,6);
			g2.fillRect(0,6,36,30);
			g2.fillRect(6,36,24,6);
			
			g2.setColor(BALLOON_WHITE);
			g2.fillRect(6,12,6,6);
			
			g2.setColor(BALLOON_GREY);
			if (currentFrame == 1)
			{
				drawStemFrame1(g2);
			}
			else if (currentFrame == 2)
			{
				drawStemFrame2(g2);
			}
			else
			{
				drawStemFrame3(g2);
			}
		}
	}
	
	private void drawStemFrame1(Graphics2D g2)
	{
		g2.fillRect(18,42,6,18);
		g2.fillRect(12,60,6,24);
	}
	
	private void drawStemFrame2(Graphics2D g2)
	{
		g2.fillRect(12,42,6,18);
		g2.fillRect(18,60,6,24);
	}
	
	private void drawStemFrame3(Graphics2D g2)
	{
		g2.fillRect(18,42,6,6);
		g2.fillRect(12,48,6,24);
		g2.fillRect(18,72,6,12);
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) && !isCollected)
		{
			collected();
		}
		return false;
	}
	
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY) && !isCollected)
		{
			collected();
		}
		return false;
	}
	
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY) && !isCollected)
		{
			collected();
		}
		return false;
	}
	
	private void collected()
	{
		m.resetDashes();
		isCollected = true;
		respawnTimer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				isCollected = false;
			}
		});
		respawnTimer.setRepeats(false);
		respawnTimer.start();
	}
	
	@Override
	public void stopAllTimers()
	{
		animationTimer.stop();
	}
	
}
