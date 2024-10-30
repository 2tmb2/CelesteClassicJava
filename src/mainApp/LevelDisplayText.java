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
		g2.translate(768/2-180, 768/2-60);
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,360,120);
		
		g2.setColor(Color.WHITE);
		g2.translate(180, 60);
		if (text.length() == 5)
			g2.translate(-112/2, -15);
		else
			g2.translate(-136/2, -15);
		BlockyText.drawText(g2, text);
	}
}
