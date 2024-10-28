package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class UpSpike extends Spike {
	
	private static final Color SPIKE_WHITE = new Color(255,241,232);
	private static final Color SPIKE_GREY = new Color(194,195,199);
	
	public UpSpike(int x, int y, int width, Madeline m)
	{
		super(x,y,width,20, m);
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY() - 18);
		for (int i = 0; i < getWidth()/24; i++)
		{
			g2.setColor(SPIKE_GREY);
			g2.fillRect(0, 18, 18, 18);
			
			g2.setColor(SPIKE_WHITE);
			g2.fillRect(6, 6, 6, 24);
			g2.fillRect(12, 18, 6, 6);
			
			g2.translate(24, 0);
		}
	}
}
