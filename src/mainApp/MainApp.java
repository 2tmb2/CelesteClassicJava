package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;
import javax.swing.JFrame;

/**
 * Class: MainApp
 * 
 * @author F24_A304 <br>
 *         Purpose: Top level class for CSSE220 Project containing main method
 *         <br>
 *         Restrictions: None
 */
public class MainApp implements KeyListener {
	
	//The holiest magic number
	public static final int PIXEL_DIM = 6;
	
	public static final int BETWEEN_FRAMES = 22;

	private final Set<Integer> pressedKeys = new HashSet<>();
	private LevelComponent lvl;
	private ErrorDisplay err;
	private JFrame frame;
	private JFrame editor;
	private LevelEditor levelEditor;
	private int frameSize = 768;
	private int strawberryCount;
	private int deathCount;
	private int currentLevel;
	private boolean strawberryAlreadyCollected;
	private boolean canMoveLevels;
	private Set<Integer> checkPressedKeys;

	public static long time = System.currentTimeMillis();
	public static long startTime = time;
	public static long deltaTime = 0;
	public static long previousTime = time;
	
	private boolean inEditor;
	private boolean canSwitchEditor;
	private boolean isInitialSpawn;
	private boolean mouseDown = false;
	private boolean byFrame = false;
	
	
	public MainApp() {
		// sets default values
		canMoveLevels = true;
		canSwitchEditor = true;
		isInitialSpawn = true;
		currentLevel = 1;
		strawberryAlreadyCollected = false;
		inEditor = false;
		deathCount = 0;
		strawberryCount = 0;
		checkPressedKeys = pressedKeys;
		frame = new JFrame();
		// adds this to the frame in order to listen for keyboard input
		frame.addKeyListener(this);
		// sets the preferred size of the inner contentPane to (768, 768)
		// frame.setPreferredSize() includes the borders so it was not desirable
		frame.getContentPane().setPreferredSize(new Dimension(frameSize, frameSize));
		// make the frame non-resizable so that drawn objects remain in the correct
		// position
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.pack();
		lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected);
		levelRefresh();
		
		editor = new JFrame();
		editor.addKeyListener(this);
		editor.getContentPane().setPreferredSize(new Dimension(frameSize + 850, frameSize));
		editor.setResizable(false);
		editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editor.getContentPane().setBackground(Color.BLACK);
		editor.pack();
		editor.setVisible(false);
		
