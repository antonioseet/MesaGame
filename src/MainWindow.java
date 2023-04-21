import javax.sound.sampled.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import uwcse.graphics.GWindow;
import uwcse.graphics.GWindowEventAdapter;
import uwcse.graphics.ImageShape;
import uwcse.graphics.Rectangle;
import uwcse.graphics.TextShape;


public class MainWindow extends GWindowEventAdapter implements KeyListener {

		// The graphics window to display the picture
		public GWindow advWindow;
		public static MainWindow main;
		public Image background;
		public ImageShape bgn;
		
		public String snow = "snowLand.png";
		public String street1 = "street.jpg";
		public String street2 ="cityStreet.png";
		public String street3 = "cityStreet2.png";
		public String vivace = "vivace.jpg";
		public String mountain1 = "mountainDeer.png";
		public String mountain2 = "mountainRange2.jpg";
		public String mountain3 = "mountains2.jpg";
		public String mesaRoom = "mesaRoom.jpg";
		public String parkingLot = "backDoor.png";
		public String rearEntrance  = "backEntrance.png";
		public String basement = "basement.png";

		////////////////////////////////////////////////////Pseudo console Assets////////////////////////////////////////////////////////////////////////////////////////////////
		public TextShape line1,  line2,  line3,  line4,  line5,  line6,  line7,  line8,  line9, line10, line11, line12, line13, line14, line15, line16, line17, line18, line19, line20,
						line21, line22, line23, line24, line25, line26, line27, line28, line29, line30, partyLabel, itemsLabel, walletLabel;
		public String s1,  s2,  s3,  s4,  s5,  s6,  s7,  s8,  s9, s10, s11, s12, s13, s14, s15, s16,
					 s17, s18, s19, s20, s21, s22, s23, s24, s25, s26, s27, s28, s29, s30;
		
		public Color c = new Color(0,0,0,215);
		public Rectangle textWindow = new Rectangle(40, 40, 685, 600, c, true);
		public Rectangle partyBox =   new Rectangle(750, 40, 200, 320, c, true);
		public Rectangle itemBox =    new Rectangle(990, 40 , 150,200, c , true);
		public Rectangle walletBox =  new Rectangle(990, 260, 70, 25, c , true);
		
		//character & Enemy: HP Bars
		public boolean battleInProgress = false;
		public Rectangle playerHP, enemyHP;// = new Rectangle(200,200,15,0,Color.GREEN,true);
		public int phpY = 640, ehpY = 640; //Where the HP bars begin
		public int phpHeight = 0 , ehpHeight = 0;
		public Color phpColor = Color.GREEN, ehpColor = Color.GREEN;
		public TextShape phpText, ehpText;
		public boolean showBattleHP = false;
		public int php = 0, ehp = 0;
		public boolean lunchFirst = false;
		
		
		//Coordinates for the icon of the player depending on order
		public int xx = 760, y1 = 40, y2 = 40+80, y3 = 40 +80+80, y4 = 40+80+80+80;
		
		//how far down the rectangle is going to go for the partyBox based on people there.
		public int xxx = 200, yy1 = 110, yy2=180, yy3=250, yy4 = 320; 
		
		//Inventories and Party Members
		public TextShape inventorySlot1, inventorySlot2, inventorySlot3, inventorySlot4;
		public TextShape wallet;
		public String item1 = "-Empty-", item2 = "-Empty-", item3 = "-Empty-", item4 = "-Empty-";
		public TextShape partyMember1, partyMem1HP, partyMem1Lvl, partyMember2, partyMem2HP, partyMem2Lvl, partyMember3, partyMem3HP, partyMem3Lvl, partyMember4, partyMem4HP, partyMem4Lvl;
		public boolean showInventory = false;
		public boolean showParty = false;
		
		//Fighting sprite(s) and Level up textShapes
		private ArrayList<String> stringArray = new ArrayList<String>();
		private ArrayList<Character> charactersArray = new ArrayList<Character>();
		private int count = 0;
		private double slower = .1;
		private double slow = .07;
		private double normal = .04;
		
		private Clip song;

		////////end of window fields//////
		
		//JFrame frame = new JFrame("No name frame");
		private Random rand = new Random();
		private String textResponse;
		private int Response=0;
	
		Battle battle = new Battle();
		chapterOne chap1 = new chapterOne();
		
		//Background and MUSIC class with all the songs and SFX
		private Music music = new Music();
		
		////CHARACTER NAMES/////
		private String Abdul     =      "Abdul"; private Character  abdul;  public Image abdulSprite; public ImageShape AbdulSprite; public Image abdulIcon;  public ImageShape AbdulIcon;
		private String Alexa     =      "Alexa"; private Character  alexa;  public Image alexaSprite; public ImageShape AlexaSprite; public Image alexaIcon;  public ImageShape AlexaIcon;
		private String Julia     =      "Julia"; private Character  julia;  public Image juliaSprite; public ImageShape JuliaSprite; public Image juliaIcon;  public ImageShape JuliaIcon; 
		private String Fran      =  "Francisco"; private Character   fran;  public Image franSprite;  public ImageShape FranSprite;  public Image franIcon;   public ImageShape FranIcon;
		private String Matt      =       "Matt"; private Character   matt;  public Image mattSprite;  public ImageShape MattSprite;  public Image mattIcon;   public ImageShape MattIcon;
		private String Sebas     =  "Sebastian"; private Character  sebas;  public Image sebasSprite; public ImageShape SebasSprite; public Image sebasIcon;  public ImageShape SebasIcon;
		private String Derric    =     "Derric"; private Character derric;  public Image derricSprite;public ImageShape DerricSprite;public Image derricIcon; public ImageShape DerricIcon;
		private String Tony      =       "Tony"; private Character   tony;  public Image tonySprite;  public ImageShape TonySprite;  public Image tonyIcon;   public ImageShape TonyIcon;
		private String Steph     =      "Steph"; private Character  steph;  public Image stephSprite; public ImageShape StephSprite; public Image stephIcon;  public ImageShape StephIcon;
		private String Kevin     =      "Kevin"; private Character  kevin;  public Image kevinSprite; public ImageShape KevinSprite; public Image kevinIcon;  public ImageShape KevinIcon;
		private String Lissy     =      "Lissy"; private Character  lissy;  public Image lissySprite; public ImageShape LissySprite; public Image lissyIcon;  public ImageShape LissyIcon;
		
		//NPC's 
		private String Francois  =    "Francois"; private Character francois; public Image francoisSprite; public ImageShape FrancoisSprite; public Image francoisIcon; public ImageShape FrancoisIcon;
		
		private String nar =    "Narrator";
		
		//Enemy sprite initialize them
		 public Image druggieSprite; public ImageShape DruggieSprite;
		 public Image ghostSprite; public ImageShape GhostSprite;
		 public Image curryLadySprite; public ImageShape CurryLadySprite;
		 public Image yugaSprite; public ImageShape YugaSprite;
		 public Image deadHandSprite; public ImageShape DeadHandSprite;
		
		
		//////MAIN CHARACTER/////
		private Character mainChar;
		private String name;
		private double money = 20.22;
		
		
		
	//Enemy Instances
		public Enemy ghost;
		private Enemy druggie;
		private Enemy curryLady;
		private Enemy yuga;
		private Enemy deadHand;
		
		public Party party;
		public Party tempParty;

