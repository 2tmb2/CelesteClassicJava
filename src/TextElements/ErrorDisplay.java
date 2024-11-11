package TextElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import mainApp.MainApp;

public class ErrorDisplay extends JComponent {

	private static final long serialVersionUID = 1L;
	private String displayMessage;
	
	/**
	 * Creates an error display JComponent
	 * @param message the error message to display
	 */
	public ErrorDisplay(String message)
	{
		displayMessage = "";
		if (message.length() <= 5*MainApp.PIXEL_DIM)
		{
			displayMessage = message;
		}
		// separates the message into parts if it is longer than the length of the screen
		else
		{
			int linesCount = 1;
			for (int i = 0; i < message.length(); i++)
			{
				if ((message + " ").indexOf(" ", i) > (5*MainApp.PIXEL_DIM-1)*linesCount)
				{
					displayMessage += "&";
					linesCount++;
				}
				displayMessage += message.substring(i,i+1);
			}
		}
	}
	
	/**
	 * Paints the error display onto g
	 * @param g representing the Graphics object to paint onto
	 */
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(28, (768/2) - (displayMessage.length()*36)/29/2);
		g2.setColor(Color.RED);
		BlockyText.drawText(g2, displayMessage);
	}
}
