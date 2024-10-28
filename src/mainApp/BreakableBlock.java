package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class: BreakableBlock <br>
 * Purpose: Used to create a BreakableBlock which reveals a strawberry when
 * dashed into
 */
public class BreakableBlock extends CollisionObject {
	private static final Color OUTER_COLOR = new Color(255, 241, 232);
	private static final Color INNER_COLOR = new Color(41, 172, 253);
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
		super(x, y, width, height);
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
			// drawings
			g2 = (Graphics2D) g2.create();
			g2.translate(getX(), getY());

			// Draws the inner section of the block
			g2.setColor(INNER_COLOR);
			g2.fillRect(12, 12, getWidth() - 24, getHeight() - 24);

			// draws the outer section of the block
			g2.setColor(OUTER_COLOR);
			// top border
			g2.fillRect(12, 6, getWidth() - 24, 6);
			// left border
			g2.fillRect(6, 6 + 6, 6, getHeight() - 24);
			// bottom border
			g2.fillRect(12, 0 + getHeight() - 12, getWidth() - 24, 6);
			// right border
			g2.fillRect(getWidth() - 12, 0 + 12, 6, getHeight() - 24);

			// protruding parts (signifies that it can be broken)
			for (int i = 0; i < getWidth() / 32; i++) {
				g2.fillRect(0, 18 + 24 * i, 18, 12);
				g2.fillRect(18 + 24 * i, 0, 12, 18);
				g2.fillRect(18 + 24 * (getWidth() / 32) - 12, 18 + 24 * i, 18, 12);
				g2.fillRect(18 + 24 * i, 18 + 24 * (getWidth() / 32) - 12, 12, 18);
			}
		}
	}

	/**
	 * Used to indicate that the block has been broken and can't be interacted with
	 * further
	 */
	public void breakBlock() {
		exists = false;
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
					m.breakBlock(getX() + getWidth() / 2, getY() + getHeight() / 2);
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
			return super.isCollidingFloor(madelineX, madelineY);
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
			return super.isCollidingCeiling(madelineX, madelineY);
		}
		return false;
	}

	// unused for BreakableBlock since there is nothing to animate
	@Override
	public void updateAnimation() {
	}
}
