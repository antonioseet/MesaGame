import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music {

    private Clip mesaRoom;
    private Clip cityStreet;
    private Clip shopTheme;
    private Clip vivace;
    private Clip northPlaza;
    private Clip yugaTheme;
    private Clip yugaTheme2;

    public Music() {
        try {
            mesaRoom = getClipFromUrl("file:mesaTheme2.wav");
            cityStreet = getClipFromUrl("file:cityStreet.wav");
            shopTheme = getClipFromUrl("file:shopTheme.wav");
            vivace = getClipFromUrl("file:vivaceTheme.wav");
            northPlaza = getClipFromUrl("file:northPlaza.wav");
            yugaTheme = getClipFromUrl("file:yugaTheme.wav");
            yugaTheme2 = getClipFromUrl("file:yugaTheme2.wav");
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private Clip getClipFromUrl(String url) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        URL soundUrl = new URL(url);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    public void playMesaRoom() {
        mesaRoom.setFramePosition(0);
        mesaRoom.start();
    }

    public void playCityStreet() {
        cityStreet.setFramePosition(0);
        cityStreet.start();
    }

    public void playShopTheme() {
        shopTheme.setFramePosition(0);
        shopTheme.start();
    }

    public void playVivace() {
        vivace.setFramePosition(0);
        vivace.start();
    }

    public void playNorthPlaza() {
        northPlaza.setFramePosition(0);
        northPlaza.start();
    }

    public void playYugaTheme() {
        yugaTheme.setFramePosition(0);
        yugaTheme.start();
    }

    public void playYugaTheme2() {
        yugaTheme2.setFramePosition(0);
        yugaTheme2.start();
    }
}
