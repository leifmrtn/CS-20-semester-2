package Mastery;

import java.util.Scanner;

public class exercise11_quadratic_formula {

	public static void main(String[] args) {
		
		//Establish variables
		int a;
		int b;
		int c;
		double answer1;
		double answer2;
			
		//inputting the variables
		try (Scanner in = new Scanner(System.in))	{
			System.out.println("What are the values of a, b ,c in the formula (ax^2 + bx + c)?: ");
			System.out.print("a:");
			a = in.nextInt();
			System.out.print("b:");
			b = in.nextInt();
			System.out.print("c:");
			c = in.nextInt();
		}
		
		//solving
		answer1 = ((-b)+ (Math.sqrt(b*b - 4*a*c)))/(2*a);
		answer2 = ((-b)- (Math.sqrt(b*b - 4*a*c)))/(2*a);
		
		//displaying the answer
		System.out.print("X is equal to " + answer1 + " or " + answer2);

	}

}


// --- console ---
//What are the values of a, b ,c in the formula (ax^2 + bx + c)?: 
//a:1
//b:5
//c:6
//X is equal to -2.0 or -3.0