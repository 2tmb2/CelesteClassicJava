package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import collisionObjects.CollisionObject;

/**
 * REQUIRED HELP CITATION
 * Referenced original code for Madeline's Hair
 */

/**
 * Includes Madeline's drawing, collision, and every way she can interact with
 * the environment
 */
public class Madeline {
	// xPos and yPos store the top left corner of Madeline
	private int xPos;
	private int yPos;
	private double xVel;
	private double yVel;
	private double yVelMax;
	private int cloudVel;
	private int numOfDashesTotal;
	private int numOfDashesRemaining;
	private int facingRight;
	private boolean isDashingHorizontally;
	private ArrayList<CollisionObject> collisionObjects;
	private boolean wallJump;
	private boolean jumpPressed;
	private boolean isDashingVertically;
	private boolean isDashingDiagonally;
	private boolean useDashDeccel = false;
	private LevelComponent lvl;
	private boolean canContinue;
	private boolean isCollidingWall = false;
	private boolean isTouchingWallRight = false;
	private boolean isTouchingWallLeft = false;
	private boolean isTouchingWall = false;
	private boolean isTouchingFloor = false;
	private boolean canControl = false;
	private boolean isCollidingFloor;
	private boolean isCollidingCeiling = false;
	private boolean canDash;
	private boolean canJump;
	private boolean controlTimerDecreased = false;
	private boolean lowGrav = false;
	private boolean noGrav = false;
	private boolean bounceHigh = true;
	private boolean wallSlide = false;
	private boolean breakState = false;
	private boolean isCoyote = true;
	private boolean isFullySpawned = false;
	private boolean canRechargeDash = true;
	private boolean canMove = true;
	private boolean dying = false;
	private boolean lookingUp = false;
	private boolean lookingDown = false;

	private int frameAtDash;
	private int frameAtWallJump;
	private int frameAtBreak;
	private int dashFrameTimer = 0;
	private int coyoteTimer = 0;
	private int timerI = 0;

	private int lifetime = 0;
	private int animationCounter = 0;

	private Color hairColor;
	private int hairSwitchFrame;
	private boolean canWallJumpLeft;
	private boolean canWallJumpRight;
	private ArrayList<Particle> particles = new ArrayList<Particle>();

	// Drawing variables
	private boolean isMoving;
	private Point spritePoint = RED_SPRITES;
	private int walkFrame = 0;
	private int dashParticles = 0;
	private Point[] hairPoints = new Point[5];

	// Drawing Constants
	private static final Point RED_SPRITES = new Point(Constants.SPRITE_WIDTH, 0);
	private static final Point BLUE_SPRITES = new Point(Constants.SPRITE_WIDTH, 8 * Constants.SPRITE_HEIGHT);
	private static final Point GREEN_SPRITES = new Point(Constants.SPRITE_WIDTH, 9 * Constants.SPRITE_HEIGHT);
	private static final Point WHITE_SPRITES = new Point(Constants.SPRITE_WIDTH, 10 * Constants.SPRITE_HEIGHT);
	private static final int WALK_CYCLE = 12;
	private static final Color RED_HAIR = new Color(255, 0, 77);
	private static final Color BLUE_HAIR = new Color(41, 173, 255);
	private static final Color GREEN_HAIR = new Color(0, 228, 54);
	private static final Color WHITE_HAIR = new Color(255, 241, 232);
	private static final int DASH_PARTICLES = 4;
	private static final int DASH_PARTICLE_GAP = 3;
	private static final int WIDTH = 48;
	private static final int HEIGHT = 42;

	private static final int X_COLLISION_OFFSET = 6;
	private static final int Y_COLLISION_OFFSET = 18;

	private static final int VERT_DASH_FRAME = 12;
	private static final int HORZ_DASH_FRAME = 24;
	private static final int WALL_JUMP_FRAME = 16;

	// After dashing into a wall, how many ms for the player to regain control
	private static final int WALL_CANCEL_CONTROL_FRAME = 6;

	// After a wall jump ends, how many ms does the player have low gravity
	private static final int LOW_GRAV_FRAME = 3;

	// After a dash, how many ms until springs bounce at full velocity
	private static final int BOUNCE_RECOVERY_FRAME = 6;

	// How many frames to float in a pure horizontal dash
	private static final int HORZ_DASH_HOVER = 11;

