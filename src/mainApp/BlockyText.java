package mainApp;

import java.awt.Graphics2D;

public class BlockyText {
	
	/**
	 * Draws a 0 onto g2
	 */
	public static void draw0(Graphics2D g2)
	{
		g2.fillRect(0, 0, 18, 6);
		g2.fillRect(0, 24, 18, 6);
		g2.fillRect(0, 0, 6, 24);
		g2.fillRect(12, 0, 6, 24);
	}
	
	/**
	 * Draws a 1 onto g2
	 */
	public static void draw1(Graphics2D g2)
	{
		g2.fillRect(0, 0, 6, 6);
		g2.fillRect(6, 0, 6, 6 * 4);
		g2.fillRect(0, 4 * 6, 6 * 3, 6);
	}
	
	/**
	 * Draws a 2 onto g2
	 */
	public static void draw2(Graphics2D g2)
	{
		g2.fillRect(0, 0, 18, 6);
		g2.fillRect(12, 6, 6, 6);
		g2.fillRect(0, 12, 18, 6);
		g2.fillRect(0, 18, 6, 6);
		g2.fillRect(0, 24, 18, 6);
	}
	
	/**
	 * Draws a 3 onto g2
	 */
	public static void draw3(Graphics2D g2)
	{
		g2.fillRect(0, 0, 18, 6);
		g2.fillRect(12, 0, 6, 30);
		g2.fillRect(6, 12, 12, 6);
		g2.fillRect(0, 24, 18, 6);
	}
	
	/**
	 * Draws a 4 onto g2
	 */
	public static void draw4(Graphics2D g2)
	{
		g2.fillRect(0,0,6,18);
		g2.fillRect(12, 0, 6, 30);
		g2.fillRect(0, 12, 18, 6);
	}
	
	/**
	 * Draws a 5 onto g2
	 */
	public static void draw5(Graphics2D g2)
	{
		g2.fillRect(0, 0, 18, 6);
		g2.fillRect(0, 6, 6, 6);
		g2.fillRect(0, 12, 18, 6);
		g2.fillRect(12, 18, 6, 6);
		g2.fillRect(0, 24, 18, 6);
	}
	
	/**
	 * Draws a 6 onto g2
	 */
	public static void draw6(Graphics2D g2)
	{
		g2.fillRect(0, 0, 6, 30);
		g2.fillRect(0, 12, 18, 6);
		g2.fillRect(0,24,18,6);
		g2.fillRect(12, 12, 6, 18);
	}
	
	/**
	 * Draws a 7 onto g2
	 */
	public static void draw7(Graphics2D g2)
	{
		g2.fillRect(0,0,18,6);
		g2.fillRect(12,0, 6, 30);
	}
	
	/**
	 * Draws an 8 onto g2
	 */
	public static void draw8(Graphics2D g2)
	{
		g2.fillRect(0, 0, 18, 6);
		g2.fillRect(0, 12, 18, 6);
		g2.fillRect(0, 24,18, 6);
		
		g2.fillRect(12, 0, 6, 30);
		g2.fillRect(0, 0, 6, 30);
	}
	
	/**
	 * Draws a 9 onto g2
	 */
	public static void draw9(Graphics2D g2)
	{
		g2.fillRect(0, 0, 18, 6);
		g2.fillRect(0, 12, 18, 6);
		g2.fillRect(12, 0, 6, 30);
		g2.fillRect(0, 0, 6, 18);
		
	}
}
