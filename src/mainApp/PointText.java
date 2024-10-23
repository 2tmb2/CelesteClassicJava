package mainApp;
import java.awt.Color;
import java.awt.Graphics2D;
public class PointText {
	
	private static final Color RED_TEXT = new Color(255, 0, 77);
	private static final Color WHITE_TEXT = new Color(255,241,232);
	private Color currentColor;
	private int x;
	private int y;
	private int numOfAnimationFrames;
	private int translateBy;
	public PointText(int x, int y)
	{
		currentColor = RED_TEXT;
		numOfAnimationFrames = 40;
		this.x = x;
		this.y = y;
	}
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D)g2.create();
		g2.translate(x-36, y-translateBy-20);
		g2.setColor(currentColor);
		
		//1
		g2.fillRect(0,0,6,6);
		g2.fillRect(6, 0, 6, 6*4);
		g2.fillRect(0, 4*6, 6*3, 6);
		
		//0
		for (int i = 0; i < 3; i++)
		{
			g2.translate(24, 0);	
			g2.fillRect(0, 0, 18, 6);
			g2.fillRect(0, 24, 18, 6);
			g2.fillRect(0, 0, 6, 24);
			g2.fillRect(12, 0, 6, 24);
		}
	}
	private void swapColor()
	{
		if (currentColor.equals(RED_TEXT))
		{
			currentColor = WHITE_TEXT;
		}
		else
		{
			currentColor = RED_TEXT;
		}
	}
	public boolean updateAnimation()
	{
		if (translateBy <= numOfAnimationFrames)
		{
			if (translateBy % 5 == 0)
			{
				swapColor();
			}
			translateBy++;
			return false;
		}
		else
		{
			return true;
		}
	}
}
