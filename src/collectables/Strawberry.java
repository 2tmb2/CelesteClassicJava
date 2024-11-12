package collectables;

import java.awt.Color;
import java.awt.Graphics2D;

import collisionObjects.CollisionObject;
import mainApp.Madeline;

public class Strawberry extends CollisionObject {

	private static final Color STRAWBERRY_RED = new Color(255, 0, 77);
	private static final Color STRAWBERRY_DARK_GREEN = new Color(0, 135, 81);
	private static final Color STRAWBERRY_LIGHT_GREEN = new Color(0, 228, 54);
	private static final Color STRAWBERRY_DARK_RED = new Color(126, 37, 83);
	private static final Color STRAWBERRY_YELLOW = new Color(255, 163, 0);
	private int numOfAnimationFrames;
	private double translateBy;
	private int currentFrame;
	private Madeline m;

	/**
	 * Creates a Strawberry object that can be collected by the player
	 * @param x representing the center x of the strawberry
	 * @param y representing the center y of the strawberry
	 * @param m representing the current Madeline object who will collect the strawberry
	 */
	public Strawberry(int x, int y, Madeline m) {
		super(x, y, 56, 56, false, false);
		this.m = m;
		currentFrame = 0;
		numOfAnimationFrames = 80;
	}

	/**
	 * Draws a Strawberry onto g2 pixel-by-pixel
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY());
		updateTranslateBy();
		g2.translate(0, getTranslateBy());
		
		// red part of the strawberry (main body)
		g2.setColor(STRAWBERRY_RED);
		g2.fillRect(0, 6, 18, 6);
		g2.fillRect(-18, 6, 12, 6);
		g2.fillRect(-12, 12, 24, 6);
		g2.fillRect(-6, 18, 12, 6);
		g2.fillRect(-18, 0, 24, 6);
		g2.fillRect(12, 0, 6, 6);
		g2.fillRect(-18, -6, 6, 6);
		g2.fillRect(-6, -6, 24, 6);
		g2.fillRect(-12, -12, 24, 6);

		// dark red part of the strawberry (corners)
		g2.setColor(STRAWBERRY_DARK_RED);
		g2.fillRect(-18, -12, 6, 6);
		g2.fillRect(12, -12, 6, 6);
		g2.fillRect(-18, 12, 6, 6);
		g2.fillRect(12, 12, 6, 6);
		g2.fillRect(-12, 18, 6, 6);
		g2.fillRect(6, 18, 6, 6);

		// yellow part of the strawberry (dots)
		g2.setColor(STRAWBERRY_YELLOW);
		g2.fillRect(-6, 6, 6, 6);
		g2.fillRect(6, 0, 6, 6);
		g2.fillRect(-12, -6, 6, 6);

		// light green part of the strawberry (top of leaves)
		g2.setColor(STRAWBERRY_LIGHT_GREEN);
		g2.fillRect(-6, -18, 6, 6);
		g2.fillRect(0, -24, 6, 6);
		g2.fillRect(12, -24, 6, 6);

		// dark green part of strawberry (bottom of leaves)
		g2.setColor(STRAWBERRY_DARK_GREEN);
		g2.fillRect(-18, -24, 6, 6);
		g2.fillRect(-12, -18, 6, 6);
		g2.fillRect(0, -18, 12, 6);
	}

	/**
	 * Updates the translated amount of the strawberry drawing
	 */
	public void updateTranslateBy() {
		currentFrame++;
		if (currentFrame > 360)
		{
			currentFrame = 0;
		}
		translateBy = Math.cos(-Math.toRadians(currentFrame * 3))*10;
	}

	/**
	 * Checks if the player is colliding with the top of the strawberry
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY)) {
			m.collectStrawberry();
			return false;
		}
		return false;
	}

	/**
	 * Checks if the player is colliding with the bottom of the strawberry
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY)) {
			m.collectStrawberry();
			return false;
		}
		return false;
	}

	/**
	 * Checks if the player is colliding with the wall of the strawberry
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int isFacing) {
		if (super.isCollidingWall(madelineX, madelineY, isFacing)) {
			m.collectStrawberry();
			return true;
		}
		return false;
	}

	/**
	 * @return the amount the drawing is translated by
	 */
	public double getTranslateBy() {
		return translateBy;
	}

	/**
	 * @param translateBy the amount to translate the drawing by
	 */
	public void setTranslateBy(double translateBy) {
		this.translateBy = translateBy;
	}

	/**
	 * @return the current animation frame
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Sets the current animation frame to a desired value
	 * @param currentFrame the frame you want the animation to be on
	 */
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	/**
	 * @return the total number of animation frames
	 */
	public int getNumOfAnimationFrames() {
		return numOfAnimationFrames;
	}

	/**
	 * sets the total number of animation frames
	 * @param numOfAnimationFrames the number of animation frames you would like
	 */
	public void setNumOfAnimationFrames(int numOfAnimationFrames) {
		this.numOfAnimationFrames = numOfAnimationFrames;
	}
	
	public void flyAway() {}
}
