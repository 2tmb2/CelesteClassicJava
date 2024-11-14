package TextElements;

import java.awt.Color;
import java.awt.Graphics2D;
import collisionObjects.CollisionObject;
import mainApp.Constants;

/**
 * The Text that shows up when Madeline approaches a gravestone, most notably
 * the one in Old Site
 */
public class GraveText extends CollisionObject {

	private String message = "-- Celeste Mountain -- &This memorial to those& Perished on the climb";
	private boolean displayText = false;
	private int timestamp;

	/**
	 * creates a GraveText object
	 * 
	 * @param x the top left x coordinate of the grave
	 * @param y the top left y coordinate of the grave
	 */
	public GraveText(int x, int y) {
		super(x, y, 2*Constants.SPRITE_WIDTH, 2*Constants.SPRITE_WIDTH, false, false);
	}

	/**
	 * Determines whether Madeline is colliding with any part of the grave
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing) || super.isCollidingFloor(madelineX, madelineY)
				|| super.isCollidingCeiling(madelineX, madelineY)) {
			displayText = true;
			return false;
		}
		displayText = false;
		timestamp = 0;
		return false;
	}

	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		return false;
	}

	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		return false;
	}

	/**
	 * Draws the grave text onto g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		if (displayText) {
			timestamp++;
			g2 = (Graphics2D) g2.create();

			g2.setColor(new Color(255, 241, 232));
			g2.translate(6*Constants.PIXEL_DIM, 12 * Constants.PIXEL_DIM * 8);
			g2.fillRect(8*Constants.PIXEL_DIM, 0, Constants.GAME_WIDTH - (4*Constants.SPRITE_WIDTH), 2*Constants.SPRITE_WIDTH + (Constants.SPRITE_WIDTH / 2));
			g2.setColor(Color.BLACK);
			g2.translate(Constants.SPRITE_WIDTH + Constants.SPRITE_WIDTH / 2, 2*Constants.PIXEL_DIM);
			if (timestamp >= 5 * message.length()) {
				timestamp = 5 * message.length();
			}
			BlockyText.drawText(g2, message.substring(0, timestamp / 5));

		}
	}
}
