package Mastery;

import java.util.Random;
import java.util.Scanner;

public class exercise16_7m_bridge {

	public static void main(String[] args) {
		//variables
		int bridge_length;
		int distance;
		int trials;
		int average = 0;
		int move;
		int steps;
		int highest = 0;

		Random random = new Random();
		Scanner in = new Scanner(System.in);
		
		//inputting bridge length and number of trials
		System.out.println("How long would you like the bridge to be?: ");
		bridge_length = in.nextInt();
		System.out.println("How many times would you like to run the experiment?: ");
		trials = in.nextInt();
		
		for (int i = 0; i < trials; i++) {	//trials
			//reset variables
			distance = 0;
			steps = 0;
			
			while (distance != bridge_length) { //running	
				move = random.nextInt(2) + 1; // determining whether it steps forward or back
				
				//change in distance
				if (move == 1) {	//move forward
					distance += 1;
					steps += 1;		
				}
				else if (move == 2) {	//move back
					if (distance > 0) { //check so it doesn't go below zero
						distance -= 1;
						steps += 1;		
					}
				}
				//update the max if there is a new one
				if (steps > highest) {
					highest = steps;
				}
			}	//running
			average += steps; //update average
		} //trials
		
		average /= trials;
		//final output
		System.out.println("the arverage number of steps needed is: " + average + " steps"); 
		System.out.print("with a peak of " + highest + " steps");	
	}

}


// --- console ---
//How long would you like the bridge to be?: 
//7
//How many times would you like to run the experiment?: 
//50
//the average number of steps needed is: 49 steps
//with a peak of 195 steps