		public void setAllChars(){
			
		  ///   Characters Instances  //
		 ///-------------------------//Name,    HP,   Atk,   Def, spAtk, spDef, Speed,           attackName, attackDmg,                       spellName,spellDmg, exp);              //||
		///____________________________________________________________________________________________________________________________________________________________________________________________//||
			tony    = new Character(       Tony,   511,   1.4,   1.5,    1.4,   1,  180,               "Fire Sword",   115,                    "Lightning",    130,   TonySprite,  TonyIcon);                    //||Attack    & spAtk
			steph   = new Character(      Steph,   557,   1.3,   1.6,    1.6, 1.4,   75,                "Jump Kick",   115,                         "Flux",    135,  StephSprite, StephIcon);
			abdul   = new Character(      Abdul,   425,   1.3,   .75,     .8,   1,   94,             "Helmet Smash",   100,                  "ShotGun Mic",    115,  AbdulSprite, AbdulIcon);                    //||speed  & Defense (done)
			julia   = new Character(      Julia,   510,    .8,    .5,    1.6,   1,   85,              "Shield Bash",   100,                      "Gravity",    125,  JuliaSprite, JuliaIcon);                    //||spAtk  & spDef
			fran    = new Character(       Fran,   424,   1.3,    .8,    1.4,   1,   55,            "Water Trident",   110,                   "Tidal Wave",    130,   FranSprite,  FranIcon);                    //||spAtk  & Defense
			matt    = new Character(       Matt,   421,   1.4,   1.2,      1,   1,   75,             "Shadow Spear",   115,                   "Death Coil",    110,   MattSprite,  MattIcon);                    //||Attack    & HP
			alexa   = new Character(      Alexa,   473,    .7,    .6,    1.4,   1,   80,          "Bike Lock Smash",   100,          "1000 Disapointments",    120,  AlexaSprite, AlexaIcon);                    //||Defense $ spAtk
			sebas 	= new Character(      Sebas,   499,   1.3,    .8,    1.4,  .4, 99, "Bash", 100, "Recurssion", 124, SebasSprite, SebasIcon); 			
			francois = new Character(  Francois,   600,     1,     1,      1,    1,  85,                 "fancyCar",   115,                   "Recursive Punch!",     145, FrancoisSprite, FrancoisIcon);
			
			//set playable characters
			charactersArray.add(tony);
			charactersArray.add(steph);
			charactersArray.add(abdul);
			charactersArray.add(julia);
			charactersArray.add(fran);
			charactersArray.add(matt);
			charactersArray.add(alexa);
			
			 //                             Name,   HP,     Atk,    Def,    spAtk,    spDef,                  Speed,  likelyhood,             attackName,                 spellName,                intro,                     win,            lose); //||
			//________________________________________________________________________________________________________________________________________________________________________________________________________________________________//
			ghost =     new Enemy( "Bob's ghost", 1560,     1.1,     .3,      1.3,      1.8,  (rand.nextInt(47)+40),          70,         "Soul Crusher",               "Ectoplasm","......Leave.....", ".....You're all weak.....", ".........",GhostSprite); 
			druggie =   new Enemy( "Meth Addict", 1117,      .9,    1.3,        1,        1,  (rand.nextInt(35)+50),          45, "Shank","Broken Bottle", "Give me all your money!", "That's what you get punk.", "Gah, I just wanted some.. change...",DruggieSprite);
			curryLady = new Enemy( "Curry Lady",   718,       1,     .8,        1,      1.1,  (rand.nextInt(35)+50),          45,     "Constricting Hug",              "Curry Waft",	"I LOVE THE SMELL OF CURRY *goes for a hug*","MMMMM CURRYYYY", "I JUST LOOVVEE CURRYYY...", CurryLadySprite);
			yuga =      new Enemy( "Yuga",        1969,     2.3,     .4,      2.4,       .5,  (rand.nextInt(25)+60),          35,          "Magic Staff",                   "Mural",  "HeeHeeHee you stand no chance.", "HA! HA!", "How could such brats get in the way of the great... ", YugaSprite);
			deadHand =  new Enemy(  "Dead Hand",   926,     1.2,    1.3,      1.3,      1.4,  (rand.nextInt(30)+60),          50,                 "Bite", "Constrict", "...Bones...","...More skin...","......"                           ,DeadHandSprite );
			
		}
		
		public void setAllSprites(){
			//Character sprite(s) and icons
		tonySprite   = advWindow.getImageFromFilename("TonySprite.png");         TonySprite    = new ImageShape(tonySprite,  800, 450);  tonyIcon   = advWindow.getImageFromFilename("TonyIcon.png");      TonyIcon = new ImageShape(tonyIcon,   -760, 0);
		stephSprite  = advWindow.getImageFromFilename("StephSprite.png");        StephSprite   = new ImageShape(stephSprite, 775, 450); stephIcon   = advWindow.getImageFromFilename("StephIcon.png");    StephIcon = new ImageShape(stephIcon,  -760, 0); 
		abdulSprite  = advWindow.getImageFromFilename("AbdulSprite.png");        AbdulSprite   = new ImageShape(abdulSprite, 800, 450);  abdulIcon  = advWindow.getImageFromFilename("AbdulIcon.png");    AbdulIcon = new ImageShape(abdulIcon,  -760, 0);
		mattSprite   = advWindow.getImageFromFilename("MattSprite.png");         MattSprite    = new ImageShape(mattSprite,  800, 450);  mattIcon   = advWindow.getImageFromFilename("MattIcon.png");      MattIcon = new ImageShape(mattIcon,   -760, 0);
		sebasSprite  = advWindow.getImageFromFilename("SebastianSprite.png");    SebasSprite   = new ImageShape(sebasSprite, 750, 450); sebasIcon   = advWindow.getImageFromFilename("SebastianIcon.png");SebasIcon = new ImageShape(sebasIcon,  -760, 0);
		franSprite   = advWindow.getImageFromFilename("FranSprite.png");         FranSprite    = new ImageShape(franSprite,  750, 450);  franIcon   = advWindow.getImageFromFilename("FranIcon.png");      FranIcon = new ImageShape(franIcon,   -760, 0);
		derricSprite = advWindow.getImageFromFilename("DerricSprite.png");       DerricSprite  = new ImageShape(derricSprite,800, 450);derricIcon   = advWindow.getImageFromFilename("DerricIcon.png");  DerricIcon = new ImageShape(derricIcon, -760, 0);
		juliaSprite  = advWindow.getImageFromFilename("JulesSprite.png");        JuliaSprite   = new ImageShape(juliaSprite, 775, 450); juliaIcon   = advWindow.getImageFromFilename("JulesIcon.png");    JuliaIcon = new ImageShape(juliaIcon,  -760, 0);
		alexaSprite  = advWindow.getImageFromFilename("AlexaSprite.png");        AlexaSprite   = new ImageShape(alexaSprite, 785, 450); alexaIcon   = advWindow.getImageFromFilename("AlexaIcon.png");    AlexaIcon = new ImageShape(alexaIcon,  -760, 0);
		kevinSprite  = advWindow.getImageFromFilename("KevinSprite.png");        KevinSprite   = new ImageShape(kevinSprite, 800, 450); kevinIcon   = advWindow.getImageFromFilename("KevinIcon.png");    KevinIcon = new ImageShape(kevinIcon,  -760, 0);
		francoisSprite=advWindow.getImageFromFilename("FrancoisSprite.png");	FrancoisSprite = new ImageShape(francoisSprite,800,450);francoisIcon = advWindow.getImageFromFilename("FrancoisIcon.png");FrancoisIcon=new ImageShape(francoisIcon, -760,0);
		//Enemy Sprite(s)
		druggieSprite   = advWindow.getImageFromFilename("DruggieSprite.png"  );  DruggieSprite  = new ImageShape(druggieSprite,   980, 450);
		ghostSprite     = advWindow.getImageFromFilename("GhostSprite2.png"   );  GhostSprite    = new ImageShape(ghostSprite,     925, 400);
		curryLadySprite = advWindow.getImageFromFilename("CurryLadySprite.png");  CurryLadySprite= new ImageShape(curryLadySprite, 970, 445);
		yugaSprite      = advWindow.getImageFromFilename("YugaSprite.png"     );  YugaSprite     = new ImageShape(yugaSprite,      960, 450);
		deadHandSprite  = advWindow.getImageFromFilename("deadHand.png"       );  DeadHandSprite = new ImageShape(deadHandSprite,  925, 440);
		}
		
		//////////////////////////////////end of fields////////////////////////////////////	
		
		public MainWindow() {
			// Create the graphics window
			this.advWindow = new GWindow("MESAdventure", 1200, 666);
			// Terminate the application when the user closes the window
			this.advWindow.setExitOnClose();
			
			
			background = advWindow.getImageFromFilename(street1);
			bgn = new ImageShape(background, 0, 0);
			
			
			//changeBackground("winterLake.jpg");
			this.advWindow.add(bgn);
			populateStrings();
			
		}
		
		public static void main(String[] args) {
			main  = new MainWindow();
			printt("before prologue");
			main.prologue();
		}
		
		public void test(){
			printt("test function");
			showParty = true;
			
			
			party.addChar(chooseYourCharacter("choose an additional party memeber! "));
			party.addChar(chooseYourCharacter("choose last party member "));
			
			
			basement();
			
		}
		
