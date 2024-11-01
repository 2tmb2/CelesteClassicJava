package spikes;

import java.awt.Color;
import java.awt.Graphics2D;

import mainApp.Madeline;
import mainApp.MainApp;

public class LeftSpike extends Spike {

	private static final Color SPIKE_WHITE = new Color(255, 241, 232);
	private static final Color SPIKE_GREY = new Color(194, 195, 199);
	private static final Color SPIKE_BROWN = new Color(95, 87, 79);

	public LeftSpike(int x, int y, int height, Madeline m) {
		super(x, y, 3*MainApp.PIXEL_DIM, height, m);
	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX() - 3*MainApp.PIXEL_DIM, getY() + MainApp.PIXEL_DIM);
		for (int i = 0; i < getHeight() / (4*MainApp.PIXEL_DIM); i++) {
			g2.setColor(SPIKE_GREY);
			g2.fillRect(3*MainApp.PIXEL_DIM, 0, 3*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM);

			g2.setColor(SPIKE_WHITE);
			g2.fillRect(MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, 4*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
			g2.fillRect(3*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);

			g2.setColor(SPIKE_BROWN);
			g2.fillRect(4*MainApp.PIXEL_DIM, 3*MainApp.PIXEL_DIM, 2*MainApp.PIXEL_DIM, MainApp.PIXEL_DIM);
			g2.translate(0, 4*MainApp.PIXEL_DIM);
		}
	}

	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		// gives a grace period at the top of the spike
		return super.isCollidingFloor(madelineX, madelineY - 2*MainApp.PIXEL_DIM);
	}

	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		// gives a grace period at the bottom of the spike
		return super.isCollidingCeiling(madelineX, madelineY + 2*MainApp.PIXEL_DIM);
	}

}
