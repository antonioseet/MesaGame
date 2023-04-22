import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import uwcse.graphics.Rectangle;


public class Battle extends Announcer{
	
	private Clip clip;
	private boolean passOnce = true;//makes sure you only populate the intro and outro phrasesonce per battle
	private ArrayList<String> winPhrases = new ArrayList<String>();
	private ArrayList<String> introPhrases = new ArrayList<String>();
	private ArrayList<String> losePhrases = new ArrayList<String>();
	
	public Rectangle playerHP, enemyHP;

	private int numberOfSpells = 4;	
	private int numberOfPotions = 3;
	private int expTurns;
	private int turns = 1;
	
	private Random rand = new Random();
	
	//Character and Enemy instances
	private Party party;
	private Character char1;
	private Enemy enemy1;
	public Music music = new Music();
	private Announcer anounce = new Announcer();
	
	//Max HP of both parties;
	private int MAX_HP;
	private int MAX_HP_ENEMY;
	private int MAX_POTIONS = 3;//also change numberOfPotions
	private int MAX_SPELLS = 4; //also change numberOfSpells
	private static int POTION_RECOVER = 300;
	private static double txt = 1.5;
	
	//HP BARS
	private boolean playerHPBAR = true;
	
	
	
	//Boolean to announce "This is getting Dangerous" just once per battle
	private boolean sayLowHP = true;
	private boolean won;
	
	private MainWindow window;
	
	
	
