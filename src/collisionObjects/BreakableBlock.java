package collisionObjects;

import java.awt.Graphics2D;
import java.awt.Point;
import mainApp.AudioPlayer;
import mainApp.Madeline;
import mainApp.MainApp;

/**
 * Class: BreakableBlock <br>
 * Purpose: Used to create a BreakableBlock which reveals a strawberry when
 * dashed into
 */
public class BreakableBlock extends CollisionObject {
	private static final Point BREAKABLE_BLOCK_SPRITE = new Point(0,192);
	private boolean exists;
	private Madeline m;
	

	/**
	 * Creates a Breakable Block object. A breakable block is a block that, upon
	 * being dashed into, reveals a strawberry.
	 * 
	 * @param x      representing the initial x value
	 * @param y      representing the initial y value
	 * @param width  representing the width of the breakable block
	 * @param height representing the height of the breakable block
	 * @param m      is a reference to the current Madeline object who will be
	 *               interacting with the BreakableBlock
	 */
	public BreakableBlock(int x, int y, int width, int height, Madeline m) {
		super(x, y, width, height, true, true);
		exists = true;
		this.m = m;
	}

	/**
	 * Draws the BreakableBlock based on it's values of x, y, width, and height
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		// exists dictates whether the block has been broken by Madeline or not
		if (exists) {
			// duplicates the Graphics2D object so that transformations don't affect future
			g2 = (Graphics2D) g2.create();
			g2.translate(getX(), getY());
			g2.drawImage(MainApp.SCALED_MAP, 0, 0, getWidth(), getHeight(), (int)BREAKABLE_BLOCK_SPRITE.getX(), (int)BREAKABLE_BLOCK_SPRITE.getY() + 1, (int)BREAKABLE_BLOCK_SPRITE.getX() + getWidth(), (int)BREAKABLE_BLOCK_SPRITE.getY() + getHeight(), null);
		}
	}

	/**
	 * Used to indicate that the block has been broken and can't be interacted with
	 * further
	 */
	public void breakBlock() {
		exists = false;
		m.breakBlock(getX() + getWidth() / 2, getY() + getHeight() / 2);
		AudioPlayer.playFile("breakableblock");
	}

	/**
	 * Checks for Madeline's collision with the block and determines if the block
	 * should be broken
	 * 
	 * @param madelineX representing Madeline's x position
	 * @param madelineY representing Madeline's y position
	 * @param facing    takes in -1 if madeline is facing left and 1 if madeline is
	 *                  facing right
	 * @returns true if madeline is colliding with the block AND it exists
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (exists) {
			if (super.isCollidingWall(madelineX, madelineY, facing)) {
				if (m.getIsDashing()) {
					breakBlock();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * checks for madeline's collision with the block if it exists
	 * 
	 * @param madelineX
	 * @param madelineY
	 * @return boolean
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (exists) {
			if (super.isCollidingFloor(madelineX, madelineY))
			{
				if (m.getIsDashing()) {
					breakBlock();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * checks for madeline's collision with the block if it exists
	 * 
	 * @param madelineX
	 * @param madelineY
	 * @return boolean
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (exists) {
			if (super.isCollidingCeiling(madelineX, madelineY))
			{
				if (m.getIsDashing()) {
					breakBlock();
					return false;
				}
				return true;
			}
		}
		return false;
	}
}
