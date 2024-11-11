package TextElements;

import java.awt.Color;
import java.awt.Graphics2D;

import collisionObjects.CollisionObject;
import mainApp.MainApp;

public class GraveText extends CollisionObject {

	private String message = "-- Celeste Mountain -- &This memorial to those& Perished on the climb";
	private boolean displayText = false;
	private int timestamp;
	
	
	public GraveText(int x, int y)
	{
		super(x, y, 96, 96, false, false);
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (super.isCollidingWall(madelineX, madelineY, facing) || super.isCollidingFloor(madelineX, madelineY) || super.isCollidingCeiling(madelineX, madelineY))
		{
			displayText = true;
			return false;
		}
		displayText = false;
		timestamp = 0;
		return false;
	}
	
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		return false;
	}
	
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		return false;
	}
	
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (displayText)
		{
			timestamp++;
			g2 = (Graphics2D)g2.create();

			g2.setColor(new Color(255, 241, 232));
			g2.translate(36, 12*MainApp.PIXEL_DIM*8);
			g2.fillRect(48,0, 768-(96*2), 96+(48/2));
			g2.setColor(Color.BLACK);
			g2.translate(48+48/2, 12);
			if (timestamp >= 5*message.length())
			{
				timestamp = 5*message.length();
			}
			BlockyText.drawText(g2, message.substring(0, timestamp / 5));
			
		}
	}
}
