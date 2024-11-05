package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import collectables.*;
import collisionObjects.*;
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
	private BreakableBlock bb;
	private Chest chest;
	private SpawningMadeline spawnMaddy;
	private boolean strawberryAlreadyCollected;
	private boolean displayMadeline;
	private PointText pt;
	private GraveText gt;
	private LevelDisplayText ldt;
	private String[] levelData;
	private Point[][] layer;
	private int madX;
	private int madY;
	private int levelNum;
	private ArrayList<CollisionObject> otherObject;
	private static final int GAME_WIDTH = 768;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private BufferedImage scaledMap;
	private int madelineTotalDashes;
	private ArrayList<Cloud> clouds;
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
	public LevelComponent(MainApp main, int levelNum, boolean strawberryAlreadyCollected, ArrayList<Cloud> clouds) {
		this.levelNum = levelNum;
		this.clouds = clouds;
		this.main = main;
		displayMadeline = false;
		otherObject = new ArrayList<CollisionObject>();
		try {
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		layer = new Point[16][16];
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
		for (Cloud c : clouds)
		{
			c.drawOn(g2);
		}
		drawBackgroundLayer(g2, layer);
		for (CollisionObject c : otherObject) {
			c.drawOn(g2);
		}
		drawForegroundLayer(g2, layer);
		if (pt != null) {
			pt.drawOn(g2);
		}
		if (spawnMaddy != null)
		{
			spawnMaddy.drawOn(g2);
			if(!spawnMaddy.getMovingUp())
			{
				this.setDisplayMadeline(true);
				spawnMaddy = null;
			}
		}
		if (strawberry != null) {
			strawberry.drawOn(g2);
		}
		if (chest != null)
		{
			chest.drawOn(g2);
		}
		if (displayMadeline)
		{
			m.drawOn(g2);
		}
		if (bb != null)
		{
			bb.drawOn(g2);
		}
		if (gt != null)
		{
			gt.drawOn(g2);
		}
		if (ldt != null)
		{
			ldt.drawOn(g2);
		}
	}

	/**
	 * Resets the 
	 */
	public void resetLevel() {
		main.resetLevel();
	}

	/**
	 * Moves to the next level
	 */
	public void nextLevel() {
		main.nextLevel();
		spawnMaddy = new SpawningMadeline(madX, 0, Color.RED);
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

	public void addLevelDisplay(String text, long startTime)
	{
		if (levelNum == 12)
			text = "Old Site";
		else if (levelNum == 31)
			text = "Summit";
		ldt = new LevelDisplayText(text, startTime);
	}
	
	public void removeLevelDisplay()
	{
		ldt = null;
	}
	
	public void resetMadelineVelocity()
	{
		m.resetVelocity();
	}
	
	public void setDisplayMadeline(boolean b)
	{
		this.displayMadeline = b;
	}
	/**
	 * Updates Madeline's position based on her current velocity
	 * 
	 * @param hasMoved whether a key has been pressed to move Madeline
	 */
	public void moveMadeline() {
		if (displayMadeline)
			m.setPosition();
	}
	
	/**
	 * Updates Madeilne's velocity
	 * 
	 * @param hasMoved whether a key has been pressed to move Madeline
	 */
	public void accelMadeline(boolean hasMoved) {
		if (displayMadeline)
			m.setVelocity(hasMoved);
	}

	/**
	 * Makes Madeline jump (if she is able)
	 */
	public void madelineJump() {
		if (displayMadeline)
			m.jump();
	}

	/**
	 * Makes Madeline dash (if she is able)
	 * 
	 * @param dir
	 */
	public void dash(String dir) {
		if (m.dash(dir) && displayMadeline) {
			if (strawberry != null)
				strawberry.flyAway();
		}
		// m.dash(dir);
	}

	/**
	 * Checks if Madeline is able to dash
	 */
	public void checkIfDashing() {
		m.checkState();
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
		if (strawberry != null)
		{
			pt = new PointText(strawberry.getX(), strawberry.getY());
			main.collectStrawberry();
			collisionObjects.remove(strawberry);
			strawberry = null;
		}
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
	public void levelFromText(String fileName) {
		m = new Madeline(this);
		levelData = getLevelData(fileName);
		createLevel();
		m.setTotalDashes(1);
		m.resetDashes();
		m.setCollisionObjects(collisionObjects);
		m.setXPos(madX);
		m.setYPos(madY - 48);
	}
	
	public void levelFromText(int levelNum) {
		m = new Madeline(this);
		levelData = getLevelData("level" + levelNum);
		createLevel();
		m.setTotalDashes(madelineTotalDashes);
		m.setCollisionObjects(collisionObjects);
		m.setXPos(madX);
		m.setYPos(madY-36);
		m.resetDashes();
		spawnMaddy = new SpawningMadeline(madX, madY - 36, m.getHairColor());
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
			if (levelData == null)
				return;
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
						RightSpike r = new RightSpike(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8, (secondChar - '0') * 8*MainApp.PIXEL_DIM, m);
						otherObject.add(r);
						collisionObjects.add(r);
						break;
					case ('<'):
						LeftSpike l = new LeftSpike(j * MainApp.PIXEL_DIM * 8 + 5*MainApp.PIXEL_DIM, i * MainApp.PIXEL_DIM * 8 - 6, (secondChar - '0') * 8*MainApp.PIXEL_DIM, m);
						collisionObjects.add(l);
						otherObject.add(l);
						break;
					case ('^'):
						UpSpike u = new UpSpike(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8 + 5*MainApp.PIXEL_DIM, (secondChar - '0') * 8*MainApp.PIXEL_DIM, m);
						collisionObjects.add(u);
						otherObject.add(u);
						break;
					case ('v'):
						DownSpike d = new DownSpike(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8 + 0, (secondChar - '0') * 8*MainApp.PIXEL_DIM, m);
						collisionObjects.add(d);
						otherObject.add(d);
						break;
					case ('p'):
						Spring s = new Spring(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8, m);
						collisionObjects.add(s);
						otherObject.add(s);
						break;
					case ('l'):
						CloudPlatform c;
						if (secondChar == 'l')
						{
							c = new CloudPlatform(j*8*MainApp.PIXEL_DIM, i*8*MainApp.PIXEL_DIM, m, -1);
						}
						else
						{
							c = new CloudPlatform(j*8*MainApp.PIXEL_DIM, i*8*MainApp.PIXEL_DIM, m, 1);
						}
						collisionObjects.add(c);
						otherObject.add(c);
						break;
					case ('d'):
						if (secondChar != 'd')
						{
							DissappearingSpring dSpring = new DissappearingSpring(j*8*MainApp.PIXEL_DIM,i*8*MainApp.PIXEL_DIM, m);
							collisionObjects.add(dSpring);
							otherObject.add(dSpring);
						}
						else
						{
							DissappearingBlock dBlock = new DissappearingBlock(j*8*MainApp.PIXEL_DIM,i*8*MainApp.PIXEL_DIM);
							collisionObjects.add(dBlock);
							otherObject.add(dBlock);
						}
						break;
					case ('r'):
						Balloon bal = new Balloon(j*MainApp.PIXEL_DIM*8,i * MainApp.PIXEL_DIM * 8, m);
						otherObject.add(bal);
						collisionObjects.add(bal);
						break;
					case ('g'):
						GraveText g = new GraveText(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8);
						collisionObjects.add(g);
						gt = g;
						break;
					case ('k'):
						if (!strawberryAlreadyCollected) {
							if (chest == null)
							{
								chest = new Chest();
							}
							Key k = new Key(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, chest, m);
							collisionObjects.add(k);
							otherObject.add(k);
						}
						break;
					case ('c'):
						if (!strawberryAlreadyCollected) {
							if (chest == null)
							{
								chest = new Chest();
							}
							chest.setX(j*MainApp.PIXEL_DIM*8);
							chest.setY(i*MainApp.PIXEL_DIM*8);
						}
						break;
					case ('b'):
						if (!strawberryAlreadyCollected) {
							bb = new BreakableBlock(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8, 2 * 8*MainApp.PIXEL_DIM, 2 * 8*MainApp.PIXEL_DIM, m);
							collisionObjects.add(bb);
						}
						break;
					case ('s'):
						if (!strawberryAlreadyCollected) {
							strawberry = new Strawberry(j * MainApp.PIXEL_DIM * 8 + 4*MainApp.PIXEL_DIM, i * MainApp.PIXEL_DIM * 8 + 3*MainApp.PIXEL_DIM, m);
							collisionObjects.add(strawberry);
						}
						break;
					case ('w'):
						if (!strawberryAlreadyCollected) {
							strawberry = new WingedStrawberry(j * MainApp.PIXEL_DIM * 8 + 4*MainApp.PIXEL_DIM, i * MainApp.PIXEL_DIM * 8 + 3*MainApp.PIXEL_DIM, m);
							collisionObjects.add(strawberry);
						}
						break;
					case ('m'):
						if (secondChar == '2')
							madelineTotalDashes = 2;
						else
							madelineTotalDashes = 1;
						madX = j * MainApp.PIXEL_DIM * 8;
						madY = i * MainApp.PIXEL_DIM * 8;
						break;
					case ('I'):
						collisionObjects.add(new CollisionObject(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8, (secondChar - '0') * 8*MainApp.PIXEL_DIM,
								(objectsData[j].charAt(2) - '0') * 8*MainApp.PIXEL_DIM, false, true));
						break;
					default:
						if (firstChar - '0' < 0 || firstChar - '0' > 10) {
							throw new ImproperlyFormattedLevelException(
									"Character " + firstChar + " was not recognized in level creation");
						}
						collisionObjects.add(new CollisionObject(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8, (firstChar - '0') * 8*MainApp.PIXEL_DIM,
								(secondChar - '0') * 8*MainApp.PIXEL_DIM, true, true));
					}
					// Creates offscreen objects
					collisionObjects.add(new CollisionObject(-8*MainApp.PIXEL_DIM, -8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, false, false)); // Invisible wall on left side
					collisionObjects.add(new CollisionObject(16 * 8*MainApp.PIXEL_DIM, -8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, false, false)); // Invisible wall on right
																							// side
					collisionObjects.add(new LevelFinishZone(-8*MainApp.PIXEL_DIM, -8*MainApp.PIXEL_DIM - MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, m)); // Finish zone on top side
					collisionObjects.add(new UpSpike(-8*MainApp.PIXEL_DIM, 17 * 8*MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, m)); // Death zone on bottom side
					m.setCanCollide(true);
				}
			}
			for (int i = 0; i < 16; i++)
			{
				String[] visualData = levelData[i+17].split(" ");
				for (int j = 0; j < visualData.length; j++)
				{
					if (!visualData[j].equals("-"))
						layer[j][i] = new Point((int)Double.parseDouble(visualData[j].split(",")[0]), (int)Double.parseDouble(visualData[j].split(",")[1]));
				}
			}
		} catch (ImproperlyFormattedLevelException e) {
			main.displayError(e.getMessage());
		}
		
	}
	
	
	public void drawForegroundLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null) continue;
				//For some reason there is a vertical offset of 1 when drawing the sprites, so the source y1 is increased by 1
				if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 0  && (int)layer[i][j].getY() == SPRITE_WIDTH*1))
					if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*2))
						if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == SPRITE_WIDTH*2))
							if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == SPRITE_WIDTH*2))
								if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*3))
									if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == SPRITE_WIDTH*3))
										if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == SPRITE_WIDTH*3))
											if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 7 && (int)layer[i][j].getY() == SPRITE_WIDTH*6))
												if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*6))
													if (!((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*5))
													{
														g.drawImage(scaledMap, i * SPRITE_WIDTH, j * SPRITE_HEIGHT, i * SPRITE_WIDTH + SPRITE_WIDTH, j * SPRITE_HEIGHT + SPRITE_HEIGHT, ((int)layer[i][j].getX() - GAME_WIDTH), (int)layer[i][j].getY() + 1, (((int)layer[i][j].getX() - GAME_WIDTH)) + SPRITE_WIDTH, ((int)layer[i][j].getY()) + SPRITE_HEIGHT, null);
													}
			}			
		}
	}
	
	public void drawBackgroundLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null) continue;
				if (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 0  && (int)layer[i][j].getY() == SPRITE_WIDTH*1)
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*2))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == SPRITE_WIDTH*2))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == SPRITE_WIDTH*2))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*3))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == SPRITE_WIDTH*3))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == SPRITE_WIDTH*3))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 7 && (int)layer[i][j].getY() == SPRITE_WIDTH*6))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*6))
				|| (((int)layer[i][j].getX() == GAME_WIDTH + SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == SPRITE_WIDTH*5)))
				{
					g.drawImage(scaledMap, i * SPRITE_WIDTH, j * SPRITE_HEIGHT, i * SPRITE_WIDTH + SPRITE_WIDTH, j * SPRITE_HEIGHT + SPRITE_HEIGHT, ((int)layer[i][j].getX() - GAME_WIDTH), (int)layer[i][j].getY() + 1, (((int)layer[i][j].getX() - GAME_WIDTH)) + SPRITE_WIDTH, ((int)layer[i][j].getY()) + SPRITE_HEIGHT, null);
				}
			}
		}
	}
	
	/**
	 * Reads in the level data as an array of Strings corresponding to line number
	 * 
	 * @param levelNum the String representing the integer level number
	 * @return the String array (always length 33) representing the level
	 */
	public String[] getLevelData(String fileName) {
		String[] output = new String[33];
		try (Scanner s1 = new Scanner(new File("src/LevelData/" + fileName + ".txt"))) {
			int index = 0;
			while (s1.hasNext()) {
				String line = s1.nextLine();
				output[index] = line;
				index++;
			}
			if (index != 33) {
				throw new ImproperlyFormattedLevelException(
						"Incorrect number of lines. Expected 33 lines but received " + index + " lines");
			}
			return output;
		} catch (FileNotFoundException e) {
			main.displayError("The file " + fileName + ".txt could not be found");
			return null;
		} catch (ImproperlyFormattedLevelException e) {
			main.displayError(e.getMessage());
			return null;
		}

	}
	
	
	public void setMadelineCanDash(boolean option)
	{
		m.setCanDash(option);
	}
}