	// How many frames after breaking block horizontally until can control
	private static final int BREAK_CONTROL_TIMER = 9;

	// How many frames of coyote time
	private static final int COYOTE_FRAMES = 7;

	// How much to multiply gravity by when in coyote time
	private static final double COYOTE_ACCEL_COEFF = 1.00;

	// How many frames until Madeline can regain her dash
	private static final int DASH_RECHARGE_FRAMES = 5;

	private static final double MOVEMENT_COEFF = 1.5;
	private static final double GRAVITY = 0.1 * (double) Constants.PIXEL_DIM;
	private static final double TERM_VEL = 0.94 * (double) Constants.PIXEL_DIM * MOVEMENT_COEFF;
	private static final double WALL_VEL = 0.25 * (double) Constants.PIXEL_DIM * MOVEMENT_COEFF;
	private static final double JUMP_VEL = -1.975 * (double) Constants.PIXEL_DIM;
	private static final double WALK_SPEED = 1.0 * (double) Constants.PIXEL_DIM;

	private static final double WALL_JUMP_X_VEL = 1.2 * (double) Constants.PIXEL_DIM;
	private static final double WALL_JUMP_Y_VEL = -1.74 * (double) Constants.PIXEL_DIM;
	private static final double WALL_JUMP_ACCEL_COEFF = 0.90;

	private static final double ACCEL = WALK_SPEED * 0.6;
	private static final double DECCEL = WALK_SPEED * 1.0;
	private static final double DASH_DECCEL = WALK_SPEED * 1.0;

	private static final double BOUNCE_VEL = JUMP_VEL * 1.27;
	private static final double BOUNCE_VEL_REDUCE = 1.0;
	private static final double BOUNCE_DASH_COEFF = 0.3;

	private static final double BREAK_VEL_X = 1.1 * (double) Constants.PIXEL_DIM;
	private static final double BREAK_VEL_Y = -1.1 * (double) Constants.PIXEL_DIM;

	private static final double Y_DASH_VEL = 2.35 * (double) Constants.PIXEL_DIM;
	private static final double DIAG_DASH_Y_COEFF = .97;
	private static final double X_DASH_VEL = Y_DASH_VEL * .53;
	private static final double DIAG_DASH_X_COEFF = 1.25;

	/**
	 * Creates an empty Madeline object
	 */
	public Madeline(LevelComponent level) {
		this.lvl = level;
		this.xPos = 0;
		this.yPos = 0;
		this.collisionObjects = null;
		this.numOfDashesTotal = 1;
		this.isMoving = false;
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

		if (numOfDashesRemaining == 1) {
			hairColor = RED_HAIR;
		} else if (numOfDashesRemaining == 2) {
			hairColor = GREEN_HAIR;
		} else {
			hairColor = BLUE_HAIR;
		}
		for (int i = 0; i < hairPoints.length; i++) {
			hairPoints[i] = new Point(-100, 0);
		}
	}

	/**
	 * Updates Madeline's position based on her current velocity, position, and any
	 * objects she is colliding with and sets her state of collision with objects
	 */
	public void setPosition() {
		lifetime++;
		isCollidingFloor = isCollidingWithFloor();
		isTouchingWallLeft = isTouchingWall(-1);
		isTouchingWallRight = isTouchingWall(1);
		isCollidingCeiling = isCollidingWithCeiling();
		isTouchingWall = isTouchingWallLeft || isTouchingWallRight;
		isTouchingFloor = isTouchingFloor();
		isCollidingWall = isCollidingWithWall();
		setCanSlide();
		if (isCollidingWall) {
			canJump = true;
			lowGrav = false;
			if (wallJump) {
				wallJump = false;
				// yVel = 0;
				frameAtWallJump = lifetime - WALL_JUMP_FRAME;
			}
			if (!canControl && !controlTimerDecreased) {
				dashFrameTimer = WALL_CANCEL_CONTROL_FRAME;
				// controlTimerDecreased = true;
			}
			isDashingVertically = false;
		}
		if (wallSlide) {
			yVelMax = WALL_VEL;
		} else {
			yVelMax = TERM_VEL;
		}
		setHorizontalPosition();
		setVerticalPosition();
		if (isTouchingFloor && !jumpPressed) {
			coyoteTimer = lifetime;
			isCoyote = true;
		}
	}

