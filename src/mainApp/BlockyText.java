package mainApp;

import java.awt.Graphics2D;

public class BlockyText {
	
	/**
	 * Draws text onto the screen
	 * @param g2 the Graphics2D object to draw on
	 * @param text the text to draw
	 */
	public static void drawText(Graphics2D g2, String text)
	{
		int totalTranslatedX = 4*MainApp.PIXEL_DIM;
		g2 = (Graphics2D)g2.create();
		for (int i = 0; i < text.length(); i++)
		{
			switch (text.substring(i,i+1).toLowerCase()) {
				case "&":
					g2.translate(-totalTranslatedX, 6*MainApp.PIXEL_DIM);
					totalTranslatedX = 0;
					break;
				case "0":
					draw0(g2);
					break;
				case "1":
					draw1(g2);
					break;
				case "2":
					draw2(g2);
					break;
				case "3":
					draw3(g2);
					break;
				case "4":
					draw4(g2);
					break;
				case "5":
					draw5(g2);
					break;
				case "6":
					draw6(g2);
					break;
				case "7":
					draw7(g2);
					break;
				case "8":
					draw8(g2);
					break;
				case "9":
					draw9(g2);
					break;
				case "a":
					drawA(g2);
					break;
				case "b":
					drawB(g2);
					break;
				case "c":
					drawC(g2);
					break;
				case "d":
					drawD(g2);
					break;
				case "e":
					drawE(g2);
					break;
				case "f":
					drawF(g2);
					break;
				case "g":
					drawG(g2);
					break;
				case "h":
					drawH(g2);
					break;
				case "i":
					drawI(g2);
					break;
				case "j":
					drawJ(g2);
					break;
				case "k":
					drawK(g2);
					break;
				case "l":
					drawL(g2);
					break;
				case "m":
					drawM(g2);
					break;
				case "n":
					drawN(g2);
					break;
				case "o":
					drawO(g2);
					break;
				case "p":
					drawP(g2);
					break;
				case "q":
					drawQ(g2);
					break;
				case "r":
					drawR(g2);
					break;
				case "s":
					drawS(g2);
					break;
				case "t":
					drawT(g2);
					break;
				case "u":
					drawU(g2);
					break;
				case "v":
					drawV(g2);
					break;
				case "w":
					drawW(g2);
					break;
				case "x":
					drawX(g2);
					break;
				case "y":
					drawY(g2);
					break;
				case "z":
					drawZ(g2);
					break;
				case ".":
					drawPeriod(g2);
					break;
				case ":":
					drawColon(g2);
					break;
				case " ":
					break;
				default:
					drawEmpty(g2);
					break;
			}
			totalTranslatedX += 4*MainApp.PIXEL_DIM;
			g2.translate(4*MainApp.PIXEL_DIM, 0);
		}
	}
	
	/**
	 * Draws a rectangle onto g2
	 */
	private static void drawEmpty(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
	}
	/**
	 * Draws a 0 onto g2
	 */
	private static void draw0(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 1 onto g2
	 */
	private static void draw1(Graphics2D g2)
	{
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM * 4);
		g2.fillRect(0, 4 * MainApp.PIXEL_DIM, MainApp.PIXEL_DIM * 3, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 2 onto g2
	 */
	private static void draw2(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 3 onto g2
	 */
	private static void draw3(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 4 onto g2
	 */
	private static void draw4(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 5 onto g2
	 */
	private static void draw5(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 6 onto g2
	 */
	private static void draw6(Graphics2D g2)
	{
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 7 onto g2
	 */
	private static void draw7(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an 8 onto g2
	 */
	private static void draw8(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		
		g2.fillRect(2*MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a 9 onto g2
	 */
	private static void draw9(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM);
		
	}
	
	/**
	 * Draws an a onto g2
	 */
	private static void drawA(Graphics2D g2)
	{
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a b onto g2
	 */
	private static void drawB(Graphics2D g2)
	{
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 4*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0, 2*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a c onto g2
	 */
	private static void drawC(Graphics2D g2)
	{
		g2.fillRect(MainApp.PIXEL_DIM, 0, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
		g2.fillRect(0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a d onto g2
	 */
	private static void drawD(Graphics2D g2)
	{
		g2.fillRect(0,0,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an e onto g2
	 */
	private static void drawE(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an f onto g2
	 */
	private static void drawF(Graphics2D g2)
	{
		g2.fillRect(0, 0, 3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a G onto g2
	 */
	private static void drawG(Graphics2D g2)
	{
		g2.fillRect(MainApp.PIXEL_DIM,0,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an H onto g2
	 */
	private static void drawH(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an I onto g2
	 */
	private static void drawI(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a J onto g2
	 */
	private static void drawJ(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a K onto g2
	 */
	private static void drawK(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an L onto g2
	 */
	private static void drawL(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an M onto g2
	 */
	private static void drawM(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an N onto g2
	 */
	private static void drawN(Graphics2D g2)
	{
		g2.fillRect(0,0,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an O onto g2
	 */
	private static void drawO(Graphics2D g2)
	{
		g2.fillRect(MainApp.PIXEL_DIM,0,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a P onto g2
	 */
	private static void drawP(Graphics2D g2)
	{
		g2.fillRect(0, 0, MainApp.PIXEL_DIM, 5*MainApp.PIXEL_DIM);
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a Q onto g2
	 */
	private static void drawQ(Graphics2D g2)
	{
		g2.fillRect(MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
		g2.fillRect(0,3*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an R onto g2
	 */
	private static void drawR(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an S onto g2
	 */
	private static void drawS(Graphics2D g2)
	{
		g2.fillRect(MainApp.PIXEL_DIM,0,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a T onto g2
	 */
	private static void drawT(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a U onto g2
	 */
	private static void drawU(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a V onto g2
	 */
	private static void drawV(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM);
		g2.fillRect(0,3*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,4*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a W onto g2
	 */
	private static void drawW(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,3*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws an X onto g2
	 */
	private static void drawX(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a Y onto g2
	 */
	private static void drawY(Graphics2D g2)
	{
		g2.fillRect(0,0,MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,0,MainApp.PIXEL_DIM,5*MainApp.PIXEL_DIM);
		g2.fillRect(0,2*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a Z onto g2
	 */
	private static void drawZ(Graphics2D g2)
	{
		g2.fillRect(0,0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(MainApp.PIXEL_DIM,2*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,4*MainApp.PIXEL_DIM,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a period onto g2
	 */
	private static void drawPeriod(Graphics2D g2)
	{
		g2.fillRect(0,4*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
	
	/**
	 * Draws a colon onto g2
	 */
	private static void drawColon(Graphics2D g2)
	{
		g2.fillRect(0,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
		g2.fillRect(0,3*MainApp.PIXEL_DIM,MainApp.PIXEL_DIM,MainApp.PIXEL_DIM);
	}
}
