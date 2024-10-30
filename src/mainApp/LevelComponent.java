package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

import collectables.Balloon;
import collectables.Strawberry;
import collectables.WingedStrawberry;
import collisionObjects.BreakableBlock;
import collisionObjects.CollisionObject;
import collisionObjects.DissappearingBlock;
import collisionObjects.DissappearingSpring;
import collisionObjects.EnvironmentObject;
import collisionObjects.LevelFinishZone;
import collisionObjects.Spring;
import spikes.DownSpike;
import spikes.LeftSpike;
import spikes.RightSpike;
import spikes.UpSpike;

/**
 * Class: LevelComponent
 */
public class LevelComponent extends JComponent {

	private static final long serialVersionUID = 6158157661752848867L;
	private Madeline m;
	private MainApp main;
	private ArrayList<CollisionObject> collisionObjects;
	private Strawberry strawberry;
	private boolean strawberryAlreadyCollected;
	private PointText pt;
	private LevelDisplayText ldt;
	private String[] levelData;
	private int madX;
	private int madY;
	private ArrayList<CollisionObject> hasConnectsAt;
	private ArrayList<CollisionObject> noConnectsAt;
	private ArrayList<CollisionObject> otherObject;

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
		hasConnectsAt = new ArrayList<CollisionObject>();
		noConnectsAt = new ArrayList<CollisionObject>();
		otherObject = new ArrayList<CollisionObject>();
		this.strawberryAlreadyCollected = strawberryAlreadyCollected;
		collisionObjects = new ArrayList<CollisionObject>();
		levelFromText(levelNum);
	}

	/**
	 * Draws Madeline, the PointText (if it exists), and every CollisionObject onto
	 * g
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) (g);

		for (CollisionObject c : noConnectsAt) {
			c.drawOn(g2);
		}
		for (CollisionObject c : hasConnectsAt) {
			c.drawOn(g2);
		}
		for (CollisionObject c : otherObject) {
			c.drawOn(g2);
		}
		m.drawOn(g2);
		if (pt != null) {
			pt.drawOn(g2);
		}
		if (strawberry != null) {
			strawberry.drawOn(g2);
		}
		if (ldt != null)
		{
			ldt.drawOn(g2);
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

	public void stopAllTimers() {
		m.stopAllTimers();
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

	public void addLevelDisplay(String text)
	{
		ldt = new LevelDisplayText(text);
	}
	
	public void removeLevelDisplay()
	{
		ldt = null;
	}
	
	public void resetMadelineVelocity()
	{
		m.resetVelocity();
	}
	
	/**
	 * Updates Madeline's position based on her current velocity
	 * 
	 * @param hasMoved whether a key has been pressed to move Madeline
	 */
	public void moveMadeline(boolean hasMoved) {
		m.setPosition(hasMoved);
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
		if (m.dash(dir) && strawberry instanceof WingedStrawberry) {
			((WingedStrawberry) strawberry).flyAway();
		}
		// m.dash(dir);
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
		if (!strawberryAlreadyCollected) {
			if (isWinged) {
				strawberry = new WingedStrawberry(x, y, m);
			} else {
				strawberry = new Strawberry(x, y, m);
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

	/**
	 * Head method for loading a level from a file. creates a new empty Madeline for
	 * the next level (to pass to objects in the level). Sets proper dashNum based
	 * on level number Sets position of Madeline based on level data and gives her
	 * the processed list of collision objects
	 * 
	 * @param levelNum the String representing the integer level number
	 */
	public void levelFromText(String levelNum) {
		m = new Madeline(this);
		levelData = getLevelData(levelNum);
		createLevel();
		if (Integer.parseInt(levelNum) > 22)
			m.setTotalDashes(2);
		m.setCollisionObjects(collisionObjects);
		m.setXPos(madX);
		m.setYPos(madY);
	}

	/**
	 * Parses the String array of level data for the information of obstacles and
	 * objects at each point '-' and '[' are characters representing empty data.
	 * There are two symbols for human readability '>', '<', '<^', and 'v' are all
	 * spikes with corresponding direction 'p' is spring 'd' is disappearing block
	 * 'r' is balloon 'k' is key 'c' is chest 'b' is breakable block (always 2x2)
	 * 's' is strawberry 'w' is winged strawberry 'm' is madeline's starting
	 * position
	 */
	public void createLevel() {
		try {
			collisionObjects = new ArrayList<CollisionObject>();
			m.setCollisionObjects(collisionObjects);
			if (levelData == null) {
				return;
			}
			String[] objectsData;
			for (int i = 0; i < 16; i++) {
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
						RightSpike r = new RightSpike(j * 48, i * 48, (secondChar - '0') * 48, m);
						otherObject.add(r);
						collisionObjects.add(r);
						break;
					case ('<'):
						LeftSpike l = new LeftSpike(j * 48 + 30, i * 48 - 6, (secondChar - '0') * 48, m);
						collisionObjects.add(l);
						otherObject.add(l);
						break;
					case ('^'):
						UpSpike u = new UpSpike(j * 48, i * 48 + 30, (secondChar - '0') * 48, m);
						collisionObjects.add(u);
						otherObject.add(u);
						break;
					case ('v'):
						DownSpike d = new DownSpike(j * 48, i * 48 + 0, (secondChar - '0') * 48, m);
						collisionObjects.add(d);
						otherObject.add(d);
						break;
					case ('p'):
						Spring s = new Spring(j * 48, i * 48, m);
						collisionObjects.add(s);
						otherObject.add(s);
						break;
					case ('d'):
						if (secondChar != 'd')
						{
							DissappearingSpring dSpring = new DissappearingSpring(j*48,i*48, m);
							collisionObjects.add(dSpring);
							otherObject.add(dSpring);
						}
						else
						{
							DissappearingBlock dBlock = new DissappearingBlock(j*48,i*48);
							collisionObjects.add(dBlock);
							otherObject.add(dBlock);
						}
						break;
					case ('r'):
						Balloon bal = new Balloon(j*48,i*48, m);
						otherObject.add(bal);
						collisionObjects.add(bal);
						break;
					case ('k'):
						// add key
						break;
					case ('c'):
						// add chest
						break;
					case ('b'):
						if (!strawberryAlreadyCollected) {
							BreakableBlock b = new BreakableBlock(j * 48, i * 48, 2 * 48, 2 * 48, m);
							collisionObjects.add(b);
							otherObject.add(b);
						}
						break;
					case ('s'):
						if (!strawberryAlreadyCollected) {
							strawberry = new Strawberry(j * 48 + 24, i * 48 + 18, m);
							collisionObjects.add(strawberry);
						}
						break;
					case ('w'):
						if (!strawberryAlreadyCollected) {
							strawberry = new WingedStrawberry(j * 48 + 24, i * 48 + 18, m);
							collisionObjects.add(strawberry);
						}
						break;
					case ('m'):
						madX = j * 48;
						madY = i * 48;
						break;
					default:
						if (firstChar - '0' < 0 || firstChar - '0' > 10) {
							throw new ImproperlyFormattedLevelException(
									"Character " + firstChar + " was not recognized");
						}
						if (connectionDataAt(i, j).get(0).equals(".")) {
							noConnectsAt.add(new EnvironmentObject(j * 48, i * 48, (firstChar - '0') * 48,
									(secondChar - '0') * 48, connectionDataAt(i, j)));
						} else
							hasConnectsAt.add(new EnvironmentObject(j * 48, i * 48, (firstChar - '0') * 48,
									(secondChar - '0') * 48, connectionDataAt(i, j)));
						collisionObjects.add(new EnvironmentObject(j * 48, i * 48, (firstChar - '0') * 48,
								(secondChar - '0') * 48, connectionDataAt(i, j)));
					}
					// Creates offscreen objects
					collisionObjects.add(new CollisionObject(-48, -48, 48, 20 * 48)); // Invisible wall on left side
					collisionObjects.add(new CollisionObject(16 * 48, -48, 48, 20 * 48)); // Invisible wall on right
																							// side
					collisionObjects.add(new LevelFinishZone(-48, -48 - 6, 20 * 48, 48, m)); // Finish zone on top side
					collisionObjects.add(new UpSpike(-48, 17 * 48, 20 * 48, m)); // Death zone on bottom side
					m.setCanCollide(true);

				}
			}
		} catch (ImproperlyFormattedLevelException e) {
			main.displayError(e.getMessage());

		}
	}
	
	public void setMadelineCanDash(boolean option)
	{
		m.setCanDash(option);
	}

	/**
	 * Obtains the connection data for a block from the identical position in the
	 * connection data map
	 * 
	 * @param vertical   the vertical position of the block
	 * @param horizontal the horizontal position of the block
	 * @return the ArrayList representing the (at most 2) sides a block can connect
	 *         at
	 */
	public ArrayList<String> connectionDataAt(int vertical, int horizontal) {
		char[] connData = new char[2];
		String[] dataAtX = levelData[17 + vertical].split(" "); // 17 is the offset of the second map from the first map
		ArrayList<String> output = new ArrayList<String>();
		connData[0] = dataAtX[horizontal].charAt(0);
		connData[1] = dataAtX[horizontal].charAt(1);
		output.add(Character.toString(connData[0]));
		output.add(Character.toString(connData[1]));
		return output;
	}

	/**
	 * Reads in the level data as an array of Strings corresponding to line number
	 * 
	 * @param levelNum the String representing the integer level number
	 * @return the String array (always length 33) representing the level
	 */
	public String[] getLevelData(String levelNum) {
		String[] output = new String[33];
		try (Scanner s1 = new Scanner(new File("src/LevelData/level" + levelNum + ".txt"))) {
			int index = 0;
			while (s1.hasNext()) {
				String line = s1.nextLine();
				if (line.length() != 47) {
					throw new ImproperlyFormattedLevelException(
							"Incorrect line length. Expected length 47 but received length " + line.length());
				}
				output[index] = line;
				index++;
			}
			if (index != 33) {
				throw new ImproperlyFormattedLevelException(
						"Incorrect number of lines. Expected 33 lines but received " + index + " lines");
			}
			return output;
		} catch (FileNotFoundException e) {
			main.displayError("The file for level " + levelNum + " could not be found");
			return null;
		} catch (ImproperlyFormattedLevelException e) {
			main.displayError(e.getMessage());
			return null;
		}

	}

}