	/**
	 * Updates Madeline's horizontal position based on her current velocity,
	 * position, and any objects she is colliding with
	 */
	public void setHorizontalPosition() {
		if (!isCollidingWall) {
			xPos = xPos + (int) (xVel);
		}
	}

	/**
	 * Updates Madeline's vertical position based on her current velocity, position,
	 * and any objects she is colliding with
	 */
	public void setVerticalPosition() {
		if (!isCollidingFloor && !isCollidingCeiling) {
			yPos = yPos + (int) (yVel);
		}
	}

	/**
	 * Updates Madeline's velocity based on current accel and deccel values and
	 * player movement data
	 * 
	 * @param hasMoved whether or not the player has pressed a button to move
	 */
	public void setVelocity(boolean hasMoved) {
		this.isMoving = hasMoved;
		setHorizontalVelocity(hasMoved);
		setVerticalVelocity();
	}

	/**
	 * Updates Madeline's horizontal velocity based on current horizontal
	 * acceleration. Also determines facing direction based on whether velocity is
	 * negative or positive
	 * 
	 * @param hasMoved whether or not the player has pressed a button to move
	 */
	public void setHorizontalVelocity(boolean hasMoved) {
		if (xVel > 0) {
			facingRight = 1;
		} else if (xVel < 0) {
			facingRight = -1;
		}
		if ((!hasMoved || Math.abs(xVel) > WALK_SPEED) && !isDashingHorizontally && !wallJump && !breakState) {
			if (useDashDeccel) {
				if (xVel > 0) {
					xVel = Math.max(0, xVel - DASH_DECCEL);
				} else {
					xVel = Math.min(0, xVel + DASH_DECCEL);
				}
			} else {
				if (xVel > 0) {
					xVel = Math.max(0, xVel - DECCEL);
				} else {
					xVel = Math.min(0, xVel + DECCEL);
				}
			}

		}
		if (isCollidingWall && !breakState) {
			xVel = 0;
		}
	}

	/**
	 * Sets Madeline's vertical velocity based on current state and gravity
	 */
	public void setVerticalVelocity() {
		double downAcc = GRAVITY;
		boolean noHover = false;
		if (isCoyote) {
			downAcc *= COYOTE_ACCEL_COEFF;
		}
		if (wallJump) {
			downAcc *= WALL_JUMP_ACCEL_COEFF;
		}
		if (isTouchingFloor && yVel >= 0) {
			yVel = 0;
		}
		if ((isDashingDiagonally && lifetime > frameAtDash + VERT_DASH_FRAME)) {
			noHover = true;
			downAcc *= 1.05;
		}
		if (lowGrav)
			downAcc *= .5;
		if (isCollidingFloor && !isCollidingWall) {
			yVel = 0.0;
		} else if (yVel < yVelMax && (!noGrav || isDashingVertically || isDashingDiagonally)) {
			if (Math.abs(yVel) > 0.5 || noHover) {
				Math.min(yVel += downAcc, TERM_VEL);
			} else {
				Math.min(yVel += (downAcc * 0.5), TERM_VEL);
			}

		} else if (yVel > yVelMax) {
			if (!isDashingVertically) {
				yVel = yVelMax;
			}
		}
		if (isCollidingCeiling && yVel < 0) {
			yVel = 0;
		}
	}

	/**
	 * Updates Madeline's state based on her lifetime and set timers
	 */
	public void checkState() {
		if (lifetime > frameAtDash + DASH_RECHARGE_FRAMES) {
			canRechargeDash = true;
		} else {
			canRechargeDash = false;
		}
		if (lifetime > frameAtDash + BOUNCE_RECOVERY_FRAME) {
			bounceHigh = true;
		}
		if (lifetime > frameAtDash + VERT_DASH_FRAME) {
			isDashingVertically = false;
			canJump = true;
		}
		if (lifetime > frameAtDash + HORZ_DASH_FRAME) {
			isDashingHorizontally = false;
		}
		if (lifetime > coyoteTimer + COYOTE_FRAMES && isCoyote) {
			isCoyote = false;
		} else if (isCoyote) {
			isCoyote = true;
		}
		if (lifetime > frameAtDash + dashFrameTimer) {
			canControl = true;
			controlTimerDecreased = false;
		}
		if (lifetime > frameAtDash + HORZ_DASH_HOVER) {
			noGrav = false;
		}
		if (lifetime > frameAtWallJump + WALL_JUMP_FRAME) {
			if (wallJump) {
				lowGrav = true;
			}
			wallJump = false;

		}
		if (lifetime > frameAtBreak + BREAK_CONTROL_TIMER) {
			breakState = false;
		}
		if (lifetime > frameAtWallJump + WALL_JUMP_FRAME + LOW_GRAV_FRAME) {
			lowGrav = false;
		}
		if (lifetime > frameAtDash + HORZ_DASH_FRAME + 0) {
			useDashDeccel = false;
		}
		if (isTouchingFloor && canRechargeDash) {
			numOfDashesRemaining = numOfDashesTotal;
		}
	}

