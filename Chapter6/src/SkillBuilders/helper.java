package SkillBuilders;

public class helper {

	
	public static void sayHello() {
		System.out.println("Hello World!");
	}
	// 									parameters
	public static void greetPerson(String name, int age) {
		System.out.println("Hi " + name + "!");
		System.out.println("You are " + age + " years old.");
		
		if(age >= 18) {
			System.out.println("You're old enough to drive.");			
		}
		else {
			System.out.println("You're still young!");
		}
		System.out.println("+-----------------------------+");
	}
	
	public static double calculateCircleArea(double radius) {
		double area = Math.PI * radius * radius;
		
		return area;
	}
	
	public static String getMoodMessage(int happinessLevel) {
		if(happinessLevel >= 80) { //>80
			return("You're absolutely glowing today!");
		}
		else if(happinessLevel >= 50) { // >50
			return("You're doing pretty good, keep it up.");
		}
		else { // <50
			return("Today feels a bit heavy.");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("+===+\tDemonstating methods\t+===+");
		
		//calling method #1
		System.out.println("+-----------------------------+");
		sayHello();
		
		//calling method #2 with arguments
		System.out.println("+-----------------------------+");
		greetPerson("Sarah", 22);
		greetPerson("David", 12);
		greetPerson("John", 47);
		
		//calling method #3
		double r = 5.0;
	//	double area = calculateCircleArea(r);
	//	System.out.println("Circle has an area of " + area);
		//or
		System.out.println("Circle has an area of " + calculateCircleArea(r));
		System.out.println("+-----------------------------+");
		
		//calling method #4
	}	

}

//what something needs to be a method
//public or private
//
//void
//parameters
