package fscregistrar;

import java.util.ArrayList;

public class FSCcourse {

	private String courseNumber;
	private int instructorID;
	private ArrayList<Integer> roster = new ArrayList<>();
	private int maxNumStudents;
	private int numStudents = 0;
	private static int numCourses = 0;

	//constructors
	public FSCcourse() {
	}

	public FSCcourse(String courseNumber, int maxNumStudents) {
		this.courseNumber = courseNumber;
		this.maxNumStudents = maxNumStudents;
		ArrayList<Integer> roster = new ArrayList<>();
	}

//	public FSCcourse(String courseNumber, int instructorID, int maxNumStudents, int numStudents) {
//		this.courseNumber = courseNumber;
//		this.instructorID = instructorID;
//		ArrayList<Integer> roster = new ArrayList<>();
//		this.maxNumStudents = maxNumStudents;
//		this.numStudents = numStudents;
//	}

	
	//mehtods for the class
	//add to roster array list
	public void addRoster(int ID){
		roster.add(ID);
	}
	public void removeRoster(int ID){
		roster.remove(ID);
	}
	//increases the number of students in a course
	public void increaseNumStudents(){
		this.numStudents++;
	}
	public void decreaseNumStudents(){
		this.numStudents--;
	}
	
	//counts the number of courses
	public static void increaseNumCourses(){
		numCourses++;
	}
	public static void decreaseNumCourses(){
		numCourses--;
	}
	
	
	
	
	//getters and setters
	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public int getInstructorID() {
		return instructorID;
	}

	public void setInstructorID(int instructorID) {
		this.instructorID = instructorID;
	}

	public ArrayList<Integer> getRoster() {
		return roster;
	}

	public void setRoster(ArrayList<Integer> roster) {
		this.roster = roster;
	}

	public int getMaxNumStudents() {
		return maxNumStudents;
	}

	public void setMaxNumStudents(int maxNumStudents) {
		this.maxNumStudents = maxNumStudents;
	}

	public int getNumStudents() {
		return numStudents;
	}

	public void setNumStudents(int numStudents) {
		this.numStudents = numStudents;
	}

	public static int getNumCourses() {
		return numCourses;
	}

	public static void setNumCourses(int numCourses) {
		FSCcourse.numCourses = numCourses;
	}
	

	//@ovveride will be printcourse method
	//course name and num students printed
	@Override
	public String toString() {
			String s;
			s = String.format("\n   %s (Number of Registered Students: %d)", this.courseNumber, this.numStudents);
			return s;
	}
	
	
}