		/**
		public void whatIsTonyDoing(){
			
			ArrayList<String> sentences = new ArrayList<String>();
			sentences.add("Tony is thinking about Joe");
			sentences.add("Tony is thinking about Joe's cute butt");
			sentences.add("Tony is thinking about Joe's gorgeous eyes");
			sentences.add("Tony is probably texting Joe, or waiting on her to reply");
			sentences.add("Tony is sleepy and taking a nap, or doing work, definitely not playing League");
			
			printLine("_____________________________________________");
			printLine("        WHHAT IS TONY DOING RIGHT NOW"        );
			printLine("_____________________________________________");
			sleep(10);
			
			while (true){
				int n = (int)(Math.random()*5);
				printSlow(sentences.get(n));
				
				sleep(1);
			}	
		}
		**/
		
		
		public void prologue(){
			printt("prologue");
			setAllSprites();
			setAllChars();
			party = new Party(chooseYourCharacter("Choose your character!"));
			music.playMesaRoom(); // FOR TEST just so the game does not crash when testing
			
			song.play();
			say2(nar, 					"Not so long ago in the beautiful land of Seattle, surrounded by mountains and water, ",0);
			printLine(); saySlow("         rumors spoke of an omnipotent Power that resided in a hidden land.", slow);
			printLine(); saySlow("         Many people aggressively sought to enter the hidden land, But no one ever returned.", slow);sleep(1);
			printLine();saySlow("                      One day, evil power began to flow from the Hidden Land...", normal); 
			printLine();
			changeBackground("mesaRoom.jpg");
			song.stop();
			song = music.mesaRoom;
			song.loop();
			sleep(2);
			printLine();
			
			saySlow("**************************************Prologue***************************************", normal);
			sleep(2);
			print("");
			say(Abdul, "Welcome to the MESA room newbie!!",2);
			say(Abdul, "Did you bring your application??", 0);
			Object[] responses1 = {"Yes", "No"};
			int n = JOptionPane.showOptionDialog(null, "Did you bring your MESA application?", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses1, responses1[0]);
			if(	n == 0 ){
				sleep(2);
				say2(Abdul, "Awesome, Let's see here..",2);
				saySlow(" Oh... ", slow);
				sleep(2);
				print("well " +name+"");
				sleep(2);
				say(Abdul, "According to this application, ",1);
				saySlow("you're a bitch!", normal);
				sleep(2);
			} else {
				sleep(2);
				say(Abdul, "That's Okay, just write your name on this paper. ", 2);
				printLine("**You wrote your name on the paper**"); sleep(2);
				say(Abdul,"Oh... ",2);
				saySlow("well " +name+"",slower);
				say(Abdul, "According to this piece of paper, you're a BITCH!",3);
			}
			
			say(Alexa, "Shut up, "+Abdul+"! ",3);
			say(Alexa, "Don't mind him, he does this to everyone.",2);
			say(Alexa, "I'm "+Alexa+", nice to meet you, "+name+"!",4);

			say(Julia, "Hey newbie! Your outfit is on point! Good job! :D",3);
			
				Object[] responses2 = {"Thank you so much!!","Thanks!","Um, who are you?", ".........."};
				Response = JOptionPane.showOptionDialog(null,"What will "+name+" say?","Options",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,responses2,responses2[0]);

				printLine();
			
				switch (Response){
			
					case 0: 	say(name, "Thank you so much!",2);
								say(Julia, "Haha, Don't mention it! :)",2);
								break;
					case 1: 	say(name, "Thanks!",2);
								say(Julia, "Yeah, no problem. :)", 2);
								break;
					case 2: 	say(name,  "Um, who are you?", 2);
								say(Julia, "Oh, I'm sorry, I didn't introduce myself",2);
								say(Julia, "I'm " + Julia+" :)",1);
								say(Julia, "nice to meet you " + name + "!",2);
								break;
					case 3: 	say3(name, "...........",2);
								say2(Julia, "...........",2);
								say(name, "...........",2);
								say(Julia,"Anyways, I'm gonna do physics now.",2);
								break;
					}
			
			printLine();
			say(nar, "As soon as "+Julia+" went back to working on physics, "+Matt+ " comes in.",1);
			printLine();
			say(Matt, "Yo! Anyone wannna get some lunch?", 3);
			if(Sebas.equals("Sebastian")){	say(Sebas, "Hell yeah! I'm feeling Chipotle today, how about it, eh? (because he's canadian)",2);
			}else{							say(Sebas, "Hell yeah! I'm feeling Chipotle today, how about it?",2);}
			say(Matt, "Works for me.",2);
			say(Matt, "Hey, newbie, you wanna join us?", 2);
			
			Object[] responses3 = {"Thanks, but I'm gonna stick around here for a bit.", "Count me in!"};
			Response = JOptionPane.showOptionDialog(null, "Will you go to Chipotle with them?", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses3, responses3[0]);
	
				sleep(1);
				printLine();
				switch (Response){
					case 0: coffeeRun();
				 		break;
					case 1: lunchRun(); lunchFirst = true; //TODO make it so we go through both runs anyway. (More play-time)
						break;	
				}     
			
			//music.northPlaza.loop();
			say("Bob's Ghost", "...You'll pay....", 2);
			ss2("Was..", "was that Bob??"); sleep(1);
			ss3("I think so,"," a ghost version of him at least.");
			
			Object[] responses4 = {"Who's Bob?","Ghosts are real??"};
			Response = JOptionPane.showOptionDialog(null,"What will "+name+" say?","Options",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,responses4,responses4[0]);
			printLine();
		
			switch (Response){
		
				case 0: 	s1("Who's Bob?");
							s2("Bob was a photophrapher for the school before he retired.");
							ss3("Yeah, he was a great dude.", " His office was just down the hall from the Mesa room.");
							s2("I still don't know why he's a ghost, or why he's haunting North Plaza in the middle of the day..");
							sleep(2);
							s3("Probably no good ghost hunnies to keep him occupied.");
							s2("Something isn't quite right, Let's go find out what's going on.");
							break;
			 		
				case 1: 	
							s1("Ghosts are real??");
							s2("They're not very common, but they do appear from time to time");
							s3("Yeah, and they usually come out in the dead of night when no one's around.");
							s2("Which makes this extra weird");
							break;
			}
			
			s1("This isn't a sick joke for some MESA initiation is it?");
			s2("Haha, no way newbie, we wouldn't even know where to get a ghost to come fight us."); sleep(1);
			s1("True.");
			printLine();
			say(nar, name+ " and the gang starts heading towards the MESA room.", 4);
			switchSong(music.yugaTheme, "mesaRoom.jpg");
			printLine(); sleep(2);
			printSlow( "********************Five minutes earlier: IN THE MESA ROOM****************");
			say(Abdul, "Who are you?!", 2);
			say(Derric, "And what sort of freak comes into our space and try to take us hostage?!",4);
			printLine();
			yuga.showSprite = true;
			pauseSay("????", "A freak?", " my, how easily you stoop to petty insults.", slow);
			pauseSay("????", "My name is Yuga, and i come here seeking nothing less than... ","A helping hand...", slow);
			pauseSay("Yuga", "And you, friends",", are exactly what we are looking for.", slow);
			printLine();
			say(Abdul, "What are you talking about?",2);
			say(Alexa, "and what's with this 'we' bussiness?",2);
			say("Khen", "Yeah, theres no one else in here but you!",3);
			printLine();
			pauseSay("Yuga", "Be quiet!", " I need not explain myself to fools like you.", slow);
			pauseSay("Cam", "Watch your mouth, clown. ","you might have not noticed but you're outnumbered.", normal);
			
			printSlow("*****"+name+", "+party.party.get(1).name+", and "+ party.party.get(2)+" slowly enter the room*****");
			
			s2("Did we miss anything?");
			s3("Yeah and why's the door locked so early?");
			
			Object[] responses5 = {"Who's the clown?","What's going on?"};
			Response = JOptionPane.showOptionDialog(null,"What will "+name+" say?","Options",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,responses5,responses5[0]);
			printLine();
		
			switch (Response){
		
				case 0: 	s1("Who's the clown?");
							say(Tony, "He's no clown, I haven't laughed once since he got here", 2);
							break;
							
				case 1: 	s1("What's going on?");
							break;
			}
			
			pauseSay("Yuga", "How nice of you to join us ", ". "+name+".",slow);
			s2(name+ "? Do you know this guy?");
			s1("No, i've never seen him in my life.");
		
			say("Yuga", "I don't have time for this.",2);
			pauseSay("Yuga", "Now then, will you all be joining me", ", or will I have to drag all of you there myself?", normal);
			
			s3("We'd like to see you try.");
			song.stop();
			party.addChar(chooseYourCharacter("Who will help??"));
			song = music.yugaTheme2;
			partyBattle(yuga, true);
			
			printLine();
			party.party.remove(3);
			party.party.remove(2);
			party.party.remove(1);
			say(nar, "One by one, everyone kept getting beaten by Yuga, and eventually, everyone had been knocked out..",4);
			printLine();
			pauseSay("Yuga", "Bob,"," Gather these imbeciles and take them to the basement", normal);
			sleep(1); yuga.showSprite = false; sleep(2); ghost.showSprite = true; 
			
			say("Bob's Ghost", "As you wish, sir.",4);
			ghost.showSprite = false;
			printLine();
			basement();
			//ChapterOne();

			            																																	
			sleep(5);
			printSlow("Congratulations! you finished the Demo version, for the Full version, please give Tony $100 ;D");
			
			printLine();
	}
		
