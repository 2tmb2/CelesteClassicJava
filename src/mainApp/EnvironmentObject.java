package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class: EnvironmentObject Purpose: Used to represent an object that is part of
 * the environment, i.e. walls, ceilings, and floors
 */
public class EnvironmentObject extends CollisionObject {
	private static final Color OUTER_COLOR = new Color(255, 241, 232);
	private static final Color INNER_COLOR = new Color(41, 172, 253);
	private static final Color CORNER_COLOR = new Color(95, 87, 79);
	private ArrayList<Integer> positionModifierValues;
	private ArrayList<String> connectsAt;
	private int randx;
	private int randy;

	/**
	 * Creates an Environment Object with collision and randomized detail location using Array
	 * 
	 * @param x          representing the initial x position
	 * @param y          representing the initial y position
	 * @param width      representing the width
	 * @param height     representing the height
	 * @param connectsAt is an array of strings that signifies which sides the
	 *                   EnvironmentObject connects to other EnvironmentObjects.
	 *                   Options are "u", "d", "l", and "r". The array
	 *                   should be of length 4.
	 */
	public EnvironmentObject(int x, int y, int width, int height, String[] connectsAt) {
		super(x, y, width, height);
		positionModifierValues = new ArrayList<Integer>();
		// creates an arraylist of strings based on the inputed array
		this.connectsAt = new ArrayList<String>(Arrays.asList(connectsAt));
		// creates values for randomized detail locations
		randx = (int) (Math.random() * (width - 36)) + 18;
		randy = (int) (Math.random() * (height - 36)) + 18;
		int positionVariance;
		if (getWidth() > getHeight()) {
			positionVariance = getHeight();
		} else {
			positionVariance = getWidth();
		}
		for (int i = 0; i < 4; i++) {
			positionModifierValues.add((int) (Math.random() * (positionVariance - 64)) - (positionVariance - 64) / 2);
		}
	}
	
