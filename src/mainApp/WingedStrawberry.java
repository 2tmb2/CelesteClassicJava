package mainApp;

import java.awt.Graphics2D;

public class WingedStrawberry extends Strawberry {
	public WingedStrawberry(int x, int y, int width, int height, Madeline m)
	{
		super(x,y,width,height,m);
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		super.drawOn(g2);
		//g2.translate(getX(), getY());
	}
}
