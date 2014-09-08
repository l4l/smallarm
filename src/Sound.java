import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {
    private boolean loaded;
    private Clip clip = null;
    private boolean playing;

    public Sound(File f) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(f));
            clip.addLineListener(new LineListener() {
                public void update(LineEvent e) {
                    if (e.getType() == LineEvent.Type.STOP) {
                        synchronized(clip) {
                            clip.notify();
                        }
                    }
                }
            });
            loaded = true;
        } catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
            loaded = false;
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void play() {
        if (isPlaying()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
        playing = true;
        try {
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clip.stop();
    }

    public void stop() {
        clip.stop();
    }
}