package collisionObjects;

import java.awt.Graphics2D;

/**
 * Class: CollisionObject <br> 
 * Purpose: Used to represent an object that the player character Madeline can
 * collide with
 */
public class CollisionObject {
	private int x;
	private int y;
	private int width;
	private int height;
	public static final int MADELINE_WIDTH = 36;
	public static final int MADELINE_HEIGHT = 24;
	private boolean canSlide;
	private boolean canWallJump;

	/**
	 * Creates an invisible collision object
	 * 
	 * @param x           representing the x value of the top left corner of the
	 *                    object
	 * @param y           representing the y value of the top left corner of the
	 *                    object
	 * @param width       representing the width of the object
	 * @param height      representing the height of the object
	 * @param canSlide    true if Madeline should be able to slide on the object,
	 *                    otherwise false
	 * @param canWallJump true if Madeline should be able to wall jump on the
	 *                    object, otherwise false
	 */
	public CollisionObject(int x, int y, int width, int height, boolean canSlide, boolean canWallJump) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.canSlide = canSlide;
		this.canWallJump = canWallJump;
	}

	public void drawOn(Graphics2D g2) {}

	/**
	 * Checks if the character located at the given x and y values would be within
	 * the floor
	 * 
	 * @param madelineX representing the x position of madeline
	 * @param madelineY representing the y position of madeline
	 * @return true if Madeline would be colliding with the floor, otherwise returns
	 *         false
	 */
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (madelineX + MADELINE_WIDTH - 5 > x && madelineX + 5 < x + width) {
			if (madelineY + MADELINE_HEIGHT > y) {
				if (madelineY + MADELINE_HEIGHT <= y + height) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the character located at the given x and y values would be within
	 * the ceiling
	 * 
	 * @param madelineX representing the x position of madeline
	 * @param madelineY representing the y position of madeline
	 * @return true if Madeline would be colliding with the ceiling, otherwise
	 *         returns false
	 */
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (madelineX + MADELINE_WIDTH - 10 > x && madelineX + 10 < x + width) {
			if (madelineY > y + height / 2) {
				if (madelineY - (y + height) <= 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the character located at the given x and y values would be within
	 * the wall
	 * 
	 * @param madelineX representing the x position of madeline
	 * @param madelineY representing the y position of madeline
	 * @return true if Madeline would be colliding with the wall, otherwise returns
	 *         false
	 */
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		// ensures that madeline is below the top of the object and above the bottom of
		// the object
		if (madelineY + MADELINE_HEIGHT > y + 5 && madelineY < y + height - 5) {
			// if madeline is facing left and moving to the right
			if (facing < 0 && madelineX > x + width / 2) {
				if (madelineX - (x + width) <= 0)
					return true;
			}
			// if madeline is facing right and moving to the right
			else if (facing > 0 && madelineX > x + width / 2) {
				if (madelineX + MADELINE_WIDTH - (x + width) <= 0)
					return true;
			}
			// if madeline is facing left and moving to the left
			else if (facing < 0 && madelineX < x + width / 2) {
				if (madelineX - x >= 0)
					return true;
			}
			// if madeline is facing right and moving to the left
			else if (facing > 0 && madelineX + MADELINE_WIDTH < x + width / 2) {
				if (madelineX + MADELINE_WIDTH - x >= 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * @return the width of the collision object
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height of the collision object
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the x location of the collision object
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y location of the collision object
	 */
	public int getY() {
		return y;
	}

	public void updateAnimation() {}

	/**
	 * Sets the y value of the collision object
	 * @param y the new Y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the x value of the collision object
	 * @param x the new X position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the height of the collisionobject
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Sets the width of the collisionobject
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return true if you can slide off the collision object
	 */
	public boolean getCanSlide() {
		return canSlide;
	}

	/**
	 * @return true if you can walljump off the collision object
	 */
	public boolean getCanWallJump() {
		return canWallJump;
	}
	
	public void stopAllTimers() {}
}
