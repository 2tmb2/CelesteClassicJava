package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.Timer;

import collisionObjects.CollisionObject;
import collisionObjects.Spring;

public class Madeline {
	// xPos and yPos store the top left corner of Madeline
	private int xPos;
	private int yPos;
	private double xVel;
	private double yVel;
	private double yVelMax;
	private int numOfDashesTotal;
	private int numOfDashesRemaining;
	private int facingRight;
	private boolean isDashingHorizontally;
	private ArrayList<CollisionObject> collisionObjects;
	private boolean wallJump;
	private boolean jumpPressed;
	private boolean isDashingVertically;
	private boolean useDashDeccel = false;
	private LevelComponent lvl;
	private boolean canContinue;
	private boolean isCollidingWall = false;
	private boolean isTouchingWall = false;
	private boolean isTouchingFloor = false;
	private boolean canControl = false;
	private boolean isCollidingFloor;
	private boolean canDash;
	private boolean canJump;
	private boolean controlTimerDecreased = false;
	private boolean lowGrav = false;
	
	private long timeAtDash;
	private long timeAtWallJump;
	private long dashTimer = 0;

	private Color hairColor;
	private int hairSwitchFrame;
	private static final Color RED_HAIR = new Color(255, 0, 77);
	private static final Color BLUE_HAIR = new Color(41, 173, 255);
	private static final Color GREEN_HAIR = new Color(0, 228, 54);
	private static final Color WHITE_HAIR = new Color(255,241,232);
	private static final Color EYE_COLOR = new Color(29, 43, 83);
	private static final Color TORSO_COLOR = new Color(0, 135, 81);
	private static final Color LEG_COLOR = new Color(255, 241, 232);
	private static final Color FACE_COLOR = new Color(255, 204, 170);
	private static final int WIDTH = 48;
	private static final int HEIGHT = 42;
	private static final int X_COLLISION_OFFSET = 6;
	private static final int Y_COLLISION_OFFSET = 18;
	
	private static final double FRAME_COEFF = (double)MainApp.BETWEEN_FRAMES / 31;
	
	private static final long VERT_DASH_TIME = (long)((200.0 / FRAME_COEFF) * .50);
	private static final long HORZ_DASH_TIME = (long)((460.0 / FRAME_COEFF) * .50);
	private static final long WALL_JUMP_TIME = (long)((300.0 / FRAME_COEFF) * .50);
	
	//After dashing into a wall, how many ms for the player to regain control
	private static final long WALL_CANCEL_CONTROL_TIME = (long)((100.0 / FRAME_COEFF) * .50);
	
	//After a wall jump ends, how many ms does the player have low gravity
	private static final long LOW_GRAV_TIME = (long)((100.0 / FRAME_COEFF) * .50);
	
	private static final double MOVEMENT_COEFF = 1.5;
	private static final double GRAVITY = 0.42 * (double)MainApp.PIXEL_DIM;
	private static final double TERM_VEL = 2.0 * (double)MainApp.PIXEL_DIM * MOVEMENT_COEFF;
	private static final double WALL_VEL = 0.4 * (double)MainApp.PIXEL_DIM * MOVEMENT_COEFF;
	private static final double JUMP_VEL = -4.4 * (double)MainApp.PIXEL_DIM;
	private static final double WALK_SPEED = 2.0 * (double)MainApp.PIXEL_DIM;
	private static final double WALL_JUMP_VEL = 2.8 * (double)MainApp.PIXEL_DIM;
	private static final double ACCEL = WALK_SPEED * 0.6;
	private static final double DECCEL = WALK_SPEED * 0.5;
	private static final double DASH_DECCEL = WALK_SPEED * 0.2;
	private static final double BOUNCE_VEL = JUMP_VEL * 1.20;
	private static final double BOUNCE_VEL_REDUCE = 0.2;
	private static final double Y_DASH_VEL = 3.0 * (double)MainApp.PIXEL_DIM;
	private static final double X_DASH_VEL = Y_DASH_VEL * .9;

