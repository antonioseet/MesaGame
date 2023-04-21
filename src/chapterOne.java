

public class chapterOne{

	public MainWindow w;
	public Party party;
	public Character char1;
	public Music music = new Music();
	
	
	
	public void play(Character character, MainWindow window, Party part){
		
		this.w = window;
		this.party = part;
		this.char1 = character;
		
		window.say(char1.name, "YO THIS WORKED, Finally", 2);
		window.partyBattle(window.ghost, false);
		
		
	}



	public chapterOne() {
	}

	public void ss1(String sentence){
		
		w.s1(sentence);
		
	}
	
	public void say(String name, String s, int t){
		this.w.say(name, s, t);
	}
	
	/**
	 * all the crap needed to start a fresh new chapter
	 * 
	 * 
	 * 
	 * 
	public MainWindow window;
	public Party party;
	public Character char1;
	public Music music = new Music();
	
	
	
	public void play(Character character, MainWindow window, Party part){
		
		this.window = window;
		this.party = part;
		this.char1 = character;
		
		window.say(char1.name, "YO THIS WORKED, Finally", 3);
		
		
		
	}



	public chapterOne() {
	}
	 * 
	 * 
	 * 
	 */
	
	
	
	
}
