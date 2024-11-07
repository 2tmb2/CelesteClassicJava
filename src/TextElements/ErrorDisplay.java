package TextElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import mainApp.MainApp;

public class ErrorDisplay extends JComponent {

	private static final long serialVersionUID = 1L;
	private String displayMessage;
	
	public ErrorDisplay(String message)
	{
		displayMessage = "";
		if (message.length() <= 5*MainApp.PIXEL_DIM)
		{
			displayMessage = message;
		}
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
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(28, (768/2) - (displayMessage.length()*36)/29/2);
		g2.setColor(Color.RED);
		BlockyText.drawText(g2, displayMessage);
	}
}