	/**
	 * Creates an empty Madeline object
	 */
	public Madeline(LevelComponent level) {
		this.lvl = level;
		this.xPos = 0;
		this.yPos = 0;
		this.collisionObjects = null;
		this.numOfDashesTotal = 1;
		hairSwitchFrame = 0;
		canDash = true;
		xVel = 0;
		yVel = 0;
		yVelMax = TERM_VEL;
		numOfDashesRemaining = 0;

		wallJump = false;
		jumpPressed = false;
		isDashingVertically = false;
		isDashingHorizontally = false;

		// facingRight is 1 if Madeline is facing right, -1 if Madeline is facing left
		facingRight = 1;
		canContinue = true;
	}

	/**
	 * Updates Madeline's position based on her current velocity, position, and any
	 * objects she is colliding with
	 * 
	 * @param hasMoved whether a key has been pressed to move Madeline
	 */
	public void setPosition(boolean hasMoved) {
		isCollidingWall = isCollidingWithWall();
		isCollidingFloor = isCollidingWithFloor();
		isTouchingWall = isTouchingWall();
		isTouchingFloor = isTouchingFloor();
		if (!isCollidingWall) {
			yVelMax = TERM_VEL;
		} else {
			yVelMax = WALL_VEL;
			canJump = true;
			lowGrav = false;
			if (!canControl && !controlTimerDecreased) {
				dashTimer = WALL_CANCEL_CONTROL_TIME;
				//controlTimerDecreased = true;
			}
			isDashingVertically = false;
		}
		setHorizontalPosition(hasMoved);
		setVerticalPosition();
	}

	/**
	 * Updates Madeline's horizontal position based on her current velocity,
	 * position, and any objects she is colliding with
	 * 
	 * @param hasMoved whether a key has been pressed to move Madeline
	 */
	public void setHorizontalPosition(boolean hasMoved) {
		if (isCollidingWall) {
			wallJump = false;
		}
		if (!hasMoved && !isDashingHorizontally && !wallJump) {
			if (useDashDeccel) {
				if (xVel > 0) xVel = Math.max(0, xVel - DASH_DECCEL);
				else { xVel = Math.min(0, xVel + DASH_DECCEL); }
			} else {
				if (xVel > 0) xVel = Math.max(0, xVel - DECCEL);
				else { xVel = Math.min(0, xVel + DECCEL); }
			}
			
			
		}
		if (isCollidingWall) {
			xVel = 0;
		}

		if (!isCollidingWall) {
			xPos += (int) xVel;
		}
		if (xVel > .25) {
			if (!wallJump && !isDashingHorizontally) {
				xVel = Math.max(xVel - 1.25, 0);
			} else {
				xVel -= .5;
			}
			facingRight = 1;
		} else if (xVel < -.25) {
			if (!wallJump && !isDashingHorizontally) {
				xVel = Math.min(xVel + 1.25, 0);
			} else {
				xVel += .5;
			}
			facingRight = -1;

		}
	}

	/**
	 * Updates Madeline's vertical position based on her current velocity, position,
	 * and any objects she is colliding with
	 */
	public void setVerticalPosition() {
		double downAcc = GRAVITY;
		if (lowGrav) downAcc *= .5;
		if (isCollidingFloor && !isCollidingWall) {
			yVel = 0.0;
		} else if (yVel < yVelMax) {
			if  (!isDashingVertically) {
				if (Math.abs(yVel) > 1.0) {
					Math.min(yVel += downAcc, TERM_VEL);
				} else {
					Math.min(yVel += (downAcc * 0.5), TERM_VEL);
				}
				
			}
			
		} else if (yVel > yVelMax) {
			if (!isDashingVertically) {
				yVel = yVelMax;
			} else {
				yVel -= .5;
			}
		}
		//if (yVel > 0 && isDashingHorizontally) {
			//yVel = 0;
		//}
		if (!isCollidingFloor) {
			if (isCollidingWithCeiling() && yVel < 0) {
				yVel = 0;
			}
			yPos += (int) yVel;
		}
	}

