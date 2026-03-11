package SkillBuilders;

public class student {
	//instance variables
	private String fullName;
	private double GPA;
	
	//constructor method
	public student(String studentName, double startingGPA) {
		fullName = studentName;
		GPA = startingGPA;
	}
	
	public double getGPA() {
		return GPA;
	}
	public  String getFullName() {
		return fullName;
	}
	//Modifier method
	public void setFullName(String name) {
		fullName = name;
	}
	public void setGPA(double temp) {
		GPA = temp;
	}
	
	public void updateGPA(double newGPA) {
		if(newGPA >= 0 && newGPA <= 4.0) {
			GPA = newGPA;
		}
		else {
			System.out.println("Invalid GPA - must be 0 - 4.0.");
		}
	}
	
	public String toString() {
		return fullName + " (GPA of: " + GPA + ")";
	}
}//closes Class


//Accessor method starts with get
//modifier method has set at beggining
