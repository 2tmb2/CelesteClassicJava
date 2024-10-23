package mainApp;

import java.awt.Graphics2D;

public class InvisibleWall extends CollisionObject{
	public InvisibleWall(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
	@Override
	public void drawOn(Graphics2D g2)
	{
	}
	@Override
	public void updateAnimation() {}
}
