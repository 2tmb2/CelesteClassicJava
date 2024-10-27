package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;

public class RightSpike extends Spike {

	private static final Color SPIKE_WHITE = new Color(255, 241, 232);
	private static final Color SPIKE_GREY = new Color(194, 195, 199);

	public RightSpike(int x, int y, int height, Madeline m) {
		super(x, y, 30, height, m);
	}

	@Override
	public void drawOn(Graphics2D g2) {

	}

}
