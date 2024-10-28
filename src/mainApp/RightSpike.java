package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class RightSpike extends Spike {

	private static final Color SPIKE_WHITE = new Color(255, 241, 232);
	private static final Color SPIKE_GREY = new Color(194, 195, 199);
	private static final Color SPIKE_BROWN = new Color(95, 87, 79);

	public RightSpike(int x, int y, int height, Madeline m) {
		super(x, y, 18, height, m);
	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX() - 6, getY() + 6);
		for (int i = 0; i < getHeight() / 24; i++) {
			g2.setColor(SPIKE_GREY);
			g2.fillRect(6, 0, 18, 18);

			g2.setColor(SPIKE_WHITE);
			g2.fillRect(12, 6, 24, 6);
			g2.fillRect(18, 12, 6, 6);

			g2.setColor(SPIKE_BROWN);
			g2.fillRect(0, -6, 18, 6);

			g2.translate(0, 24);
		}
	}

	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		// gives a grace period at the top of the spike
		return super.isCollidingFloor(madelineX, madelineY - 12);
	}

	@Override
	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		// gives a grace period at the bottom of the spike
		return super.isCollidingCeiling(madelineX, madelineY + 12);
	}
}
