package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Console;

public class CollisionObject {
	private int x;
	private int y;
	private int width;
	private int height;
	private static final int MADELINE_WIDTH = 48;
	private static final int MADELINE_HEIGHT = 42;
	
	public CollisionObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D) g2.create();
		g2.setColor(Color.GRAY);
		g2.fillRect(x, y, width, height);
	}
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (madelineX + MADELINE_WIDTH - 10 > x && madelineX + 10 < x + width)
		{
			if (madelineY + MADELINE_HEIGHT > y)
			{
				if (madelineY + MADELINE_HEIGHT - y <= height)
				{
					return true;
				}
			}
		}
		return false;
	}
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (madelineX + MADELINE_WIDTH - 10 > x && madelineX + 10 < x + width)
		{
			if (madelineY > y + height/2)
			{
				if (madelineY - (y+height) <= 0)
				{
					return true;
				}
			}
		}
		return false;
	}
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (madelineY + MADELINE_HEIGHT > y + 5 && madelineY < y + height - 5)
		{
			// facing left, to the right
			if (facing < 0 && madelineX > x + width/2)
			{
				if (madelineX - (x+width) <= 0)
					//System.out.println("yes");
					return true;
			}
			// facing right, to the right
			else if (facing > 0 && madelineX > x + width/2)
			{
				if (madelineX + MADELINE_WIDTH - (x+width) <= 0)
					return true;
			}
			// facing left, to the left
			else if (facing < 0 && madelineX < x + width/2)
			{
				if (madelineX - x >= 0)
					return true;
			}
			// facing right, to the left
			else if (facing > 0 && madelineX + MADELINE_WIDTH < x + width/2)
			{
				if (madelineX + MADELINE_WIDTH - x >= 0)
					return true;
			}
		}
		return false;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void updateAnimation() {}
	public String toString() {
		return this.getClass().toString().substring(6) + ", " + this.getX() + " " + this.getY() + " " + this.getWidth() + " " + this.getHeight() + " ";
	}
}
