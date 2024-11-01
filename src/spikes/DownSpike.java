package spikes;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.Madeline;
import mainApp.MainApp;

public class DownSpike extends Spike {

	private static final Color SPIKE_WHITE = new Color(255, 241, 232);
	private static final Color SPIKE_GREY = new Color(194, 195, 199);
	private static final Color SPIKE_BROWN = new Color(95, 87, 79);

	public DownSpike(int x, int y, int width, Madeline m) {
		super(x, y, width, 20, m);
	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY() - MainApp.PIXEL_DIM);
		for (int i = 0; i < getWidth() / (4*MainApp.PIXEL_DIM); i++) {
			g2.setColor(SPIKE_GREY);
			g2.fillRect(0, MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM);

			g2.setColor(SPIKE_WHITE);
			g2.fillRect(MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM);
			g2.fillRect(2*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);

			g2.setColor(SPIKE_BROWN);
			g2.fillRect(3*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM);

			g2.translate(4*MainApp.PIXEL_DIM, 0);
		}
	}
	
	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		// ensures that madeline is below the top of the object and above the bottom of
		// the object
		if (madelineY + getMadelineHeight() > getY() + 10 && madelineY < getY() + getHeight() - 10) {
			// if madeline is facing left and moving to the right
			if (facing < 0 && madelineX > getX() + getWidth() / 2) {
				if (madelineX - (getX() + getWidth()) <= 0)
					return true;
			}
			// if madeline is facing right and moving to the right
			else if (facing > 0 && madelineX > getX() + getWidth() / 2) {
				if (madelineX + getMadelineWidth() - (getX() + getWidth()) <= 0)
					return true;
			}
			// if madeline is facing left and moving to the left
			else if (facing < 0 && madelineX < getX() + getWidth() / 2) {
				if (madelineX - getX() >= 0)
					return true;
			}
			// if madeline is facing right and moving to the left
			else if (facing > 0 && madelineX + getMadelineWidth() < getX() + getWidth() / 2) {
				if (madelineX + getMadelineWidth() - getX() >= 0)
					return true;
			}
		}
		return false;
	}
}
