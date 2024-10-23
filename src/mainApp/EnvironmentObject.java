package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

public class EnvironmentObject extends CollisionObject {
	private static final Color OUTER_COLOR = new Color(255,241,232);
	private static final Color INNER_COLOR = new Color(41,172,253);
	private ArrayList<Integer> positionModifierValues;
	private ArrayList<String> connectsAt;
	private int randx;
	private int randy;
	public EnvironmentObject(int x, int y, int width, int height, String[] connectsAt)
	{
		super(x, y, width, height);
		positionModifierValues = new ArrayList<Integer>();
		this.connectsAt = new ArrayList<String>(Arrays.asList(connectsAt));
		randx = (int)(Math.random()*(width-36))+18;
		randy = (int)(Math.random()*(height-36))+18;
		int positionVariance;
		if (getWidth() > getHeight())
		{
			positionVariance = getHeight();
		}
		else
		{
			positionVariance = getWidth();
		}
		for (int i = 0; i < 4; i++)
		{
			positionModifierValues.add((int)(Math.random()*(positionVariance-64))-(positionVariance-64)/2);
		}
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY());
		int leftSideModifier = 0;
		int rightSideModifier = 0;
		int bottomModifier = 0;
		int topModifier = 0;
		if (connectsAt.contains("left"))
		{
			leftSideModifier += 12;
			rightSideModifier += 12;
		}
		if (connectsAt.contains("right"))
		{
			rightSideModifier += 12;
		}
		if (connectsAt.contains("top"))
		{
			topModifier += 12;
			bottomModifier += 12;
		}
		if (connectsAt.contains("bottom"))
		{
			bottomModifier += 12;
		}
		
		g2.setColor(INNER_COLOR);
		g2.fillRect(6 - (int)(leftSideModifier*(18.0/12)), 6 - (int)(topModifier*(18.0/12)), getWidth()-12 + (int)(rightSideModifier*(18.0/12)), getHeight()-12 + (int)(bottomModifier*(18.0/12)));
		g2.setColor(OUTER_COLOR);
		//top border
		if (!connectsAt.contains("top"))
			g2.fillRect(6 - leftSideModifier, 0, getWidth() - 12 + rightSideModifier, 6);
		//left border
		if (!connectsAt.contains("left"))
			g2.fillRect(0 , 0 + 6 - topModifier, 6, getHeight() - 12 + bottomModifier);
		//bottom border
		if (!connectsAt.contains("bottom"))
			g2.fillRect(6 - leftSideModifier, 0 + getHeight() - 6, getWidth() - 12 + rightSideModifier, 6);
		//right border
		if (!connectsAt.contains("right"))
			g2.fillRect(getWidth()-6, 0 + 6 - topModifier, 6, getHeight()-12 + bottomModifier);
		
		//top left
		if (!(connectsAt.contains("top") || connectsAt.contains("left")))
			g2.fillRect(6, 6, 6, 6);
		// top right
		if (!(connectsAt.contains("top") || connectsAt.contains("right")))
			g2.fillRect(getWidth()-12, 6, 6, 6);
		// bottom left
		if (!(connectsAt.contains("bottom") || connectsAt.contains("left")))
			g2.fillRect(6, getHeight()-12, 6, 6);
		// bottom right
		if (!(connectsAt.contains("bottom") || connectsAt.contains("right")))
			g2.fillRect(getWidth()-12, getHeight()-12, 6, 6);
		if (getWidth() > 48 && getHeight() > 48)
		{
			largeObjectDetail(g2);
		}
		else
		{
			smallObjectDetail(g2);
		}
	}
	private void largeObjectDetail(Graphics2D g2)
	{
		// additional detail
		int divNum = 64;
		if (getWidth() >= 48 || getHeight() >= 48)
		{
			if (getWidth() > getHeight())
			{
				g2.translate(0,getHeight()/2);
				for (int i = 0; i < getWidth()/divNum - 1; i++)
				{
					int randAdjust = positionModifierValues.get(i % 4);
					g2.translate(getWidth()/(getWidth()/divNum), 0);
					g2.fillRect(-12, -12+randAdjust, 12, 12);
					g2.fillRect(-6, 10+randAdjust, 6, 6);
					g2.fillRect(10, -6+randAdjust, 6, 6);
					g2.fillRect(10, 18+randAdjust, 6, 6);
				}
			}
			else if (getWidth() <= getHeight())
			{
				g2.translate(getWidth()/2,0);
				for (int i = 0; i < getHeight()/divNum - 1; i++)
				{
					int randAdjust = positionModifierValues.get(i % 4);
					g2.translate(0, getHeight()/(getHeight()/divNum));
					g2.fillRect(-12+randAdjust, -12, 12, 12);
					g2.fillRect(-6+randAdjust, 10, 6, 6);
					g2.fillRect(10+randAdjust, -6, 6, 6);
					g2.fillRect(10+randAdjust, 18, 6, 6);
				}
			}
		}
	}
	private void smallObjectDetail(Graphics2D g2)
	{	
		// adding details
		g2.fillRect(randx, randy, 6, 6);
	}
	@Override
	public String toString()
	{
		return super.toString() + ", " + connectsAt.get(0) + " " + connectsAt.get(1) + " " + connectsAt.get(2) + " " + connectsAt.get(3);
	}
}
