package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.Constants;
import mainApp.Madeline;

/**
 * A Spring causes Madeline to bounce upwards when she collides with it.
 */
public class Spring extends CollisionObject {
	
	private static final long REACTIVATION_FRAME = 12;
	private static final Color SPRING_DARK_YELLOW = new Color(171, 82, 54);
	private static final Color SPRING_LIGHT_YELLOW = new Color(255, 163, 0);
	private static final Color SPRING_GREY = new Color(95, 87, 79);
	private Madeline m;
	private int originalY;
	private int drawFrame;
	private int currentFrame;

	/**
	 * Creates a Spring object
	 * 
	 * @param x the top left x coordinate of the spring
	 * @param y the top left y coordinate of the spring
	 * @param m the Madeline to interact with the spring
	 */
	public Spring(int x, int y, Madeline m) {
		super(x + Constants.PIXEL_DIM, y + 3*Constants.PIXEL_DIM, 6*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, false, false);
		this.m = m;
		originalY = getY();
		drawFrame = 1;
	}

	/**
	 * Draws the spring onto g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY());
		currentFrame++;
		if (drawFrame == 1) {
			frame1(g2);
		} else {
			frame2(g2);
			if (currentFrame > REACTIVATION_FRAME) {
				drawFrame = 1;
				setY(originalY);
				setHeight(5*Constants.PIXEL_DIM);
			}
		}

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
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(Constants.PIXEL_DIM, 0, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Check if Madeline is colliding with a wall
	 * 
	 * @return true if Madeline is colliding with the spring, otherwise false
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing)) {
			bounce();
		}
		return false;
	}

	/**
	 * Check if Madeline is colliding with a ceiling
	 * 
	 * @return true if Madeline is colliding with the spring, otherwise false
	 */
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY)) {
			bounce();
		}
		return false;
	}

	/**
	 * Check if Madeline is colliding with a floor
	 * 
	 * @return true if Madeline is colliding with the spring, otherwise false
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY)) {
			bounce();
		}
		return false;
	}

	/**
	 * Makes Madeline bounce off the spring
	 */
	private void bounce() {
		if (currentFrame < REACTIVATION_FRAME)
			return;
		m.springBounce();
		currentFrame = 1;
		drawFrame = 2;
		if (getY() == originalY) {
			super.setY(getY() + 4*Constants.PIXEL_DIM);
		}
	}
}
