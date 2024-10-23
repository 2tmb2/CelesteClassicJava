package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Madeline {
	private int xPos;
	private int yPos;
	private double xVel;
	private double yVel;
	private int numOfDashesTotal;
	private int numOfDashesRemaining;
	private int facingRight;
	private boolean isDashingHorizontally;
	private ArrayList<CollisionObject> collisionObjects;
	private int yVelMax;
	private boolean wallJump;
	private boolean jumpPressed;
	private boolean isDashing;
	private LevelComponent lvl;
	
	private Color hairColor; 
	private static final Color RED_HAIR = new Color(255, 0, 77);
	private static final Color BLUE_HAIR = new Color(41, 173, 255);
	private static final Color GREEN_HAIR = new Color(0,0,0);
	private static final Color EYE_COLOR = new Color(29,43,83);
	private static final Color TORSO_COLOR = new Color(0, 135, 81);
	private static final Color LEG_COLOR = new Color(255, 241, 232);
	private static final Color FACE_COLOR = new Color(255, 204, 170);
	
	
	//48pix by 42pix
	// screen is 770
	public Madeline(int xPos, int yPos, int numOfDashesTotal, ArrayList<CollisionObject> c, LevelComponent lvl) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.lvl = lvl;
		
		xVel = 0;
		yVel = 0;
		yVelMax = 6;
		collisionObjects = c;
		wallJump = false;
		jumpPressed = false;
		isDashing = false;
		isDashingHorizontally = false;
		facingRight = 1;
		this.numOfDashesTotal = numOfDashesTotal;
		numOfDashesRemaining = 0;
		if (this.numOfDashesTotal == 1)
		{
			hairColor = new Color(255, 0, 77);
		}
	}
	public void increaseX()
	{
		if (xVel <= 3 && !wallJump)
		{
			xVel += 1;
		}
	}
	public void decreaseX()
	{
		if (xVel >= -3 && !wallJump)
		{
			xVel -= 1;
		}
		
	}
	public void setHorizontalPosition()
	{
		if (Math.abs(xVel) <= 2)
		{
			wallJump = false;
		}
		if (!isCollidingWithWall())
		{
			yVelMax = 6;
			xPos += (int)xVel;
		}
		else
		{
			while (isCollidingWithWall() && Math.abs(xVel) != 0)
			{
				xVel += -.5*facingRight;
			}
			yVelMax = 2;
		}
		if (xVel > 0)
		{
			xVel -= .5;
			facingRight = 1;
		}
		else if (xVel < 0)
		{
			facingRight = -1;
			xVel += .5;
		}
	}
	public void setVerticalPosition()
	{
		if (!isCollidingWithFloor() && !isDashingHorizontally)
		{
			if (isCollidingWithCeiling() && yVel < 0)
			{
				yVel = 0;
			}
			yPos += (int)yVel;
		}
		else
		{
			yVel = 0;
		}
		if (isCollidingWithFloor())
		{
			yVel = 0;
		}
		else if (yVel < yVelMax)
		{
			yVel += 0.5;
		}
		else if (yVel > yVelMax)
		{
			if (!isDashing)
			{
				yVel = yVelMax;
			}
			else
			{
				yVel -= .5;
			}
		}
	}
	public void checkIfDashing()
	{
		if (Math.abs(xVel) <= 8 && yVel >= 0)
		{
			isDashingHorizontally = false;
		}
		if (Math.abs(xVel) <= 4 && Math.abs(yVel) <= yVelMax)
		{
			isDashing = false;
		}
		if (isCollidingWithFloor())
		{
			numOfDashesRemaining = numOfDashesTotal;
		}
	}
	public boolean isCollidingWithWall()
	{
		for (CollisionObject c : collisionObjects)
		{
			if (c.isCollidingWall(xPos + (int)xVel, yPos + (int)yVel, facingRight))
			{
				return true;
			}
		}
		return false;
	}
	public boolean isCollidingWithFloor()
	{
		for (CollisionObject c : collisionObjects)
		{
			if (c.isCollidingFloor(xPos + (int)xVel, yPos + (int)yVel))
			{
				if (c instanceof Spike)
				{
					death();
				}
				return true;
			}
		}
		return false;
	}
	public boolean isCollidingWithCeiling()
	{
		for (CollisionObject c : collisionObjects)
		{
			if (c.isCollidingCeiling(xPos + (int)xVel, yPos + (int)yVel))
			{
				return true;
			}
		}
		return false;
	}
	public void jump()
	{
		if (isCollidingWithFloor() && !jumpPressed)
		{
			numOfDashesRemaining = numOfDashesTotal;
			yVel = -10;
			jumpPressed = true;
		}
		else if (isCollidingWithWall() && !jumpPressed && isCollidingWithFloor())
		{
			jumpPressed = true;
			wallJump = true;
			xVel = -facingRight * 10;
			yVel = -10;
		}
		else if (isCollidingWithWall() && !jumpPressed && !isCollidingWithFloor() && !wallJump)
		{
			jumpPressed = true;
			wallJump = true;
			xVel = -facingRight * 10;
			yVel = -10;
		}
	}
	public void setJumpPressed(boolean b)
	{
		jumpPressed = b;
	}
	public void dash(String dir)
	{
		if (numOfDashesRemaining > 0 && !(numOfDashesRemaining == 0))
		{
			isDashing = true;
			numOfDashesRemaining--;
			if (dir.equals("up"))
			{
				yVel = -13;
			}
			if (dir.equals("down"))
			{
				yVel = 13;
			}
			if (dir.equals("upleft"))
			{
				yVel = -12;
				xVel = -12;
			}
			if (dir.equals("upright"))
			{
				yVel = -12;
				xVel = 12;
			}
			if (dir.equals("downleft"))
			{
				yVel = 12;
				xVel = -12;
			}
			if (dir.equals("downright"))
			{
				yVel = 12;
				xVel = 12;
			}
			if (dir.equals(""))
			{
				isDashingHorizontally = true;
				xVel = 13*facingRight;
				yVel = 0;
			}
		}
	}
	public void drawOn(Graphics2D g2)
	{
		if (numOfDashesRemaining == 1)
		{
			hairColor = RED_HAIR;
			numOfDashesRemaining = numOfDashesTotal;
		}
		else if (numOfDashesRemaining == 2)
		{
			hairColor = GREEN_HAIR;
		}
		else
		{
			hairColor = BLUE_HAIR;
		}
		
		
		g2 = (Graphics2D)g2.create();
		g2.setColor(Color.YELLOW);
		if (facingRight < 0)
		{
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-48, 0);
			g2.transform(tx);
			g2.translate(-xPos, yPos);
		}
		else
		{
			g2.translate(xPos, yPos);
		}
		
		// drawing hair
		g2.setColor(hairColor);
		g2.fillRect(6, 0, 36, 6);
		g2.fillRect(0, 6, 48, 18);
		g2.fillRect(0, 24, 12, 6);
		g2.fillRect(6, 30, 6, 6);
