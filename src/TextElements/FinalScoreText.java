package TextElements;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.MainApp;

public class FinalScoreText {

	private int strawberryTotal;
	private int deathCount;
	private boolean isIncomplete;
	private String seconds;
	private String minutes;
	private String hours;
	
	private static final Color STRAWBERRY_RED = new Color(255, 0, 77);
	private static final Color STRAWBERRY_DARK_GREEN = new Color(0, 135, 81);
	private static final Color STRAWBERRY_LIGHT_GREEN = new Color(0, 228, 54);
	private static final Color STRAWBERRY_DARK_RED = new Color(126, 37, 83);
	private static final Color STRAWBERRY_YELLOW = new Color(255, 163, 0);
	
	public FinalScoreText(Long time, int strawberryTotal, int deathCount, boolean isIncomplete)
	{
		this.strawberryTotal = strawberryTotal;
		this.isIncomplete = isIncomplete;
		this.deathCount = deathCount;
		seconds = ((time) / 1000) % 60 + "";
		if (seconds.length() == 1)
			seconds = "0" + seconds;
		minutes = ((time) / (1000*60)) % 60 + "";
		if (minutes.length() == 1)
			minutes = "0" + minutes;
		hours = ((time) / (1000*60*60)) % 24 + "";
		if (hours.length() == 1)
			hours = "0" + hours;
	}
	
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		
		g2.setColor(Color.BLACK);
		g2.translate(4*8*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 0, 8*8*MainApp.PIXEL_DIM, 4*8*MainApp.PIXEL_DIM);
		if (isIncomplete)
		{
			g2.translate(1.5*8*MainApp.PIXEL_DIM, 1.75*8*MainApp.PIXEL_DIM);
			g2.setColor(Color.WHITE);
			BlockyText.drawText(g2,  "INCOMPLETE");
		}
		else
		{
			g2.translate(3.4*8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM);
			g2.setColor(STRAWBERRY_RED);
			g2.fillRect(0, 6, 18, 6);
			g2.fillRect(-18, 6, 12, 6);
			g2.fillRect(-12, 12, 24, 6);
			g2.fillRect(-6, 18, 12, 6);
			g2.fillRect(-18, 0, 24, 6);
			g2.fillRect(12, 0, 6, 6);
			g2.fillRect(-18, -6, 6, 6);
			g2.fillRect(-6, -6, 24, 6);
			g2.fillRect(-12, -12, 24, 6);

			g2.setColor(STRAWBERRY_DARK_RED);
			g2.fillRect(-18, -12, 6, 6);
			g2.fillRect(12, -12, 6, 6);
			g2.fillRect(-18, 12, 6, 6);
			g2.fillRect(12, 12, 6, 6);
			g2.fillRect(-12, 18, 6, 6);
			g2.fillRect(6, 18, 6, 6);

			g2.setColor(STRAWBERRY_YELLOW);
			g2.fillRect(-6, 6, 6, 6);
			g2.fillRect(6, 0, 6, 6);
			g2.fillRect(-12, -6, 6, 6);

			g2.setColor(STRAWBERRY_LIGHT_GREEN);
			g2.fillRect(-6, -18, 6, 6);
			g2.fillRect(0, -24, 6, 6);
			g2.fillRect(12, -24, 6, 6);

			g2.setColor(STRAWBERRY_DARK_GREEN);
			g2.fillRect(-18, -24, 6, 6);
			g2.fillRect(-12, -18, 6, 6);
			g2.fillRect(0, -18, 12, 6);
			
			g2.setColor(Color.WHITE);
			g2.translate(6*MainApp.PIXEL_DIM, -6);
			BlockyText.drawText(g2, "x" + strawberryTotal);
			
			g2.translate(-9*MainApp.PIXEL_DIM + -8*MainApp.PIXEL_DIM, 48);
			BlockyText.drawText(g2, hours + ":" + minutes + ":" + seconds);
			
			g2.translate(0, 48);
			g2.translate(-(((deathCount) + "").length() - 1)*12, 0);
			BlockyText.drawText(g2, "Deaths:" + deathCount);
		}
	}
}
