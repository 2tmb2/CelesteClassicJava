package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class DownSpike extends Spike {
	
	private static final Color SPIKE_WHITE = new Color(255,241,232);
	private static final Color SPIKE_GREY = new Color(194,195,199);
	
	public DownSpike(int x, int y, int width, Madeline m)
	{
		super(x,y,width,1, m);
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		
	}
}