	/**
	 * Make Madeline jump or wall jump if she is able to
	 */
	public void jump() {
		if ((isTouchingFloor && !jumpPressed && canJump && isFullySpawned && canMove)
				|| (isCoyote && isFullySpawned && canMove && !jumpPressed)) {
			numOfDashesRemaining = numOfDashesTotal;
			yVel = JUMP_VEL;
			jumpPressed = true;
			isCoyote = false;
			coyoteTimer = lifetime - 1;
			AudioPlayer.playFile("jump");
			particles.add(new Particle(xPos, yPos + (3 * Constants.PIXEL_DIM)));
		} else if (isTouchingWall && !jumpPressed && !isTouchingFloor && canJump
				&& (canWallJumpLeft || canWallJumpRight) && !wallJump) {
			jumpPressed = true;
			wallJump = true;
			frameAtWallJump = lifetime;
			if (isTouchingWallRight && canWallJumpRight) {
				xVel = -WALL_JUMP_X_VEL;
			} else if (isTouchingWallLeft && canWallJumpLeft) {
				xVel = WALL_JUMP_X_VEL;
			}
			yVel = WALL_JUMP_Y_VEL;
			if (facingRight > 0) {
				AudioPlayer.playFile("jump_wall_right");
			} else {
				AudioPlayer.playFile("jump_wall_left");
			}
			particles.add(new Particle(xPos, yPos));
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
		if (numOfDashesRemaining > 0 && !(numOfDashesRemaining == 0) && canDash && isFullySpawned && canMove) {
			useDashDeccel = true;
			numOfDashesRemaining--;
			canDash = false;
			wallJump = false;
			frameAtDash = lifetime;
			canControl = false;
			canJump = false;
			bounceHigh = false;
			if (dir.equals("up")) {
				yVel = -Y_DASH_VEL;
				xVel = 0;
				dashFrameTimer = VERT_DASH_FRAME;
				isDashingVertically = true;
			}
			if (dir.equals("down")) {
				yVel = Y_DASH_VEL;
				xVel = 0;
				dashFrameTimer = VERT_DASH_FRAME;
				isDashingVertically = true;
			}
			if (dir.equals("upleft")) {
				yVel = -Y_DASH_VEL * DIAG_DASH_Y_COEFF;
				xVel = -X_DASH_VEL * DIAG_DASH_X_COEFF;
				dashFrameTimer = HORZ_DASH_FRAME;
				isDashingHorizontally = true;
				isDashingVertically = true;
			}
			if (dir.equals("upright")) {
				yVel = -Y_DASH_VEL * DIAG_DASH_Y_COEFF;
				xVel = X_DASH_VEL * DIAG_DASH_X_COEFF;
				dashFrameTimer = HORZ_DASH_FRAME;
				isDashingHorizontally = true;
				isDashingVertically = true;
			}
			if (dir.equals("downleft")) {
				yVel = Y_DASH_VEL * DIAG_DASH_Y_COEFF;
				xVel = -X_DASH_VEL * DIAG_DASH_X_COEFF;
				dashFrameTimer = HORZ_DASH_FRAME;
				isDashingHorizontally = true;
				isDashingVertically = true;
			}
			if (dir.equals("downright")) {
				yVel = Y_DASH_VEL * DIAG_DASH_Y_COEFF;
				xVel = X_DASH_VEL * DIAG_DASH_X_COEFF;
				dashFrameTimer = HORZ_DASH_FRAME;
				isDashingHorizontally = true;
				isDashingVertically = true;
			}
			if (dir.equals("")) {
				xVel = X_DASH_VEL * facingRight * 1.10;
				yVel = 0;
				dashFrameTimer = HORZ_DASH_FRAME;
				isDashingHorizontally = true;
				noGrav = true;
			}
			isDashingDiagonally = isDashingHorizontally && isDashingVertically;
			if (isDashingVertically || isDashingHorizontally)
				dashParticles = DASH_PARTICLES * DASH_PARTICLE_GAP;
			AudioPlayer.playFile("dash");
			return true;
		}
		return false;
	}

	/**
	 * Checks if Madeline is currently colliding with any walls
	 * 
	 * @return true if she is colliding with a wall, otherwise false
	 */
	public boolean isCollidingWithWall() {
		for (int i = 0; i < collisionObjects.size(); i++) {
			if (collisionObjects.get(i).isCollidingWall(xPos + (int) (xVel / 2) + X_COLLISION_OFFSET - facingRight,
					yPos + Y_COLLISION_OFFSET, facingRight)) {
				if (i < collisionObjects.size()) {
					if (facingRight == 1 && (Math.abs(collisionObjects.get(i).getX() - WIDTH + 6 - xPos) < 20))
						xPos = collisionObjects.get(i).getX() - WIDTH + 6;
					else if (Math
							.abs(collisionObjects.get(i).getX() + collisionObjects.get(i).getWidth() + 6 - xPos) < 20)
						xPos = collisionObjects.get(i).getX() + collisionObjects.get(i).getWidth() - 6;
					return true;
				}
			}
			if (cloudVel != 0) {
				if (collisionObjects.get(i).isCollidingWall(xPos + (int) (xVel / 2) + X_COLLISION_OFFSET - facingRight,
						yPos + Y_COLLISION_OFFSET, -facingRight)) {
					if (facingRight == 1 && (Math.abs(collisionObjects.get(i).getX() - WIDTH + 6 - xPos) < 20))
						xPos = collisionObjects.get(i).getX() - WIDTH + 6;
					else if (Math
							.abs(collisionObjects.get(i).getX() + collisionObjects.get(i).getWidth() + 6 - xPos) < 20)
						xPos = collisionObjects.get(i).getX() + collisionObjects.get(i).getWidth() - 6;
				}
			}
		}
		return false;
	}

	/**
	 * determines if the currently colliding object allows Madeline to slide
	 */
	public void setCanSlide() {
		CollisionObject object;
		for (int i = 0; i < collisionObjects.size(); i++) {
			object = collisionObjects.get(i);
			if (object.isCollidingWall(xPos + X_COLLISION_OFFSET, yPos + Y_COLLISION_OFFSET, facingRight) && isMoving) {
				wallSlide = object.getCanSlide();
				return;
			}
		}
		wallSlide = false;
	}

	/**
	 * Checks if Madeline is directly adjacent to a wall (not moving into one)
	 * 
	 * @param side The side of Madeline to check (-1 for left, 1 for right)
	 * @return true if she is colliding with a wall on specified side, otherwise
	 *         false
	 */
	public boolean isTouchingWall(int side) {
		CollisionObject object;
		for (int i = 0; i < collisionObjects.size(); i++) {
			object = collisionObjects.get(i);
			if (object.isCollidingWall(xPos + X_COLLISION_OFFSET + (side * ((0 * Constants.PIXEL_DIM) + 1)),
					yPos + Y_COLLISION_OFFSET, side)) {
				if (side == -1)
					canWallJumpLeft = object.getCanWallJump();
				if (side == 1)
					canWallJumpRight = object.getCanWallJump();
				return true;
			}
			;
		}
		if (side == -1)
			canWallJumpLeft = false;
		if (side == 1)
			canWallJumpRight = false;
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
				if (Math.abs(collisionObjects.get(i).getY() - HEIGHT - yPos) < 20)
					yPos = collisionObjects.get(i).getY() - HEIGHT;
				lvl.removeLevelDisplay();
				isFullySpawned = true;
				particles.add(new Particle(xPos, yPos + (3 * Constants.PIXEL_DIM)));
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a floor just below Madeline Different from Colliding
	 * because it does not factor in Madeline's velocity. This is important because
	 * coyote time requires setting Madeline's y velocity to 0 while touching the
	 * floor which disables floor collision This method can be used instead when a
	 * condition requires that Madeline is constantly touching a floor
	 * 
	 * @return true if there is a floor below, otherwise false
	 */
	public boolean isTouchingFloor() {
		for (int i = 0; i < collisionObjects.size(); i++) {
			if (collisionObjects.get(i).isCollidingFloor(xPos + X_COLLISION_OFFSET, yPos + 1 + Y_COLLISION_OFFSET)) {
				isFullySpawned = true;
				lvl.removeLevelDisplay();
				return true;
			}
		}
		cloudVel = 0;
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
				if (Math.abs(collisionObjects.get(i).getY() + collisionObjects.get(i).getHeight() - 18 - yPos) < 20)
					yPos = collisionObjects.get(i).getY() + collisionObjects.get(i).getHeight() - 18;
				return true;
			}
		}
		return false;
	}

