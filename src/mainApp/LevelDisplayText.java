package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class LevelDisplayText {
	
	private String text;
	
	public LevelDisplayText(String text)
	{
		this.text = text;
	}
	public void drawOn(Graphics2D g2)
	{
		g2.translate((128*MainApp.PIXEL_DIM)/2-(30*MainApp.PIXEL_DIM), (128*MainApp.PIXEL_DIM)/2 - 10*MainApp.PIXEL_DIM);
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,60*MainApp.PIXEL_DIM,20*MainApp.PIXEL_DIM);
		
		g2.setColor(Color.WHITE);
		g2.translate(30*MainApp.PIXEL_DIM, 10*MainApp.PIXEL_DIM);
		if (text.length() == 5)
			g2.translate(-112/2, -15);
		else
			g2.translate(-136/2, -15);
		BlockyText.drawText(g2, text);
	}
}
