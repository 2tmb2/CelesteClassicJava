package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpawningMadeline {

	private int x;
	private int y;
	private int maxHeight;
	private Color hairColor;
	private boolean movingUp;
	
	private static final int MOVE_UP_SPEED = 10;
	private static final Color EYE_COLOR = new Color(29, 43, 83);
	private static final Color TORSO_COLOR = new Color(0, 135, 81);
	private static final Color LEG_COLOR = new Color(255, 241, 232);
	private static final Color FACE_COLOR = new Color(255, 204, 170);
	
	/**
	 * Creates an object that looks like Madeline but without collision for the purpose of spawning her into the level
	 * @param x her starting x position
	 * @param maxHeight the maximum height she should move to
	 * @param hairColor the hair color she should have
	 */
	public SpawningMadeline(int x, int maxHeight, Color hairColor)
	{
		y = 768;
		this.x = x;
		this.maxHeight = maxHeight;
		this.hairColor = hairColor;
		movingUp = true;
	}
	
	/**
	 * Draws the faux madeline onto g2
	 */
	public void drawOn(Graphics2D g2) {
		
		g2 = (Graphics2D) g2.create();

		if (movingUp && y > maxHeight)
		{
			y -= MOVE_UP_SPEED;
		}
		else
		{
			movingUp = false;
		}
		g2.translate(x, y);
		
		// drawing hair
		g2.setColor(hairColor);
		g2.fillRect(MainApp.PIXEL_DIM, 0, 6*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);

		// drawing face
		g2.setColor(FACE_COLOR);
		g2.fillRect(3*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM);

		// drawing torso
		g2.setColor(TORSO_COLOR);
		g2.fillRect(2*MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);

		// drawing legs
		g2.setColor(LEG_COLOR);
		g2.fillRect(2*MainApp.PIXEL_DIM, 6*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(5*MainApp.PIXEL_DIM, 6*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);

		// drawing eyes
		g2.setColor(EYE_COLOR);
		g2.fillRect(3*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(6*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * @return true if the SpawningMadeline is moving up
	 */
	public boolean getMovingUp()
	{
		return movingUp;
	}
}
