package collectables;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.Madeline;
import mainApp.MainApp;

public class Chest {

	private int x;
	private int y;
	private boolean shouldDraw;
	
	public Chest()
	{
		x = 768/2;
		y = 768/2;
		shouldDraw = true;
	}
	
	public void setX(int x)
	{
		this.x =x ;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public void drawOn(Graphics2D g2)
	{
		if (shouldDraw)
		{
			g2 = (Graphics2D)g2.create();
			g2.translate(x, y);
			g2.setColor(Color.GREEN);
			g2.fillRect(0, 0, 8*MainApp.PIXEL_DIM, 6*MainApp.PIXEL_DIM);
		}
	}
	
	public void collect(Madeline m)
	{
		shouldDraw = false;
		m.openChest(x + 8*MainApp.PIXEL_DIM / 2, y + 6*MainApp.PIXEL_DIM / 2);
	}
}
