
public class gameCharacter {
	//instance variables
	private String name;
	private int health;
	
	//constructor method 
	//Public means that the method can be called from anywhere
	public gameCharacter(String charName, int starHealth) {
		name = charName;
		health = starHealth;
		
	}
	
	//Behaviour: actions the character can do
	public void attack() {
		System.out.println(name + " attacks! Deals 10 damage.");
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		
		if(health < 0) {
			health = 0;
			System.out.println(name + "'s health is now " + health + ".");
		}
	}
	
	public String getStatus() {
		return name + " has " + health + " health.";
	}
	
}
