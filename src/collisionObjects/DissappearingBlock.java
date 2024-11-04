package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class DissappearingBlock extends CollisionObject{

	private static final Color BLOCK_BROWN = new Color(171, 82, 54);
	private static final Color BLOCK_YELLOW = new Color(255, 163, 0);
	private static final Color BLOCK_DARK_BLUE = new Color(29, 43, 83);
	private boolean isVisible;
	private boolean isDissappearing;
	private int animationFrame;
	
	public DissappearingBlock(int x, int y)
	{
		super(x,y,48,48, true, true);
		isVisible = true;
		animationFrame = 0;
		
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (isVisible)
		{
			g2 = (Graphics2D)(g2.create());
			
			g2.translate(getX(),getY());
			
			if (isDissappearing)
			{
				animationFrame++;
			}
			
			if (animationFrame < 10)
			{
				drawFullBlock(g2);
			}
			else if (animationFrame < 21)
			{
				drawDissappearingFrame1(g2);
			}
			else if (animationFrame <= 32)
			{
				drawDissappearingFrame2(g2);
			}
		}
	}
	
	private void drawFullBlock(Graphics2D g2)
	{
		g2.setColor(BLOCK_YELLOW);
		g2.fillRect(0,0,48,48);
		
		g2.setColor(BLOCK_DARK_BLUE);
		g2.fillRect(6,6,36,36);
		
		g2.setColor(BLOCK_BROWN);
		g2.fillRect(0,0,6,6);
		g2.fillRect(42,0,6,6);
		g2.fillRect(0,42,6,6);
		g2.fillRect(42,42,6,6);
	}
	
	private void drawDissappearingFrame1(Graphics2D g2)
	{
		
		g2.setColor(BLOCK_DARK_BLUE);
		g2.fillRect(6,6,36,12);
		g2.fillRect(6,6,12,36);
		g2.fillRect(30,6,12,36);
		g2.fillRect(6,30,36,12);
		
		g2.setColor(BLOCK_YELLOW);
		g2.fillRect(6,0,36,6);
		g2.fillRect(0,6,6,36);
		g2.fillRect(42,6,6,36);
		g2.fillRect(6,42,36,6);
		g2.fillRect(12,18,6,6);
		g2.fillRect(24,12,6,6);
		g2.fillRect(24,30,6,6);
		g2.fillRect(30,24,6,6);
		
		g2.setColor(BLOCK_BROWN);
		g2.fillRect(0,0,6,6);
		g2.fillRect(42,0,6,6);
		g2.fillRect(0,42,6,6);
		g2.fillRect(42,42,6,6);
		g2.fillRect(6,18,6,6);
		g2.fillRect(18,18,6,12);
		g2.fillRect(24,6,6,6);
		g2.fillRect(18,36,6,6);
		g2.fillRect(36,24,6,6);
		g2.fillRect(30,18,6,6);
	}
	
	private void drawDissappearingFrame2(Graphics2D g2)
	{
		g2.setColor(BLOCK_DARK_BLUE);
		g2.fillRect(6,6,12,6);
		g2.fillRect(36,6,6,12);
		g2.fillRect(6,30,6,12);
		g2.fillRect(30,36,12,6);
		
		g2.setColor(BLOCK_YELLOW);
		g2.fillRect(6,0,18,6);
		g2.fillRect(0,6,6,6);
		g2.fillRect(0,24,6,18);
		g2.fillRect(30,0,12,6);
		g2.fillRect(30,0,6,12);
		g2.fillRect(42,6,6,12);
		g2.fillRect(36,30,12,6);
		g2.fillRect(42,30,6,12);
		g2.fillRect(30,42,12,6);
		g2.fillRect(6,12,6,6);
		
		g2.setColor(BLOCK_BROWN);
		g2.fillRect(0,0,6,6);
		g2.fillRect(42,0,6,6);
		g2.fillRect(0,42,6,6);
		g2.fillRect(42,42,6,6);
		g2.fillRect(0,12,6,6);
		g2.fillRect(12,12,6,6);
		g2.fillRect(18,6,6,6);
		g2.fillRect(6,24,6,6);
		g2.fillRect(12,30,6,12);
		g2.fillRect(0,42,12,6);
		g2.fillRect(30,12,6,6);
		g2.fillRect(36,18,12,6);
		g2.fillRect(30,30,6,6);
		g2.fillRect(24,36,6,12);
		
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) && isVisible)
		{
			if (!isDissappearing)
				dissappear();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (super.isCollidingFloor(madelineX, madelineY) && isVisible)
		{
			if (!isDissappearing)
				dissappear();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (isVisible)
		{
			return super.isCollidingCeiling(madelineX, madelineY);
		}
		return false;
	}
	private void dissappear()
	{
		animationFrame = 0;
		isDissappearing = true;
		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				isVisible = false;
				isDissappearing = false;
				Timer reappearTimer = new Timer(2000, new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						animationFrame = 0;
						isVisible = true;
					}
				});
				reappearTimer.setRepeats(false);
				reappearTimer.start();
				
			}
		});
		t.setRepeats(false);
		t.start();
	}
}
