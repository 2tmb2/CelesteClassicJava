package mainApp;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class: Particle
 * 
 * @author Matthew Weyer <br>
 *         Purpose: Hold information for a dust particle effect at a certain
 *         position <br>
 *         Restrictions: Needs a position to work
 * 
 * 
 */
public class Particle {
	private int xPos;
	private int yPos;
	private int frame;
	private static final Point FRAME_ONE = new Point(13 * Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT);
	private static final Point FRAME_TWO = new Point(14 * Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT);
	private static final Point FRAME_THREE = new Point(15 * Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT);
	private static final int LIFETIME = 30;

	public Particle(int xPos, int yPos) {
		this.xPos = Madeline.roundPos(xPos);
		this.yPos = Madeline.roundPos(yPos);
		frame = 0;
	}
	
	public void drawOn(Graphics2D g2) {
		frame++;
		g2 = (Graphics2D) g2.create();
		g2.translate(xPos, yPos);
		if (frame == 5 || frame == (LIFETIME / 3) + 5 || frame == (2*(LIFETIME/3)) + 5) {
			xPos += Constants.PIXEL_DIM;
		}
		if (frame < (LIFETIME / 3) + 1) {
			g2.drawImage(MainApp.SCALED_MAP, 0, 0, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT, (int)FRAME_ONE.getX(), (int)FRAME_ONE.getY(), (int)FRAME_ONE.getX() + Constants.SPRITE_WIDTH, (int)FRAME_ONE.getY() + Constants.SPRITE_HEIGHT, null);
		} else if (frame < (2 * (LIFETIME / 3)) + 1) {
			g2.drawImage(MainApp.SCALED_MAP, 0, 0, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT, (int)FRAME_TWO.getX(), (int)FRAME_TWO.getY(), (int)FRAME_TWO.getX() + Constants.SPRITE_WIDTH, (int)FRAME_TWO.getY() + Constants.SPRITE_HEIGHT, null);
		} else if (frame < (3 * (LIFETIME / 3)) + 1) {
			g2.drawImage(MainApp.SCALED_MAP, 0, 0, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT, (int)FRAME_THREE.getX(), (int)FRAME_THREE.getY(), (int)FRAME_THREE.getX() + Constants.SPRITE_WIDTH, (int)FRAME_THREE.getY() + Constants.SPRITE_HEIGHT, null);
		}
	}
	
	public int getFrame() {
		return this.frame;
	}

}
