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
 * @author Put your team name here
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
		currentLevel = 1;
		strawberryAlreadyCollected = false;
		frame = new JFrame();
		frame.addKeyListener(this);
		frame.getContentPane().setPreferredSize(new Dimension(frameSize, frameSize));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.pack();
		lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected);
		frame.add(lvl);
		frame.setVisible(true);
		Timer t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateMadelinePosition();
				frame.repaint();
			}
		});
		t.start();
	}
	public void resetLevel()
	{
		frame.remove(lvl);
		lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected);
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
	@Override
    public synchronized void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }
    @Override
    public synchronized void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == 74)
    	{
    		lvl.setMadelineJumpPressed(false);
    	}
        pressedKeys.remove(e.getKeyCode());
    }
    public void keyTyped(KeyEvent e) {}
    public void updateMadelinePosition()
    {
    	lvl.checkIfDashing();
    	lvl.moveMadelineVertically();
    	lvl.moveMadelineHorizontally();
    	lvl.updateAnimations();
    	if (pressedKeys.contains(68))
		{
			lvl.moveMadelineRight();
		}
		if (pressedKeys.contains(65))
		{
			lvl.moveMadelineLeft();
		}
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
			lvl.dash("");
		}
		if (pressedKeys.contains(74))
		{
			lvl.madelineJump();
		}
    }
    public void collectStrawberry()
    {
    	strawberryAlreadyCollected = true;
    	strawberryCount++;
    }
    public void nextLevel()
    {
    	strawberryAlreadyCollected = false;
    	frame.remove(lvl);
    	currentLevel++;
    	lvl = new LevelComponent(this, currentLevel, strawberryAlreadyCollected);
    	frame.add(lvl);
    	frame.setVisible(true);
    }
	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		new MainApp();	
	} // main

}