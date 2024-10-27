package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

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
	private Strawberry strawberry;
	private boolean strawberryAlreadyCollected;
	private PointText pt;
	private String[] levelData;
	private int madX;
	private int madY;
	

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
	public LevelComponent(MainApp main, String levelNum, boolean strawberryAlreadyCollected) {
		this.main = main;
		this.strawberryAlreadyCollected = strawberryAlreadyCollected;

		collisionObjects = new ArrayList<CollisionObject>();
		// createLevelFromText();
		levelFromText(levelNum);
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
		if (m.dash(dir) && strawberry instanceof WingedStrawberry)
		{
			((WingedStrawberry) strawberry).flyAway();
		}
		//m.dash(dir);
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
		if (!strawberryAlreadyCollected)
		{
			if (isWinged)
			{
				strawberry = new WingedStrawberry(x, y, m);
			}
			else
			{
				strawberry = new Strawberry(x,y,m);
			}
			collisionObjects.add(strawberry);
		}
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
	
	public void levelFromText(String levelNum) {
		m = new Madeline(this);
		levelData = getLevelData(levelNum);
		createLevel();
		if (Integer.parseInt(levelNum) > 22) m.setTotalDashes(2);
		m.setCollisionObjects(collisionObjects);
		m.setXPos(madX);
		m.setYPos(madY);
	}
	
	public ArrayList<CollisionObject> createLevel() {
		collisionObjects = new ArrayList<CollisionObject>();
		m.setCollisionObjects(collisionObjects);
		String[] objectsData;
		for (int i = 0; i < 16; i++) {
			//if (levelData[i].equals("")) continue;
			objectsData = levelData[i].split(" ");
			for (int j = 0; j < objectsData.length; j++) {
				char firstChar = objectsData[j].charAt(0);
				char secondChar = objectsData[j].charAt(1);
				switch (firstChar) {
					case ('-'):
						break;
					case ('['):
						break;
					case ('>'):
						collisionObjects.add(new RightSpike(j*48, i*48, (secondChar - '0')*48, m));
						break;
					case ('<'):
						collisionObjects.add(new LeftSpike(j*48, i*48, (secondChar - '0')*48, m));
						break;
					case ('^'):
						collisionObjects.add(new UpSpike(j*48, i*48+12, (secondChar - '0')*48, m));
						break;
					case ('v'):
						collisionObjects.add(new DownSpike(j*48, i*48, (secondChar - '0')*48, m));
						break;
					case ('b'):
						if (!strawberryAlreadyCollected)
							collisionObjects.add(new BreakableBlock(j*48,i*48, 2*48, 2*48, m));
						break;
					case ('s'):
						if (!strawberryAlreadyCollected)
							strawberry = new Strawberry(j*48, i*48, m);
						break;
					case ('w'):
						if (!strawberryAlreadyCollected)
							strawberry = new WingedStrawberry(j*48, i*48, m);
						break;
					case ('m'):
						madX = j*48;
						madY = i*48;
						break;
					default:
						//if (firstChar - '0' >= 1 && (firstChar - '0') <= 9) {
							collisionObjects.add(new EnvironmentObject(j*48, i*48, (firstChar - '0')*48, 
									(secondChar - '0')*48, connectionDataAt(i, j)));
						//}
				}
				collisionObjects.add(new CollisionObject(-48, -48, 48, 20*48));
				collisionObjects.add(new CollisionObject(16*48, -48, 48, 20*48));
				collisionObjects.add(new LevelFinishZone(-48, -48, 20*48, 48, m));
				collisionObjects.add(new UpSpike(-48, 16*48, 20*48, m));
				m.setCanCollide(true);
			}
		}
		return new ArrayList<CollisionObject>();
	}
	
	public ArrayList<String> connectionDataAt(int vert, int hor) {
		char[] connData = new char[2];
		String[] dataAtX = levelData[17 + vert].split(" ");
		ArrayList<String> output = new ArrayList<String>();
		connData[0] = dataAtX[hor].charAt(0);
		connData[1] = dataAtX[hor].charAt(1);
		output.add(Character.toString(connData[0]));
		output.add(Character.toString(connData[1]));
		return output;
	}
	public String[] getLevelData(String levelNum) {
		String[] output = new String[33];
		try (Scanner s1 = new Scanner(new File("src/LevelData/level" + levelNum + ".txt"))){
			int index = 0;
			while (s1.hasNext()) {
				output[index] = s1.nextLine();
				index++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file for level " + levelNum + " could not be found");
		}
		return output;
	}

}
