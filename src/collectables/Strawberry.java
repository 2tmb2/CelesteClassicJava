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

	public Strawberry(int x, int y, Madeline m) {
		super(x, y, 56, 56, false, false);
		this.m = m;
		setCurrentFrame(0);
		setNumOfAnimationFrames(80);
	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY());
		updateTranslateBy();
		g2.translate(0, getTranslateBy());
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

		g2.setColor(STRAWBERRY_DARK_RED);
		g2.fillRect(-18, -12, 6, 6);
		g2.fillRect(12, -12, 6, 6);
		g2.fillRect(-18, 12, 6, 6);
		g2.fillRect(12, 12, 6, 6);
		g2.fillRect(-12, 18, 6, 6);
		g2.fillRect(6, 18, 6, 6);

		g2.setColor(STRAWBERRY_YELLOW);
		g2.fillRect(-6, 6, 6, 6);
		g2.fillRect(6, 0, 6, 6);
		g2.fillRect(-12, -6, 6, 6);

		g2.setColor(STRAWBERRY_LIGHT_GREEN);
		g2.fillRect(-6, -18, 6, 6);
		g2.fillRect(0, -24, 6, 6);
		g2.fillRect(12, -24, 6, 6);

		g2.setColor(STRAWBERRY_DARK_GREEN);
		g2.fillRect(-18, -24, 6, 6);
		g2.fillRect(-12, -18, 6, 6);
		g2.fillRect(0, -18, 12, 6);
	}

	public void updateTranslateBy() {
		if (getCurrentFrame() > getNumOfAnimationFrames() / 4
				&& getCurrentFrame() < 3 * getNumOfAnimationFrames() / 4) {
			setTranslateBy(getTranslateBy() - .5);
		} else {
			setTranslateBy(getTranslateBy() + .5);
		}
	}

	@Override
	public void updateAnimation() {
		if (getCurrentFrame() >= getNumOfAnimationFrames() - 2) {
			setTranslateBy(getTranslateBy() - .5);
			setCurrentFrame(0);
		} else {
			setCurrentFrame(getCurrentFrame() + 1);
		}
	}

	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY)) {
			m.collectStrawberry();
			return false;
		}
		return false;
	}

	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY)) {
			m.collectStrawberry();
			return false;
		}
		return false;
	}

	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int isFacing) {
		if (super.isCollidingWall(madelineX, madelineY, isFacing)) {
			m.collectStrawberry();
			return true;
		}
		return false;
	}

	public double getTranslateBy() {
		return translateBy;
	}

	public void setTranslateBy(double translateBy) {
		this.translateBy = translateBy;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getNumOfAnimationFrames() {
		return numOfAnimationFrames;
	}

	public void setNumOfAnimationFrames(int numOfAnimationFrames) {
		this.numOfAnimationFrames = numOfAnimationFrames;
	}
	
	public void flyAway() {}
}