	/**
	 * Draws Madeline onto the screen. Madeline is 48 pixels wide by 42 pixels tall.
	 * 
	 * @param Graphics2D g2
	 */
	public void drawOn(Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		// updates Madeline's hair color based on her number of dashes remaining
		if (numOfDashesRemaining == 1) {
			spritePoint = RED_SPRITES;
			hairColor = RED_HAIR;
		} else if (numOfDashesRemaining == 2) {
			if (hairSwitchFrame == 5) {
				hairSwitchFrame = 0;
				if (spritePoint.equals(GREEN_SPRITES)) {
					spritePoint = WHITE_SPRITES;
					hairColor = WHITE_HAIR;
				} else {
					spritePoint = GREEN_SPRITES;
					hairColor = GREEN_HAIR;
				}
			} else {
				hairSwitchFrame++;
			}
		} else {
			spritePoint = BLUE_SPRITES;
			hairColor = BLUE_HAIR;
		}
		if (canMove) {
			if (isMoving && animationCounter != (3 * WALK_CYCLE) - 1) {
				animationCounter++;
			} else {
				animationCounter = 0;
			}
			if (isTouchingFloor && lookingUp) {
				walkFrame = 6;
			} else if (isTouchingFloor && lookingDown) {
				walkFrame = 5;
			} else if (isMoving && isTouchingFloor && !isDashingHorizontally && !isDashingVertically) {
				walkFrame = (animationCounter / WALK_CYCLE) + 1;
			} else {
				if (isTouchingFloor) {
					walkFrame = 0;
				} else if (wallSlide) {
					walkFrame = 4;
				} else {
					walkFrame = 2;
				}
			}
		} else {
			walkFrame = 6;
		}

		updateHair(g2);

		// duplicates the Graphics2D object so that transformations don't affect other
		// objects
		Graphics2D g2p = (Graphics2D) g2.create();

		g2.translate(roundPos(xPos), roundPos(yPos) - Constants.PIXEL_DIM);
		if (facingRight < 0)
			g2.translate(Constants.SPRITE_WIDTH, 0);
		g2.drawImage(MainApp.SCALED_MAP, 0, 0, (facingRight * Constants.SPRITE_WIDTH), Constants.SPRITE_HEIGHT,
				(int) spritePoint.getX() + (walkFrame * Constants.SPRITE_WIDTH), (int) spritePoint.getY() + 1,
				(int) spritePoint.getX() + (Constants.SPRITE_WIDTH * (walkFrame + 1)),
				(int) spritePoint.getY() + Constants.SPRITE_HEIGHT, null);
		drawParticles(g2p);
	}