	/**
	 * Creates an Environment Object with collision and randomized detail location using ArrayList
	 * 
	 * @param x          representing the initial x position
	 * @param y          representing the initial y position
	 * @param width      representing the width
	 * @param height     representing the height
	 * @param connectsAt is an arrayList of strings that signifies which sides the
	 *                   EnvironmentObject connects to other EnvironmentObjects.
	 *                   Options are "u", "d", "l", and "r".
	 */
	public EnvironmentObject(int x, int y, int width, int height, ArrayList<String> connectsAt) {
		super(x, y, width, height);
		positionModifierValues = new ArrayList<Integer>();
		// creates an arraylist of strings based on the inputed array
		this.connectsAt = connectsAt;
		// creates values for randomized detail locations
		randx = (int) (Math.random() * (width - 36)) + 18;
		randy = (int) (Math.random() * (height - 36)) + 18;
		int positionVariance;
		if (getWidth() > getHeight()) {
			positionVariance = getHeight();
		} else {
			positionVariance = getWidth();
		}
		for (int i = 0; i < 4; i++) {
			positionModifierValues.add((int) (Math.random() * (positionVariance - 64)) - (positionVariance - 64) / 2);
		}
	}
	public ArrayList<String> getConnectsAt()
	{
		return connectsAt;
	}
	/**
	 * Draws the EnvironmentObject based on it's values of x, y, width, height, and
	 * randomized detail location
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(getX(), getY());

		int leftSideModifier = 0;
		int rightSideModifier = 0;
		int bottomModifier = 0;
		int topModifier = 0;
		if (connectsAt.contains("l")) {
			leftSideModifier += 12;
			rightSideModifier += 12;
		}
		if (connectsAt.contains("r")) {
			rightSideModifier += 12;
		}
		if (connectsAt.contains("u")) {
			topModifier += 12;
			bottomModifier += 12;
		}
		if (connectsAt.contains("d")) {
			bottomModifier += 12;
		}

		// draws the inner blue section of the EnvironmentObject
		g2.setColor(INNER_COLOR);
		g2.fillRect(6 - (int) (leftSideModifier * (18.0 / 12)), 6 - (int) (topModifier * (18.0 / 12)),
				getWidth() - 12 + (int) (rightSideModifier * (18.0 / 12)),
				getHeight() - 12 + (int) (bottomModifier * (18.0 / 12)));

		// draws the outer white borders of the EnvironmentObject (if they should exist)
		g2.setColor(OUTER_COLOR);
		// top border
		if (!connectsAt.contains("u"))
			g2.fillRect(6 - leftSideModifier, 0, getWidth() - 12 + rightSideModifier, 6);
		// left border
		if (!connectsAt.contains("l"))
			g2.fillRect(0, 0 + 6 - topModifier, 6, getHeight() - 12 + bottomModifier);
		// bottom border
		if (!connectsAt.contains("d"))
			g2.fillRect(6 - leftSideModifier, 0 + getHeight() - 6, getWidth() - 12 + rightSideModifier, 6);
		// right border
		if (!connectsAt.contains("r"))
			g2.fillRect(getWidth() - 6, 0 + 6 - topModifier, 6, getHeight() - 12 + bottomModifier);

		// draws the corners of the block (if they should exist)
		// top left
		if (!(connectsAt.contains("u") || connectsAt.contains("l")))
		{
			g2.fillRect(6, 6, 6, 6);
			g2.setColor(CORNER_COLOR);
			g2.fillRect(0, 0, 6, 6);
			g2.setColor(OUTER_COLOR);
		}
			
		// top right
		if (!(connectsAt.contains("u") || connectsAt.contains("r")))
		{
			g2.fillRect(getWidth() - 12, 6, 6, 6);
			g2.setColor(CORNER_COLOR);
			g2.fillRect(getWidth()-6, 0, 6, 6);
			g2.setColor(OUTER_COLOR);
		}
			
		// bottom left
		if (!(connectsAt.contains("d") || connectsAt.contains("l")))
		{
			g2.fillRect(6, getHeight() - 12, 6, 6);
			g2.setColor(CORNER_COLOR);
			g2.fillRect(0, getHeight()-6, 6, 6);
			g2.setColor(OUTER_COLOR);
		}
			
		// bottom right
		if (!(connectsAt.contains("d") || connectsAt.contains("r")))
		{
			g2.fillRect(getWidth() - 12, getHeight() - 12, 6, 6);
			g2.setColor(CORNER_COLOR);
			g2.fillRect(getWidth()-6, getHeight()-6, 6, 6);
			g2.setColor(OUTER_COLOR);
		}
		
		// add detail to the object
		if (getWidth() > 48 && getHeight() > 48) {
			largeObjectDetail(g2);
		} else {
			smallObjectDetail(g2);
		}
	}

	/**
	 * Draws the details for a large object onto g2
	 */
	private void largeObjectDetail(Graphics2D g2) {
		// creates multiple detail objects based on the size of the EnvironmentObject
		int divNum = 64;
		if (getWidth() >= 48 || getHeight() >= 48) {
			if (getWidth() > getHeight()) {
				g2.translate(0, getHeight() / 2);
				for (int i = 0; i < getWidth() / divNum - 1; i++) {
					int randAdjust = positionModifierValues.get(i % 4);
					g2.translate(getWidth() / (getWidth() / divNum), 0);
					g2.fillRect(-12, -12 + randAdjust, 12, 12);
					g2.fillRect(-6, 10 + randAdjust, 6, 6);
					g2.fillRect(10, -6 + randAdjust, 6, 6);
					g2.fillRect(10, 18 + randAdjust, 6, 6);
				}
			} else if (getWidth() <= getHeight()) {
				g2.translate(getWidth() / 2, 0);
				for (int i = 0; i < getHeight() / divNum - 1; i++) {
					int randAdjust = positionModifierValues.get(i % 4);
					g2.translate(0, getHeight() / (getHeight() / divNum));
					g2.fillRect(-12 + randAdjust, -12, 12, 12);
					g2.fillRect(-6 + randAdjust, 10, 6, 6);
					g2.fillRect(10 + randAdjust, -6, 6, 6);
					g2.fillRect(10 + randAdjust, 18, 6, 6);
				}
			}
		}
	}

	/**
	 * Draws the detail for a small object onto g2
	 */
	private void smallObjectDetail(Graphics2D g2) {
		g2.fillRect(randx, randy, 6, 6);
	}

	/**
	 * @return a string representation of the EnvironmentObject in the format Class,
	 *         x y width height, connectsAt.get(0) connectsAt.get(1)
	 *         connectsAt.get(2) connectsAt.get(3)
	 */
	@Override
	public String toString() {
		return super.toString() + ", " + connectsAt.get(0) + " " + connectsAt.get(1) + " " + connectsAt.get(2) + " "
				+ connectsAt.get(3);
	}
}