		public void basement(){
			
			switchSong(music.northPlaza, "basement.png");
			sleep(3);
			say(nar, "As you snap back to conciousness, you notice you're the only one there",3);
			say(nar, "but you hear something coming from a dark corner...", 3);
			printLine();
			Object[] responses5 = {"Who's there?","Come out from there!!"};
			Response = JOptionPane.showOptionDialog(null,"What will "+name+" say?","Options",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,responses5,responses5[0]);
			printLine();
		
			switch (Response){
		
				case 0: 	s1("Who's there?");
							sleep(2);
							deadHand.showSprite = true;
							say("Dead Hand", "...........",2);
							say(name, "Oh my god...",2);
							break;
				case 1: 	s1("Come out from there!");
							printLine();sleep(3);
							printSlow("....there was no response.....");
							sleep(3);
							say(name, "Hello?!",2);
							break;
			}
			
			partyBattle(deadHand, false);
			
			
		}
		
		public int choosePortal(){
			
			Object[] response = {"Farscape HQ", "Blood on the Ice", "Wings of Time", "Ancient Forest", "Toxic Tropics"};
			Response = JOptionPane.showOptionDialog(null, "Choose a chapter", "THE VALLEY", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, response, response[0]);
			
			switch (Response){
			case 0: 	
						return 0;		
			case 1: 	
						return 1;
			case 2: 	
						return 2;
			case 3: 	
						return 3;
			case 4: 	
						return 4;
			}
			return 5;
		}
		
			
		public void lunchRun(){
			
			say(name, "Count me in!",1);
			say(Matt, "Sweet. Let's get going!", 1);
			say(Sebas, "Let's roll!",1);
			switchSong(music.cityStreet, "cityStreet.png");
			showParty = true;
			showInventory = true;
			printSlow("***********Walking down Broadway to get Chipotle*************");
			party.party.add(matt);
			if(party.party.get(0) == sebas ){
				party.party.add(tony);
			}else{
				party.party.add(sebas);
			}
			
			printSlow("                 **"+Sebas+" and "+Matt+" joined your party**");
			sleep(2);
			
			say(Matt, "So whats your name newbie?", 1);
			say(Sebas, "It's "+name+", weren't you listening earlier?", 2);
			say(Matt, "Well, I had just gotten there. So.", 1);
			say(Sebas, "Oh that's right.",2);
			say(Sebas, "My bad.",1);
			say(Matt, "Anyways, I'm "+Matt+" it's nice to meet you, "+name, 2);
			say(name, "Likewise!", 2);
			printLine();
			say(nar, "As you keep walking down broadway towards Chipotle",3);	
			say(nar, "a weird homeless guy comes up to you guys",3);
			printLine();
			druggie.showSprite = true;
			say("Weird Homeless Guy", "Spare some change?",3);
			say(Matt, "Sorry.",1);
			say(Sebas, "Nope.", 1);
			
			Object[] responses = {"Sorry, I don't have any change", "*Give him your change*"};
			Response = JOptionPane.showOptionDialog(null, "What will you do?", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses, responses[0]);
				
				switch (Response){
				
					case 0: say(name, "I'm sorry, i dont have change.",3);
							printLine();
							druggie.showSprite = false;
							say(nar, "As you walk away, you hear someone briskly walking towards you from behind",4);
							printLine();
							druggie.showSprite = true;
							say("Weird Homeless guy", "I'll teach you to disrespect me!!",3);
							printLine();
							say(Matt, "Watch out!", 0);
							say(Sebas, "Woah!",2);
							///////////////////////////////////////////////////
							partyBattle(druggie, false);
							///////////////////////////////////////////////////
							say(Sebas, "Holy crap, that was insane!",2);
							say(Matt, "That was out of nowhere!",2);
							say(Sebas, "Sorry I didnt help,",1);saySlow(" I didn't wanna cramp your style. ;]", normal);
					 		break;
					 		
					case 1: say(name, "Here you go!", 2);
							money-=.22;
							printLine("**"+name+" gave the Weird Homeless Man 22c**"); sleep(2);
							say("Weird Homeless Guy", "That's it??", 2);
							say("Weird Homeless Guy", "You little cheapskate! I'll show you!",2);
							say(Matt, "Watch out!", 0);
							say(Sebas, "Woah!",2);
							////////////////////////////////////////////////////
							partyBattle(druggie, false);
							////////////////////////////////////////////////////
							say(Sebas, "Holy crap, that was insane!",2);
							say(Matt, "That was out of nowhere!",2);
							break;
				}
			
			printLine();
			say(nar, "After a short walk, they arrive at Chipotle", 2); printLine();
			switchSong(music.shopTheme,"chipotle.jpg");
			printSlow("***********************Inside Chipotle************************");		
			printLine();
			
			say(Sebas, "Oh god, I'm starving", 3);
			say(Sebas, "I'll have a burrito with extra sour cream please, and a medium drink", 3);
			printLine("**"+Sebas+" bought a burrito and a medium drink**");sleep(2);
			printLine();
			say(Matt, "Afternoon, I'll take a burrito aswell, and some nachos.",3);
			printLine("**"+Matt+" bought a burrito and some nachos**"); sleep(2);
			printLine();
			say("Chipotle Worker", "Good afternoon, how may i help you?",4);
			say(name, "Oh, my turn already? um.. let's see...",3);
			
			Object[] responses2 = {"Burrito - $4.99", "Quesadilla - $3.99", "Nachos - $3.50", "Nothing"};
			Response = JOptionPane.showOptionDialog(null, "What are you going to buy?", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses2, responses2[0]);
			
				printLine();
				
				switch (Response){
				
					case 0: 	say(name, "Ah yes, I'll have a burrito please!",3);
								say("Chipotle Worker", "Sure! that'll be $5.46",3);
								printLine("             **You paid $5.46**");
								//music.SFXitemGet.play();
								printLine(); 
								printLine("          ***You got a Burrito!***"); 
								printLine(); 
								money -= 5.46;
								sleep(3);
								printLine("***You placed the burrito in your inventory***"); 
								item1 = "Burrito";
								sleep(2); 
								say("Chipotle Worker", "Thank you, have a nice day!", 3);textResponse = "1";
								break;
					case 1:	    say(name, "Ah yes, i'll have a quesadilla please!",3);
								say("Chipotle Worker", "Sure! that'll be $4.37",3);
								printLine("              **You paid $4.37**");
								//music.SFXitemGet.play();
								printLine(); 
								printLine("          ***You got a Quesadilla!***"); 
								printLine(); 
								money-=4.37;
								sleep(2);
								printLine("   ***You placed the quesadilla in your inventory***");
								item1 = "Quesadilla";sleep(2);
								say("Chipotle Worker", "Thank you, have a nice day!", 3);textResponse = "2";
								break;
					case 2: 	say(name, "Ah yes, i'll have the nachos please!",3);
								say("Chipotle Worker", "Sure! that'll be $3.83",3);
								printLine("              **You paid $3.83**");
								//music.SFXitemGet.play();
								printLine(); 
								printLine("          ***You got Zesty Nachos!***"); 
								printLine(); 
								sleep(4);
								printLine("    ***You placed the Nachos in your inventory***"); 
								say("Chipotle Worker", "Thank you, have a nice day!", 3);
								money -= 3.83;
								item1 = "Nachos";
								break;
					case 3: 	say(name, "Actually, I change my mind, i won't have anything today.", 3);
								say("Chipotle Worker", "No problem, have a nice day!", 3);
								say(Matt, "Hey, you didn't get anything?",2);
								say(name, "Nah, I changed my mind.", 3);
								break;
				
				}
			
				say(Sebas, "Alright, let's head back so we can eat, I don't think i can wait much longer",3);
				switchSong(music.cityStreet,"cityStreet2.png");
				say(Matt, "Just eat some as we walk, it would be much more efficient if you're 'starving'.",3);
				printLine();
				say(nar, name+ " and company headed back to North Plaza", 2);
				say(nar, "but as "+Sebas+" reaches for the door knob...",2);
				printLine();
				say(Sebas, "Uh, it's locked.", 2);
				say(Matt, "Really? it's barely 2 in the afternoon",2);
				say(name, "Maybe you guys could call or text someone who might be in there?", 2);
				say(Matt, "Good thinking newbie.", 1);
				say(Sebas, "Yeah, let's try that.", 1);
				printLine();
				printSlow("**********FIVE MINUTES LATER*************");
				printLine();
				sleep(3);
				//Back-door of northPlaza
				changeBackground("backDoor.png");
				
				say(Sebas, "Welp, i say we just go through the backdoor", 3);
				say(Matt, "That's what she said ;D",3);
				say(Sebas, "Wow, okay, calm down buddy", 3);
				say(name, "oh god..",2);
				say(Matt, "*Ahem* Alright alright, enough joking around, you two. let's go.",3);
				switchSong(music.northPlaza,"backEntrance.png");
				printLine();
				say(nar, "The three walk in through the back door and towards the mesa room, but they hear something",3);
				printLine();
				s1("What was that?");
				s2("I'm not sure, I think it came from the basement");
				ss3("Yeah... " ," I'm not gonna go find out what that was..");
				printLine();
				s2("Probably just a rat, nothing to worry about.");
				s3("i sure hope so.");
				s1("yeah me too.");
				sleep(1);
				say("Ghost", "Boo...",2);
				say(Matt, "O_O",1);
				say(Sebas, "O___O'",1);
				say(name, "What the..",1);
				//music.northPlaza.stop();
				partyBattle(ghost, false);
			//Story goes back to Prologue
			
		}
			
