package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import javax.swing.JFrame;

/**
 * Class: MainApp
 * @author F24_A304
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp implements KeyListener{
	
	private final Set<Integer> pressedKeys = new HashSet<>();
	private LevelComponent lvl;
	private JFrame frame;
	private int frameSize = 768;
	private int strawberryCount;
	private int currentLevel;
	private boolean strawberryAlreadyCollected;
	
	public MainApp() {
		// sets default values
		currentLevel = 3;
		strawberryAlreadyCollected = false;
		frame = new JFrame();
		// adds this to the frame in order to listen for keyboard input
		frame.addKeyListener(this);
		// sets the preferred size of the inner contentPane to (768, 768)
		// frame.setPreferredSize() includes the borders so it was not desirable
		frame.getContentPane().setPreferredSize(new Dimension(frameSize, frameSize));
		// make the frame non-resizable so that drawn objects remain in the correct position
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.pack();
		lvl = new LevelComponent(this, currentLevel + "", strawberryAlreadyCollected);
		frame.add(lvl);
		frame.setVisible(true);
		// creates a timer that fires every 10 milliseconds. This acts as our main game loop.
		Timer t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateMadelinePosition();
				lvl.updateAnimations();
				frame.repaint();
			}
		});
		t.start();
	}
	
	/**
	 * Resets the current level to it's default state, 
	 * with the exception of Strawberries and Breakable blocks which remain in their pre-reset state
	 */
	public void resetLevel()
	{
		frame.remove(lvl);
		lvl = new LevelComponent(this, currentLevel + "", strawberryAlreadyCollected);
		frame.add(lvl);
		Timer t = new Timer(250, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(true);
			}
		});
		t.setRepeats(false);
		t.start();
		
	}
	
	/**
	 * Handles pressing a key
	 */
	@Override
    public synchronized void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }
	
	/**
	 * Handles releasing a key
	 */
    @Override
    public synchronized void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == 74)
    	{
    		lvl.setMadelineJumpPressed(false);
    	}
        pressedKeys.remove(e.getKeyCode());
    }
    
    public void keyTyped(KeyEvent e) {}
    
    /**
     * Adds to the Strawberry counter and sets the strawberry in that level to collected
     */
    public void collectStrawberry()
    {
    	strawberryAlreadyCollected = true;
    	strawberryCount++;
    }
    
    /**
     * Moves to the next level sequentially
     */
    public void nextLevel()
    {
    	strawberryAlreadyCollected = false;
    	frame.remove(lvl);
    	currentLevel++;
    	lvl = new LevelComponent(this, currentLevel + "", strawberryAlreadyCollected);
    	frame.add(lvl);
    	frame.setVisible(true);
    }
    
    /**
     * Updates Madeline's current position
     */
    private void updateMadelinePosition()
    {
    	// 68 is d
    	if (pressedKeys.contains(68))
		{
			lvl.moveMadelineRight();
		}
    	// 65 is s
		if (pressedKeys.contains(65))
		{
			lvl.moveMadelineLeft();
		}
		checkJump();
    	checkDash();
    	lvl.moveMadelineVertically();
    	lvl.moveMadelineHorizontally();
    }
    
    private void checkJump()
    {
    	// 74 is j
    	if (pressedKeys.contains(74))
		{
			lvl.madelineJump();
		}
    }
    
    /**
     * Checks if the dash button is being held. If it is, calls LevelComponent's Dash method in the appropriate direction
     */
    private void checkDash()
    {
    	lvl.checkIfDashing();
		// 75 is k
		if (pressedKeys.contains(75))
		{
			if (pressedKeys.contains(87) && pressedKeys.contains(68))
			{
				lvl.dash("upright");
			}
			else if (pressedKeys.contains(87) && pressedKeys.contains(65))
			{
				lvl.dash("upleft");
			}
			else if (pressedKeys.contains(68) && pressedKeys.contains(83))
			{
				lvl.dash("downright");
			}
			else if (pressedKeys.contains(65) && pressedKeys.contains(83))
			{
				lvl.dash("downleft");
			}
			else if (pressedKeys.contains(83))
			{
				lvl.dash("down");
			}
			else if (pressedKeys.contains(87))
			{
				lvl.dash("up");
			}
			else
			{
				lvl.dash("");
			}

		}
    }
    
	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		new MainApp();	
	} // main

}