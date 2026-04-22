package SkillBuilders;

import java.text.DecimalFormat;

public class testCircle {

	public static void main(String[] args) {
		DecimalFormat dc = new DecimalFormat("0.00");
		//Create a Circle object using the default constructor
		circle c1 = new circle();
		System.out.println(c1.getRadius());
		c1.setRadius(12.9);
		System.out.println(c1.getRadius());
		System.out.println(dc.format(c1.calculateArea()));
		
		
	}

}
