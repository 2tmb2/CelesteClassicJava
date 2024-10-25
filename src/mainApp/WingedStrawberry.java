package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class WingedStrawberry extends Strawberry {

	private static final Color WING_WHITE = new Color(255, 241, 232);
	private static final Color WING_GREY = new Color(194, 195, 199);
	private Graphics2D g2;
	private int drawFrame;
	private boolean isFlyingAway;
	private double flyAwayAmount;
	public WingedStrawberry(int x, int y, int width, int height, Madeline m) {
		super(x, y, width, height, m);
		drawFrame = 1;
		isFlyingAway = false;
		flyAwayAmount = 0;
	}

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
		g2.fillRect(-24, -18, 6, 24);
		g2.fillRect(-30, -24, 6, 6);
		g2.fillRect(-30, -12, 6, 12);
		g2.fillRect(-36, -30, 6, 18);
		g2.fillRect(-42, -6, 12, 6);
		g2.fillRect(-48, -18, 18, 6);
		g2.fillRect(-54, -24, 6, 6);
		g2.fillRect(-60, -30, 30, 6);

		// right side of wing
		g2.fillRect(18, -18, 6, 24);
		g2.fillRect(24, -12, 6, 12);
		g2.fillRect(24, -6, 18, 6);
		g2.fillRect(30, -18, 18, 6);
		g2.fillRect(24, -24, 12, 6);
		g2.fillRect(48, -24, 6, 6);
		g2.fillRect(30, -30, 30, 6);

		// left side of the wing
		g2.setColor(WING_GREY);
		g2.fillRect(-30, -18, 6, 6);
		g2.fillRect(-42, -12, 12, 6);
		g2.fillRect(-48, -24, 12, 6);

		// right side of the wing
		g2.fillRect(30, -12, 12, 6);
		g2.fillRect(24, -18, 6, 6);
		g2.fillRect(36, -24, 12, 6);
	}

	/**
	 * Draws frame 2 (fully retracted) of the Winged Strawberry's flapping animation
	 */
	private void drawFrame2() {
		g2.setColor(WING_WHITE);
		// left side of the wing
		g2.fillRect(-18 - 12, -6, 12, 6);
		g2.fillRect(-18 - 36, 0, 36, 6);
		g2.fillRect(-30 - 30, 6, 30, 6);

		// right side of the wing
		g2.fillRect(18, -6, 12, 6);
		g2.fillRect(18, 0, 36, 6);
		g2.fillRect(30, 6, 30, 6);
	}

	/**
	 * Draws frame 3 (partially retracted) of the Winged Strawberry's flapping
	 * animation
	 */
	private void drawFrame3() {
		g2.setColor(WING_WHITE);

		// left side of the wing
		g2.fillRect(-18 - 30, -12, 30, 6);
		g2.fillRect(-18 - 24, -6, 24, 6);
		g2.fillRect(-18 - 24 - 6 - 6, -6, 6, 6);
		g2.fillRect(-18 - 6, 0, 6, 6);
		g2.fillRect(-18 - 24 - 6 - 12, 0, 12, 6);

		// right side of the wing
		g2.fillRect(18, -12, 30, 6);
		g2.fillRect(18, -6, 24, 6);
		g2.fillRect(18 + 24 + 6, -6, 6, 6);
		g2.fillRect(18, 0, 6, 6);
		g2.fillRect(18 + 24 + 6, 0, 12, 6);

		g2.setColor(WING_GREY);

		// left side of the wing
		g2.fillRect(-18 - 24 - 6, -6, 6, 6);

		// right side of the wing
		g2.fillRect(18 + 24, -6, 6, 6);
	}

	@Override
	public void updateAnimation() {
		super.updateAnimation();
		if (getCurrentFrame() >= getNumOfAnimationFrames() - 2) {
			drawFrame = 1;
		} else if (getCurrentFrame() >= 2 * (getNumOfAnimationFrames() - 2) / 3) {
			drawFrame = 3;
		} else if (getCurrentFrame() >= (getNumOfAnimationFrames() - 2) / 2) {
			drawFrame = 2;
		}
		if (isFlyingAway && getY() > -50)
		{
			super.setY(super.getY() - (int)flyAwayAmount);
			if (flyAwayAmount < 10)
				flyAwayAmount += .5;
		}
	}
	public void flyAway()
	{
		isFlyingAway = true;
	}
}
