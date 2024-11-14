package collisionObjects;

import java.awt.Graphics2D;

import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

/**
 * A cloud platform is a thin platform that has no collision on it's bottom but
 * has collision on the top, allowing you to dash up through it. It also
 * constantly moves from side to side.
 */
public class CloudPlatform extends CollisionObject {

	private int translatedPosition;
	private int travelingDirection;
	private boolean isColliding;
	private Madeline m;
	private static final int CLOUD_LOCATION_X = 1296;
	private static final int CLOUD_LOCATION_Y = 0;

	/**
	 * Creates a new CloudPlatform
	 * 
	 * @param x                  representing the starting x position of the
	 *                           platform
	 * @param y                  representing the starting y position of the
	 *                           platform
	 * @param m                  representing the current level's Madeline
	 * @param travelingDirection 1 if the platform is traveling to the right, -1 if
	 *                           it is traveling to the left
	 */
	public CloudPlatform(int x, int y, Madeline m, int travelingDirection) {
		super(x, y, 2*Constants.SPRITE_WIDTH, 4 * Constants.PIXEL_DIM, false, false);
		translatedPosition = x;
		this.travelingDirection = travelingDirection;
		this.m = m;
		isColliding = false;
	}

	/**
	 * Draws the CloudPlatform onto g2 based on it's sprite
	 */
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		translatedPosition += travelingDirection * Constants.PIXEL_DIM / 3;
		if (isColliding)
			m.moveWithCloud(travelingDirection * Constants.PIXEL_DIM / 3);
		if (translatedPosition >= Constants.GAME_WIDTH && travelingDirection == 1) {
			translatedPosition = -getWidth();
		} else if (translatedPosition + getWidth() <= 0) {
			translatedPosition = Constants.GAME_WIDTH + getWidth();
		}
		super.setX(translatedPosition);
		g2.drawImage(MainApp.SCALED_MAP, Madeline.roundPos(getX()), getY(),
				Madeline.roundPos(getX() + 2 * Constants.SPRITE_WIDTH), getY() + Constants.SPRITE_HEIGHT,
				(CLOUD_LOCATION_X - Constants.GAME_WIDTH), CLOUD_LOCATION_Y + 1,
				((CLOUD_LOCATION_X - Constants.GAME_WIDTH)) + 2 * Constants.SPRITE_WIDTH,
				CLOUD_LOCATION_Y + Constants.SPRITE_HEIGHT, null);
	}

	/**
	 * prevent Madeline from colliding with the side of the cloud platform
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		return false;
	}

	/**
	 * prevent madeline from colliding with the bottom of the cloud platform
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		return false;
	}

	/**
	 * Checks Madeline's collision with the top of the cloud platform.
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		// she is not colliding if she is moving up through the platform
		if (m.getYVelocity() >= 0) {
			if (super.isCollidingFloor(madelineX, madelineY)) {
				isColliding = true;
				return true;
			}
		}
		isColliding = false;
		return false;
	}
}
