import uwcse.graphics.ImageShape;

public class Character {
	
	public String name;
	public int HP;
	public double defense;
	public double attack;
	public int speed;
	public double spAttack;
	public double spDefense;
	public Attack standard;
	public Spell spell;
	public int exp;
	public ImageShape sprite = null;
	public ImageShape icon = null;
	
	//shows battle sprite
	public boolean inBattle = false;
	
	
	public int nextLevelExp = 300;
	public int level = 1;
	
	private int MAX_HP;

	public Character(String name2, int hp, double atk, double def, double spAtk, double spDef, int speed2, String attackName, int attackDmg, String spellName, int spellDmg){
		
		MAX_HP = hp;
		
		name = name2;
		HP = hp;
		attack = atk;
		defense = def;
		speed = speed2;
		spAttack = spAtk;
		spDefense = spDef;
		
		
		standard = new Attack(attackName, attackDmg);
		spell = new Spell(spellName, spellDmg);
		
	}
	
	public Character(String name2, int hp, double atk, double def, double spAtk, double spDef, int speed2, String attackName, int attackDmg, String spellName, int spellDmg, ImageShape i, ImageShape i2){
		
		MAX_HP = hp;
		
		name = name2;
		HP = hp;
		attack = atk;
		defense = def;
		speed = speed2;
		spAttack = spAtk;
		spDefense = spDef;
		sprite = i;
		icon = i2;
		
		standard = new Attack(attackName, attackDmg);
		spell = new Spell(spellName, spellDmg);
		
	}
	
	public Character deepCopy(){
		
		
		String tempName = this.name;
		String attackName = this.standard.attackName;
		String spellName = this.spell.spellName;
		int tempHP = this.HP;
		int speed2 = this.speed;
		int attackDmg = this.standard.damage;
		int spellDmg = this.spell.damage;
		double atk = this.attack;
		double def = this.defense;
		double spDef = this.spDefense;
		double spAtk = this.spAttack;
		
		
		Character tempChar = new Character(tempName, tempHP, atk, def, spAtk, spDef, speed2, attackName, attackDmg, spellName, spellDmg);
		
		
		return tempChar;	
		
	}
	
	public void lostHP(double lose){
		HP -= (int)(lose);
	}
	
	public String gainHP(double gain, double charHP){
		String text;
		if(HP+gain >= MAX_HP){
			HP = MAX_HP;
			text = (name+" Used a potion and restored " + (int)(MAX_HP-charHP) +" HP");
			return text;
			}else{
			HP += (int)(gain);		
			text = (name+" Used a potion and restored " + (int)(gain) +" HP");
			return text;
		}
		
		
	}
	
	
	public String toString(){
		String s = name;
		return s;
		
	}
	

}
