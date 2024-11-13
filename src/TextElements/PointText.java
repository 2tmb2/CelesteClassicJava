package TextElements;

import java.awt.Color;
import java.awt.Graphics2D;
import mainApp.Constants;

/**
 * Class: PointText Purpose: Display the text 1000 after collecting a strawberry
 */
public class PointText {

	private static final Color RED_TEXT = new Color(255, 0, 77);
	private static final Color WHITE_TEXT = new Color(255, 241, 232);
	private Color currentColor;
	private int x;
	private int y;
	private int numOfAnimationFrames;
	private int translateBy;

	/**
	 * Create a PointText object
	 * 
	 * @param x representing the center of where the text should spawn
	 * @param y representing the center of where the text should spawn
	 */
	public PointText(int x, int y) {
		currentColor = RED_TEXT;
		numOfAnimationFrames = 40;
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws the text onto g2
	 */
	public void drawOn(Graphics2D g2) {
		// creates a copy of the Graphics2D object so that translations don't affect
		// future drawings
		g2 = (Graphics2D) g2.create();
		g2.translate(x - 6*Constants.PIXEL_DIM, y - translateBy - 20);
		g2.setColor(currentColor);

		BlockyText.drawText(g2, "1000");
	}
	
	
	/**
	 * Swaps color to whatever color is currently inactive
	 */
	private void swapColor() {
		if (currentColor.equals(RED_TEXT)) {
			currentColor = WHITE_TEXT;
		} else {
			currentColor = RED_TEXT;
		}
	}

	/**
	 * Updates the position and color of the PointText
	 * 
	 * @return true if the animation should stop, false if the animation should
	 *         continue
	 */
	public boolean updateAnimation() {
		if (translateBy <= numOfAnimationFrames) {
			if (translateBy % 5 == 0) {
				swapColor();
			}
			translateBy++;
			return false;
		} else {
			return true;
		}
	}
}
