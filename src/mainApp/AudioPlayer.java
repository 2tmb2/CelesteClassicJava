package mainApp;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * REQUIRED HELP CITATION
 * 
 * I utilized the following stack overflow answer for the basic structure of reading audio files
 * https://stackoverflow.com/questions/8979914/audio-clip-wont-loop-continuously/8980146#8980146
 */

/**
 * An Audio Player handles playing a .wav audio file
 */
public class AudioPlayer {

	private static final float DEFAULT_VOLUME_DECREASE = -25.0f;
	
	/**
	 * Plays an audio file based on the file name
	 * @param audioFile representing the name of a .wav file located in src/AudioFiles/
	 */
	public static void playFile(String audioFile)
	{
		playFile(audioFile, 0, DEFAULT_VOLUME_DECREASE);
	}
	
	/**
	 * Plays an audio file based on the file name
	 * @param audioFile representing the name of a .wav file located in src/AudioFiles/
	 * @param loopAmount representing the number of times the audio should loop (for infinit, use Clip.LOOP_CONTINUOUSLY
	 * @param volumeDecrease representing the amount to decrease the audio by
	 */
	public static void playFile(String audioFile, int loopAmount, float volumeDecrease)
	{
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/AudioFiles/" + audioFile + ".wav"));
	        Clip clip;
			clip = AudioSystem.getClip();
			clip.loop(loopAmount);
			clip.open(inputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        gainControl.setValue(volumeDecrease);
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
	 * Mutes or unmutes the music
	 * @param mute true if the music should be muted, false if it should be unmuted
	 */
	public static void setMute(boolean mute) {
		Mixer.Info[] infos = AudioSystem.getMixerInfo();
		for (Mixer.Info info: infos) {
		    Mixer mixer = AudioSystem.getMixer(info);
		    for (Line line : mixer.getSourceLines()) {
		    	BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
				if (bc != null) {
				    bc.setValue(mute);
				}
			}
		}
	}
}
