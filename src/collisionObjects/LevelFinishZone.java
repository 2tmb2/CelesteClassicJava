package collisionObjects;

import mainApp.Madeline;

/**
 * <pre>
 * Class: LevelFinishZone 
 * Purpose: Create a collision trigger for finishing the level
 * </pre>
 */
public class LevelFinishZone extends CollisionObject {

	private Madeline m;

	/**
	 * Creates a LevelFinishZone object. A LevelFinishZone is a CollisionObject that
	 * triggers the next level when Madeline collides with it
	 * 
	 * @param x      representing the initial x value
	 * @param y      representing the initial y value
	 * @param width  representing the width of the LevelFinishZone
	 * @param height representing the height of the LevelFinishZone
	 * @param m      is a reference to the current Madeline object who will be
	 *               interacting with the LevelFinishZone
	 */
	public LevelFinishZone(int x, int y, int width, int height, Madeline m) {
		super(x, y, width, height, false, false);
		this.m = m;
	}

	/**
	 * Returns false for a LevelFinishZone to disable vertical collision
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY)) {
			m.nextLevel();
		}
		return false;
	}

	/**
	 * Returns false for a LevelFinishZone to disable vertical collision
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY)) {
			m.nextLevel();
		}
		return false;
	}

	/**
	 * Purpose: To advance to the next level when collided with
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int isFacing) {
		if (super.isCollidingWall(madelineX, madelineY, isFacing)) {
			m.nextLevel();
		}
		return false;
	}
}
