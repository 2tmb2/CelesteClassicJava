package mainApp;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public static void playFile(String audioFile)
	{
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/AudioFiles/" + audioFile + ".wav"));
	        Clip clip;
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void playFile(String audioFile, int loopAmount)
	{
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/AudioFiles/" + audioFile + ".wav"));
	        Clip clip;
			clip = AudioSystem.getClip();
			clip.loop(loopAmount);
			clip.open(inputStream);
			clip.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
