package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class MenuComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private LogoDisplay logoDisplay;
	
	/**
	 * Creates a MenuComponent JComponent
	 */
	public MenuComponent()
	{
		logoDisplay = new LogoDisplay();
	}
	
	/**
	 * Draws the MenuComponent onto graphics g
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		logoDisplay.drawOn(g2);
	}
}
