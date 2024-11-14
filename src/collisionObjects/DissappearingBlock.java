package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mainApp.AudioPlayer;
import mainApp.Constants;

/**
 * A Dissappearing Block is a CollisionObject that, once Madeline steps on it,
 * begins to dissappear, then reappears two seconds later.
 */
public class DissappearingBlock extends CollisionObject {

	private static final Color BLOCK_BROWN = new Color(171, 82, 54);
	private static final Color BLOCK_YELLOW = new Color(255, 163, 0);
	private static final Color BLOCK_DARK_BLUE = new Color(29, 43, 83);
	private boolean isVisible;
	private boolean isDissappearing;
	private int animationFrame;

	/**
	 * Creates a dissappearing block
	 * 
	 * @param x the top left x coordinate
	 * @param y the top left y coordinate
	 */
	public DissappearingBlock(int x, int y) {
		super(x, y, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT, true, true);
		isVisible = true;
		animationFrame = 0;

	}

	/**
	 * Draws the dissappearing block in it's current animation frame onto g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		if (isVisible) {
			g2 = (Graphics2D) (g2.create());

			g2.translate(getX(), getY());

			if (isDissappearing) {
				animationFrame++;
			}

			if (animationFrame < 10) {
				drawFullBlock(g2);
			} else if (animationFrame < 21) {
				drawDissappearingFrame1(g2);
			} else if (animationFrame <= 32) {
				drawDissappearingFrame2(g2);
			}
		}
	}

	/**
	 * Draws a fully intact block
	 */
	private void drawFullBlock(Graphics2D g2) {
		g2.setColor(BLOCK_YELLOW);
		g2.fillRect(0, 0, 8*Constants.PIXEL_DIM, 8*Constants.PIXEL_DIM);

		g2.setColor(BLOCK_DARK_BLUE);
		g2.fillRect(Constants.PIXEL_DIM, Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);

		g2.setColor(BLOCK_BROWN);
		g2.fillRect(0, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, 7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Draws frame 1 of the block's dissappearance
	 */
	private void drawDissappearingFrame1(Graphics2D g2) {

		g2.setColor(BLOCK_DARK_BLUE);
		g2.fillRect(Constants.PIXEL_DIM, Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);

		g2.setColor(BLOCK_YELLOW);
		g2.fillRect(Constants.PIXEL_DIM, 0, 6*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, Constants.PIXEL_DIM, Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 7*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(2*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(BLOCK_BROWN);
		g2.fillRect(0, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, 7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(6*Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Draws frame 2 of the block's dissappearance
	 */
	private void drawDissappearingFrame2(Graphics2D g2) {
		g2.setColor(BLOCK_DARK_BLUE);
		g2.fillRect(Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(6*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(BLOCK_YELLOW);
		g2.fillRect(Constants.PIXEL_DIM, 0, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 0, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(6*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 7*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(BLOCK_BROWN);
		g2.fillRect(0, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, 7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(7*Constants.PIXEL_DIM, 7*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(0, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(2*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(2*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(0, 7*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(6*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
	}

	/**
	 * Checks if Madeline is colliding with the side of the dissappearing block
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing) && isVisible) {
			if (!isDissappearing)
				dissappear();
			return true;
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the top of the dissappearing block
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY) && isVisible) {
			if (!isDissappearing)
				dissappear();
			return true;
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the bottom of the dissappearing block
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (isVisible) {
			return super.isCollidingCeiling(madelineX, madelineY);
		}
		return false;
	}

	/**
	 * Causes the dissappearing block to dissappear
	 */
	private void dissappear() {
		animationFrame = 0;
		isDissappearing = true;
		AudioPlayer.playFile("dissappearingblock");
		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isVisible = false;
				isDissappearing = false;
				Timer reappearTimer = new Timer(2000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
