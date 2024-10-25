package mainApp;

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
	private static final int MADELINE_WIDTH = 48;
	private static final int MADELINE_HEIGHT = 42;

	public CollisionObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		if (madelineX + MADELINE_WIDTH - 1 > x && madelineX + 1 < x + width) {
			if (madelineY + MADELINE_HEIGHT > y) {
				if (madelineY + MADELINE_HEIGHT - y <= height) {
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
		if (madelineX + MADELINE_WIDTH - 1 > x && madelineX + 1 < x + width) {
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void updateAnimation() {
	}

	/**
	 * Returns a String representing the CollisionObject in the following format:
	 * Class, x y width height
	 */
	public String toString() {
		return this.getClass().toString().substring(6) + ", " + this.getX() + " " + this.getY() + " " + this.getWidth() + " " + this.getHeight();
	}
}