	public void coffeeRun(){
		
		String Khen = "Khen";
		
		say(name, "Thanks, but I'm going to stick around here for a bit more.",1);
		say(Matt, "Alright, sounds good. ",1); saySlow(Khen+"! how about you?", normal); sleep(2);
		say(Khen, "Nah not today man.", 1);
		pauseSay(Matt, "Oh come on, you always say next time, ","well today is next time. Lets go!",normal);
		pauseSay(Khen, "Alright, alright. ", "Just for you<3", normal);
		say(Derric, "Hey, what about me?!",1);
		say(Matt, "I was just about to ask you!",2);
		say(Derric, "Well now I don't wanna go because you didn't ask me soon enough.",2);
		pauseSay(Matt, " Oh, come on, "+Khen+"'s going too,"," we cant go if you dont go.",normal);
		say(Julia, "The bromance in this room is too real..",2);
		pauseSay(Derric, "Hahaha, just for that,"," i'm going.", normal);
		say(Sebas, "Alright! Let's roll boys!",1);
		printLine();
		say(nar, Matt +", "+ Sebas +", "+ Khen +", & "+ Derric +" headed out the door to get lunch. ",2);
		say(nar, "but as they were walking out, " +Fran+ " walks in", 1);
		printLine();
		say(Matt, "Hey "+Fran+"! you trying to get some lunch?",2);
		say(Fran, "Oh, no thanks guys, maybe tomorrow!",2);
		say(Sebas, "Alright, suit yourself!", 2);
		say(Khen, "Hey, "+Fran+", League tonight?",2);
		say(Fran, "You know it!", 2);
		printLine();
		say(nar, "After "+Khen+" invites "+Fran+" for league, "+Khen+ " and the others head out.",2);
		printLine();
		sleep(2);
		say(Julia, "Phew, glad that's over.",1);
		say(Alexa, "Hahaha, at least they get along.", 1);
		say(Julia, "Yeah, I guess you're right.",1);
		say(Fran, "Hey guys, how's it going?",1);
		say((Julia+ " & " + Alexa), "hey, Francisco!", 1);
		say(Alexa, "meet "+name+".", 1);
		
		Object[] responses2 = {"Nice to meet you "+Fran+" :)","Good afternoon!","Howdy parter!", "Greetings earthling."};
		Response = JOptionPane.showOptionDialog(null,"What will "+name+" say?","Options",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,responses2,responses2[0]);

		printLine();
	
		switch (Response){
	
			case 0: 	say(name, "Nice to meet you "+Fran+" :)",1);
						say(Fran, "Hey, nice to meet you too!",2);
						break;
		 		
			case 1: 	say(name, "Good afternoon!",1);
						say(Fran, "Good afternoon, "+name+".", 2);
						break;
			
			case 2: 	say(name,  "Howdy partner!", 1);
						say(Fran, "Howdy "+name+"!",2);
						break;
				
			case 3: 	say2(name, "Greetings earthling.",1);
						say(Fran, "Haha, hey there!",2);
						break;
		 			
			}//end switch
		
		say(Julia, "I need some coffee",1);
		pauseSay(Julia, "I'm thinking Vivace", ", anyone wanna join me?", normal);
		say(Fran, "I'll come!",2);
		
		Object[] responses = {"I'll come too!","Nah, I'm good."};
		Response = JOptionPane.showOptionDialog(null,"What will "+name+" say?","Options",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,responses,responses[0]);

		printLine();
	
		switch (Response){
	
			case 0: 	say(name, "I'll come too!",1);
						say(Julia, "Awesome, lets get going!",1);
						say(Fran, "Yeah!", 2);
						break;
		 		
			case 1: 	say(name, "Nah, I'm good.",1);
						pauseSay(Julia, "Oh come on newbie",", it's just a coffee run! You'll have fun!", normal);
						say(name, "Okay, I'll join you guys haha.",1);
						say(Julia, "Awesome, lets get going!",1);
						say(Fran, "Yeah!", 2);
						break;
		 			
			}//end switch
		
		switchSong(music.cityStreet,"cityStreet.png");
		showParty = true;
		showInventory = true;
		printSlow("***********Walking up Broadway to get some coffee at Vivache*************");
		printSlow("                 **"+Julia+" and "+Fran+" joined your party**");
		party.party.add(julia);
		party.party.add(fran);
		sleep(2);
		
		say("Julia", "Phew, the fresh air is nice!",2);
		say("Francisco", "Not anymore, I smell some curry",3);
		curryLady.showSprite = true;
		say("Random Lady", "Don't you just love the smell of curry?? :D",3);
		printLine();
		saySlow("**The random lady starts to hug Julia out of nowhere**", normal);
		printLine(); sleep(2);
		saySlow("Julia: Ummm...", slow); sleep(2);
		say("Julia", "A little help guys?",2);
		printLine();
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		partyBattle(curryLady,false);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		printLine();
		say("Francisco", "What was that about?",2);
		say("Julia", "I don't know, but apparently she really likes curry..",2);
		say(name, "That was pretty weird. Who in their right mind just hugs a random person.",3);
		say(name, "Specially in Seattle.",1);
		say(Fran, "You don't see that every day",2);
		printLine();
		say(nar, "After a short walk, the group enters the coffee shop",2);
		printLine();
		switchSong(music.vivace, "vivace.jpg");
		printSlow("****************Vivace****************");
		printLine();
		sleep(1);
		printLine();
		
		say(Julia, "Ah yes, time for some coffee",3);
		
		say(Julia, "I'll have a White Velvet please", 3);
		printLine("**"+Julia+" bought a White Velvet**");sleep(2);
		
		say(Fran, "Good afternoon, I'll take a White Velvet as well.",3);
		printLine("**"+Fran+" bought a White Velvet**"); sleep(2);
		say("Barista", "Good afternoon, how may i help you?",4);
		say(name, "Oh, my turn already? um.. let's see...",3);
		
		Object[] responses3 = {"Espresso - $2.99", "Hot Chocolate - $3.99", "White Velvet - $5.50", "Strawberry Frapuccino - $5.00"};
		Response = JOptionPane.showOptionDialog(null, "What are you going to buy?", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses3, responses3[0]);
		
			
			printLine();
			
			switch (Response){
			
				case 0: 	say(name, "Ah yes, I'll have an Espresso please!",3);
							say("Barista", "Sure! that'll be $3.27",3);
							printLine("             **You paid $3.27**");
							//music.SFXitemGet.play();
							printLine(); 
							sleep(1);
							money -= 3.27;
							item1 = "Espresso";
							printLine("          ***You got an Espresso!***"); 
							printLine(); 
							sleep(2);
							printLine("***You placed the espresso in your inventory***"); 
							sleep(2); 
							say("Barista", "Thank you, have a nice day!", 3);textResponse = "1";
							break;
				 		
				case 1:	    say(name, "Ah yes, i'll have a hot chocolate please!",3);
							say("Barista", "Sure! that'll be $4.37",3);
							printLine("              **You paid $4.37**");
							sleep(1);
							//music.SFXitemGet.play();
							printLine(); 
							money-=4.37;
							item1 = "Hot Chocolate";
							printLine("          ***You got a Hot Chocolate!***"); 
							printLine(); 
							
							sleep(2);
							printLine("   ***You placed the Hot Chocolate in your inventory***");
							sleep(2);
							say("Barista", "Thank you, have a nice day!", 3);textResponse = "2";
							break;
					
				case 2: 	say(name, "Ah yes, i'll have the White Velvet as well please!",3);
							say("Barista", "Sure! that'll be $6.02",3);
							printLine("              **You paid $6.02**");
							money -= 6.02;
							item1 = "White Velvet";
							//music.SFXitemGet.play(); sleep(1);
							printLine(); 
							printLine("          ***You got White Velvet!***"); 
							printLine(); 
							sleep(2);
							printLine("    ***You placed the White Velvet in your inventory***"); 
							sleep(2);
							say("Barista", "Thank you, have a nice day!", 3);
							break;
						
				case 3: 	say(name, "Ah yes, i'll have the Straberry Frapuccino please!",3);
							say("Barista", "Sure! that'll be $5.48",3);
							printLine("              **You paid $5.48**");
							money -= 5.48;
							item1 = "Strawberry Frapuccino";
							//music.SFXitemGet.play(); sleep(1);
							printLine(); 
							printLine("          ***You got Strawberry Frapuccino!***"); 
							printLine(); 
							sleep(2);
							printLine("    ***You placed the Strawberry Frapuccino in your inventory***"); 
							sleep(2);
							say("Barista", "Thank you, have a nice day!", 3);
							break;
			
			}


			say(Fran, "Alright, let's head out!!",3);
			switchSong(music.cityStreet, "cityStreet2.png");
			
			say(Julia, "Phew, I guess this White Velvet will have to replace my need for coffee, It's just too good",3);
			pauseSay(Fran, "I must agree."," How do you like your "+item1+", "+name+"?", normal);
			say(name, "I'll haven't tried it yet, I'm gonna save it for after the MESA meeting.",2);
			say(Fran, "But then it's gonna get cold!",2);
			say(Julia, "Don't let him tell you how to run your life!",2);
			say(name, "Haha, no it's all good!",2);
			
			printLine();
			say(nar, name+ " and company headed back to North Plaza", 2);
			say(nar, "but as "+Fran+" reaches for the door knob...",2);
			printLine();
			say(Fran, "Uh, it's locked.", 2);
			say(Julia, "Really? it's barely 2 in the afternoon",2);
			say(name, "Maybe you guys could call or text someone who might be in there?", 2);
			say(Julia, "Good thinking "+name+".", 1);
			say(Fran, "Yeah, let's try that.", 1);
			
			printLine();
			printSlow("**********FIVE MINUTES LATER*************");
			printLine();
			sleep(4);
			//Backdoor of northPlaza
			changeBackground("backDoor.png");
			say(Fran, "Welp, i say we just go through the backdoor", 3);
			say(name, "That's what she said ;D",3);
			say(Fran, "Wow, okay, calm down newbie haha.", 3);
			say(Julia, "oh god..",2);
			say(Julia, "Alright alright, enough joking around, you two. let's go.",3);
			printLine();
			switchSong(music.northPlaza,"backEntrance.png");
			say(nar, "The three walk in through the back door and towards the mesa room, but they hear something",3);
			printLine();
			s1("What was that?");
			s2("I'm not sure, I think it came from the basement");
			ss3("Yeah... " ," I'm not gonna go find out what that was..");
			printLine();
			s2("Probably just a rat, nothing to worry about.");
			s3("i sure hope so.");
			s1("yeah me too.");
			sleep(2);
			printLine();
			saySlow("Ghost: Boo...",slow);
			sleep(1);
			say(Julia, "O_O",1);
			say(Fran, "O___O'",1);
			say(name, "What the..",0);
			partyBattle(ghost, false);
			//end coffeeRun
			
	}
		
		
		
		
		///////////////////////////////////////////////////////////////////////////ALLL THE STUFF NEEDED TO SIMULATE A COSOLE//////////////////////////////////////////////////////////////
		