	/**
	 * Iterates through particle list and draws each particle
	 * 
	 * @param g2 graphics object to draw on
	 */
	private void drawParticles(Graphics2D g2) {
		if (dashParticles > 0) {
			if (dashParticles % DASH_PARTICLE_GAP == 0) {
				particles.add(new Particle(xPos, yPos));
			}
			dashParticles--;
		}
		// Iterates through list in reverse order so removing particles doesn't cause
		// out of bounds exception
		for (int i = particles.size() - 1; i >= 0; i--) {
			if (particles.get(i).getFrame() == Particle.LIFETIME + 1) {
				particles.remove(i);
				continue;
			}
			particles.get(i).drawOn(g2);
		}
	}

	/**
	 * Updates the hair points based on preceding hair points position and
	 * Madeline's position and then draws them
	 * 
	 * @param g2 the graphics object to draw on
	 */
	private void updateHair(Graphics2D g2) {
		for (int i = hairPoints.length - 1; i >= 0; i--) {
			if (i != 0) {
				hairPoints[i].setLocation(hairPoints[i - 1].getX(), hairPoints[i - 1].getY());
				continue;
			}
			hairPoints[i].setLocation((double) roundPos(xPos), (double) roundPos(yPos));
		}

		int radius = 1;
		for (int i = 0; i < hairPoints.length; i++) {
			if (i <= 1) {
				radius = 2;
			} else {
				radius = 1;
			}
			drawCircle((int) hairPoints[i].getX(), (int) hairPoints[i].getY(), radius, hairColor, g2);
		}

	}

