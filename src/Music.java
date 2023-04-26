import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Music {

	public Clip currentSong;
	public Clip mesaRoom;
	public Clip cityStreet;
	public Clip shopTheme;
	public Clip vivace;
	public Clip northPlaza;
	public Clip yugaBattle;
	public Clip yugaTheme;
	public Clip yugaTheme2;
	
	public Clip thunder;
	public Clip door;
	
	public Clip atkSound;
	public Clip splSound;
	public Clip SFXlevelUP;
	public Clip victory;
	public Clip loss;
	public Clip item;
	
	public ArrayList<Clip> battleThemes = new ArrayList<Clip>();
	

    public Music() {
        try {
            mesaRoom = getClipFromUrl("file:mesaTheme2.wav");
            cityStreet = getClipFromUrl("file:cityStreet.wav");
            shopTheme = getClipFromUrl("file:shopTheme.wav");
            vivace = getClipFromUrl("file:vivaceTheme.wav");
            northPlaza = getClipFromUrl("file:northPlaza.wav");
            yugaTheme = getClipFromUrl("file:yugaTheme.wav");
            yugaTheme2 = getClipFromUrl("file:yugaTheme2.wav");

            yugaBattle = getClipFromUrl("file:YugaBattle.wav");
            
            thunder = getClipFromUrl("file:thunderSFX.wav");
            door = getClipFromUrl("file:doorSound.wav");
            
            atkSound = getClipFromUrl("file:atkSound.wav");
            splSound = getClipFromUrl("file:doorSound.wav");
            SFXlevelUP = getClipFromUrl("file:doorSound.wav");
            victory = getClipFromUrl("file:doorSound.wav");
            loss = getClipFromUrl("file:doorSound.wav");
            item = getClipFromUrl("file:itemGet.wav");
            currentSong = mesaRoom;
            
            for(int i = 1; i <= 17; i++) {
            	battleThemes.add(getClipFromUrl("file:Battle" + i + ".wav"));
            }

            
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
    
    public Clip getRandomBattleTheme() {
    	int n =(int)(Math.random()*17)+1;
    	return battleThemes.get(n);
    }
    
    public void playCurrent() {
    	currentSong.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stopCurrentSong() {
    	currentSong.stop();
    }
    
    public void switchSong(Clip newSong) {
    	currentSong.stop();
    	currentSong = newSong;
    	currentSong.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void playDoor() {
    	door.start();
    }
    
    public void itemGet() {
    	item.start();
    }
    
    public void playVictory() {
    	victory.start();
    }
    
    public void playLoss() {
    	loss.start();
    }
}