		levelEditor = new LevelEditor(this);
		editor.add(levelEditor);
		
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDown = true;
				levelEditor.doMouseClick(e.getX(), e.getY());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseDown = false;
				levelEditor.doMouseRelease(e.getX(), e.getY());
			}
		});
		// creates a timer that fires every 33 milliseconds. This acts as our main game loop.
		Timer t = new Timer(BETWEEN_FRAMES, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!byFrame) {
					time += BETWEEN_FRAMES;
					//deltaTime = time - previousTime;
					//if (deltaTime < 31) return;
					//previousTime = time;
					update();
				}
			}
		});
		t.start();
	}
	
	/**
	 * Handles pressing a key
	 */
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		if (e.getKeyCode() == 70) { //70 is f
			byFrame = !byFrame;
		}
		if (byFrame) {
			if (e.getKeyCode() == 71) { //71 is g
				update();
			}
		}
	}

	@Override
    public synchronized void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == 74 || e.getKeyCode() == 67)
    	{
    		lvl.setMadelineJumpPressed(false);
    	}
    	if (e.getKeyCode() == 79 || e.getKeyCode() == 80)
    	{
    		canMoveLevels = true;
    	}
    	if (e.getKeyCode() == 76) {
    		canSwitchEditor = true;
    	}
    	if (e.getKeyCode() == 75 || e.getKeyCode() == 88)
    	{
    		lvl.setMadelineCanDash(true);
    	}
        pressedKeys.remove(e.getKeyCode());
    }
	
	private void update() {
		if (mouseDown && inEditor) {
			levelEditor.doMouseHold((int)MouseInfo.getPointerInfo().getLocation().getX(), (int)MouseInfo.getPointerInfo().getLocation().getY());
		}
		checkToggleEditor();
		checkMoveLevels();
		updateMadelinePosition();
		lvl.updateAnimations();
		
		frame.repaint();
		editor.repaint();
	}
	
	public Set<Integer> getKeys() {
		return pressedKeys;
	}
    
    public void keyTyped(KeyEvent e) {}
    
	/**
	 * Refreshes the level to whatever currentLevel indicates
	 */
	private void levelRefresh() {
		lvl.stopAllTimers();
		// ensures that a button held before the user can move still fires once they are
		// able to move
		checkPressedKeys = new HashSet<>();

		// resets the level to currentLevel
			
		frame.remove(lvl);
		lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected);
		if (err == null)
		{
			frame.add(lvl);
		}
		frame.setVisible(true);
		Timer madelineMoveTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPressedKeys = pressedKeys;
				lvl.removeLevelDisplay();
			}
		});
		if (isInitialSpawn)
		{
			lvl.addLevelDisplay(currentLevel + "00 m");
			madelineMoveTimer.setInitialDelay(1000);
		}
		else
		{
			madelineMoveTimer.setInitialDelay(400);
		}
		lvl.resetMadelineVelocity();
		// starts a 1/4 second timer that, once completed, allows Madeline to move
		madelineMoveTimer.setRepeats(false);
		madelineMoveTimer.restart();
	}

	/**
	 * Resets the current level to it's default state, with the exception of
	 * Strawberries and Breakable blocks which remain in their pre-reset state
	 */
	public void resetLevel() {
		deathCount++;
		isInitialSpawn = false;
		levelRefresh();
	}

	/**
	 * Moves to the next level sequentially
	 */
	public void nextLevel() {
		isInitialSpawn = true;
		strawberryAlreadyCollected = false;
		if (currentLevel < 30) {
			currentLevel++;
		}
		levelRefresh();
	}

	/**
	 * Moves to the previous level
	 */
	public void previousLevel() {
		isInitialSpawn = true;
		strawberryAlreadyCollected = false;
		if (currentLevel > 1) {
			currentLevel--;
		}
		levelRefresh();
	}
    
    /**
     * Adds to the Strawberry counter and sets the strawberry in that level to collected
     */
    public void collectStrawberry()
    {
    	strawberryAlreadyCollected = true;
    	strawberryCount++;
    }
    
    /**
     * Displays an error on the screen
     * @param error
     */
    public void displayError(String error)
    {
    	frame.getContentPane().removeAll();
    	err = new ErrorDisplay(error);
    	frame.add(err);
    	frame.setVisible(true);
    	frame.repaint();
    }
    
    /**
     * Updates Madeline's current position
     */
    private void updateMadelinePosition()
    {
    	Boolean hasMoved = false;
    	// 68 is d, 39 is right arrow
    	if (checkPressedKeys.contains(68) || checkPressedKeys.contains(39))
		{
			lvl.moveMadelineRight();
			hasMoved = true;
		}
		// 65 is s, 37 is left arrow
		if (checkPressedKeys.contains(65) || checkPressedKeys.contains(37)) {
			lvl.moveMadelineLeft();
			hasMoved = true;
		}
		checkJump();
		checkDash();
		lvl.moveMadeline(hasMoved);
	}

	private void checkJump() {
		// 74 is j, 67 is c
		if (checkPressedKeys.contains(74) || checkPressedKeys.contains(67)) {
			lvl.madelineJump();
		}
	}

	/**
	 * Checks if the dash button is being held. If it is, calls LevelComponent's
	 * Dash method in the appropriate direction
	 */
	private void checkDash() {
		lvl.checkIfDashing();
		// 75 is k, 88 is x
		if (checkPressedKeys.contains(75) || checkPressedKeys.contains(88)) {
			if ((checkPressedKeys.contains(87) && checkPressedKeys.contains(68))
					|| (checkPressedKeys.contains(38) && checkPressedKeys.contains(39))) {
				lvl.dash("upright");
			} else if ((checkPressedKeys.contains(87) && checkPressedKeys.contains(65))
					|| (checkPressedKeys.contains(38) && checkPressedKeys.contains(37))) {
				lvl.dash("upleft");
			} else if ((checkPressedKeys.contains(68) && checkPressedKeys.contains(83))
					|| (checkPressedKeys.contains(40) && checkPressedKeys.contains(39))) {
				lvl.dash("downright");
			} else if ((checkPressedKeys.contains(65) && checkPressedKeys.contains(83))
					|| (checkPressedKeys.contains(40) && checkPressedKeys.contains(37))) {
				lvl.dash("downleft");
			} else if (checkPressedKeys.contains(83) || checkPressedKeys.contains(40)) {
				lvl.dash("down");
			} else if (checkPressedKeys.contains(87) || checkPressedKeys.contains(38)) {
				lvl.dash("up");
			} else {
				lvl.dash("");
			}

		}
	}

	/**
	 * Allows level select via the p and o keys
	 */
	private void checkMoveLevels() {
		if (canMoveLevels) {
			// pressedKeys is checked instead of checkPressedKeys to allow the user to
			// switch to alternate levels without waiting for the spawn timer
			
			// 80 is p
			if (pressedKeys.contains(80)) {
				if (err != null)
				{
					frame.remove(err);
					err = null;
				}
				nextLevel();
				canMoveLevels = false;	
			}
			// 79 is o
			else if (pressedKeys.contains(79)) {
				if (err != null)
				{
					frame.remove(err);
					err = null;
				}
				previousLevel();
				canMoveLevels = false;
			}
		}
	}
    
    
    private void checkToggleEditor() {
    	if (canSwitchEditor) {
    		// 76 is l
	    	if (pressedKeys.contains(76)) {
	    		inEditor = !inEditor;
	    		if (!inEditor) {
		    		canSwitchEditor = false;
		    		frame.setVisible(true);
		    		editor.setVisible(false);
		    	} else {
		    		canSwitchEditor = false;
		    		frame.setVisible(false);
		    		editor.setVisible(true);
		    	}
	    	}
    	}
    	
    }

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		new MainApp();
	} // main

}