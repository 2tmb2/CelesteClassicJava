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

import TextElements.FinalScoreText;
import TextElements.GraveText;
import TextElements.LevelDisplayText;
import TextElements.PointText;
import collectables.*;
import collisionObjects.*;

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
	private FinalScoreText fst;
	private String[] levelData;
	private Point[][] layer;
	private int madX;
	private int madY;
	private int levelNum;
	private ArrayList<CollisionObject> otherObject;
	private int madelineTotalDashes;
	private ArrayList<Cloud> clouds;
	private Long timeDiff;
	private int strawberryCount;
	private int deathCount;
	private boolean isIncomplete;
	
	/**
	 * Creates a LevelComponent Object
	 * 
	 * @param main                       representing the MainApp
	 * @param level                      representing the current level as an
	 *                                   integer
	 * @param strawberryAlreadyCollected is a boolean value. It is true if the
	 *                                   strawberry in the level has already been
	 *                                   collected and false otherwise.
	 * @param clouds 					 an arraylist of clouds representing the backgorund clouds to draw for the level
	 * @param timeDiff 					 a Long representing the amount of time that has passed since the game was started and the current level was created
	 * @param strawberryCount			 an integer representing the number of strawberries that have been collected
	 * @param deathCount				 an integer representing the number of times the player has died
	 * @param isIncomplete				 a boolean that is true if any levels have been skipped and false otherwise
	 */
	public LevelComponent(MainApp main, int levelNum, boolean strawberryAlreadyCollected, ArrayList<Cloud> clouds, Long timeDiff, int strawberryCount, int deathCount, boolean isIncomplete) {
		this.levelNum = levelNum;
		this.clouds = clouds;
		this.main = main;
		this.timeDiff = timeDiff;
		this.strawberryCount = strawberryCount;
		this.deathCount = deathCount;
		this.isIncomplete = isIncomplete;
		displayMadeline = false;
		otherObject = new ArrayList<CollisionObject>();
		layer = new Point[16][16];
		this.strawberryAlreadyCollected = strawberryAlreadyCollected;
		collisionObjects = new ArrayList<CollisionObject>();
		levelFromText(levelNum);
	}
	
	
	public LevelComponent(MainApp main, String filePath, String levelName, ArrayList<Cloud> clouds, Long timeDiff, int deathCount) {
		this.clouds = clouds;
		this.main = main;
		this.timeDiff = timeDiff;
		this.deathCount = deathCount;
		displayMadeline = false;
		otherObject = new ArrayList<CollisionObject>();
		layer = new Point[16][16];
		collisionObjects = new ArrayList<CollisionObject>();
		levelFromText(filePath, levelName);
	}

	/**
	 * Draws objects onto g in the following order:
	 * Clouds
	 * Background objects
	 * CollisionObjects
	 * Foreground objects
	 * points text for strawberrys (if it exists)
	 * spawning madeline (if it exists)
	 * the level's strawberry (if it exists)
	 * the level's chest (if it exists)
	 * the game's final score text (if it exists)
	 * madeline (if she should be displayed)
	 * the breakable block (if it exists)
	 * the grave text (if it exists)
	 * the level display text (if it exists)
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) (g);
		for (Cloud c : clouds)
		{
			c.drawOn(g2);
		}
		drawBackgroundLayer(g2, layer);
		for (CollisionObject c : collisionObjects) {
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
		if (fst != null)
		{
			fst.drawOn(g2);
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
		if (levelNum <= 31)
		{
			finalScore();
		}
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

	/**
	 * Adds a display to the level to show the level name/number
	 * @param text
	 * @param startTime
	 */
	public void addLevelDisplay(String text, long startTime)
	{
		if (levelNum == 12)
			text = "Old Site";
		else if (levelNum == 31)
			text = "Summit";
		ldt = new LevelDisplayText(text, startTime);
	}
	
	/**
	 * Removes the level number display
	 */
	public void removeLevelDisplay()
	{
		ldt = null;
	}
	
	/**
	 * Resets Madeline's velocity to 0
	 */
	public void resetMadelineVelocity()
	{
		m.resetVelocity();
	}
	
	/**
	 * Makes madeline either displayed or not displayed
	 * @param b true if madeline should be displayed, otherwise false
	 */
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
	 * Creates a level based on a full filename. To be used when reading in user-created levels.
	 * @param fileName representing the file name without the .txt extension.
	 */
	public void levelFromText(String filePath, String fileName) {
		m = new Madeline(this);
		levelData = getLevelData(filePath, fileName);
		createLevel(false);
		m.setTotalDashes(madelineTotalDashes);
		m.setCollisionObjects(collisionObjects);
		m.setXPos(madX);
		m.setYPos(madY-36);
		m.resetDashes();
		spawnMaddy = new SpawningMadeline(madX, madY - 36, m.getHairColor());
	}
	
	/**
	 * Head method for loading a level from a file. creates a new empty Madeline for
	 * the next level (to pass to objects in the level). Sets proper dashNum based
	 * on level number. Sets position of Madeline based on level data and gives her
	 * the processed list of collision objects.
	 * 
	 * @param levelNum the String representing the integer level number
	 */
	public void levelFromText(int levelNum) {
		m = new Madeline(this);
		levelData = getLevelData("src/LevelData/" + "level" + levelNum + ".txt", levelNum + ".txt");
		createLevel(true);
		m.setTotalDashes(madelineTotalDashes);
		m.setCollisionObjects(collisionObjects);
		m.setXPos(madX);
		m.setYPos(madY-36);
		m.resetDashes();
		spawnMaddy = new SpawningMadeline(madX, madY - 36, m.getHairColor());
	}

	
	
	/**
	 * Parses the String array of level data for the information of obstacles and
	 * objects at each point. There are certain text elements that signify different objects.
	 * -- is an empty filler character
	 * [] is an empty filler character
	 * >1 represents a spike facing right
	 * <1 represents a spike facing left
	 * ^1 represents a spike facing up
	 * v1 represents a spike facing down
	 * ff represents the finish flag (shows player their death count/time/strawberries)
	 * pp represents a spring object
	 * dd represents a dissappearing block
	 * dp represents a dissappearing spring
	 * nn represents a gem
	 * ll represents a cloud moving left
	 * lr represents a cloud moving right
	 * rr represents a balloon
	 * gg represents grave text
	 * kk represents a key
	 * cc represents a chest
	 * bb represents a breakable block
	 * ss represents a strawberry
	 * ww represents a winged strawberry
	 * CC represents a big chest
	 * m1 represents a madeline with 1 dash
	 * m2 represents a madeline with 2 dashes
	 * I followed by two numbers creates ice collision with width and height based on those two numbers
	 * two numbers alone creates a regular collision object with width and height based on those two numbers
	 * 
	 * @param canMoveToNextLevel true if the level finish zone should be spawned, otherwise false.
	 * The level finish zone should not be spawned if reading in a user-created level.
	 * 
	 */
	public void createLevel(boolean canMoveToNextLevel) {
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
						RotatableSpike r = new RotatableSpike(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, MainApp.PIXEL_DIM*2, MainApp.PIXEL_DIM*6, 'r', m);
						otherObject.add(r);
						collisionObjects.add(r);
						break;
					case ('<'):
						RotatableSpike l = new RotatableSpike(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, MainApp.PIXEL_DIM*2, MainApp.PIXEL_DIM*6, 'l', m);
						collisionObjects.add(l);
						otherObject.add(l);
						break;
					case ('^'):
						RotatableSpike u = new RotatableSpike(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, MainApp.PIXEL_DIM*6, MainApp.PIXEL_DIM*2, 'u', m);
						collisionObjects.add(u);
						otherObject.add(u);
						break;
					case ('v'):
						RotatableSpike d = new RotatableSpike(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, MainApp.PIXEL_DIM*6, MainApp.PIXEL_DIM*2, 'd', m);
						collisionObjects.add(d);
						otherObject.add(d);
						break;
					case ('f'):
						FinishFlag f = new FinishFlag(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, m);
						collisionObjects.add(f);
						otherObject.add(f);
						break;
					case ('p'):
						Spring s = new Spring(j * MainApp.PIXEL_DIM * 8, i * MainApp.PIXEL_DIM * 8, m);
						collisionObjects.add(s);
						otherObject.add(s);
						break;
					case ('n'):
						Gem gem = new Gem(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, m);
						collisionObjects.add(gem);
						otherObject.add(gem);
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
							chest.setX(j*MainApp.PIXEL_DIM*8 - 24);
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
					case ('C'):
						BigChest bc = new BigChest(j*MainApp.PIXEL_DIM*8, i*MainApp.PIXEL_DIM*8, m);
						collisionObjects.add(bc);
						otherObject.add(bc);
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
					// Creates offscreen walls
					collisionObjects.add(new CollisionObject(-8*MainApp.PIXEL_DIM, -8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, false, false)); // Invisible wall on left side
					collisionObjects.add(new CollisionObject(16 * 8*MainApp.PIXEL_DIM, -8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, false, false)); // Invisible wall on right
					
					// creates the level finish zone to advance levels
					if (levelNum != 31 && canMoveToNextLevel)
						collisionObjects.add(new LevelFinishZone(-8*MainApp.PIXEL_DIM, -8*MainApp.PIXEL_DIM - MainApp.PIXEL_DIM, 20 * 8*MainApp.PIXEL_DIM, 8*MainApp.PIXEL_DIM, m)); // Finish zone on top side
					
					// creates the death zone at the bottom of the world
					collisionObjects.add(new RotatableSpike(MainApp.PIXEL_DIM*-8, 17*MainApp.PIXEL_DIM*8, 20*MainApp.PIXEL_DIM*8, MainApp.PIXEL_DIM*8, 'u', m));
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
	
	/**
	 * Draws the foreground objects onto g
	 * @param layer an array of points of the textures to draw
	 */
	public void drawForegroundLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null) continue;
				// These if statements ensure that only the foreground sprites are drawn
				if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 0  && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*1))
					if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*2))
						if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*2))
							if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*2))
								if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*3))
									if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*3))
										if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*3))
											if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 7 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*6))
												if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*6))
													if (!((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*5))
													{
														g.drawImage(MainApp.SCALED_MAP, i * Constants.SPRITE_WIDTH, j * Constants.SPRITE_HEIGHT, i * Constants.SPRITE_WIDTH + Constants.SPRITE_WIDTH, j * Constants.SPRITE_HEIGHT + Constants.SPRITE_HEIGHT, ((int)layer[i][j].getX() - Constants.GAME_WIDTH), (int)layer[i][j].getY() + 1, (((int)layer[i][j].getX() - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH, ((int)layer[i][j].getY()) + Constants.SPRITE_HEIGHT, null);
													}
			}			
		}
	}
	
	/**
	 * Draws the background objects onto g
	 * @param layer an array of points of the textures to draw
	 */
	public void drawBackgroundLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null) continue;
				// these if statements ensure that only the background (the grey objects) are drawn 
				if (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 0  && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*1)
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*2))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*2))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*2))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*3))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 9 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*3))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 10 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*3))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 7 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*6))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*6))
				|| (((int)layer[i][j].getX() == Constants.GAME_WIDTH + Constants.SPRITE_WIDTH * 8 && (int)layer[i][j].getY() == Constants.SPRITE_WIDTH*5)))
				{
					g.drawImage(MainApp.SCALED_MAP, i * Constants.SPRITE_WIDTH, j * Constants.SPRITE_HEIGHT, i * Constants.SPRITE_WIDTH + Constants.SPRITE_WIDTH, j * Constants.SPRITE_HEIGHT + Constants.SPRITE_HEIGHT, ((int)layer[i][j].getX() - Constants.GAME_WIDTH), (int)layer[i][j].getY() + 1, (((int)layer[i][j].getX() - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH, ((int)layer[i][j].getY()) + Constants.SPRITE_HEIGHT, null);
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
	private String[] getLevelData(String filePath, String fileName) {
		String[] output = new String[33];
		try (Scanner s1 = new Scanner(new File(filePath))) {
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
	
	/**
	 * Sets the color of the clouds to pink
	 */
	public void setCloudsPink()
	{
		main.setCloudsPink();
	}
	
	/**
	 * Creates the final score text object
	 * Allows the player to see their time, strawberry #, death #
	 */
	public void finalScore()
	{
		fst = new FinalScoreText(timeDiff, strawberryCount, deathCount, isIncomplete);
	}
	
	/**
	 * Sets Madeline's ability to dash
	 * @param option true if she can dash, false otherwise
	 */
	public void setMadelineCanDash(boolean option)
	{
		m.setCanDash(option);
	}
	
	/**
	 * Sets the background color
	 * @param c representing the color to set the background to
	 */
	public void setBackgroundColor(Color c)
	{
		main.setBackgroundColor(c);
	}
	
	/**
	 * Spawns a new Gem
	 * @param x the top left x value of where to spawn the gem
	 * @param y the top left y value of where to spawn the gem
	 */
	public void spawnNewGem(int x, int y)
	{
		Gem gem = new Gem(x, y, m);
		collisionObjects.add(gem);
		otherObject.add(gem);
	}
}