//		
		double xModifier = -2*Math.abs(xVel);
		if (Math.abs(xVel) >= 3)
		{
			xModifier = (Math.abs(xVel)/xVel) * 3 * -2 * facingRight;
		}
		double yModifier = 0;
		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 0, 6, 6);
		
		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 6, 12, 6);
		
		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 12, 18,6);
		
		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 18, 24, 6);
		
		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 24, 30, 6);
		
		g2.translate(xModifier, yModifier);
		g2.fillRect(24, 30, 18, 6);
		
		g2.translate(-6*xModifier, -6*yModifier);
		
		
		// drawing face
		g2.setColor(FACE_COLOR);
		g2.fillRect(18, 12, 24, 6);
		g2.fillRect(12, 18, 30, 12);
		
		// drawing torso
		g2.setColor(TORSO_COLOR);
		g2.fillRect(12, 30, 24, 6);
		
		// drawing legs
		g2.setColor(LEG_COLOR);
		g2.fillRect(12, 36, 6, 6);
		g2.fillRect(30, 36, 6, 6);
		
		// drawing eyes
		g2.setColor(EYE_COLOR);
		g2.fillRect(18,18,6,6);
		g2.fillRect(36, 18, 6, 6);
	
	}
	public void death()
	{
		lvl.resetLevel();
	}
	public void breakBlock(int x, int y)
	{
		lvl.addNewStrawberry(x, y);
		//jumpPressed = true;
		//wallJump = true;
		xVel = -facingRight * 7;
		yVel = -10;
		yPos -= 10;
	}
	public void updateAnimations()
	{
		for (CollisionObject c : collisionObjects)
		{
			c.updateAnimation();
		}
	}
	public void collectStrawberry()
	{
		lvl.collectStrawberry();
	}
	public boolean getIsDashing()
	{
		return isDashing;
	}
	public void nextLevel()
	{
		lvl.nextLevel();
	}
}