	/**
	 * Updates Madeline's state
	 */
	public void checkState() {
		if (MainApp.time > timeAtDash + VERT_DASH_TIME) {
			isDashingVertically = false;
			canJump = true;
		}
		if (MainApp.time > timeAtDash + HORZ_DASH_TIME) {
			isDashingHorizontally = false;
		}
		if (MainApp.time > timeAtDash + dashTimer) {
			canControl = true;
			controlTimerDecreased = false;
		}
		if (MainApp.time > timeAtWallJump + WALL_JUMP_TIME) {
			wallJump = false;
			lowGrav = true;
		}
		if (MainApp.time > timeAtWallJump + WALL_JUMP_TIME + LOW_GRAV_TIME) {
			lowGrav = false;
		}
		if (MainApp.time > timeAtDash + HORZ_DASH_TIME + 0) {
			useDashDeccel = false;
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
		for (int i = 0; i < collisionObjects.size(); i++) {
			if (collisionObjects.get(i).isCollidingWall(xPos + (int) (xVel / 2) + X_COLLISION_OFFSET - facingRight, yPos + Y_COLLISION_OFFSET, facingRight)) {
				if (i < collisionObjects.size())
				{
					if (facingRight == 1 && (Math.abs(collisionObjects.get(i).getX() - WIDTH + 6 - xPos) < 20)) xPos = collisionObjects.get(i).getX() - WIDTH + 6;
					else if (Math.abs(collisionObjects.get(i).getX() + collisionObjects.get(i).getWidth() + 6 - xPos) < 20){ xPos = collisionObjects.get(i).getX() + collisionObjects.get(i).getWidth() - 6; }
					return true;
				}
			}
		}
		return false;
	}

	public boolean isTouchingWall() {
		CollisionObject object;
		for (int i = 0; i < collisionObjects.size(); i++) {
			object = collisionObjects.get(i);
			if (object.isCollidingWall(xPos + X_COLLISION_OFFSET, yPos + Y_COLLISION_OFFSET, facingRight)) {
				return true;
			};
			
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
			if (collisionObjects.get(i).isCollidingFloor(xPos + X_COLLISION_OFFSET,
					yPos + (int) yVel + Y_COLLISION_OFFSET)) {
				if (Math.abs(collisionObjects.get(i).getY() - HEIGHT - yPos) < 20) yPos = collisionObjects.get(i).getY() - HEIGHT;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a floor just below Madeline Different from Colliding
	 * because it does not factor in Madeline's velocity This is important because
	 * coyote time requires setting Madeline's y velocity to 0 while touching the
	 * floor which disables floor collision This method can be used instead when a
	 * condition requires that Madeline is constantly touching a floor
	 * 
	 * @return true if there is a floor below, otherwise false
	 */
	public boolean isTouchingFloor() {
		for (int i = 0; i < collisionObjects.size(); i++) {
			if (collisionObjects.get(i).isCollidingFloor(xPos + X_COLLISION_OFFSET, yPos + 5 + Y_COLLISION_OFFSET)) {
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
			if (collisionObjects.get(i).isCollidingCeiling(xPos + X_COLLISION_OFFSET,
					yPos + (int) yVel + Y_COLLISION_OFFSET)) {
				if (Math.abs(collisionObjects.get(i).getY() + collisionObjects.get(i).getHeight() - 18 - yPos) < 20) yPos = collisionObjects.get(i).getY() + collisionObjects.get(i).getHeight() - 18;
				return true;
			}
		}
		return false;
	}

	/**
	 * Make Madeline jump or wall jump if she is able to
	 */
	public void jump() {
		if (isTouchingFloor && !jumpPressed && canJump) {
			numOfDashesRemaining = numOfDashesTotal;
			yVel = JUMP_VEL;
			jumpPressed = true;
		} else if (isTouchingWall && !jumpPressed && !isTouchingFloor && canJump) {
			jumpPressed = true;
			wallJump = true;
			timeAtWallJump = MainApp.time;
			xVel = -facingRight * WALL_JUMP_VEL;
			yVel = JUMP_VEL * .9;
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
		if (numOfDashesRemaining > 0 && !(numOfDashesRemaining == 0) && canDash) {
			isDashingVertically = true;
			isDashingHorizontally = true;
			useDashDeccel = true;
			numOfDashesRemaining--;
			canDash = false;
			timeAtDash = MainApp.time;
			canControl = false;
			canJump = false;
			if (dir.equals("up")) {
				yVel = -Y_DASH_VEL;
				xVel = 0;
				dashTimer = VERT_DASH_TIME;
			}
			if (dir.equals("down")) {
				yVel = Y_DASH_VEL;
				xVel = 0;
				dashTimer = VERT_DASH_TIME;
			}
			if (dir.equals("upleft")) {
				yVel = -Y_DASH_VEL;
				xVel = -X_DASH_VEL;
				dashTimer = HORZ_DASH_TIME;
			}
			if (dir.equals("upright")) {
				yVel = -Y_DASH_VEL;
				xVel = X_DASH_VEL;
				dashTimer = HORZ_DASH_TIME;
			}
			if (dir.equals("downleft")) {
				yVel = Y_DASH_VEL;
				xVel = -X_DASH_VEL;
				dashTimer = HORZ_DASH_TIME;
			}
			if (dir.equals("downright")) {
				yVel = Y_DASH_VEL;
				xVel = X_DASH_VEL;
				dashTimer = HORZ_DASH_TIME;
			}
			if (dir.equals("")) {
				xVel = X_DASH_VEL * facingRight * 1.10;
				yVel = 0;
				dashTimer = HORZ_DASH_TIME;
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
		} else if (numOfDashesRemaining == 2) {
			if (hairSwitchFrame == 5)
			{
				hairSwitchFrame = 0;
				if (hairColor.equals(GREEN_HAIR))
				{
					hairColor = WHITE_HAIR;
				}
				else
				{
					hairColor = GREEN_HAIR;
				}
			}
			else
			{
				hairSwitchFrame++;
			}
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
			g2.translate(-roundPos(xPos), roundPos(yPos));
		} else {
			// translates g2 to make 0, 0 be the top left of Madeline's head
			g2.translate(roundPos(xPos), roundPos(yPos));
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

		// ======================================
		// END WIP
		// ======================================

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

	public static int roundPos(int toRound) {
		if (toRound % 6 <= 2) {
			return (toRound - (toRound % 6));
		} else {
			return (toRound + (6 - (toRound % 6)));
		}
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
		lvl.addNewStrawberry(x, y-10, false);
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

	public void stopAllTimers()
	{
		for (CollisionObject c : collisionObjects)
		{
			c.stopAllTimers();
		}
	}
	/**
	 * Increases Madeline's X velocity
	 */
	public void increaseX() {
		if (!wallJump && canControl) {
			xVel = Math.min(WALK_SPEED, xVel + ACCEL);
		}
	}

	/**
	 * Decreases Madeline's X velocity
	 */
	public void decreaseX() {
		if (!wallJump && canControl) {
			xVel = Math.max(-WALK_SPEED, xVel - ACCEL);
		}

	}
	
	/**
	 * resets the level upon death
	 */
	public void death() {
		lvl.resetLevel();
	}

	public void collectStrawberry() {
		resetDashes();
		lvl.collectStrawberry();
	}

	public boolean getIsDashing() {
		return isDashingVertically;
	}

	public void springBounce() {
		yVel = BOUNCE_VEL;
		yPos -= 4 * MainApp.PIXEL_DIM;
		xVel *= BOUNCE_VEL_REDUCE;
		numOfDashesRemaining = numOfDashesTotal;
	}

	public void resetDashes()
	{
		this.numOfDashesRemaining = numOfDashesTotal;
	}
	
	public void resetVelocity()
	{
		this.xVel = 0;
		this.yVel = 0;
	}
	public void nextLevel() {
		if (!canContinue)
			return;
		canContinue = false;
		// collisionObjects = null;
		lvl.nextLevel();
	}
	
	public void setCanDash(boolean option)
	{
		canDash = option;
	}
}
