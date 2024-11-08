package mainApp;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * REQUIRED HELP CITATION
 * 
 * I utilized the following stack overflow answer for the basic structure of reading audio files
 * https://stackoverflow.com/questions/8979914/audio-clip-wont-loop-continuously/8980146#8980146
 */

public class AudioPlayer {

	/**
	 * Plays an audio file based on the file name
	 * @param audioFile representing the name of a .wav file located in src/AudioFiles/
	 */
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
	
	/**
	 * Plays an audio file based on the file name
	 * @param audioFile representing the name of a .wav file located in src/AudioFiles/
	 * @param loopAmount representing the number of times the audio should loop (for infinit, use Clip.LOOP_CONTINUOUSLY
	 */
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
