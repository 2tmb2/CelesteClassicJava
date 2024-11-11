package TextElements;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.Constants;
import mainApp.MainApp;

public class LevelDisplayText {
	
	private String text;
	private Long startTime;
	private String seconds;
	private String minutes;
	private String hours;
	
	/**
	 * Creates a level display text object to display the level name and strawberry number
	 * @param startTime when the game was started
	 */
	public LevelDisplayText(String text, long startTime)
	{
		this.text = text;
		this.startTime = startTime;
		Long currentTime = System.currentTimeMillis();
		seconds = ((currentTime - startTime) / 1000) % 60 + "";
		minutes = ((currentTime - startTime) / (1000*60)) % 60 + "";
		hours = ((currentTime - startTime) / (1000*60*60)) % 24 + "";
		if (seconds.length() == 1)
			seconds = "0" + seconds;
		if (minutes.length() == 1)
			minutes = "0" + minutes;
		if (hours.length() == 1)
			hours = "0" + hours;
	}
	
	/**
	 * Draws the level display text onto g2
	 */
	public void drawOn(Graphics2D g2)
	{
		Graphics2D g2Copy = (Graphics2D)g2.create();
		g2Copy.setColor(Color.BLACK);
		g2Copy.translate(4*Constants.PIXEL_DIM, 4*Constants.PIXEL_DIM);
		g2Copy.fillRect(0, 0, 36*Constants.PIXEL_DIM, 8*Constants.PIXEL_DIM);
		g2Copy.setColor(Color.WHITE);
		g2Copy.translate(2*Constants.PIXEL_DIM, 1.5*Constants.PIXEL_DIM);
		if (startTime != 0)
			BlockyText.drawText(g2Copy, hours + ":" + minutes + ":" + seconds);
		else
			BlockyText.drawText(g2Copy,  "??:??:??");
		
		g2.translate((128*Constants.PIXEL_DIM)/2-(30*Constants.PIXEL_DIM), (128*Constants.PIXEL_DIM)/2 - 10*Constants.PIXEL_DIM);
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,60*Constants.PIXEL_DIM,20*Constants.PIXEL_DIM);
		
		g2.setColor(Color.WHITE);
		g2.translate(30*Constants.PIXEL_DIM, 10*Constants.PIXEL_DIM);
		if (text.length() == 5)
			g2.translate(-112/2, -15);
		else if (text.length() == 8)
		{
			g2.translate(-176/2, -15);
		}
		else
			g2.translate(-136/2, -15);
		BlockyText.drawText(g2, text);
	}
}
