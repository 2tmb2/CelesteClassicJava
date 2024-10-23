package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class BreakableBlock extends CollisionObject{
	private static final Color OUTER_COLOR = new Color(255,241,232);
	private static final Color INNER_COLOR = new Color(41,172,253);
	private boolean exists;
	private Madeline m;
	public BreakableBlock(int x, int y, int width, int height, Madeline m)
	{
		super(x,y,width,height);
		exists = true;
		this.m = m;
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
		if (exists)
		{
			g2 = (Graphics2D) g2.create();
			g2.translate(getX(), getY());
			g2.setColor(INNER_COLOR);
			g2.fillRect(12 , 12, getWidth()-24, getHeight()-24);
			g2.setColor(OUTER_COLOR);
			//top border
			g2.fillRect(12, 6, getWidth() - 24, 6);
			//left border
			g2.fillRect(6 , 6 + 6, 6, getHeight() - 24);
			//bottom border
			g2.fillRect(12, 0 + getHeight() - 12, getWidth() - 24, 6);
			//right border
			g2.fillRect(getWidth()-12, 0 + 12, 6, getHeight()-24);
			
			//protruding parts (signifies that it can be broken)
			for (int i = 0; i < getWidth()/32; i++)
			{
				g2.fillRect(0, 18 + 24*i, 18, 12);
				g2.fillRect(18+24*i, 0, 12, 18);
				g2.fillRect(18+24*(getWidth()/32)-12, 18+24*i, 18, 12);
				g2.fillRect(18+24*i, 18+24*(getWidth()/32)-12, 12, 18);
			}
		}
	}
	public void breakBlock()
	{
		exists = false;
	}
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing)
	{
		if (exists)
		{
			if (super.isCollidingWall(madelineX, madelineY, facing))
			{
				if (m.getIsDashing())
				{
					breakBlock();
					m.breakBlock(getX() + getWidth()/2, getY() + getHeight()/2);
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY)
	{
		if (exists)
		{
			return super.isCollidingFloor(madelineX, madelineY);
		}
		return false;
	}
	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY)
	{
		if (exists)
		{
			return super.isCollidingCeiling(madelineX, madelineY);
		}
		return false;
	}
	@Override
	public void updateAnimation() {}
	
}
