package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Class: LevelComponent
 */
public class LevelComponent extends JComponent {

	private static final long serialVersionUID = 6158157661752848867L;
	private Madeline m;
	private MainApp main;
	private ArrayList<CollisionObject> collisionObjects;
	private int madelineSpawnX;
	private int madelineSpawnY;
	private int numberOfDashesTotal;
	private CollisionObject strawberry;
	private boolean strawberryAlreadyCollected;
	private PointText pt;

	/**
	 * Creates a LevelComponent Object
	 * 
	 * @param main                       representing the MainApp
	 * @param level                      representing the current level as an
	 *                                   integer
	 * @param strawberryAlreadyCollected is a boolean value. It is true if the
	 *                                   strawberry in the level has already been
	 *                                   collected and false otherwise.
	 */
	public LevelComponent(MainApp main, int level, boolean strawberryAlreadyCollected) {
		this.main = main;
		this.strawberryAlreadyCollected = strawberryAlreadyCollected;

		collisionObjects = new ArrayList<CollisionObject>();
		// createLevelFromText();
		createLevel(level);
	}

	/**
	 * Draws Madeline, the PointText (if it exists), and every CollisionObject onto
	 * g
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) (g);
		for (CollisionObject c : collisionObjects) {
			c.drawOn(g2);
		}
		m.drawOn(g2);
		if (pt != null) {
			pt.drawOn(g2);
		}

	}

	/**
	 * Resets the level
	 */
	public void resetLevel() {
		main.resetLevel();
	}

	/**
	 * Moves to the next level
	 */
	public void nextLevel() {
		main.nextLevel();
	}

	/**
	 * Increases Madeline's X velocity
	 */
	public void moveMadelineRight() {
		m.increaseX();
	}

	/**
	 * Decreases Madeline's X velocity
	 */
	public void moveMadelineLeft() {
		m.decreaseX();
	}

	/**
	 * Updates Madeline's vertical position based on her current y velocity
	 */
	public void moveMadelineVertically() {
		m.setVerticalPosition();
	}

	/**
	 * Updates Madeline's horizontal position based on her current x velocity
	 */
	public void moveMadelineHorizontally() {
		m.setHorizontalPosition();
	}

	/**
	 * Makes Madeline jump (if she is able)
	 */
	public void madelineJump() {
		m.jump();
	}

	/**
	 * Makes Madeline dash (if she is able)
	 * 
	 * @param dir
	 */
	public void dash(String dir) {
		m.dash(dir);
	}

	/**
	 * Checks if Madeline is able to dash
	 */
	public void checkIfDashing() {
		m.checkIfDashing();
	}

	/**
	 * Sets Madeline's jumpPressed field
	 * 
	 * @param b: true if jump has been pressed, false if jump has not been pressed
	 */
	public void setMadelineJumpPressed(boolean b) {
		m.setJumpPressed(b);
	}

	/**
	 * Purpose: Adds a new Strawberry to the current level Usage: used to spawn a
	 * Strawberry following a BreakableBlock being destroyed
	 * 
	 * @param x representing the center of the strawberry
	 * @param y representing the center of the strawberry
	 */
	public void addNewStrawberry(int x, int y, boolean isWinged) {
		if (isWinged)
		{
			strawberry = new Strawberry(x, y, 36, 48, m);
		}
		else
		{
			strawberry = new WingedStrawberry(x,y,36,48,m);
		}
		
		collisionObjects.add(strawberry);
	}

	/**
	 * Collects the strawberry currently present in the level and spawns the point
	 * text associated with that strawberry
	 */
	public void collectStrawberry() {
		pt = new PointText(strawberry.getX(), strawberry.getY());
		main.collectStrawberry();
		collisionObjects.remove(strawberry);
		strawberry = null;
	}

	/**
	 * Update the animation state of all CollisionObjects and the PointText (if it
	 * exists)
	 */
	public void updateAnimations() {
		m.updateAnimations();
		if (pt != null) {
			if (pt.updateAnimation()) {
				pt = null;
			}
		}
	}

