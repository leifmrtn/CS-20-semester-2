package SkillBuilders;

public class circle {
	private double radius;
	
	public circle() { // default Constructor (becouse of circle(**empty**))
		radius = 1.0;
	}
	public circle(double r) {
		radius = r;
	}
	public double getRadius() { // accesor
		return radius;
	}
	public void setRadius(double r) { // modifier
		if (r > 0) {
			radius = r;
		}
		else {
			System.out.println("Error: Radius must be positive");
		}
	}
	public double calculateArea() {
		return Math.PI * Math.pow(radius, 2);
	}
	public String toString() {
		return "Circe[radius = " + radius + ", area = " + calculateArea() + "]"; 
	}
}
