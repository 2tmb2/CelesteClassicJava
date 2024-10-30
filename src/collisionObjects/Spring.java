package collisionObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import mainApp.Madeline;

public class Spring extends CollisionObject {
	private static final Color SPRING_DARK_YELLOW = new Color(171, 82, 54);
	private static final Color SPRING_LIGHT_YELLOW = new Color(255, 163, 0);
	private static final Color SPRING_GREY = new Color(95, 87, 79);
	private Madeline m;
	private int originalY;
	private int drawFrame;
	Timer restoreTimer;

	public Spring(int x, int y, Madeline m) {
		super(x + 6, y + 18, 36, 30);
		this.m = m;
		originalY = getY();
		drawFrame = 1;
		restoreTimer = new Timer(400, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawFrame = 1;
				setY(originalY);
				setHeight(30);
			}
		});
		restoreTimer.setRepeats(false);
	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY());
		if (drawFrame == 1) {
			frame1(g2);
		} else {
			frame2(g2);
		}

	}

	private void frame1(Graphics2D g2) {
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, 6, 6);
		g2.fillRect(30, 0, 6, 6);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(6, 0, 24, 6);

		g2.setColor(SPRING_GREY);
		g2.fillRect(6, 6, 6, 6);
		g2.fillRect(24, 6, 6, 6);
		g2.fillRect(12, 12, 12, 6);
		g2.fillRect(6, 18, 6, 6);
		g2.fillRect(24, 18, 6, 6);
		g2.fillRect(12, 24, 12, 6);
	}

	private void frame2(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.setColor(SPRING_DARK_YELLOW);
		g2.fillRect(0, 0, 6, 6);
		g2.fillRect(30, 0, 6, 6);

		g2.setColor(SPRING_LIGHT_YELLOW);
		g2.fillRect(6, 0, 24, 6);
	}

	@Override
	public boolean isCollidingWall(int madelineX, int madelineY, int facing) {
		if (super.isCollidingWall(madelineX, madelineY, facing)) {
			bounce();
		}
		return false;
	}

	public boolean isCollidingCeiling(int madelineX, int madelineY) {
		if (super.isCollidingCeiling(madelineX, madelineY)) {
			bounce();
		}
		return false;
	}

	@Override
	public boolean isCollidingFloor(int madelineX, int madelineY) {
		if (super.isCollidingFloor(madelineX, madelineY)) {
			bounce();
		}
		return false;
	}

	private void bounce() {
		m.springBounce();
		drawFrame = 2;
		if (getY() == originalY) {
			super.setY(getY() + 24);
		}
		restoreTimer.start();
	}
}
