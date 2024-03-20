package utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Anselm
 * @date 2024/2/4 12 11
 * description
 */

public class MusicPlayer {
    private Clip clip;

    public void load(InputStream inputStream) {
        getClip(inputStream);
    }

    public void load(String pathname) {
//        有一个可以获取resources目录的方法见gongBang1;
        InputStream inputStream = MusicPlayer.class.getResourceAsStream(pathname);
        getClip(inputStream);
    }

    private void getClip(InputStream inputStream) {
        try {
            BufferedInputStream buffer = new BufferedInputStream(inputStream);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(buffer);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            inputStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void playLoop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setLoudness(float num) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(num);
    }

    public void close() {
        clip.close();
    }
}
