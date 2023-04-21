import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;


public class Announcer {
	
	public AudioClip win, hit, crit, zeroHP, lowHP, miss, start;
	
	public Announcer(){
		
		try {
			start = Applet.newAudioClip(new URL("file:la010_1.wav"));
		    }
		catch (Exception e) {
		    System.out.println("Error reading file: " + e);
		    }
		
		
	}
	
	public void winClip(){
		
		
		int n =(int)(Math.random()*3)+1;
		String s = "ended"+n+".wav";
		try {
			win = Applet.newAudioClip(new URL("file:" +s));
		    }
		catch (Exception e) {
		    System.out.println("Error reading file: " + e);
		    }
		
		win.play();
		
	}
	
	
	public void hitClip(){
		int n =(int)(Math.random()*5)+1;
		String s = "hit"+n+".wav";
		try {
			hit = Applet.newAudioClip(new URL("file:" +s));
		    }
		catch (Exception e) {
		    System.out.println("Error reading file: " + e);
		    }
		
		hit.play();
	}
	
public void critClip(){
		String s = "hit6.wav";
		try {
			crit = Applet.newAudioClip(new URL("file:" +s));
		    }
		catch (Exception e) {
		    System.out.println("Error reading file: " + e);
		    }
		
		crit.play();
	}
	
public void missClip(){
	String s = "miss.wav";
	try {
		miss = Applet.newAudioClip(new URL("file:" +s));
	    }
	catch (Exception e) {
	    System.out.println("Error reading file: " + e);
	    }
	
	miss.play();
}

public void lowClip(){
	String s = "lowHP.wav";
	try {
		lowHP = Applet.newAudioClip(new URL("file:" +s));
	    }
	catch (Exception e) {
	    System.out.println("Error reading file: " + e);
	    }
	
	lowHP.play();
}

public void zeroClip(){
	
	int n =(int)(Math.random()*2)+1;
	String s = "zeroHP"+n+".wav";
	try {
		zeroHP = Applet.newAudioClip(new URL("file:" +s));
	    }
	catch (Exception e) {
	    System.out.println("Error reading file: " + e);
	    }
	
	zeroHP.play();
}
	

}
