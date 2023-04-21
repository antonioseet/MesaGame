import java.util.ArrayList;


public class Party {
	
	public ArrayList<Character> party = new ArrayList<Character>();
	
	private Character empty = new Character("Empty", 0 ,0,0,0,0,0,"",0,"",0);
	
	
	
	public Party(Character c0){

		party.add(c0);
		
	
	}
	
	
	public void addChar(Character c){
		
		party.add(c);
		System.out.println(c.name + " joined the party");
	}
	
	 public void removeChar(Character c){
		 
		 party.remove(c);
		 System.out.println(c.name + " left the party");
		 
	 }
	
	
	private boolean slotOpen(){
		
		if(party.get(1) == null || party.get(2) == null || party.get(3) == null){
			return true;
		}else{
			return false;
		}
		
		
	}
	

}
