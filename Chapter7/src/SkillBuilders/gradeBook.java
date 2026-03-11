package SkillBuilders;

public class gradeBook {

	public static void main(String[] args) {
		//client creates objects
		student alex = new student("Alex Ross", 3.2);
		student ali = new student("Mohomed Ali", 3.3);
		student amy = new student("Amy Li", 3.4);
		student jasmine = new student("Jasmine Jordan", 3.5);
		
		System.out.println(alex); // Automatically calls to toString()
		System.out.println(ali);
		System.out.println(amy);
		System.out.println(jasmine);
		
		alex.updateGPA(4.0);
		System.out.println(alex);
		
	}

}
