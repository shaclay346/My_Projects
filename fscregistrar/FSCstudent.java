package fscregistrar;

import java.util.ArrayList;

public class FSCstudent extends FSCmember {

	
	private ArrayList<String> courses = new ArrayList<>();
	//number of courses the student has registered for
	private int numCourses = 0;
	private static int numStudents = 0;

	//constructors
	public FSCstudent() {

	}

	public FSCstudent(int ID, String firstName, String lastName) {
		super(ID, firstName, lastName);
		ArrayList<String> courses = new ArrayList<>();
	}

//	public FSCstudent(int numCourses, int ID, String firstName, String lastName) {
//		super(ID, firstName, lastName);
//		ArrayList<String> courses = new ArrayList<>();
//	}

	//methods to add to arraylist
	public void addCourses(String course) {
		courses.add(course);
	}

	public void deleteCourse(String course) {
		courses.remove(course);
	}
	//increases and decreases the students objects num courses 
	public static void increaseNumStudents() {
		numStudents++;
	}

	public static void decreaseNumStudents() {
		numStudents--;
	}
	//increases and decreases the students number of courses
	public void addNumCourse() {
		this.numCourses++;
	}

	public void dereaseNumCourse() {
		this.numCourses--;
	}

	//getters and setters
	public ArrayList<String> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<String> courses) {
		this.courses = courses;
	}

	public int getNumCourses() {
		return numCourses;
	}

	public void setNumCourses(int numCourses) {
		this.numCourses = numCourses;
	}

	public static int getNumStudents() {
		return numStudents;
	}

	public static void setNumStudents(int numStudents) {
		FSCstudent.numStudents = numStudents;
	}

	//to string to print student objects
	@Override
	public String toString() {
		String s = String.format("\n   ID:   %d", getID());
		s += String.format("\n   Name: %s %s", getFirstName(), getLastName());
		//loop over their number of courses registered
		s += String.format("\n   Number of Courses Registered: %s", this.getCourses().size());
		if (getNumCourses() > 0) {
			s += "\n   Courses:";
			for (int i = 0; i < this.getCourses().size(); i++) {
				s += String.format("\n      %s", this.courses.get(i));
			}
		}
		return s;
	}

}
