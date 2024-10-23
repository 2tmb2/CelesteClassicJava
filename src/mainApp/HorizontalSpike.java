package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class HorizontalSpike extends Spike {
	
	private static final Color SPIKE_WHITE = new Color(255,241,232);
	private static final Color SPIKE_GREY = new Color(194,195,199);
	
	public HorizontalSpike(int x, int y, int width, int height, Madeline m)
	{
		super(x,y,width,height, m);
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D) g2.create();
		g2.translate(getX() + 6, getY());
		for (int i = 0; i < getWidth()/24; i++)
		{
			g2.setColor(SPIKE_GREY);
			g2.fillRect(0, 12, 18, 24);
			
			g2.setColor(SPIKE_WHITE);
			g2.fillRect(6, 0, 6, 18);
			g2.fillRect(12, 12, 6, 6);
			
			g2.translate(24, 0);
		}
	}
}
