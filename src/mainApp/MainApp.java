package mainApp;

import java.awt.Color;
import java.awt.Dimension;
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

	private final Set<Integer> pressedKeys = new HashSet<>();
	private LevelComponent lvl;
	private JFrame frame;
	private JFrame editor;
	private int frameSize = 768;
	private int strawberryCount;
	private int deathCount;
	private int currentLevel;
	private boolean strawberryAlreadyCollected;
	private boolean canMoveLevels;
	private Set<Integer> checkPressedKeys;

	
	private boolean inEditor;
	private boolean canSwitchEditor;
	
	public MainApp() {
		// sets default values
		canMoveLevels = true;
		canSwitchEditor = true;
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
		lvl = new LevelComponent(this, currentLevel + "", strawberryAlreadyCollected);
		frame.add(lvl);
		frame.setVisible(true);
		
		editor = new JFrame();
		editor.addKeyListener(this);
		editor.getContentPane().setPreferredSize(new Dimension(frameSize + 850, frameSize));
		editor.setResizable(false);
		editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editor.getContentPane().setBackground(Color.BLACK);
		editor.pack();
		editor.setVisible(false);
		
		LevelEditor levelEditor = new LevelEditor();
		editor.add(levelEditor);
		
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				levelEditor.doMouseClick(e.getX(), e.getY());
			}
		});
		// creates a timer that fires every 10 milliseconds. This acts as our main game loop.
		Timer t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				checkToggleEditor();
				checkMoveLevels();
				updateMadelinePosition();
				lvl.updateAnimations();
				
				frame.repaint();
				editor.repaint();
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
        pressedKeys.remove(e.getKeyCode());
    }
    
    public void keyTyped(KeyEvent e) {}
    
	/**
	 * Refreshes the level to whatever currentLevel indicates
	 */
	private void levelRefresh() {
		// ensures that a button held before the user can move still fires once they are
		// able to move
		checkPressedKeys = new HashSet<>();

		// resets the level to currentLevel
		frame.remove(lvl);
		lvl = new LevelComponent(this, currentLevel + "", strawberryAlreadyCollected);
		frame.add(lvl);
		frame.setVisible(true);

		// starts a 1/4 second timer that, once completed, allows Madeline to move
		Timer madelineMoveTimer = new Timer(250, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPressedKeys = pressedKeys;
			}
		});
		madelineMoveTimer.setRepeats(false);
		madelineMoveTimer.restart();
	}

	/**
	 * Resets the current level to it's default state, with the exception of
	 * Strawberries and Breakable blocks which remain in their pre-reset state
	 */
	public void resetLevel() {
		deathCount++;
		levelRefresh();
	}

	/**
	 * Moves to the next level sequentially
	 */
	public void nextLevel() {
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
     * Updates Madeline's current position
     */
    private void updateMadelinePosition()
    {
    	Boolean hasMoved = false;
    	// 68 is d, 39 is right arrow
    	if (pressedKeys.contains(68) || pressedKeys.contains(39))
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
				nextLevel();
				canMoveLevels = false;
			}
			// 79 is o
			else if (pressedKeys.contains(79)) {
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