package com.trippin.chaosFlip.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundMusic {

    private final static BackgroundMusic instance;

    static {
        instance = new BackgroundMusic();
    }

    private Clip clip;

    public static BackgroundMusic instance() {
        return instance;
    }


    public synchronized void start() {

        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/audio/music2.wav"));
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void stop() {

        if (clip == null)
            return;

        clip.stop();
        clip.close();
        clip = null;
    }

    public synchronized boolean isPlaying() {
        return clip != null;
    }
}