	/**
	 * Draws a circle at specified x, y, and radius Radius must be within 1 and 3
	 * 
	 * Yes, this is all hardcoded, yes the original game does it this way. We don't
	 * need a complex algorithm to make pixely circles when there are only ever 3
	 * radii to consider
	 * 
	 * @param x position of circle
	 * @param y position of circle
	 * @param r radius of circle
	 * @param c color of circle
	 */
	private void drawCircle(int x, int y, int r, Color c, Graphics2D g2) {
		g2 = (Graphics2D) g2.create();
		g2.translate(Constants.SPRITE_WIDTH / 2 + (-facingRight * 2 * Constants.PIXEL_DIM), 2 * Constants.PIXEL_DIM);
		g2.setColor(c);
		if (r == 1) {
			g2.fillRect(x - (1 * Constants.PIXEL_DIM), y, 3 * Constants.PIXEL_DIM, 1 * Constants.PIXEL_DIM);
			g2.fillRect(x, y - (1 * Constants.PIXEL_DIM), 1 * Constants.PIXEL_DIM, 3 * Constants.PIXEL_DIM);
		} else if (r == 2) {
			g2.fillRect(x - (2 * Constants.PIXEL_DIM), y - Constants.PIXEL_DIM, 5 * Constants.PIXEL_DIM,
					3 * Constants.PIXEL_DIM);
			g2.fillRect(x - Constants.PIXEL_DIM, y - (2 * Constants.PIXEL_DIM), 3 * Constants.PIXEL_DIM,
					5 * Constants.PIXEL_DIM);
		} else if (r == 3) {
			g2.fillRect(x - (3 * Constants.PIXEL_DIM), y - (1 * Constants.PIXEL_DIM), 7, 3);
			g2.fillRect(x - (1 * Constants.PIXEL_DIM), y - (3 * Constants.PIXEL_DIM), 3, 7);
			g2.fillRect(x - (2 * Constants.PIXEL_DIM), y - (2 * Constants.PIXEL_DIM), 5, 5);
		}
	}

	/*
	 * Sets Madeline's looking direction. if both, default to down
	 */
	public void setLooking(boolean lookingUp, boolean lookingDown) {
		if (lookingUp && lookingDown)
			lookingUp = false;
		this.lookingUp = lookingUp;
		this.lookingDown = lookingDown;
	}

