package collectables;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.AudioPlayer;
import mainApp.Constants;
import mainApp.Madeline;

/**
 * A Winged Strawberry is similar to a Strawberry, except when Madeline dashes
 * it flies away from the player.
 */
public class WingedStrawberry extends Strawberry {

	private static final Color WING_WHITE = new Color(255, 241, 232);
	private static final Color WING_GREY = new Color(194, 195, 199);
	private static final int MAX_FLY_AWAY_AMOUNT = -100;
	private Graphics2D g2;
	private int drawFrame;
	private boolean isFlyingAway;
	private double flyAwayAmount;

	/**
	 * Creates a winged strawberry that flies away when Madeline dashes
	 * 
	 * @param x the center x location of the strawberry spawn
	 * @param y the center y location of the strawberry spawn
	 * @param m the current Madeline object that will interact with the
	 *          WingedStrawberry
	 */
	public WingedStrawberry(int x, int y, Madeline m) {
		super(x, y, m);
		drawFrame = 1;
		isFlyingAway = false;
		flyAwayAmount = 0;
	}

	/**
	 * Draws a WingedStrawberry onto g2 based on it's current animation frame
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		this.g2 = (Graphics2D) g2.create();
		super.drawOn(this.g2);

		this.g2.translate(getX(), getY());
		this.g2.translate(0, getTranslateBy());

		if (drawFrame == 1) {
			drawFrame1();
		} else if (drawFrame == 2) {
			drawFrame2();
		} else if (drawFrame == 3) {
			drawFrame3();
		}

	}

	/**
	 * Draws frame 1 (fully extended) of the Winged Strawberry's flapping animation
	 */
	private void drawFrame1() {
		g2.setColor(WING_WHITE);
		// left side of wing
		g2.fillRect(-4*Constants.PIXEL_DIM, -3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM);
		g2.fillRect(-5*Constants.PIXEL_DIM, -4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-5*Constants.PIXEL_DIM, -2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(-6*Constants.PIXEL_DIM, -5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM);
		g2.fillRect(-7*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-8*Constants.PIXEL_DIM, -3*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-9*Constants.PIXEL_DIM, -4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-10*Constants.PIXEL_DIM, -5*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		// right side of wing
		g2.fillRect(3*Constants.PIXEL_DIM, -3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, -2*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, -3*Constants.PIXEL_DIM, 3*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, -4*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(8*Constants.PIXEL_DIM, -4*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, -5*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		// left side of the wing
		g2.setColor(WING_GREY);
		g2.fillRect(-5*Constants.PIXEL_DIM, -3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-7*Constants.PIXEL_DIM, -2*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-8*Constants.PIXEL_DIM, -4*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		// right side of the wing
		g2.fillRect(5*Constants.PIXEL_DIM, -2*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(4*Constants.PIXEL_DIM, -3*Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(6*Constants.PIXEL_DIM, -4*Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Draws frame 2 (fully retracted) of the Winged Strawberry's flapping animation
	 */
	private void drawFrame2() {
		g2.setColor(WING_WHITE);
		// left side of the wing
		g2.fillRect(-3*Constants.PIXEL_DIM - 2*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-3*Constants.PIXEL_DIM - 6*Constants.PIXEL_DIM, 0, 6*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-5*Constants.PIXEL_DIM - 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		// right side of the wing
		g2.fillRect(3*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM, 0, 6*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(5*Constants.PIXEL_DIM, Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Draws frame 3 (partially retracted) of the Winged Strawberry's flapping
	 * animation
	 */
	private void drawFrame3() {
		g2.setColor(WING_WHITE);

		// left side of the wing
		g2.fillRect(-3*Constants.PIXEL_DIM - 5*Constants.PIXEL_DIM, -2*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-3*Constants.PIXEL_DIM - 4*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-3*Constants.PIXEL_DIM - 4*Constants.PIXEL_DIM - 2*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-3*Constants.PIXEL_DIM - Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(-3*Constants.PIXEL_DIM - 4*Constants.PIXEL_DIM - 3*Constants.PIXEL_DIM, 0, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		// right side of the wing
		g2.fillRect(3*Constants.PIXEL_DIM, -2*Constants.PIXEL_DIM, 5*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM + 4*Constants.PIXEL_DIM + Constants.PIXEL_DIM, -Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM, 0, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
		g2.fillRect(3*Constants.PIXEL_DIM + 4*Constants.PIXEL_DIM + Constants.PIXEL_DIM, 0, 2*Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		g2.setColor(WING_GREY);

		// left side of the wing
		g2.fillRect(-3*Constants.PIXEL_DIM - 4*Constants.PIXEL_DIM - Constants.PIXEL_DIM, -Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);

		// right side of the wing
		g2.fillRect(3*Constants.PIXEL_DIM + 4*Constants.PIXEL_DIM, -Constants.PIXEL_DIM, Constants.PIXEL_DIM, Constants.PIXEL_DIM);
	}

	/**
	 * Updates the flying animation of the WingedStrawberry
	 */
	@Override
	public void updateAnimation() {
		super.updateAnimation();
		if (getCurrentFrame() >= numOfAnimationFrames - 2) {
			drawFrame = 1;
		} else if (getCurrentFrame() >= 2 * (numOfAnimationFrames - 2) / 3) {
			drawFrame = 3;
		} else if (getCurrentFrame() >= (numOfAnimationFrames - 2) / 2) {
			drawFrame = 2;
		}
		if (isFlyingAway && getY() > MAX_FLY_AWAY_AMOUNT) {
			super.setY(super.getY() - (int) flyAwayAmount);
			if (flyAwayAmount < 10)
				flyAwayAmount += .5;
		}
	}

	/**
	 * Makes the strawberry fly away
	 */
	@Override
	public void flyAway() {
		if (getY() >= -8*Constants.PIXEL_DIM && isFlyingAway == false) {
			AudioPlayer.playFile("strawberry_flyaway");
			isFlyingAway = true;
		}
	}
}
