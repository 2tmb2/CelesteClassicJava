package collectables;

import java.awt.Graphics2D;
import mainApp.AudioPlayer;
import mainApp.Constants;
import mainApp.Madeline;
import mainApp.MainApp;

public class Chest {

	private int x;
	private int y;
	private boolean shouldDraw;
	private static final int CHEST_LOCATION_X = 960;
	private static final int CHEST_LOCATION_Y = 48;
	
	/**
	 * Creates a Chest at the center of the screen
	 * Chests dissappear when the key is collected, revealing a Strawberry
	 */
	public Chest()
	{
		x = 768/2;
		y = 768/2;
		shouldDraw = true;
	}
	
	/**
	 * Sets the x-location of the chest
	 */
	public void setX(int x)
	{
		this.x =x ;
	}
	
	/**
	 * Sets the y-location of the chest
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * If the key hasn't been collected, draws the chest onto g2 based on it's sprite
	 * @param g2
	 */
	public void drawOn(Graphics2D g2)
	{
		if (shouldDraw)
		{
			g2 = (Graphics2D)g2.create();
			g2.drawImage(MainApp.SCALED_MAP, x, y, x + Constants.SPRITE_WIDTH, y + Constants.SPRITE_HEIGHT, (CHEST_LOCATION_X - Constants.GAME_WIDTH), CHEST_LOCATION_Y + 1, ((CHEST_LOCATION_X - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH, CHEST_LOCATION_Y + Constants.SPRITE_HEIGHT, null);
		}
	}
	
	/**
	 * Collects the given chest
	 * @param m representing the current Madeline object
	 */
	public void collect(Madeline m)
	{
		AudioPlayer.playFile("key");
		shouldDraw = false;
		m.openChest(x + 8*Constants.PIXEL_DIM / 2, y + 7*Constants.PIXEL_DIM / 2);
	}
}
