package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * One particle of Madeline's Death animation
 */
public class DeathParticle {

	private int x;
    private int y;
    private double xvel;
    private double yvel;
    private static final Color PINK = new Color(255, 119, 168);
    private static final Color YELLOW = new Color(255, 204, 170);
    private Color currentColor;
    
    /**
     * Creates a single Death Particle
     * @param x the top-left x position of the particle
     * @param y the top-left y position of the particle
     * @param xVel the x velocity of the particle
     * @param yVel the y velocity of the particle
     */
    public DeathParticle(int x, int y, double xVel, double yVel)
    {
    	this.x = x;
    	this.y = y;
    	this.xvel = xVel;
    	this.yvel = yVel;
    	currentColor = YELLOW;
    }
    
    /**
     * Draws the Death Particle onto g2
     */
    public void drawOn(Graphics2D g2)
    {
    	this.x += (int)xvel/1.75;
    	this.y += (int)yvel/1.75;
    	g2 = (Graphics2D)g2.create();
    	if (currentColor == YELLOW)
    	{
    		currentColor = PINK;
    	}
    	else
    	{
    		currentColor = YELLOW;
    	}
    	g2.setColor(currentColor);
    	g2.translate(Madeline.roundPos(x), Madeline.roundPos(y));
    	g2.fillRect(0, 0, Constants.PIXEL_DIM*3, Constants.PIXEL_DIM*3);
    }
}
