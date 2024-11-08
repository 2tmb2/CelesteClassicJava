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

public class AudioPlayer {

	private static final float DEFAULT_VOLUME_DECREASE = -25.0f;
	
	public static void playFile(String audioFile)
	{
		playFile(audioFile, 0, DEFAULT_VOLUME_DECREASE);
	}
	
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
