package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import TextElements.BlockyText;

/**
 * Displays the Celeste logo
 */
public class LogoDisplay {

	private static final int GAME_HEIGHT = Constants.GAME_WIDTH;
	private static final int SPRITE_LOCATION_X = Constants.GAME_WIDTH + 9*8*Constants.PIXEL_DIM;
	private static final int SPRITE_LOCATION_Y = 4*8*Constants.PIXEL_DIM;
	private static final int XPOS = (Constants.GAME_WIDTH)/2 - (Constants.SPRITE_WIDTH*7)/2;
	private static final int YPOS = GAME_HEIGHT/2 - (int)((Constants.SPRITE_HEIGHT*4) * 1);
	
	/**
	 * Creates a logo display object
	 */
	public LogoDisplay()
	{
		try {
			MainApp.SCALED_MAP = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws the logo onto g2
	 */
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		g2.drawImage(MainApp.SCALED_MAP, XPOS, YPOS, XPOS + 7*Constants.SPRITE_WIDTH, YPOS + 4*Constants.SPRITE_HEIGHT, (SPRITE_LOCATION_X - Constants.GAME_WIDTH), SPRITE_LOCATION_Y + 1, ((SPRITE_LOCATION_X - Constants.GAME_WIDTH)) + 7*Constants.SPRITE_WIDTH, SPRITE_LOCATION_Y + 4*Constants.SPRITE_HEIGHT, null);
		g2.translate(48,YPOS + (int)(4*Constants.SPRITE_HEIGHT *1.5));
		g2.setColor(Color.WHITE);
		BlockyText.drawText(g2, "S to start&&L to edit levels&&T to load level from file");
	}
}
