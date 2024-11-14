package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mainApp.Constants;
import mainApp.Madeline;

/**
 * A Dissappearing Spring is a Spring ontop of a Dissappearing Block. When the
 * Spring is jumped on, the spring and the block it's sitting on dissappear.
 */
public class DissappearingSpring extends CollisionObject {

	private Madeline m;
	private int springDrawFrame;
	private Timer restoreTimer;
	private boolean isVisible;
	private boolean isDissappearing;
	private int animationFrame;

	private static final Color SPRING_DARK_YELLOW = new Color(171, 82, 54);
	private static final Color SPRING_LIGHT_YELLOW = new Color(255, 163, 0);
	private static final Color SPRING_GREY = new Color(95, 87, 79);
	private static final Color BLOCK_BROWN = new Color(171, 82, 54);
	private static final Color BLOCK_YELLOW = new Color(255, 163, 0);
	private static final Color BLOCK_DARK_BLUE = new Color(29, 43, 83);

	/**
	 * Creates a Dissappearing Spring object. A dissappearing spring is a spring
	 * ontop of a dissappearing block.
	 * 
	 * @param x representing the top left x coordinate of the spring
	 * @param y representing the top left y coordinate of the spring
	 * @param m representing the Madeline object to interact with
	 */
	public DissappearingSpring(int x, int y, Madeline m) {
		super(x, y, 48, 96, true, true);
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

	/**
	 * Draws the DissappearingSpring onto g2 based on it's current animation frame
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();

		if (isVisible) {
			g2.translate(getX() + 6, getY() + 18);
			if (springDrawFrame == 1) {
				frame1(g2);
			} else {
				frame2(g2);
			}
			g2.translate(-6, 48 - 18);

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
	 * Draws frame 1 (fully extended) of the Spring's animation
	 */
	private void frame1(Graphics2D g2) {
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(Constants.PIXEL_DIM, 0, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(SPRING_GREY);
		g2.fillRect(Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(2*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(2*Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Draws frame 2 (fully retracted) of the Spring's animation
	 */
	private void frame2(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(0, 4*Constants.PIXEL_DIM);
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(Constants.PIXEL_DIM, 0, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Checks if Madeline is colliding with the side of the dissappearingspring
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing) && isVisible) {
			if (!isDissappearing) {
				dissappear();
			}
			if (madelineY < this.getY() + 48) {
				bounce();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the bottom of the dissappearing spring
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY) && isVisible) {
			if (!isDissappearing) {
				dissappear();
			}
			if (madelineY < this.getY() + 48) {
				bounce();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the top of the dissappearing spring
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY) && isVisible) {
			if (!isDissappearing) {
				dissappear();
			}
			if (madelineY < this.getY() + 48) {
				bounce();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Causes Madeline to bounce
	 */
	private void bounce() {
		m.springBounce();
		springDrawFrame = 2;
		restoreTimer.start();
	}

	/**
	 * Causes the dissappearing block to dissappear
	 */
	private void dissappear() {
		animationFrame = 0;
		isDissappearing = true;
		Timer t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isVisible = false;
				isDissappearing = false;
				Timer reappearTimer = new Timer(3000, new ActionListener() {
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
