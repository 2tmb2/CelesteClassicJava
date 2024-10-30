package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mainApp.Madeline;

public class DissappearingSpring extends CollisionObject {

	private Madeline m;
	private int springDrawFrame;
	private Timer restoreTimer;
	public boolean isVisible;
	public boolean isDissappearing;
	public int animationFrame;
	
	private static final Color SPRING_DARK_YELLOW = new Color(171, 82, 54);
	private static final Color SPRING_LIGHT_YELLOW = new Color(255, 163, 0);
	private static final Color SPRING_GREY = new Color(95, 87, 79);
	private static final Color BLOCK_BROWN = new Color(171, 82, 54);
	private static final Color BLOCK_YELLOW = new Color(255, 163, 0);
	private static final Color BLOCK_DARK_BLUE = new Color(29, 43, 83);
	
	
	public DissappearingSpring(int x, int y, Madeline m)
	{
		super(x,y,48,96);
		this.m = m;
		isVisible = true;
		animationFrame = 0;
		springDrawFrame = 1;
		restoreTimer = new Timer(400, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				springDrawFrame = 1;
			}
		});
		restoreTimer.setRepeats(false);
	}
	
	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		
		if (isVisible)
		{	
			g2.translate(getX()+6, getY()+18);
			if (springDrawFrame == 1) {
				frame1(g2);
			} else {
				frame2(g2);
			}
			g2.translate(-6,48-18);
			
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

	private void frame1(Graphics2D g2) {
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, 6, 6);
		g2.fillRect(30, 0, 6, 6);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(6, 0, 24, 6);

		g2.setColor(SPRING_GREY);
		g2.fillRect(6, 6, 6, 6);
		g2.fillRect(24, 6, 6, 6);
		g2.fillRect(12, 12, 12, 6);
		g2.fillRect(6, 18, 6, 6);
		g2.fillRect(24, 18, 6, 6);
		g2.fillRect(12, 24, 12, 6);
	}

	private void frame2(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(0,24);
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, 6, 6);
		g2.fillRect(30, 0, 6, 6);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(6, 0, 24, 6);
	}
	

	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing) && isVisible) {
			if (!isDissappearing)
			{
				dissappear();
			}
			if ( madelineY < this.getY() + 48)
			{
				bounce();
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY) && isVisible) {
			if (!isDissappearing)
			{
				dissappear();
			}
			if ( madelineY < this.getY() + 48)
			{
				bounce();
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY) && isVisible) {
			if (!isDissappearing)
			{
				dissappear();
			}
			if ( madelineY < this.getY() + 48)
			{
				bounce();
				return false;
			}
			return true;
		}
		return false;
	}

	private void bounce() {
		m.springBounce();
		springDrawFrame = 2;
		restoreTimer.start();
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
				Timer reappearTimer = new Timer(3000, new ActionListener() {
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
