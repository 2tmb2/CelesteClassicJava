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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import TextElements.ErrorDisplay;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.JFileChooser;
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
	// scaled map must be public because it is the image used by every other class's drawing methods.
	// This avoids reading the same file in over and over again.
	public static BufferedImage SCALED_MAP;
	
	
	public static final int BETWEEN_FRAMES = 22;
	public static final double FRAME_COEFF = (double)BETWEEN_FRAMES / 33.0;
	
	private static final Color BACKGROUND_PINK = new Color(126, 37, 83);
	private static final Color BACKGROUND_BLACK = new Color(0, 0, 0);
	private static final Color BLUE_CLOUDS = new Color(29, 43, 83);
	private static final Color PINK_CLOUDS = new Color(255, 119, 168);
	private Color cloudColor;
	private final Set<Integer> pressedKeys = new HashSet<>();
	private LevelComponent lvl;
	private ErrorDisplay err;
	private JFrame frame;
	private JFrame editor;
	private LevelEditor levelEditor;
	private MenuComponent menu;
	private int frameSize = 768;
	private int strawberryCount;
	private int deathCount;
	private int currentLevel;
	private boolean strawberryAlreadyCollected;
	private boolean canMoveLevels;
	private boolean gameStarted;
	private boolean errorIsDisplayed;
	private boolean isCustomLevel;
	private ArrayList<Cloud> clouds;

	private boolean inEditor;
	private boolean canSwitch;
	private boolean mouseDown = false;
	private boolean byFrame = false;
	private boolean muted = false;
	
	private String filePath;
	private String fileName;
	
	private long startTime = System.currentTimeMillis();
	private long endTime;
	
	private Timer updateTimer;
	
	public MainApp() {
		// sets default values
		cloudColor = BLUE_CLOUDS;
		try {
			SCALED_MAP = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		menu = new MenuComponent();
		gameStarted = false;
		canMoveLevels = true;
		canSwitch = true;
		errorIsDisplayed = false;
		currentLevel = 1;
		strawberryAlreadyCollected = false;
		inEditor = false;
		deathCount = 0;
		strawberryCount = 0;
		endTime = 0;
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
		frame.getContentPane().add(menu);
		frame.setVisible(true);
		clouds = new ArrayList<Cloud>();
        for (int i = 0; i <= 16; i++)
        {
        	clouds.add(new Cloud(cloudColor));
        }
        
		
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
		updateTimer = new Timer(11, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!byFrame) {
					update();
				}
			}
		});
		
		AudioPlayer.playFile("beyondtheheart", Clip.LOOP_CONTINUOUSLY, -20.0f);
	}
	
	/**
	 * Handles pressing a key
	 */
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		if (errorIsDisplayed && e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			frame.getContentPane().removeAll();
			frame.add(menu);
			frame.setVisible(true);
			frame.repaint();
			errorIsDisplayed = false;
		}
		else if (gameStarted == true)
		{
			pressedKeys.add(e.getKeyCode());
			if (e.getKeyCode() == KeyEvent.VK_F) {
				byFrame = !byFrame;
			}
			if (byFrame) {
				if (e.getKeyCode() == KeyEvent.VK_G) {
					update();
					update();
				}
			}
		}
		else if (!errorIsDisplayed && !gameStarted)
		{
			
			if (e.getKeyCode() == KeyEvent.VK_S)
			{
				gameStarted = true;
				mainGame();
			}
			else if (e.getKeyCode() == KeyEvent.VK_L)
			{
				updateTimer.start();
				inEditor = !inEditor;
	    		if (!inEditor) {
		    		canSwitch = false;
		    		frame.setVisible(true);
		    		editor.setVisible(false);
		    	} else {
		    		canSwitch = false;
		    		frame.setVisible(false);
		    		editor.setVisible(true);
		    	}
			}
			else if (e.getKeyCode() == KeyEvent.VK_T)
			{
				loadCustomLevel();
			}
		}
	}

	@Override
    public synchronized void keyReleased(KeyEvent e) {
		if (gameStarted == true)
		{
			if (e.getKeyCode() == KeyEvent.VK_J || e.getKeyCode() == KeyEvent.VK_C)
	    	{
	    		lvl.setMadelineJumpPressed(false);
	    	}
	    	if (e.getKeyCode() == KeyEvent.VK_O || e.getKeyCode() == KeyEvent.VK_P)
	    	{
	    		startTime = 0;
	    		canMoveLevels = true;
	    	}
	    	if (e.getKeyCode() == KeyEvent.VK_L) {
	    		canSwitch = true;
	    	}
	    	if (e.getKeyCode() == KeyEvent.VK_K || e.getKeyCode() == KeyEvent.VK_X)
	    	{
	    		lvl.setMadelineCanDash(true);
	    	}
	        pressedKeys.remove(e.getKeyCode());
		}
    }
	
	private void update() {
		if (mouseDown && inEditor) {
			levelEditor.doMouseHold((int)MouseInfo.getPointerInfo().getLocation().getX(), (int)MouseInfo.getPointerInfo().getLocation().getY());
		}
		if (!errorIsDisplayed && !inEditor && gameStarted)
		{
			checkToggles();
			checkMoveLevels();
			updateMadelinePosition();
			updateMadelineVelocity();
			lvl.updateAnimations();
			frame.repaint();
			editor.repaint();
		}
		else if (inEditor)
		{
			editor.repaint();
		}
	}
	
	public Set<Integer> getKeys() {
		return pressedKeys;
	}
    
    public void keyTyped(KeyEvent e) {}
    
    private void mainGame() {
    	frame.remove(menu);
    	isCustomLevel = false;
    	lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected, clouds, endTime - startTime, strawberryCount, deathCount, (startTime == 0));
		mainGameLevelRefresh();	
		updateTimer.start();
		
    }
    
    private void loadCustomLevel() {
    	final JFileChooser fc = new JFileChooser();
    	fc.showOpenDialog(null);
    	frame.requestFocus();
    	filePath = fc.getSelectedFile().getPath();
    	fileName = fc.getSelectedFile().getName();
    	if (fc.getSelectedFile() != null && fileName.substring(fileName.length() - 3).equals("txt"))
    	{
    		frame.remove(menu);
    		gameStarted = true;
    		lvl = new LevelComponent(this, filePath, fileName, clouds, endTime - startTime, strawberryCount);
    		isCustomLevel = true;
    		customLevelRefresh();
    		updateTimer.start();
    	}
    	else if (fc.getSelectedFile() == null)
    	{
    		frame.requestFocus();
    	}
    	else
    	{
    		this.displayError("File " + fc.getSelectedFile().getName() + " is not a .txt file");
    	}
    }
    
    private void customLevelRefresh() {
    	lvl.stopAllTimers();			
		frame.remove(lvl);
    	lvl = new LevelComponent(this, filePath, fileName, clouds, endTime - startTime, strawberryCount);
    	lvl.addLevelDisplay(fileName, startTime);
    	levelRefresh();
    }
    
    private void levelRefresh() {
    	if (err == null)
		{
			frame.add(lvl);
		}
		frame.setVisible(true);
		lvl.setDisplayMadeline(false);
		lvl.resetMadelineVelocity();
    }
	/**
	 * Refreshes the level to whatever currentLevel indicates
	 */
	private void mainGameLevelRefresh() {
		if (currentLevel >= 23)
		{
			for (Cloud c : clouds)
			{
				c.setColor(PINK_CLOUDS);
			}
		}
		else
		{
			for (Cloud c : clouds)
			{
				c.setColor(BLUE_CLOUDS);
			}
		}
		lvl.stopAllTimers();
		// resets the level to currentLevel
		frame.remove(lvl);
		
		lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected, clouds, endTime - startTime, strawberryCount, deathCount, (startTime == 0));
		levelRefresh();
		lvl.addLevelDisplay(currentLevel + "00 m", startTime);
		if (currentLevel >= 23)
		{
			frame.getContentPane().setBackground(BACKGROUND_PINK);
		}
		else
		{
			frame.getContentPane().setBackground(BACKGROUND_BLACK);
		}
	}

	/**
	 * Resets the current level to it's default state, with the exception of
	 * Strawberries and Breakable blocks which remain in their pre-reset state
	 */
	public void resetLevel() {
		deathCount++;
		if (isCustomLevel)
		{
			customLevelRefresh();
		}
		else
		{
			mainGameLevelRefresh();
		}
	}

	/**
	 * Moves to the next level sequentially
	 */
	public void nextLevel() {
		strawberryAlreadyCollected = false;
		if (currentLevel < 31) {
			currentLevel++;
		}
		if (currentLevel == 31 && endTime == 0)
		{
			endTime = System.currentTimeMillis();
		}
		mainGameLevelRefresh();
	}

	/**
	 * Moves to the previous level
	 */
	public void previousLevel() {
		strawberryAlreadyCollected = false;
		if (currentLevel > 1) {
			currentLevel--;
		}
		mainGameLevelRefresh();
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
    	errorIsDisplayed = true;
    	gameStarted = false;
    	frame.getContentPane().removeAll();
    	err = new ErrorDisplay(error + " &&press esc to reset");
    	frame.add(err);
    	frame.setVisible(true);
    	frame.repaint();
    }
    
    /**
     * Updates Madeline's current position
     */
    private void updateMadelinePosition()
    {
		checkJump();
		checkDash();
		lvl.moveMadeline();
	}
    
    private void updateMadelineVelocity() {
    	Boolean hasMoved = false;
    	if (pressedKeys.contains(KeyEvent.VK_D) || pressedKeys.contains(KeyEvent.VK_RIGHT))
		{
			lvl.moveMadelineRight();
			hasMoved = true;
		}
		if (pressedKeys.contains(KeyEvent.VK_A) || pressedKeys.contains(KeyEvent.VK_LEFT)) {
			lvl.moveMadelineLeft();
			hasMoved = true;
		}
		lvl.accelMadeline(hasMoved);
    }

	private void checkJump() {
		if (pressedKeys.contains(KeyEvent.VK_J) || pressedKeys.contains(KeyEvent.VK_C)) {
			lvl.madelineJump();
		}
	}

	/**
	 * Checks if the dash button is being held. If it is, calls LevelComponent's
	 * Dash method in the appropriate direction
	 */
	private void checkDash() {
		lvl.checkIfDashing();
		if (pressedKeys.contains(KeyEvent.VK_K) || pressedKeys.contains(KeyEvent.VK_X)) {
			if ((pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_D))
					|| (pressedKeys.contains(KeyEvent.VK_UP) && pressedKeys.contains(KeyEvent.VK_RIGHT))) {
				lvl.dash("upright");
			} else if ((pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_A))
					|| (pressedKeys.contains(KeyEvent.VK_UP) && pressedKeys.contains(KeyEvent.VK_LEFT))) {
				lvl.dash("upleft");
			} else if ((pressedKeys.contains(KeyEvent.VK_D) && pressedKeys.contains(KeyEvent.VK_S))
					|| (pressedKeys.contains(KeyEvent.VK_DOWN) && pressedKeys.contains(KeyEvent.VK_RIGHT))) {
				lvl.dash("downright");
			} else if ((pressedKeys.contains(KeyEvent.VK_A) && pressedKeys.contains(KeyEvent.VK_S))
					|| (pressedKeys.contains(KeyEvent.VK_DOWN) && pressedKeys.contains(KeyEvent.VK_LEFT))) {
				lvl.dash("downleft");
			} else if (pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_DOWN)) {
				lvl.dash("down");
			} else if (pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_UP)) {
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
			if (pressedKeys.contains(KeyEvent.VK_P)) {
				if (err != null)
				{
					frame.remove(err);
					err = null;
				}
				nextLevel();
				canMoveLevels = false;	
			}
			else if (pressedKeys.contains(KeyEvent.VK_O)) {
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
    
    
    private void checkToggles() {
    	if (canSwitch) {
	    	if (pressedKeys.contains(KeyEvent.VK_M)) {
	    		muted = !muted;
	    		muteMusic();
	    		canSwitch = false;
	    	}
    	}
    	
    }
    
    public void setCloudsPink()
    {
    	for (Cloud c : clouds)
		{
			c.setColor(PINK_CLOUDS);
		}
    }
    
    public void setBackgroundColor(Color c)
    {
    	frame.getContentPane().setBackground(c);
    	frame.setVisible(true);
    }
    
    /**
     * Muted audio once for testing
     */
    public void muteMusic() {
    	AudioPlayer.setMute(!muted);
    }

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		new MainApp();
	}

}