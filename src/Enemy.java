import uwcse.graphics.ImageShape;


public class Enemy {
	
	public String name;
	public int HP;
	public double defense;
	public double attack;
	public double spAttack;
	public double spDefense;
	public int speed;
	public int likelyhood;
	public boolean human;
	public Attack standard;
	public Spell spell;
	public String introPhrase, winPhrase, losePhrase;
	public ImageShape sprite = null;
	//shows battle sprite
	public boolean showSprite = false;

	public Enemy(String name2, int hp, double atk, double def, double spAtk, double spDef, int speed2, int spellLikelyhood, String attackName, String spellName, String intro, String won, String lost){
		
		name = name2;
		HP = hp;
		attack = atk;
		defense = def;
		speed = speed2;
		spAttack = spAtk;
		spDefense = spDef;
		standard = new Attack(attackName, 100);
		spell = new Spell(spellName, 130);
		introPhrase = intro;
		winPhrase = won;
		losePhrase = lost;
		likelyhood = spellLikelyhood;
		
	}
	
public Enemy(String name2, int hp, double atk, double def, double spAtk, double spDef, int speed2, int spellLikelyhood, String attackName, String spellName, String intro, String won, String lost, ImageShape i){
		
		name = name2;
		HP = hp;
		attack = atk;
		defense = def;
		speed = speed2;
		spAttack = spAtk;
		spDefense = spDef;
		standard = new Attack(attackName, 100);
		spell = new Spell(spellName, 130);
		introPhrase = intro;
		winPhrase = won;
		losePhrase = lost;
		likelyhood = spellLikelyhood;
		sprite = i;
		
	}
	
	
	
	
	
	public void lostHP(double lose){
		HP -= (int)(lose);
	}
	
	public String toString(){
		String s = "Name: " + name + "  HP: " + HP + " Atk: "+ attack + " Def: " + defense   +"";
		return s;
		
	}
	
	
	
}