	/**
	 * Creates a level
	 * 
	 * @param level integer representing the level to create
	 */
	public void createLevel(int level) {
		// the following is temporary while a system to read levels from text files is still being implemented
		if (level == 1) {
			madelineSpawnX = 60;
			madelineSpawnY = 768 - 144 - 42;
			numberOfDashesTotal = 1;
			m = new Madeline(madelineSpawnX, madelineSpawnY, numberOfDashesTotal, collisionObjects, this);
			CollisionObject c1 = new EnvironmentObject(0, 768 - 144, 240, 144, new String[] { "", "", "", "" });
			collisionObjects.add(c1);
			CollisionObject c2 = new EnvironmentObject(150, 768 - 144 - 48, 240 - 150, 48,
					new String[] { "bottom", "", "", "" });
			collisionObjects.add(c2);
			CollisionObject c4 = new EnvironmentObject(240 + 96, 768 - 246, 96, 96, new String[] { "", "", "", "" });
			collisionObjects.add(c4);
			CollisionObject c23 = new EnvironmentObject(240 + 96, 768 - 246 + 96, 96, 246 - 96,
					new String[] { "", "", "", "" });
			collisionObjects.add(c23);
			CollisionObject c8 = new EnvironmentObject(768 - 144, 768 - 336, 144, 48, new String[] { "", "", "", "" });
			collisionObjects.add(c8);
			CollisionObject c10 = new EnvironmentObject(768 - 96, 768 - 336 - 144 - 96, 96, 96,
					new String[] { "", "", "", "" });
			collisionObjects.add(c10);
			CollisionObject c11 = new EnvironmentObject(768 - 96, 768 - 336 - 144 - 96 - 48, 48, 48,
					new String[] { "", "", "", "" });
			collisionObjects.add(c11);
			CollisionObject c12 = new EnvironmentObject(768 - 96 - 48, 768 - 336 - 144 - 96 - 48, 48, 48,
					new String[] { "", "", "", "" });
			collisionObjects.add(c12);
			CollisionObject c13 = new EnvironmentObject(768 - 96 - 96, 768 - 336 - 144 - 96, 96, 48,
					new String[] { "right", "", "", "" });
			collisionObjects.add(c13);
			CollisionObject c14 = new EnvironmentObject(768 - 48, 0, 48, 192, new String[] { "bottom", "", "", "" });
			collisionObjects.add(c14);
			CollisionObject c15 = new EnvironmentObject(0, 0, 48, 768 - 288, new String[] { "", "", "", "" });
			collisionObjects.add(c15);
			CollisionObject c16 = new EnvironmentObject(48, 768 - 288 - 96 - 96, 48, 96,
					new String[] { "left", "", "", "" });
			collisionObjects.add(c16);
			CollisionObject c17 = new EnvironmentObject(48 + 48, 768 - 288 - 96 - 96, 144, 48,
					new String[] { "", "", "", "" });
			collisionObjects.add(c17);
			CollisionObject c19 = new EnvironmentObject(48, 0, 96, 96 + 96, new String[] { "left", "", "", "" });
			collisionObjects.add(c19);
			CollisionObject c20 = new EnvironmentObject(48 + 96, 0, 48 * 4, 48 * 2,
					new String[] { "left", "", "", "" });
			collisionObjects.add(c20);
			CollisionObject c21 = new EnvironmentObject(48 + 96, 96, 48, 48, new String[] { "", "", "", "" });
			collisionObjects.add(c21);
			CollisionObject c22 = new CollisionObject(-100, -100, 100, 868);
			collisionObjects.add(c22);

			// These CollisionObjects were moved below the previous ones to ensure they draw
			// on top of all other objects
			CollisionObject c18 = new EnvironmentObject(48 + 96 + 96 + 96, 0, 48 * 6, 48,
					new String[] { "left", "", "", "" });
			collisionObjects.add(c18);
			CollisionObject c9 = new EnvironmentObject(768 - 48, 768 - 336 - 144, 48, 144,
					new String[] { "bottom", "top", "", "" });
			collisionObjects.add(c9);
			CollisionObject c7 = new EnvironmentObject(768 - 96, 768 - 288, 96, 288,
					new String[] { "top", "", "", "" });
			collisionObjects.add(c7);
			CollisionObject c6 = new EnvironmentObject(240 + 96 + 96 + 96, 768 - 144, 144, 144,
					new String[] { "right", "", "", "" });
			collisionObjects.add(c6);
			CollisionObject c5 = new EnvironmentObject(240 + 96 + 96, 768 - 96, 96, 96,
					new String[] { "left", "right", "", "" });
			collisionObjects.add(c5);
			CollisionObject c3 = new EnvironmentObject(240, 768 - 48, 96, 48, new String[] { "left", "right", "", "" });
			collisionObjects.add(c3);

			// adding the spikes for level 1
			CollisionObject s1 = new HorizontalSpike(150 + (240 - 150), 768 - 36 - 48, 96, 28, m);
			collisionObjects.add(s1);
			CollisionObject s2 = new HorizontalSpike(240 + 96 + 96, 768 - 96 - 36, 96, 28, m);
			collisionObjects.add(s2);
			CollisionObject s3 = new HorizontalSpike(240 + 96 + 96 + 96, 768 - 144 - 36, 144, 28, m);
			collisionObjects.add(s3);

			addNewStrawberry(300, 300, true);
			// adding the BreakableBlock for level 1 (if the strawberry has not already been
			// collected)
			if (!strawberryAlreadyCollected) {
				CollisionObject b1 = new BreakableBlock(48, 768 - 288 - 96 - 96 - 96, 96, 96, m);
				collisionObjects.add(b1);
			}

			// adding the level finish zone
			CollisionObject lvlFinish = new LevelFinishZone(0, -10, 786, 10, m);
			collisionObjects.add(lvlFinish);
		} else {
			// for any level other than 1, spawn Madeline at (50, 50)
			madelineSpawnX = 50;
			madelineSpawnY = 50;
			numberOfDashesTotal = 1;
			m = new Madeline(madelineSpawnX, madelineSpawnY, numberOfDashesTotal, collisionObjects, this);
		}

	}

