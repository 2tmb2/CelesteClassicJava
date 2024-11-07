package collectables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import collisionObjects.CollisionObject;
import mainApp.AudioPlayer;
import mainApp.Madeline;

public class BigChest extends CollisionObject {

	private boolean isOpen;
	private BufferedImage scaledMap;
	private static final int GAME_WIDTH = 768;
	private static final int SPRITE_WIDTH = 96;
	private int spriteHeight;
	private static final int CHEST_LOCATION_X = 768;
	private int chestLocationY;
	private Madeline m;
	private int drawY;

	/**
	 * Creates a big chest object that, when opened, reveals a dash crystal.
	 * 
	 * @param x representing the top left x value
	 * @param y representing the top left y value
	 * @param m representing the Madeline to interact with the chest
	 */
	public BigChest(int x, int y, Madeline m) {
		super(x, y, 96, 96, false, false);
		this.m = m;
		spriteHeight = SPRITE_WIDTH;
		chestLocationY = 48 * 6;
		drawY = y;
		isOpen = false;
		try {
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draws the big chest based on it's sprite
	 */
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();

		if (true) {
			g2.drawImage(scaledMap, getX(), drawY, getX() + SPRITE_WIDTH, drawY + spriteHeight,
					(CHEST_LOCATION_X - GAME_WIDTH), chestLocationY + 1,
					((CHEST_LOCATION_X - GAME_WIDTH)) + SPRITE_WIDTH, chestLocationY + spriteHeight, null);
		}
	}

	/**
	 * Checks if Madeline is colliding with the side of the BigChest. Ensures that
	 * the chest is opened if it is not already
	 */
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing) && !isOpen) {
			openBigChest();
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the floor of the BigChest. Ensures that
	 * the chest is opened if it is not already
	 */
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY) && !isOpen) {
			openBigChest();
		}
		return false;
	}

	/**
	 * Checks if Madeline is colliding with the ceiling of the BigChest. Ensures
	 * that the chest is opened if it is not already
	 */
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY) && !isOpen) {
			openBigChest();
		}
		return false;
	}

	/**
	 * Opens the Big Chest, which includes changing it's sprite to display that it
	 * is open
	 */
	private void openBigChest() {
		m.openBigChest(getX() + 24, getY() - 72);
		AudioPlayer.playFile("bigchest");
		spriteHeight = 48;
		chestLocationY = 48 * 7;
		drawY = getY() + 48;
		isOpen = true;
	}

}