		/*
		 * The following s# methods print out the regular say methods based on who's in the party.
		 * ex: s1 would print out a say for the person in the front of the party. etc.
		 */
	
		public void s1(String s){
			say(party.party.get(0).name, s ,2);
		}
		public void s2(String s){
			say(party.party.get(1).name, s ,2);
		}
		public void s3(String s){
			say(party.party.get(2).name, s ,2);
		}
		public void s4(String s){
			say(party.party.get(3).name, s ,2);
		}
		
		public void ss1(String s1, String s2){
			pauseSay(party.party.get(0).name, s1, s2, normal);
		}
		public void ss2(String s1, String s2){
			pauseSay(party.party.get(1).name, s1, s2, normal);
		}
		public void ss3(String s1, String s2){
			pauseSay(party.party.get(2).name, s1, s2, normal);
		}
		public void ss4(String s1, String s2){
			pauseSay(party.party.get(3).name, s1, s2, normal);
		}
		
		
	
		public void printLine(){
			if(count<27){
				stringArray.set(count, "");
				setText(true);
			}else{
				stringArray.remove(0);
				stringArray.add("");
				setText(true);
			}
			
		}
		public void printLine(String s){
			if(count<27){
				stringArray.set(count, s);
				setText(true);
			}else{
				stringArray.remove(0);
				stringArray.add(s);
				setText(true);
			}
			
		}
		
		public void print(String s){
			stringArray.set(count-1, stringArray.get(count-1)+s);
			setText(false);
		}
		
		public void say(String name, String sentence, double sleep){
			String temp = name+": ";
			
			if(count<27){
				stringArray.set(count, temp);
				setText(true);
			}else{
				stringArray.remove(0);
				stringArray.add(temp);
				setText(true);
			}
			saySlow(sentence, normal);
			sleep(2);
		}
		public void say2(String name, String sentence, double sleep){
			String temp = name+": ";
			
			if(count<27){
				stringArray.set(count, temp);
				setText(true);
			}else{
				stringArray.remove(0);
				stringArray.add(temp);
				setText(true);
			}
			saySlow(sentence, slow);
			sleep(2);
		}
		
		
		public void say3(String name, String sentence, double sleep){
			String temp = name+": ";
			
			if(count<27){
				stringArray.set(count, temp);
				setText(true);
			}else{
				stringArray.remove(0);
				stringArray.add(temp);
				setText(true);
			}
			saySlow(sentence, slower);
			sleep(2);
		}
		
		public void saySlow(String s, double howSlow){
			
			for(int i =0; i < s.length(); i++){
				stringArray.set(count-1, stringArray.get(count-1)+s.charAt(i));
				setText(false);
				sleep(howSlow);
			}
		}
		
		
		/**
		 * This makes a character speak with a small pause and how slowly they say the second sentence with a pause of 1.5s
		 * @param name
		 * @param sentence
		 * @param sentence2
		 * @param howSlow
		 */
		public void pauseSay(String name, String sentence, String sentence2, double howSlow){
			
			say(name, sentence, 1);
			saySlow(sentence2, howSlow);
			sleep(1.5);
			
		}
		
		
		public void printSlow(String s){
			printLine();
			saySlow(s,slow);
		}
		
