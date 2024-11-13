package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * One snow particle that moves across the front of the screen during gameplay.
 */
public class SnowParticle {
	private int x;
	private int y;
	private int speed;
	private int size;
	private double translateBy;
	private int timestamp;
	private Color snowColor;

	/**
	 * Creates a snow, which is one element of the foreground group of snow, with a
	 * randomized y position, speed, and size
	 */
	public SnowParticle() {
		this.x = Madeline.roundPos((int) (Math.random() * 120 * Constants.PIXEL_DIM) + 4 * Constants.PIXEL_DIM);
		;
		this.y = Madeline.roundPos((int) (Math.random() * 120 * Constants.PIXEL_DIM) + 4 * Constants.PIXEL_DIM);
		this.speed = (int) (Math.random() * 4) + 2;
		this.size = Constants.PIXEL_DIM * ((int) (Math.random() * 2) + 1);
		timestamp = (int) (Math.random() * 100);
		snowColor = new Color(255, 242, 231);
	}

	/**
	 * Draws the snow onto g2
	 */
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		x += speed;
		timestamp++;
		if (timestamp > 360) {
			timestamp = 0;
		}
		g2.setColor(snowColor);

		translateBy = Math.cos(Math.toRadians(timestamp * speed)) * size * speed;
		g2.translate(0, translateBy);
		g2.fillRect(x, y, size, size);
		if (x > Constants.GAME_WIDTH) {
			x = -size;
			y = Madeline.roundPos((int) (Math.random() * 120 * Constants.PIXEL_DIM) + 4 * Constants.PIXEL_DIM);
			this.speed = (int) (Math.random() * 3) + 3;
			this.size = Constants.PIXEL_DIM * ((int) (Math.random() * 2) + 1);
		}

	}
}