	/**
	 * Rounds a position to the nearest game pixel (6 JFrame pixels)
	 * 
	 * @param toRound the position to round
	 * @return the rounded position
	 */
	public static int roundPos(int toRound) {
		if (toRound % Constants.PIXEL_DIM <= 2) {
			return (toRound - (toRound % Constants.PIXEL_DIM));
		} else {
			return (toRound + (Constants.PIXEL_DIM - (toRound % Constants.PIXEL_DIM)));
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
		lvl.addNewStrawberry(x, y - 10, false);
		if (isDashingHorizontally) {
			frameAtBreak = lifetime;
			breakState = true;
			xVel = -facingRight * BREAK_VEL_X;
		}
		yVel = BREAK_VEL_Y;
		// yPos -= 10;
	}

	/**
	 * Triggers when a chest is opened to instantiate a strawberry at designated
	 * position
	 * 
	 * @param x horizontal position of strawberry
	 * @param y vertical position of strawberry
	 */
	public void openChest(int x, int y) {
		lvl.addNewStrawberry(x, y - 30, false);
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

	public void setCloudsPink() {
		lvl.setCloudsPink();
	}

	/**
	 * Stops all timers on currently active objects. Will be deprecated
	 */
	public void stopAllTimers() {
		for (CollisionObject c : collisionObjects) {
			c.stopAllTimers();
		}
	}

	/**
	 * Increases Madeline's X velocity
	 */
	public void increaseX() {
		if (!wallJump && canControl && !breakState && isFullySpawned && canMove) {
			xVel = Math.min(WALK_SPEED, xVel + ACCEL);
		}
	}

	/**
	 * Decreases Madeline's X velocity
	 */
	public void decreaseX() {
		if (!wallJump && canControl && !breakState && isFullySpawned && canMove) {
			xVel = Math.max(-WALK_SPEED, xVel - ACCEL);
		}
	}

	/**
	 * resets the level upon death
	 */
	public void death() {
		if (!isFullySpawned || dying)
			return;
		dying = true;
		AudioPlayer.playFile("death");
		lvl.resetLevel();
	}

	/**
	 * Collects the levels strawberry, deleting it from the level and resetting the
	 * players dashes
	 */
	public void collectStrawberry() {
		if (breakState)
			return;
		AudioPlayer.playFile("strawberrycollect");
		resetDashes();
		lvl.collectStrawberry();
	}

	/**
	 * returns true if the player is dashing either vertically or horizontally
	 * 
	 * @return
	 */
	public boolean getIsDashing() {
		return isDashingVertically || isDashingHorizontally;
	}

	/**
	 * Increases the players y velocity by factors determined by spring bounce
	 * velocity
	 */
	public void springBounce() {
		AudioPlayer.playFile("spring");
		if (bounceHigh) {
			yVel = BOUNCE_VEL;
		} else {
			yVel = BOUNCE_VEL * BOUNCE_DASH_COEFF;
		}
		yPos -= 4 * Constants.PIXEL_DIM;
		xVel *= BOUNCE_VEL_REDUCE;
		numOfDashesRemaining = numOfDashesTotal;
		dashFrameTimer = lifetime - 1000;
	}

	public void resetDashes() {
		this.numOfDashesRemaining = numOfDashesTotal;
	}

	public void resetVelocity() {
		this.xVel = 0;
		this.yVel = 0;
	}

	/**
	 * Makes the level component go to the next level
	 */
	public void nextLevel() {
		if (!canContinue)
			return;
		canContinue = false;
		lvl.nextLevel();
	}

	public void setCanDash(boolean option) {
		canDash = option;
	}

	public double getYVelocity() {
		return yVel;
	}

	public double getXVelocity() {
		return xVel;
	}

	/**
	 * Moves Madeline with the cloud she is standing on
	 * 
	 * @param x the x value to increment position by
	 */
	public void moveWithCloud(int x) {
		this.xPos += x;
		cloudVel = x;
	}

	public Color getHairColor() {
		if (numOfDashesRemaining == 1) {
			hairColor = RED_HAIR;
		} else if (numOfDashesRemaining == 2) {
			hairColor = GREEN_HAIR;
		} else {
			hairColor = BLUE_HAIR;
		}
		return hairColor;
	}

	/**
	 * Displays the score text at the end of the game
	 */
	public void displayFinalText() {
		lvl.finalScore();
	}

	/**
	 * Performs the actions associated with opening the big chest
	 * 
	 * @param x the x location to spawn the gem at
	 * @param y the y location to spawn the gem at
	 */
	public void openBigChest(int x, int y) {
		this.canMove = false;
		xVel = 0;
		yVel = 0;
		Color bgColor1 = new Color(171, 81, 51);
		Color bgColor2 = new Color(93, 87, 77);
		Color bgColor3 = Color.BLACK;
		Color bgColor4 = new Color(28, 42, 80);
		Color bgColor5 = new Color(125, 36, 81);
		Color bgColor6 = new Color(0, 134, 80);
		final Color[] backgroundColorList = { bgColor1, bgColor2, bgColor3, bgColor4, bgColor5, bgColor6 };
		Timer t = new Timer(150, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerI++;
				if (timerI >= backgroundColorList.length) {
					timerI = 0;
				}
				lvl.setBackgroundColor(backgroundColorList[timerI]);
			}
		});
		Timer stopTimer = new Timer(2500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t.setRepeats(false);
				t.stop();
				lvl.spawnNewGem(x, y);
				lvl.setBackgroundColor(new Color(125, 36, 81));
				lvl.setCloudsPink();
				canMove = true;
			}
		});
		t.start();
		stopTimer.setRepeats(false);
		stopTimer.start();
	}

	public int getCurrentDashNum() {
		return numOfDashesRemaining;
	}

	public int getTotalDashNum() {
		return numOfDashesTotal;
	}
}
