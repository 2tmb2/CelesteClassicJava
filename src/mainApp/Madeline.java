package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Madeline {
	// xPos and yPos store the top left corner of Madeline
	private int xPos;
	private int yPos;
	private double xVel;
	private double yVel;
	private int numOfDashesTotal;
	private int numOfDashesRemaining;
	private int facingRight;
	private boolean isDashingHorizontally;
	private ArrayList<CollisionObject> collisionObjects;
	private int yVelMax;
	private boolean wallJump;
	private boolean jumpPressed;
	private boolean isDashing;
	private LevelComponent lvl;
	private boolean isNextLevel;
	private boolean canContinue;

	private Color hairColor;
	private static final Color RED_HAIR = new Color(255, 0, 77);
	private static final Color BLUE_HAIR = new Color(41, 173, 255);
	private static final Color GREEN_HAIR = new Color(0, 0, 0);
	private static final Color EYE_COLOR = new Color(29, 43, 83);
	private static final Color TORSO_COLOR = new Color(0, 135, 81);
	private static final Color LEG_COLOR = new Color(255, 241, 232);
	private static final Color FACE_COLOR = new Color(255, 204, 170);

	/**
	 * Creates an empty Madeline object
	 */
	public Madeline(LevelComponent level) {
		this.lvl = level;
		this.xPos = 0;
		this.yPos = 0;
		this.collisionObjects = null;
		this.numOfDashesTotal = 1;
		
		xVel = 0;
		yVel = 0;
		yVelMax = 6;
		numOfDashesRemaining = 0;

		wallJump = false;
		jumpPressed = false;
		isDashing = false;
		isDashingHorizontally = false;

		// facingRight is 1 if Madeline is facing right, -1 if Madeline is facing left
		facingRight = 1;
		isNextLevel = false;
		canContinue = true;
	}
	/**
	 * Creates a Madeline object
	 * 
	 * @param xPos             representing madeline's starting x position
	 * @param yPos             representing madeline's starting y position
	 * @param numOfDashesTotal representing madeline's number of available dashes (1
	 *                         or 2)
	 * @param c                representing an ArrayList of CollisionObjects
	 * @param lvl              representing the level component
	 */
	public Madeline(int xPos, int yPos, int numOfDashesTotal, ArrayList<CollisionObject> c, LevelComponent lvl) {
		// initialize starting values
		this.xPos = xPos;
		this.yPos = yPos;
		this.lvl = lvl;
		this.collisionObjects = c;
		this.numOfDashesTotal = numOfDashesTotal;

		xVel = 0;
		yVel = 0;
		yVelMax = 6;
		numOfDashesRemaining = 0;

		wallJump = false;
		jumpPressed = false;
		isDashing = false;
		isDashingHorizontally = false;

		// facingRight is 1 if Madeline is facing right, -1 if Madeline is facing left
		facingRight = 1;
		canContinue = true;
	}
	
	public void setCanCollide(boolean canCollide) {
		this.canContinue = canCollide;
	}
	
	public boolean getCanCollide() {
		return this.canContinue;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setLevel(LevelComponent lvl) {
		this.lvl = lvl;
	}
	
	public void setCollisionObjects(ArrayList<CollisionObject> c) {
		this.collisionObjects = c;
	}
	
	public void setTotalDashes(int totalDashes) {
		this.numOfDashesTotal = totalDashes;
	}

	/**
	 * Increases Madeline's X velocity
	 */
	public void increaseX() {
		if (xVel <= 5 && !wallJump) {
			xVel += 1.25;
		}
	}

	/**
	 * Decreases Madeline's X velocity
	 */
	public void decreaseX() {
		if (xVel >= -5 && !wallJump) {
			xVel -= 1.25;
		}

	}

	/**
	 * Updates Madeline's horizontal position based on her current velocity,
	 * position, and any objects she is colliding with
	 */
	public void setHorizontalPosition() {
		if (Math.abs(xVel) <= 2.5) {
			wallJump = false;
		}
		if (!isCollidingWithWall()) {
			yVelMax = 6;
			xPos += (int) xVel;
		} else {
			while (isCollidingWithWall() && Math.abs(xVel) != 0) {
				xVel += -.5 * facingRight;
			}
			yVelMax = 2;
		}
		if (xVel > .25) {
			xVel -= .5;
			facingRight = 1;
		} else if (xVel < -.25) {
			facingRight = -1;
			xVel += .5;
		}
	}

	/**
	 * Updates Madeline's vertical position based on her current velocity, position,
	 * and any objects she is colliding with
	 */
	public void setVerticalPosition() {
		if (!isCollidingWithFloor() && !isDashingHorizontally) {
			if (isCollidingWithCeiling() && yVel < 0) {
				yVel = 0;
			}
			yPos += (int) yVel;
		} else {
			yVel = 0;
		}
		if (isCollidingWithFloor()) {
			yVel = 0;
		} else if (yVel < yVelMax) {
			yVel += 0.5;
		} else if (yVel > yVelMax) {
			if (!isDashing) {
				yVel = yVelMax;
			} else {
				yVel -= .5;
			}
		}
	}

	/**
	 * Checks if Madeline is currently in a dashing state
	 */
	public void checkIfDashing() {
		if (Math.abs(xVel) <= 8 && yVel >= 0) {
			isDashingHorizontally = false;
		}
		if (Math.abs(xVel) <= 4 && Math.abs(yVel) <= yVelMax) {
			isDashing = false;
		}
		if (isCollidingWithFloor()) {
			numOfDashesRemaining = numOfDashesTotal;
		}
	}

	/**
	 * Checks if Madeline is currently colliding with any walls
	 * 
	 * @return true if she is colliding with a wall, otherwise false
	 */
	public boolean isCollidingWithWall() {
		for (int i = 0; i < collisionObjects.size(); i++)
		{
			if (collisionObjects.get(i).isCollidingWall(xPos + (int) xVel, yPos + (int) yVel, facingRight)) {
				if (isNextLevel)
				{
					i = collisionObjects.size() + 2;
					return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if Madeline is currently colliding with any floors
	 * 
	 * @return true if she is colliding with a floor, otherwise false
	 */
	public boolean isCollidingWithFloor() {
		for (int i = 0; i < collisionObjects.size(); i++) {
			if (collisionObjects.get(i).isCollidingFloor(xPos + (int) xVel, yPos + (int) yVel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if Madeline is currently colliding with any ceilings
	 * 
	 * @return true if she is colliding with a ceiling, otherwise false
	 */
	public boolean isCollidingWithCeiling() {
		for (int i = 0; i < collisionObjects.size(); i++) {
			if (collisionObjects.get(i).isCollidingCeiling(xPos + (int) xVel, yPos + (int) yVel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Make Madeline jump or wall jump if she is able to
	 */
	public void jump() {
		if (isCollidingWithFloor() && !jumpPressed) {
			numOfDashesRemaining = numOfDashesTotal;
			yVel = -11.5;
			jumpPressed = true;
		} else if (isCollidingWithWall() && !jumpPressed && isCollidingWithFloor()) {
			jumpPressed = true;
			wallJump = true;
			xVel = -facingRight * 11;
			yVel = -9.5;
		} else if (isCollidingWithWall() && !jumpPressed && !isCollidingWithFloor() && !wallJump) {
			jumpPressed = true;
			wallJump = true;
			xVel = -facingRight * 11;
			yVel = -9.5;
		}
	}

	public void setJumpPressed(boolean b) {
		jumpPressed = b;
	}

	/**
	 * If she is able, makes Madeline dash in the given direction
	 * 
	 * @param dir represents the direction to dash. Dir is made up of either a
	 *            single direction or a combination of directions. When combining
	 *            directions, the vertical direction must come first. (e.g.
	 *            "upright", not "rightup") To dash horizontally, dir must be an
	 *            empty string. This will make Madeline dash in the direction she is
	 *            facing.
	 */
	public boolean dash(String dir) {
		if (numOfDashesRemaining > 0 && !(numOfDashesRemaining == 0)) {
			isDashing = true;
			numOfDashesRemaining--;
			int dashVel = 13;
			if (dir.equals("up")) {
				yVel = -dashVel;
			}
			if (dir.equals("down")) {
				yVel = dashVel;
			}
			if (dir.equals("upleft")) {
				yVel = -dashVel;
				xVel = -dashVel;
			}
			if (dir.equals("upright")) {
				yVel = -dashVel;
				xVel = dashVel;
			}
			if (dir.equals("downleft")) {
				yVel = dashVel;
				xVel = -dashVel;
			}
			if (dir.equals("downright")) {
				yVel = dashVel;
				xVel = dashVel;
			}
			if (dir.equals("")) {
				isDashingHorizontally = true;
				xVel = dashVel * facingRight;
				yVel = 0;
			}
			return true;
		}
		return false;
	}

	/**
	 * Draws Madeline onto the screen. Madeline is 48 pixels wide by 42 pixels tall.
	 * 
	 * @param Graphics2D g2
	 */
	public void drawOn(Graphics2D g2) {
		// updates Madeline's hair color based on her number of dashes remaining
		if (numOfDashesRemaining == 1) {
			hairColor = RED_HAIR;
			numOfDashesRemaining = numOfDashesTotal;
		} else if (numOfDashesRemaining == 2) {
			hairColor = GREEN_HAIR;
		} else {
			hairColor = BLUE_HAIR;
		}

		// duplicates the Graphics2D object so that transformations don't affect other
		// objects
		g2 = (Graphics2D) g2.create();

		// facingRight < 0 when Madeline is facing left
		if (facingRight < 0) {
			// create an AffineTransform to mirror Madeline's model when she is facing left
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			// moves the transform left by 48 pixels to adjust for the difference in x/y
			// location
			tx.translate(-48, 0);
			g2.transform(tx);
			// translates g2 to make 0, 0 be the top right of Madeline's head
			g2.translate(-xPos, yPos);
		} else {
			// translates g2 to make 0, 0 be the top left of Madeline's head
			g2.translate(xPos, yPos);
		}

		// drawing hair
		g2.setColor(hairColor);
		g2.fillRect(6, 0, 36, 6);
		g2.fillRect(0, 6, 48, 18);
		g2.fillRect(0, 24, 12, 6);
		g2.fillRect(6, 30, 6, 6);

		// ======================================
		// WIP
		// ======================================
		// draws the velocity-affected section of Madeline's hair
		double xModifier = -2 * Math.abs(xVel);
		if (Math.abs(xVel) >= 3) {
			xModifier = (Math.abs(xVel) / xVel) * 3 * -2 * facingRight;
		}
		double yModifier = 0;
		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 0, 6, 6);

		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 6, 12, 6);

		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 12, 18, 6);

		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 18, 24, 6);

		g2.translate(xModifier, yModifier);
		g2.fillRect(12, 24, 30, 6);

		g2.translate(xModifier, yModifier);
		g2.fillRect(24, 30, 18, 6);

		g2.translate(-6 * xModifier, -6 * yModifier);

		// drawing face
		g2.setColor(FACE_COLOR);
		g2.fillRect(18, 12, 24, 6);
		g2.fillRect(12, 18, 30, 12);

		// drawing torso
		g2.setColor(TORSO_COLOR);
		g2.fillRect(12, 30, 24, 6);

		// drawing legs
		g2.setColor(LEG_COLOR);
		g2.fillRect(12, 36, 6, 6);
		g2.fillRect(30, 36, 6, 6);

		// drawing eyes
		g2.setColor(EYE_COLOR);
		g2.fillRect(18, 18, 6, 6);
		g2.fillRect(36, 18, 6, 6);

	}

	/**
	 * Spawns a Strawberry in the location of the breakable block
	 * 
	 * @param x representing the horizontal center of the strawberry in absolute
	 *          coordinates (from 0 to 768)
	 * @param y representing the vertical center of the strawberry in absolute
	 *          coordinates (from 0 to 768)
	 */
	public void breakBlock(int x, int y) {
		lvl.addNewStrawberry(x, y, false);
		xVel = -facingRight * 7;
		yVel = -10;
		yPos -= 10;
	}

	/**
	 * Updates the current animation frame of all CollisionObjects
	 */
	public void updateAnimations() {
		for (CollisionObject c : collisionObjects) {
			c.updateAnimation();
		}
	}

	/**
	 * resets the level upon death
	 */
	public void death() {
		lvl.resetLevel();
	}

	public void collectStrawberry() {
		lvl.collectStrawberry();
	}

	public boolean getIsDashing() {
		return isDashing;
	}
	public void springBounce()
	{
		yVel = -15;
		numOfDashesRemaining = numOfDashesTotal;
	}

	public void nextLevel() {
		if (!canContinue) return;
		isNextLevel = true;
		canContinue = false;
		//collisionObjects = null;
		lvl.nextLevel();
	}
}