	/**
	 * The following methods are temporary and are used exclusively for testing
	 * reading information from strings/text files.
	 */

	public void printLevel() {
		for (CollisionObject c : collisionObjects) {
			System.out.println(c.toString());
		}
	}

	public void createLevelFromText() {
		String t = "mainApp.EnvironmentObject, 0 624 240 144 ,    \r\n"
				+ "mainApp.EnvironmentObject, 150 576 90 48 , bottom   \r\n"
				+ "mainApp.EnvironmentObject, 336 522 96 96 ,    \r\n"
				+ "mainApp.EnvironmentObject, 336 618 96 150 ,    \r\n"
				+ "mainApp.EnvironmentObject, 624 432 144 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 672 192 96 96 ,    \r\n"
				+ "mainApp.EnvironmentObject, 672 144 48 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 624 144 48 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 576 192 96 48 , right   \r\n"
				+ "mainApp.EnvironmentObject, 720 0 48 192 , bottom   \r\n"
				+ "mainApp.EnvironmentObject, 0 0 48 480 ,    \r\n"
				+ "mainApp.EnvironmentObject, 48 288 48 96 , left   \r\n"
				+ "mainApp.EnvironmentObject, 96 288 144 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 48 0 96 192 , left   \r\n"
				+ "mainApp.EnvironmentObject, 144 0 192 96 , left   \r\n"
				+ "mainApp.EnvironmentObject, 144 96 48 48 ,    \r\n" + "mainApp.InvisibleWall, -100 -100 100 868 \r\n"
				+ "mainApp.EnvironmentObject, 336 0 288 48 , left   \r\n"
				+ "mainApp.EnvironmentObject, 720 288 48 144 , bottom top  \r\n"
				+ "mainApp.EnvironmentObject, 672 480 96 288 , top   \r\n"
				+ "mainApp.EnvironmentObject, 528 624 144 144 , right   \r\n"
				+ "mainApp.EnvironmentObject, 432 672 96 96 , left right  \r\n"
				+ "mainApp.EnvironmentObject, 240 720 96 48 , left right  \r\n"
				+ "mainApp.Spike, 240, 684, 4, true, false\r\n" + "mainApp.Spike, 432, 636, 4, true, false\r\n"
				+ "mainApp.Spike, 528, 588, 6, true, false\r\n" + "mainApp.BreakableBlock, 48 192 96 96 \r\n"
				+ "mainApp.LevelFinishZone, 0 -10 786 10 ";

		String[] tList = t.split("\n");
		for (int i = 0; i < tList.length; i++) {
			String[] options = tList[i].split(",");
			String clsName = options[0];
			String[] positions = options[1].split(" ");
			Object collisionObject;
			try {
				Class<?> cls = Class.forName(clsName);
				if (options.length == 6) {
					Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, boolean.class,
							boolean.class);
					collisionObject = constructor.newInstance(Integer.parseInt(options[1].substring(1)),
							Integer.parseInt(options[2].substring(1)), Integer.parseInt(options[3].substring(1)),
							Boolean.parseBoolean(options[4].substring(1)),
							Boolean.parseBoolean(options[5].substring(1)));
				} else if (options.length == 3) {
					String[] touchingDirections = options[2].split(" ");
					Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, int.class,
							String[].class);
					collisionObject = constructor.newInstance(Integer.parseInt(positions[1]),
							Integer.parseInt(positions[2]), Integer.parseInt(positions[3]),
							Integer.parseInt(positions[4]), touchingDirections);
				} else {
					Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, int.class);
					collisionObject = constructor.newInstance(Integer.parseInt(positions[1]),
							Integer.parseInt(positions[2]), Integer.parseInt(positions[3]),
							Integer.parseInt(positions[4]));
				}
				CollisionObject c = (CollisionObject) collisionObject;
				collisionObjects.add(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
