package collectables;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import collisionObjects.CollisionObject;
import mainApp.AudioPlayer;
import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

public class Balloon extends CollisionObject {
	
	private static final Point BALLOON_TOP_SPRITE = new Point(288,48);
	private static final Point BALLOON_BOTTOM_ONE = new Point(624, 0);
	private static final Point BALLOON_BOTTOM_TWO = new Point(672, 0);
	private static final Point BALLOON_BOTTOM_THREE = new Point(720, 0);
	
	private static final int RESPAWN_FRAMES = 120;
	private static final int ANIMATION_DELTA = 28;
	private static final int PERIOD = 192; //How many frames to hover up and then back down
	private static final double AMPLITUDE = 1.5 * (double)Constants.PIXEL_DIM; //Amplitude of periodic motion
	
	private static final int BALLOON_TOP_WIDTH = 48;
	private static final int BALLOON_TOP_HEIGHT = 48;
	
	private static final int BALLOON_BOTTOM_WIDTH = 48;
	private static final int BALLOON_BOTTOM_HEIGHT = 48;
	
	private BufferedImage spriteMap;
	private int timeAtCollect = 0;
	private int lifetime = 0;
	private int currentFrame;
	private int verticalDelta = 0;
	private Madeline m;
	private boolean isCollected;
	public Balloon(int x, int y, Madeline m)
	{
		super(x,y,42,48, false, false);
		try {
			spriteMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentFrame = 0;
		isCollected = false;
		this.m = m;
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		lifetime++;
		if (lifetime > timeAtCollect + RESPAWN_FRAMES && isCollected == true)
		{
			isCollected = false;
			AudioPlayer.playFile("balloonreturn");
		}
		if (lifetime % ANIMATION_DELTA == 0) {
			currentFrame++;
			currentFrame = currentFrame % 3;
		}
		verticalDelta = Madeline.roundPos(((int)(-AMPLITUDE * Math.cos(Math.PI * (double)lifetime / (double)(PERIOD / 2)) + AMPLITUDE)));
		//this.setY(originalY + verticalDelta);
		if (!isCollected)
		{
			g2 = (Graphics2D)g2.create();
			g2.translate(getX(), getY());
			
			g2.drawImage(spriteMap, 0, 0 - verticalDelta, BALLOON_TOP_WIDTH, BALLOON_TOP_HEIGHT - verticalDelta, (int)BALLOON_TOP_SPRITE.getX(), (int)BALLOON_TOP_SPRITE.getY() + 1, (int)BALLOON_TOP_SPRITE.getX() + BALLOON_TOP_WIDTH, (int)BALLOON_TOP_SPRITE.getY() + BALLOON_TOP_HEIGHT, null);
			if (currentFrame == 0)
			{
				drawStemFrame(g2,BALLOON_BOTTOM_ONE);
			}
			else if (currentFrame == 1)
			{
				drawStemFrame(g2,BALLOON_BOTTOM_TWO);
			}
			else
			{
				drawStemFrame(g2,BALLOON_BOTTOM_THREE);
			}
		}
	}
	
	/**
	 * Draws the stem of the balloon with the sprite at spritePoint
	 * @param g2 graphics object to draw on
	 * @param spritePoint location of sprite to draw on sprite sheet
	 */
	private void drawStemFrame(Graphics2D g2, Point spritePoint)
	{
		g2.drawImage(spriteMap, 0, BALLOON_TOP_HEIGHT - verticalDelta, BALLOON_TOP_WIDTH, BALLOON_TOP_HEIGHT + BALLOON_BOTTOM_HEIGHT - verticalDelta, (int)spritePoint.getX(), (int)spritePoint.getY() + 1, (int)spritePoint.getX() + BALLOON_BOTTOM_WIDTH, (int)spritePoint.getY() + BALLOON_BOTTOM_HEIGHT, null);
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
	
	/**
	 * Collects the balloon, starting the inactivity timer
	 */
	private void collected()
	{
		if (m.getCurrentDashNum() != m.getTotalDashNum())
		{
			AudioPlayer.playFile("ballooncollect");
			m.resetDashes();
			isCollected = true;
			timeAtCollect = lifetime;
		}
	}
	
}
