package Controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * AudioManager is a singleton class that handles the playing, pausing, and stopping of audio files in the game.
 * It supports both background music and individual sound effects.
 */
public class AudioManager {

    private static AudioManager instance;
    private String path;              // Path to the sound files
    private Clip clip;                // Clip for individual sound effects
    private Clip backgroundClip;      // Clip for background music

    /**
     * Returns the singleton instance of AudioManager.
     *
     * @return The single instance of AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Private constructor to initialize the AudioManager and set the sound file path.
     */
    private AudioManager() {
        String projectPath = System.getProperty("user.dir");
        path = projectPath + "/res/Sound/";
    }

    /**
     * Plays the specified background music file and loops it continuously.
     *
     * @param filename The name of the background music file (without extension).
     */
    public void playBackgroundMusic(String filename) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path + filename + ".wav"));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the background music indefinitely
            backgroundClip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Stops the background music if it's currently playing and releases the resources.
     */
    public void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    /**
     * Pauses the background music if it's currently playing.
     */
    public void pauseBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    /**
     * Resumes the background music if it was paused.
     */
    public void resumeBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.start();
        }
    }

    /**
     * Plays a single sound effect (non-looping).
     *
     * @param filename The name of the sound effect file (without extension).
     */
    public void play(String filename) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path + filename + ".wav"));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Pauses the current sound effect if it's playing.
     */
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Resumes the sound effect if it was paused.
     */
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }
}
