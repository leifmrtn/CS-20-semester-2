
public class game {
	
	
	public static void main(String[] args) {
		//Create instances (objects) from the gameCharacter Class
		gameCharacter player1 = new gameCharacter("Hero", 100, 10); //instance 1
		gameCharacter player2 = new gameCharacter("Villain", 180, 20);//instance 2
		
		player1.attack(); //Behavior(player1)-calls a method(attack())
		player2.takeDamage(20);
		System.out.println(player1.getStatus());
		System.out.println(player2.getStatus());
	}
	
}