		public void portal(){
			for(int i = 0; i<20; i++){
			printLine("/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
			sleep(.11);
			printLine("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
			sleep(.11);
			printLine("/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
			sleep(.11);
			printLine("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
			sleep(.11);
			}
			for(int i = 0; i<29; i++){
				printLine();
			}
		}
		
		public void reset(){
			for(int i = 0; i<35;i++){
				printLine();
			}
			count = 1;
		}
		
		
		public void setText(boolean addCount){
			advWindow.suspendRepaints();
			//Rectangle back = new Rectangle(0, 0, 2000, 2000, Color.BLACK, true);
			
			
			advWindow.erase();
			//advWindow.add(back);
			advWindow.add(bgn);
			
			line1  = new TextShape( stringArray.get(0) , 100,  50,  Color.WHITE);line2  = new TextShape( stringArray.get(1) , 100,  70,  Color.WHITE);line3  = new TextShape( stringArray.get(2) , 100,  90,  Color.WHITE);
			line4  = new TextShape( stringArray.get(3) , 100, 110,  Color.WHITE);line5  = new TextShape( stringArray.get(4) , 100, 130,  Color.WHITE);line6  = new TextShape( stringArray.get(5) , 100, 150,  Color.WHITE);
			line7  = new TextShape( stringArray.get(6) , 100, 170,  Color.WHITE);line8  = new TextShape( stringArray.get(7) , 100, 190,  Color.WHITE);line9  = new TextShape( stringArray.get(8) , 100, 210,  Color.WHITE);
			line10 = new TextShape( stringArray.get(9) , 100, 230,  Color.WHITE);line11 = new TextShape( stringArray.get(10), 100, 250,  Color.WHITE);line12 = new TextShape( stringArray.get(11), 100, 270,  Color.WHITE);
			line13 = new TextShape( stringArray.get(12), 100, 290,  Color.WHITE);line14 = new TextShape( stringArray.get(13), 100, 310,  Color.WHITE);line15 = new TextShape( stringArray.get(14), 100, 330,  Color.WHITE);
			line16 = new TextShape( stringArray.get(15), 100, 350,  Color.WHITE);line17 = new TextShape( stringArray.get(16), 100, 370,  Color.WHITE);line18 = new TextShape( stringArray.get(17), 100, 390,  Color.WHITE);
			line19 = new TextShape( stringArray.get(18), 100, 410,  Color.WHITE);line20 = new TextShape( stringArray.get(19), 100, 430,  Color.WHITE);line21 = new TextShape( stringArray.get(20), 100, 450,  Color.WHITE);
			line22 = new TextShape( stringArray.get(21), 100, 470,  Color.WHITE);line23 = new TextShape( stringArray.get(22), 100, 490,  Color.WHITE);line24 = new TextShape( stringArray.get(23), 100, 510,  Color.WHITE);
			line25 = new TextShape( stringArray.get(24), 100, 530,  Color.WHITE);line26 = new TextShape( stringArray.get(25), 100, 550,  Color.WHITE);line27 = new TextShape( stringArray.get(26), 100, 570,  Color.WHITE);
			if(showInventory){
				partyLabel = new TextShape("Party:",750,25,Color.WHITE);
				advWindow.add(partyLabel);
				inventorySlot1 = new TextShape(item1, 1000,55,  Color.WHITE);
				inventorySlot2 = new TextShape(item2, 1000,105, Color.WHITE);
				inventorySlot3 = new TextShape(item3, 1000,155, Color.WHITE);
				inventorySlot4 = new TextShape(item4, 1000,205, Color.WHITE);
				itemsLabel = new TextShape("Items:",990,25,Color.WHITE);
				walletLabel = new TextShape("Wallet:",990,245,Color.WHITE);
				wallet         = new TextShape("$"+Math.round(money*100.0)/100.0, 1000, 265, Color.WHITE);
				
				//Boxes for party, inventory, and wallet 
				 advWindow.add(itemBox); advWindow.add(walletBox);
				
				//TextShapes for the items, and Wallet
				advWindow.add(inventorySlot1); advWindow.add(inventorySlot2); 
				advWindow.add(inventorySlot3); advWindow.add(inventorySlot4);
				advWindow.add(wallet);
				
				//Labels
				advWindow.add(itemsLabel);
				advWindow.add(walletLabel);
			}
			//tgis will show the HP and HP bars
			if(battleInProgress){
				
				playerHP = new Rectangle(730,phpY,7,phpHeight,phpColor,true);
				enemyHP = new Rectangle(1182,ehpY,7,ehpHeight,ehpColor,true);
				
				
				advWindow.add(enemyHP);
				advWindow.add(playerHP);
		
				
				if(showBattleHP){
				phpText = new TextShape("HP: "+php, 725,640, Color.WHITE);
				advWindow.add(phpText);

				ehpText = new TextShape("HP: "+ehp, 1145, 640,  Color.WHITE);
				advWindow.add(ehpText);
				}
			}
			
			
			//Attach Enemy sprites to Battle
			//TODO DO NOT REMOVE THIS UNTIL ALL ENEMIES HAVE BEEN ACCOUNTED FOR
			//ENEMY COUNT: 4
			
			if(druggie.showSprite){
				advWindow.add(DruggieSprite);
			}else if(ghost.showSprite){
				advWindow.add(GhostSprite);
			}else if(curryLady.showSprite){
				advWindow.add(CurryLadySprite);
			}else if(yuga.showSprite){
				advWindow.add(YugaSprite);
			}else if(deadHand.showSprite){
				advWindow.add(DeadHandSprite);
			}
			
			
			if(showParty){
				int x = 820;
				int y = 65;
				int scale = 80 ;

				
				
				
				
				if(party.party.size() >= 1){
					int party1HP;
					int i = 0;
					if(party.party.get(0).HP < 0){
						party1HP = 0;
					}else{
						party1HP = party.party.get(i).HP;
					}
					partyMember1 = new TextShape(party.party.get(i).name, x,y, Color.WHITE);
					partyMem1HP = new TextShape(" HP: "+party1HP, x,y+15, Color.WHITE);
					partyMem1Lvl = new TextShape(" Lvl. "+party.party.get(i).level, x,y+30, Color.WHITE);
					party.party.get(i).icon.moveTo(xx, y1);partyBox.resize(xxx, yy1);advWindow.add(partyBox);
					advWindow.add(partyMember1); advWindow.add(partyMem1HP); advWindow.add(partyMem1Lvl);
					if(party.party.get(i).inBattle){
						
						
					advWindow.add(party.party.get(i).sprite);
					}
					advWindow.add(party.party.get(i).icon);
					
					if(showBattleHP&&party.party.get(0).inBattle){
						phpText = new TextShape("HP: "+party1HP, 775,640, Color.WHITE);
						advWindow.add(ehpText);
					}
					
				}
				if(party.party.size() >=2){
					int party2HP;
					if(party.party.get(1).HP < 0){
						party2HP = 0;
					}else{
						party2HP = party.party.get(1).HP;
					}
					partyMember2 = new TextShape(party.party.get(1).name, x,y+scale, Color.WHITE);
					partyMem2HP = new TextShape(" HP: "+party2HP, x,y+scale+15, Color.WHITE);
					partyMem2Lvl = new TextShape(" Lvl. "+party.party.get(1).level, x,y+30+scale, Color.WHITE);
					partyBox.resize(xxx, yy2);advWindow.add(partyBox);
					advWindow.add(partyMember2); advWindow.add(partyMem2HP); advWindow.add(partyMem2Lvl);
					party.party.get(1).icon.moveTo(xx, y2);
					if(party.party.get(1).inBattle){
						advWindow.add(party.party.get(1).sprite);
					}
					advWindow.add(party.party.get(1).icon);
				}
				if(party.party.size() >= 3){
					int party3HP;
					if(party.party.get(2).HP < 0){
						party3HP = 0;
					}else{
						party3HP = party.party.get(2).HP;
					}
					partyMember2 = new TextShape(party.party.get(2).name, x,y+(scale*2), Color.WHITE);
					partyMem2HP = new TextShape(" HP: "+party3HP, x,y+(scale*2)+15, Color.WHITE);
					partyMem2Lvl = new TextShape(" Lvl. "+party.party.get(2).level, x,y+30+(scale*2), Color.WHITE);
					partyBox.resize(xxx, yy3);advWindow.add(partyBox);
					advWindow.add(partyMember2); advWindow.add(partyMem2HP); advWindow.add(partyMem2Lvl);
					party.party.get(2).icon.moveTo(xx, y3);
					if(party.party.get(2).inBattle){
						advWindow.add(party.party.get(2).sprite);
					}
					advWindow.add(party.party.get(2).icon);
				}
				if(party.party.size() >= 4){
					int party3HP;
					int i = 3;
					if(party.party.get(3).HP < 0){
						party3HP = 0;
					}else{
						party3HP = party.party.get(3).HP;
					}
					partyMember3 = new TextShape(party.party.get(i).name, x,y+(scale*i), Color.WHITE);
					partyMem3HP = new TextShape(" HP: "+party3HP, x,y+(scale*i)+15, Color.WHITE);
					partyMem3Lvl = new TextShape(" Lvl. "+party.party.get(i).level, x,y+30+(scale*i), Color.WHITE);
					partyBox.resize(xxx, yy4); advWindow.add(partyBox);
					advWindow.add(partyMember3); advWindow.add(partyMem3HP); advWindow.add(partyMem3Lvl);
					party.party.get(i).icon.moveTo(xx, y4);
					if(party.party.get(i).inBattle){
					advWindow.add(party.party.get(i).sprite);
				}
					advWindow.add(party.party.get(i).icon);
				}
				
			}
			
			advWindow.add(textWindow); 
			advWindow.add(line1);  advWindow.add(line2); advWindow.add(line3); advWindow.add(line4); advWindow.add(line5); advWindow.add(line6); advWindow.add(line7); advWindow.add(line8); advWindow.add(line9);
			advWindow.add(line10);advWindow.add(line11);advWindow.add(line12);advWindow.add(line13);advWindow.add(line14);advWindow.add(line15);advWindow.add(line16);advWindow.add(line17);advWindow.add(line18);
			advWindow.add(line19);advWindow.add(line20);advWindow.add(line21);advWindow.add(line22);advWindow.add(line23);advWindow.add(line24);advWindow.add(line25);advWindow.add(line26);advWindow.add(line27);
			
			
			if(addCount){
					count++;
			}
			if(count>27){
				count = 27;
			}
			advWindow.resumeRepaints();
		}
		
		private void populateStrings(){
			
			s1  = "";s2  = "";s3  = "";s4  = "";s5  = "";s6  = "";s7  = "";s8  = "";s9  = "";s10 = "";s11 = "";s12 = "";s13 = "";
			s14 = "";s15 = "";s16 = "";s17 = "";s18 = "";s19 = "";s20 = "";s21 = "";s22 = "";s23 = "";s24 = "";s25 = "";s26 = "";s27 = "";
			
			stringArray.add(s1); stringArray.add(s2); stringArray.add(s3); stringArray.add(s4); stringArray.add(s5); stringArray.add(s6); stringArray.add(s7); stringArray.add(s8); stringArray.add(s9);
			stringArray.add(s10);stringArray.add(s11);stringArray.add(s12);stringArray.add(s13);stringArray.add(s14);stringArray.add(s15);stringArray.add(s16);stringArray.add(s17);stringArray.add(s18);
			stringArray.add(s19);stringArray.add(s20);stringArray.add(s21);stringArray.add(s22);stringArray.add(s23);stringArray.add(s24);stringArray.add(s25);stringArray.add(s26);stringArray.add(s27);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Character chooseYourCharacter(String WhatAreYouChoosing){
			
			Object[] responses3 = new Object[charactersArray.size()]; 
			
			for(int i = 0; i < charactersArray.size(); i++){
				responses3[i] = charactersArray.get(i);
			}
			
			Response = JOptionPane.showOptionDialog(null, WhatAreYouChoosing, "MesAdventure", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses3, responses3[0]);
			
			if(charactersArray.get(Response).name.equals("Abdul")){
				name = "Abdul";
				Abdul = "Steven";
				return charactersArray.remove(Response);	
			} else if(charactersArray.get(Response).name.equals("Alexa")){
				name = "Alexa";
				Alexa = "Mea";
				return charactersArray.remove(Response);	
			} else if(charactersArray.get(Response).name.equals("Abdul")){
				name = "Abdul";
				Abdul = "Steven";
				return charactersArray.remove(Response);	
			} else if(charactersArray.get(Response).name.equals("Francisco")){
				name = "Francisco";
				Fran = "Tony";
				return charactersArray.remove(Response);	
			} else if(charactersArray.get(Response).name.equals("Tony")){
				name = "Tony";
				Tony = "Musa";
				return charactersArray.remove(Response);	
			} else {
				
				return charactersArray.remove(Response);
				
			}
			
			
			
		}
		
		/**
		 * Makes sure every character in the party gets to fight, if the enemy has not been defeated
		 * @param party
		 * @param enemy
		 * @param boss
		 * @return
		 */
		public boolean partyBattle(Enemy enemy, boolean boss){
			
			boolean won = false; //default, not final
			chooseFighter(party); 
			song.stop(); //stops the song playing
			battleInProgress = true; 
			
			if(party.party.size() == 1){
				if(battle.Battle(party.party.get(0),enemy, boss, main, party)){
					ehpColor = Color.green;
					battleInProgress = false;
					sleep(1);
					song.loop(); //starts looping the story song playing before the battle
					php = 0;
					ehp = 0;
					won = true;
					return won;
					
				}else{
					battleInProgress = false;
					sleep(1);
					if(!won){
						System.exit(0);
					}
					php = 0;
					ehp = 0;
					return won;
				}
				
			}
			
			for(int i =0; i< tempParty.party.size(); i++){ //loop through all party members until they all die or someone wins
				System.out.println("Enter for loop" + i);
				
				if(battle.Battle(tempParty.party.get(i),enemy, boss, main, party)){
					System.out.println("if battle win 1");
					ehpColor = Color.green;
					won = true;

					battleInProgress = false;
					sleep(1);
					song.loop();
					php = 0;
					ehp = 0;
					return won;
					
					
				}else{
					won = false;
					System.out.println("else battle else 1");
					sleep(1);
					php = 0;
					ehp = 0;
					
				}
				
			

				
			}
			if(!won && !enemy.name.equals("Yuga")){
				System.exit(0);
			}
			System.out.println("out of for loop");
			return won;
			
		}
		
		/**
		 * This method let's you choose the order of the battle.
		 * THIS METHOD IS ALSO A BUNCH OF NONSENSE BUT IT WORKS SO WHATEVER
		 * But if party is 4 or more, you choose who will go first, and the rest of the team will be randomized
		 * @param mainParty
		 * ffffffffffffffffffffffffffffffffffffuck this took longer than i thought and i hate myself for it.
		 * 
		 */
		public void chooseFighter(Party mainParty){
			int n = mainParty.party.size();
			
			if(n == 1){
				
			}else if(n == 2){
				Object[] options = {mainParty.party.get(0).name,mainParty.party.get(1).name};
				int op = JOptionPane.showOptionDialog(null,"Who will fight first?",	"Choose your Character", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
				switch (op){
					case 0:	tempParty = new Party(mainParty.party.get(0)); tempParty.addChar(mainParty.party.get(1)); break;
					case 1: tempParty = new Party(mainParty.party.get(1)); tempParty.addChar(mainParty.party.get(0)); break;
			}}else if(n==3){
				Object[] options = {mainParty.party.get(0).name,mainParty.party.get(1).name,mainParty.party.get(2).name};
				int op = JOptionPane.showOptionDialog(null,"Who will fight first?",	"Choose your Character", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
				switch (op){
					case 0:	tempParty = new Party(mainParty.party.get(0));
					
							Object[] option = {mainParty.party.get(1).name,mainParty.party.get(2).name};
							int o0 = JOptionPane.showOptionDialog(null,"Who will fight second?",	"Choose your Character", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
							switch (o0){
								case 0:	tempParty.addChar(mainParty.party.get(1)); tempParty.addChar(mainParty.party.get(2)); break;
								case 1:	tempParty.addChar(mainParty.party.get(2)); tempParty.addChar(mainParty.party.get(1)); break;
							}break;
					case 1:	tempParty = new Party(mainParty.party.get(1));
							Object[] option1 = {mainParty.party.get(0).name,mainParty.party.get(2).name};
							int o1 = JOptionPane.showOptionDialog(null,"Who will fight second?",	"Choose your Character", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option1,option1[0]);
							switch (o1){
								case 0:	tempParty.addChar(mainParty.party.get(0)); tempParty.addChar(mainParty.party.get(2)); break;
								case 1:	tempParty.addChar(mainParty.party.get(2)); tempParty.addChar(mainParty.party.get(0)); break;
							}break;
					case 2: 	tempParty = new Party(mainParty.party.get(2));
								Object[] option2 = {mainParty.party.get(0).name,mainParty.party.get(1).name};
								int o2 = JOptionPane.showOptionDialog(null,"Who will fight second?",	"Choose your Character", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option2,option2[0]);
							switch (o2){
								case 0:	tempParty.addChar(mainParty.party.get(0)); tempParty.addChar(mainParty.party.get(1)); break;
								case 1:	tempParty.addChar(mainParty.party.get(1)); tempParty.addChar(mainParty.party.get(0)); break;
							}break;	
			}}else if(n == 4){
				Object[] options = {mainParty.party.get(0).name,mainParty.party.get(1).name,mainParty.party.get(2).name,mainParty.party.get(3).name};
				int op = JOptionPane.showOptionDialog(null,"Who will fight first?",	"Choose your Character", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
				Integer n0,n1,n2,n3;
				ArrayList<Integer> ar = new ArrayList<Integer>();
				n0 = 0;
				n1 = 1;
				n2 = 2;
				n3 = 3;
				ar.add(n0); ar.add(n1); ar.add(n2); ar.add(n3);
				switch (op){
					case 0:	tempParty = new Party(mainParty.party.get(n0));
							ar.remove(n0);
							Collections.shuffle(ar);
							tempParty.addChar(mainParty.party.get(ar.get(0)));
							tempParty.addChar(mainParty.party.get(ar.get(1)));
							tempParty.addChar(mainParty.party.get(ar.get(2)));
							break;
					case 1: tempParty = new Party(mainParty.party.get(n1));
							ar.remove(n1);
							Collections.shuffle(ar);
							tempParty.addChar(mainParty.party.get(ar.get(0)));
							tempParty.addChar(mainParty.party.get(ar.get(1)));
							tempParty.addChar(mainParty.party.get(ar.get(2)));
							break;	
					case 2: tempParty = new Party(mainParty.party.get(n2));
							ar.remove(n2);
							Collections.shuffle(ar);
							tempParty.addChar(mainParty.party.get(ar.get(0)));
							tempParty.addChar(mainParty.party.get(ar.get(1)));
							tempParty.addChar(mainParty.party.get(ar.get(2)));
							break;
					case 3: tempParty = new Party(mainParty.party.get(n3));
							ar.remove(n3);
							Collections.shuffle(ar);
							tempParty.addChar(mainParty.party.get(ar.get(0)));
							tempParty.addChar(mainParty.party.get(ar.get(1)));
							tempParty.addChar(mainParty.party.get(ar.get(2)));
							break;
				}
			}
		}
		
		
		public void changeBackground(String s){
			background = advWindow.getImageFromFilename(s);
			bgn = new ImageShape(background, 0, 0);
			sleep(2);
		}
		
		
		public void switchSong(AudioClip newSong, String background){
			sleep(1);
			song.stop();
			changeBackground(background);
			music.SFXdoorSound.play();
			sleep(2);
			
			song = newSong;
			song.loop();
		}
		
		public static void waitForKeyPress(){
			System.out.println("Waiting...    ");
			boolean waitingForKeyPress = true;
			
			while(waitingForKeyPress){
				
				//TODO add click or key-press support
				
			}
			
			
		}
		
		public static void sleep(double seconds){
			
			try {
				Thread.sleep((int)(seconds*500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public static void printt(String s) {
			System.out.println(s);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}



}