	public boolean Battle(Character character, Enemy enemy, boolean bossBattle, MainWindow adVwindow, Party part){
		
		//Set parameter characters as the stars of the show
		this.party = part;
		char1 = character;
		enemy1 = enemy;
		window = adVwindow;
		
		//Get their HP to replenish after battle ends
		MAX_HP = char1.HP;
		MAX_HP_ENEMY = enemy1.HP;
		window.phpColor = Color.green;
		
		//Start music
		Announcer announcer = new Announcer();
		
		initiateMusic(bossBattle);
		
		//Show FIGHT banner
		banner();
		sleep(1);
		window.say(enemy1.name, enemy1.introPhrase, 2);
		window.printLine();
		window.say(char1.name, introPhrases.get(rand.nextInt(introPhrases.size())), 2);
		//Displays who's Fighting
		window.printLine();
		sleep(2);
		char1.inBattle=true;
		window.showBattleHP = true;
		window.printLine("    "+char1.name);
		startHP();
		window.printLine("        V.S."); 
		if(rand.nextInt(99)>50){
			announcer.start.play();
		}
		sleep(1);
		enemy1.showSprite = true;
		window.printLine("            "+enemy1.name);
		startHP();
		
		sleep(2);
		expTurns = 1;
		turns  = 1;
		
		do{
			
			window.printLine();
			window.printLine("TURN: "+turns);
			sleep(.5);
			//showBothHP(char1, enemy1);
			
			//window.printLine(" ______________________________________________________________");
			//window.printLine("|                                                                                                                                                |");
			//window.printLine("| 1)  "+char1.standard.attackName+"!                                          2) "+char1.spell.spellName+"! ("+numberOfSpells+")        ");
			//window.printLine("|                                                              ");
			//window.printLine("| 3)  Potions("+numberOfPotions+")                              ");
			//window.printLine("|   [heals "+POTION_RECOVER+ " HP]                                             ");
			//window.printLine("|______________________________________________________________|");
			
			//Shows dialog box that will only close when you choose one of the options
			String s;
			String p;
			if(numberOfSpells == 0){
				s = "No spells left!   ";
			}else{
				s = "  Ability ("+numberOfSpells+" left)     ";
			}
			if(numberOfPotions == 0){
				p = "No potions left!";
			}else{
				p = "Potions ("+numberOfPotions+" left)";
			}
			
			Object[] options = {char1.standard.attackName,char1.spell.spellName, "Potion: Heals "+POTION_RECOVER+" HP"};
			int op = JOptionPane.showOptionDialog(null," Attack                         "+s+"                 "+p,
					char1.name+" V.S. "+enemy1.name,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
			
			window.printLine("");
			
			switch (op){
			//determines who goes first and subtracts the HP from the player or enemy
			//if player is faster
			case 0:	standardAttack();
			 		break;
			 		
			case 1: spell();
			        break;
				
			case 2: potionUsed(char1, enemy1);
					break;
					
			case 3: 
					break;
		}
			
			expTurns += .1;
			turns++;
			
		}while(char1.HP>0 && enemy1.HP>0);
		
		sleep(2);
		
		
		//Restore HP to both Parties so they're ready for the next battle and restores potions & spells//
		char1.HP = MAX_HP;
		//TODO
		numberOfSpells = MAX_SPELLS;
		numberOfPotions = MAX_POTIONS;
		
		// RETURN HEALTH BARS TO NORMAL
		window.phpHeight = 0;
		window.ehpHeight = 0;
		window.ehpY = 620;
		window.phpY = 620;
		
		char1.inBattle = false;
		return won;
		
	}
	
	
	
	/**
	 * This executes when you choose "ATTACK!" 
	 * @param char1 
	 * @param enemy1
	 */
	private void standardAttack(){
		double damageDone = 0;
		if(char1.speed>=enemy1.speed){
			pVeAttack(damageDone);
			if(enemy1.HP<=0){
				showBothHP(char1, enemy1);
				endBattle(true);
			}else{
				eVpAttack(damageDone);
				if(char1.HP<=0){
					showBothHP(char1, enemy1);
					endBattle(false);
				}
			}
		}else{
				eVpAttack(damageDone);
				if(char1.HP<=0){
					showBothHP(char1, enemy1);
					endBattle(false);
				}else{
					pVeAttack(damageDone);
					if(enemy1.HP<=0){
						showBothHP(char1, enemy1);
						endBattle(true);
					}
				}
		}
	}
	
	
	/**
	 * Character Attacks opponent with Standard attack
	 * @param char1 Character
	 * @param enemy1 Opponent
	 * @param damageDone Damage inflicted on Opponent
	 */
	private void pVeAttack(double damageDone){
		window.printLine(char1.name+" used "+char1.standard.attackName);
		sleep(txt);
		if(rand.nextInt(99)<10){
			PlayerCritAnimation(anounce, music.atkSound);
			damageDone = char1.standard.damage * char1.attack * enemy1.defense*1.5 + rand.nextInt(14);
			window.printLine("CRITICAL HIT!!");
		}else if(rand.nextInt(99)<15){
			PlayerMissAnimation(anounce);
			damageDone = 0;
			window.printLine(char1.name+"'s "+char1.standard.attackName+" missed!!");
		}else{
			PlayerAttackAnimation(anounce, music.atkSound);
			damageDone = char1.standard.damage * char1.attack * enemy1.defense + rand.nextInt(10);
		}
		enemy1.lostHP(damageDone);
		enemyLoseHP(damageDone);
		window.printLine("Damage upon "+enemy1.name+": "+  (int)(damageDone));
		sleep(txt);
		window.printLine("");
	}
	/**
	 * Opponent Attacks Character with standard Attack
	 * The attack is chose based on a random number between 1-100
	 * the likelyhood of speels will increase the chance of striking with a spell
	 * @param enemy1 Opponent
	 * @param char1 Character
	 * @param damageDone Damage inflicter on Character
	 * 
	 */
	private void eVpAttack(double damageDone){
		String a;
		boolean spell = false;
		
		if(rand.nextInt(99) < enemy1.likelyhood){
			spell = true;
		}else{
			spell = false;
		}
		
		if(spell){
			a = enemy1.spell.spellName;
		}else{
			a = enemy1.standard.attackName;
		}
		window.printLine(enemy1.name + " used " + a);
		sleep(txt);
		
		if(spell){
			if(rand.nextInt(99)<17){
				damageDone = 0;
				EnemyMissAnimation(anounce);
				window.printLine(enemy1.name + "'s " + a + " missed!!");
			}else{
				sleep(.5);
				EnemySpecialAnimation(anounce, music.atkSound);
				damageDone = (enemy1.spell.damage * enemy1.spAttack * char1.spDefense) + rand.nextInt(15);
			}
			
		}else{
			if(rand.nextInt(99)<17){
				damageDone = 0;
				EnemyMissAnimation(anounce);
				window.printLine(enemy1.name + "'s " + a + " missed!!");
			}else{
				sleep(.5);
				EnemyAttackAnimation(anounce, music.atkSound);
				damageDone = (enemy1.standard.damage * enemy1.attack * char1.defense) + rand.nextInt(15);
			}
		}
		
		
		char1.lostHP(damageDone);
		charLoseHP(damageDone);
		window.printLine("Damage inflicted upon "+char1.name+": "+ (int)(damageDone));
		sleep(txt);
		if(char1.HP < 150 && sayLowHP){
			anounce.lowClip();
			sleep(1);
			sayLowHP = false;
		}
		window.printLine("");
	}

private void spell(){
		
		double damageDone = 0;
		if(char1.speed>=enemy1.speed){
			pVeSpell(damageDone);
			if(enemy1.HP<=0){
				showBothHP(char1, enemy1);
				endBattle(true);
			}else{
				eVpAttack(damageDone);
				if(char1.HP<=0){
					showBothHP(char1, enemy1);
					endBattle(false);
				}
			}
			if(numberOfSpells <= 0){
				numberOfSpells =0;
			}else{
				numberOfSpells -=1;
			}
		}else{
				eVpAttack(damageDone);
				if(char1.HP<=0){
					showBothHP(char1, enemy1);
					endBattle(false);
				}else{
					pVeSpell(damageDone);
					if(enemy1.HP<=0){
						showBothHP(char1, enemy1);
						endBattle(true);
					}
				}
				if(numberOfSpells <= 0){
					numberOfSpells =0;
				}else{
					numberOfSpells -=1;
				}
		}
	}
	
	
	private void pVeSpell(double damageDone){
		if(numberOfSpells >= 1){
			window.printLine(char1.name+" used "+char1.spell.spellName);
			sleep(txt*2);
			if(rand.nextInt(99)<13){
				anounce.critClip();
				//TODO Animation is missing?
			
				damageDone = char1.spell.damage * char1.spAttack * enemy1.spDefense*1.5 + rand.nextInt(10);
				window.printLine("CRITICAL HIT!!!");
			}else if(rand.nextInt(99)<13){
				PlayerMissAnimation(anounce);
				damageDone = 0;
				window.printLine(char1.name+"'s "+char1.spell.spellName+" missed!!");
			}else{
				sleep(.5);
				PlayerSpecialAttackAnimation(anounce, music.splSound);
				damageDone = char1.spell.damage * char1.spAttack * enemy1.spDefense + rand.nextInt(10);
			}
			
		} else{
			window.printLine("You're all out of SPELLS!");
			sleep(1);
			//TODO figure out what to do after you run out of spells
//			window.printLine("");
//			window.printLine("**"+enemy1.name + " stood there confused**");
//			window.printLine("");
//			sleep(1);
			
			return;
		}
		
		if(numberOfSpells > 0){
			enemy1.lostHP(damageDone);
			enemyLoseHP(damageDone);
			window.printLine(char1.name +" inflicted damage upon "+enemy1.name+": "+  (int)(damageDone));
			sleep(txt);
			window.printLine("");
		}
	}
	
	private void potionUsed(Character char1, Enemy enemy1){
		sleep(txt);
		if(numberOfPotions>0){
			int heal = POTION_RECOVER;
			int currentHP = char1.HP;
			
			window.printLine(char1.gainHP(heal, char1.HP));
			
			if(currentHP+POTION_RECOVER >= MAX_HP){
				int newHeal = MAX_HP-currentHP;
				charGainHP(newHeal);
			}else{
				charGainHP(POTION_RECOVER);
			}
			sleep(txt);
			numberOfPotions -=1;
			window.printLine("");
		} else {
			window.printLine(char1.name + " has no more potions! Heal failed!");
			window.printLine("");
		}
		sleep(txt);
		int damageDone = 0;
		eVpAttack(damageDone);
		if(char1.HP<=0){
			showBothHP(char1, enemy1);
			endBattle(false);
		}
	}
	
	/**
	 * gets everything ready to start the battle
	 * -Starts playing the music
	 * -calls the banner function
	 */
	private void initiateMusic(boolean bossBattle){
		//Choose between 5 different tracks
		
		if(bossBattle){
			//TODO make sure to add the boss music according to boss.
			if(enemy1.name.equals("Yuga")){
				clip = 	music.yugaBattle;
			}
			
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			clip = music.getRandomBattleTheme();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		
		
	}
	
	/**
	 * Ends the battle
	 * -Stops the music
	 * -Starts victory or Loss music
	 * -Checks for level ups
	 */
	private void endBattle(boolean wonBattle){
		clip.stop();
		window.printLine(""); 
		Random ran1 = new Random();
		if(wonBattle){
			endBattleSong(true);
			sleep(.5);
			window.printLine("");
			anounce.winClip();
			sleep(2);
			window.say3(enemy1.name, enemy1.losePhrase, 2);
			enemy1.showSprite = false;
			if(char1.name.equals("Kevin")){
				window.say(char1.name, "HA, GOT 'EM!", 2);
			}else{
				window.say(char1.name,  winPhrases.get(ran1.nextInt(winPhrases.size())), 2);
			}
			sleep(1);
			checkLevelUp(party);
			window.ehp = 0;
			window.php = 0;
			won = true;
			
			
		}else{
			endBattleSong(false);
			sleep(1);
			window.say2(char1.name, losePhrases.get(ran1.nextInt(losePhrases.size())), 2);
			window.say2(enemy1.name, enemy1.winPhrase, 2);
			char1.inBattle = false;
			window.printLine("**"+char1.name+" fainted!**");
			window.php = 0;
			window.ehp = 0;
			won = false;
		}
		sleep(3);
	}
	
	private void checkLevelUp(Party party) {
		
		boolean pass = false;
		
		for(int i = 0; i < party.party.size(); i++){
			
			double expGain = expTurns*(MAX_HP_ENEMY)*(enemy1.attack)*(2-enemy1.defense)+rand.nextInt(100);
		int expGainn = (int)(expGain);
		party.party.get(i).exp += expGainn;
		
		if(char1.name.equals(party.party.get(i).name)){
			pass = true;
			expGain+=300*expTurns;
			window.printLine();
			window.printLine("++Fighter defeated enemy Bonus++");
			window.printLine("***"+party.party.get(i).name+" gained " + (int)(expGain) +  " exp***");
			
		}else if (pass){
			window.printLine();
			window.printLine("***"+party.party.get(i).name+" gained " + (int)(expGain) +  " exp***");
		}else{ 
			window.printLine();
			window.printLine("***"+party.party.get(i).name+" gained " + (int)(expGain) +  " exp***");
		}
		
		sleep(2);
		if(expGainn >= party.party.get(i).nextLevelExp){
			do{
				music.SFXlevelUP.start();
				party.party.get(i).level++;
				window.printLine("***"+party.party.get(i).name+" went up to Level "+ party.party.get(i).level + "!***");
				sleep(1);
				//int hp = rand.nextInt(5)+15;//TODO fix the hp increase
				//Right now whoever beats the enemy is the only one who does not get an HP increase.
				//party.party.get(i).HP+=hp;
				//window.printLine("**HP increased by "+hp+"**");
				//sleep(2);
				expGainn -= party.party.get(i).nextLevelExp;
				party.party.get(i).nextLevelExp +=500;
				window.printLine();
				sleep(2);
						
			}while(expGainn >= party.party.get(i).nextLevelExp);
			
		}
		
			
		}	//end for	
	}



	/**
	 * plays the victory or defeat song after the match ends.
	 * @param won
	 */
	public void endBattleSong(boolean won){
		String s;
		if(won){
			clip = music.victory;
		}else{
			clip = music.loss;
		}
		
		clip.start();
	}
	
	
	private void showBothHP(Character char1, Enemy enemy1){
		showHP(enemy1.name, enemy1.HP);
		sleep(txt);
		showHP(char1.name, char1.HP);
		sleep(txt);	
	}
	
	/**
	 * Shows the HP of passed in character with HP
	 * @param name
	 * @param HP
	 */
	private void showHP(String name, int HP){
		window.printLine();
	
		
		if(HP<0){
			HP =0;
		}
		window.printLine(name +": " + HP + "HP");
		sleep(.07);
		window.printLine("HP: ");
		for(int i = 0; i<(HP/10); i++){
			sleep(.013);
			window.print("|");
		}
		
	}
	
	
	/**
	 * Updates the HP BARS based on health lost or gained
	 * @param alphaHP
	 * @param omegaHP
	 */
	public void startHP(){
		int HP = char1.HP;
		int HP2 = enemy1.HP;
		int rate = 4;
		
		if(playerHPBAR){
			for(int i = 0; i<(HP/rate); i++){
				
				window.phpHeight++;
				window.phpY--;
				window.php+=rate;
				sleep(.01);
				window.setText(false);
			}
			if(HP%2 != 0){
				window.php++;
			}
			playerHPBAR = false;
		}else{
			for(int i = 0; i<(HP2/rate); i++){
				
				window.ehpHeight++;
				window.ehpY--;
				window.ehp+=rate;
				sleep(.01);
				window.setText(false);
			}
			if(HP%2 != 0){
				window.ehp++;
			}
			playerHPBAR = true;
		}
		
		
	}
	
	public void charLoseHP(double damageDone){
		int rate = 4;
		
		for(int i = 0; i<(damageDone/rate); i++){
			
			if(char1.HP <= (MAX_HP/2) && char1.HP >= (MAX_HP/4)){
				window.phpColor = Color.YELLOW;
			}else if(char1.HP < (MAX_HP/4)){
				window.phpColor = Color.RED;
			}
			
			window.phpHeight--;
			window.phpY++;
			sleep(.01);
			window.setText(false);
		}
		updateHP();
		
	}
	
	public void enemyLoseHP(double damageDone){
		int rate = 4;
		
		for(int i = 0; i<(damageDone/rate); i++){
			
			if(enemy1.HP <= (MAX_HP_ENEMY/2) && enemy1.HP >= (MAX_HP_ENEMY/4)){
				window.ehpColor = Color.YELLOW;
			}else if(enemy1.HP < (MAX_HP_ENEMY/4)){
				window.ehpColor = Color.RED;
			}
			
			window.ehpHeight--;
			window.ehpY++;
			sleep(.01);
			window.setText(false);
		}
		updateHP();
		
	}
	
	
	public void charGainHP(int hp){
		int rate = 4;
		
		for(int i = 0; i<(hp/rate); i++){
			
			if(char1.HP <= (MAX_HP/4) && char1.HP >= (MAX_HP/4)){
				window.phpColor = Color.YELLOW;
			}else if(char1.HP < (MAX_HP/4)){
				window.phpColor = Color.RED;
			}else{
				window.phpColor = Color.GREEN;
			}
			
			window.phpHeight++;
			window.phpY--;
			sleep(.01);
			window.setText(false);
		}
		updateHP();
		
	}
	
	public void updateHP(){
		
		window.php = char1.HP;
		window.ehp = enemy1.HP;
		
	}
	
	
	/**
	 * Displays the FIGHT! banner
	 */
	private void banner(){
		
		//Populate winning phrases (just oonce)
		
		if(passOnce){
		
		winPhrases.add("Over already?");
		winPhrases.add("Didn't even break a sweat.");
		winPhrases.add("Pathetic.");
		winPhrases.add("That's it? Dissapointing.");
		winPhrases.add("Fool.");
		winPhrases.add("Piece of cake!");
		winPhrases.add("Is my hair okay?");
		winPhrases.add("Know your place.");
		winPhrases.add("Remember this face.");
		winPhrases.add("What a waste of time.");
		winPhrases.add("Too easy.");
		winPhrases.add("Is that all you've got?");
		winPhrases.add("That was boring.");
		winPhrases.add("That was pretty fun.");
		winPhrases.add("You did your best, but it still wasn't good enough.");
		winPhrases.add("Phew, not too bad.");
		winPhrases.add("That's it?");
		winPhrases.add("Get lost.");
		winPhrases.add("You're reckless.");
		winPhrases.add("Even beasts know when to give up.");
		winPhrases.add("Ha! As Expected.");
		winPhrases.add("Stay down, it suits you.");
		winPhrases.add("Over already?");
		winPhrases.add("Hurts, doesn't it?");
		winPhrases.add("Your luck ran out when you messed with me.");
		winPhrases.add("I think we learned something today, I'm not sure what though..");
		winPhrases.add("You tried your best, I guess.");
		winPhrases.add("You were a bit overconfident.");
		winPhrases.add("Wait, I won already?");
		winPhrases.add("Had enough?");
		winPhrases.add("Not bad, not bad.");
		winPhrases.add("Is that all you've got?");
		winPhrases.add("Stay out of my way.");
		winPhrases.add("Come back when you can put up a fight.");
		
		losePhrases.add("This.. can't be!");
		losePhrases.add("How!?");
		losePhrases.add("You've gotta be kidding..");
		losePhrases.add("Damn it.. You're stronger than I thought..");
		losePhrases.add("I failed...");
		losePhrases.add("I messed up..");
		losePhrases.add("I don't believe this..");
		losePhrases.add("This must be some kind of mistake..");
		losePhrases.add("So that's how it ends..");
		
		introPhrases.add("Can we make this quick? I've got things to do.");
		introPhrases.add("You won't suffer much.");
		introPhrases.add("Any last words?");
		introPhrases.add("Shall we get started?");
		introPhrases.add("What's the matter, scared?");
		introPhrases.add("You're going down!");
		introPhrases.add("Try me.");
		introPhrases.add("I'm not gonna go easy on you.");
		introPhrases.add("Challenge accepted.");
		introPhrases.add("Don't cry when this is over.");
		introPhrases.add("Don't know when to quit, do you?");
		introPhrases.add("Let's go!");
		introPhrases.add("Let's get this over with.");
		introPhrases.add("You'll regret this.");
		introPhrases.add("Don't blame me when you get hurt.");
		introPhrases.add("Prepare yourself!");
		introPhrases.add("Who do you think you are?");
		introPhrases.add("Don't waste my time, let's go!");
		introPhrases.add("Out of my sight!");
		introPhrases.add("Have you lost your mind?");
		introPhrases.add("Do you really want to do this?");
		introPhrases.add("Come on, just let me through.");
		introPhrases.add("Watch and learn.");
		introPhrases.add("Another reckless fool.");
		introPhrases.add("This is a waste of my time, and yours.");
		introPhrases.add("Don't cry yourself to sleep tonight.");
		introPhrases.add("You want a piece of me?");
		introPhrases.add("Come on, Let's finish this!");
		introPhrases.add("Think you can keep up?");
		introPhrases.add("Don't underestimate me.");
		introPhrases.add("Quit now, if you want to live.");
		introPhrases.add("You're going down!");
		introPhrases.add("Do you seriously want to fight?");
		introPhrases.add("Come and get it!");
		introPhrases.add("Don't come crying to me afterwards.");
		introPhrases.add("You're gonna be sorry");
		introPhrases.add("You're gonna feel pretty stupid after we're done here.");
		introPhrases.add("Show me what you've got!");
		introPhrases.add("Okay, here we go!");
		introPhrases.add("Alright, let's get this over with.");
		introPhrases.add("You're in for a world of hurt.");
		introPhrases.add("Get ready!");
		introPhrases.add("Last chance to get outta my face!");
		passOnce = false;
		}
		
		
		
		
		window.printLine("");
		window.printLine("**********************************************************************************************");
		window.printLine("********************************************FIGHT!!******************************************");
		window.printLine("***********************************************************************************************");
		window.printLine("");
	}
	
	
	
	
	public String winningPhrase(){
		String phrase = "";
		
		
		
		return phrase;
	}
	
	/**
	 * Attack Animation Choreography for the characters fighting
	 */
	private void PlayerAttackAnimation(Announcer a, Clip sound){

		char1.sprite.moveBy(20, 0);
		enemy1.sprite.moveBy(0, 10);
		sound.start();
		sleep(.1);
		enemy1.sprite.moveBy(10, 0);
		a.hitClip();
		sleep(.1);
		enemy1.sprite.moveBy(0, -10);
		sleep(.1);
		char1.sprite.moveBy(-20, 0);
		enemy1.sprite.moveBy(-10, 0);
		
	}
	
	private void PlayerCritAnimation(Announcer a, Clip sound){
		
		char1.sprite.moveBy(20, 0);
		enemy1.sprite.moveBy(0, 20);
		sound.start();
		sleep(.1);
		enemy1.sprite.moveBy(20, 0);
		
		sleep(.1);
		enemy1.sprite.moveBy(0, -20);
		sleep(.1);
		enemy1.sprite.moveBy(-20, 0);
		a.critClip();
		enemy1.sprite.moveBy(0, 20);
		sleep(.1);
		enemy1.sprite.moveBy(20, 0);
		sleep(.1);
		enemy1.sprite.moveBy(0, -20);
		sleep(.1);
		enemy1.sprite.moveBy(-20, 0);
		char1.sprite.moveBy(-20, 0);
		
	}
	
	private void PlayerMissAnimation(Announcer a){
		
		
		char1.sprite.moveBy(20, 0);
		enemy1.sprite.moveBy(20, 0);
		sleep(.3);
		a.missClip();
		char1.sprite.moveBy(-20, 0);
		sleep(.2);
		enemy1.sprite.moveBy(-20, 0);
		
		
	}
	
	
	private void EnemyAttackAnimation(Announcer a, Clip sound){
		
		enemy1.sprite.moveBy(-20, 0);
		sleep(.1);
		sound.start();
		char1.sprite.moveBy(-10, 0);
		sleep(.3);
		a.hitClip();
		enemy1.sprite.moveBy(20, 0);
		char1.sprite.moveBy(10,0);
		
		
	}
	
	private void EnemyMissAnimation(Announcer a){
		
		enemy1.sprite.moveBy(-20, 0);
		sleep(.5);
		a.missClip();
		enemy1.sprite.moveBy(20,0);
		
		
		
	}
	
	private void EnemySpecialAnimation(Announcer a, Clip sound){
		enemy1.sprite.moveBy(-20,-0);
		sleep(.3);
		sound.start();
		enemy1.sprite.moveBy(50, 0);
		char1.sprite.moveBy(-30, 0);
		a.hitClip();
		sleep(.5);
		char1.sprite.moveBy(30, 0);
		enemy1.sprite.moveBy(-30,0);
		
		
	}
	
	
	private void PlayerSpecialAttackAnimation(Announcer a, Clip sound){
		
		char1.sprite.moveBy(-20, -10);
		sleep(.2);
		sound.start();
		char1.sprite.moveBy(50,10);
		enemy1.sprite.moveBy(30, 10);
		a.hitClip();
		sleep(.35);
		char1.sprite.moveBy(-30, 0);
		enemy1.sprite.moveBy(-30, -10);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * pauses the program for a set amount of seconds
	 * @param seconds
	 */
	private void sleep(double seconds){
		try {
			Thread.sleep((int)(seconds*950));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
