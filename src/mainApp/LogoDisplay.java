package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import TextElements.BlockyText;

public class LogoDisplay {

	private static final int GAME_WIDTH = 768;
	private static final int GAME_HEIGHT = 768;
	private static final int SPRITE_WIDTH = 7*8*MainApp.PIXEL_DIM;
	private static final int SPRITE_HEIGHT = 4*8*MainApp.PIXEL_DIM;
	private static final int SPRITE_LOCATION_X = GAME_WIDTH + 9*8*MainApp.PIXEL_DIM;
	private static final int SPRITE_LOCATION_Y = 4*8*MainApp.PIXEL_DIM;
	private static final int XPOS = GAME_WIDTH/2 - SPRITE_WIDTH/2;
	private static final int YPOS = GAME_HEIGHT/2 - (int)(SPRITE_HEIGHT * 1);
	
	public LogoDisplay()
	{
		try {
			MainApp.SCALED_MAP = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		g2.drawImage(MainApp.SCALED_MAP, XPOS, YPOS, XPOS + SPRITE_WIDTH, YPOS + SPRITE_HEIGHT, (SPRITE_LOCATION_X - GAME_WIDTH), SPRITE_LOCATION_Y + 1, ((SPRITE_LOCATION_X - GAME_WIDTH)) + SPRITE_WIDTH, SPRITE_LOCATION_Y + SPRITE_HEIGHT, null);
		g2.translate(48,YPOS + (int)(SPRITE_HEIGHT *1.5));
		g2.setColor(Color.WHITE);
		BlockyText.drawText(g2, "S to start&&L to edit levels&&T to load level from file");
	}
}
