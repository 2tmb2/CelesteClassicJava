package collisionObjects;

import java.awt.Graphics2D;

import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

/**
 * The Finish Flag displays the player's stats for the game, including death
 * count, time, and number of strawberries collected.
 */
public class FinishFlag extends CollisionObject {

	private Madeline m;
	private int flagLocationX;
	private static final int FLAG_LOCATION_X_FRAME_1 = 6 * Constants.SPRITE_WIDTH + Constants.GAME_WIDTH;
	private static final int FLAG_LOCATION_X_FRAME_2 = 7 * Constants.SPRITE_WIDTH + Constants.GAME_WIDTH;
	private static final int FLAG_LOCATION_X_FRAME_3 = 8 * Constants.SPRITE_WIDTH + Constants.GAME_WIDTH;
	private static final int FLAG_LOCATION_Y = 7 * 8 * Constants.PIXEL_DIM;
	private int animationFrame;

	/**
	 * Creates a FinishFlag object, which when collided with displays the player's
	 * statistics
	 * 
	 * @param x representing the top left x coordinate
	 * @param y representing the top left y coordinate
	 * @param m representing the current Madeline object
	 */
	public FinishFlag(int x, int y, Madeline m) {
		super(x, y, 48, 48, false, false);
		this.m = m;
		animationFrame = 0;
	}

	/**
	 * Checks if Madeline is colliding with the side of the FinishFlag
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing) || super.isCollidingFloor(madelineX, madelineY)
				|| super.isCollidingCeiling(madelineX, madelineY)) {
			m.displayFinalText();
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the top of the FinishFlag
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the bottom of the FinishFlag
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		return false;
	}

	/**
	 * Draws the FinishFlag in it's current animation frame onto g2
	 * 
	 * @param g2 the Graphics2D object to draw onto
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		animationFrame++;
		if (animationFrame == 0) {
			flagLocationX = FLAG_LOCATION_X_FRAME_1;
		} else if (animationFrame == 12) {
			flagLocationX = FLAG_LOCATION_X_FRAME_2;
		} else if (animationFrame == 24) {
			flagLocationX = FLAG_LOCATION_X_FRAME_3;
		} else if (animationFrame == 36) {
			animationFrame = 0;
			flagLocationX = FLAG_LOCATION_X_FRAME_1;
		}
		g2 = (Graphics2D) g2.create();
		g2.drawImage(MainApp.SCALED_MAP, getX(), getY(), getX() + Constants.SPRITE_WIDTH,
				getY() + Constants.SPRITE_HEIGHT, (flagLocationX - Constants.GAME_WIDTH), FLAG_LOCATION_Y + 1,
				((flagLocationX - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH,
				FLAG_LOCATION_Y + Constants.SPRITE_HEIGHT, null);
	}
}
