package fscregistrar;

import java.util.ArrayList;

public class FSCfaculty extends FSCmember {

	private String rank;
	private String department;
	private ArrayList<String> courses = new ArrayList<>();
	private static int numFaculty;
	private int numCourses;

	//constructors
	public FSCfaculty() {
		//super(ID, firstName, lastName);
	}
	public FSCfaculty(String rank, String department, int ID, String firstName, String lastName) {
		super(ID, firstName, lastName);
		this.rank = rank;
		this.department = department;
		ArrayList<String> courses = new ArrayList<>();
	}


	//these methods will add and remove from the faculty members arrayList of courses
	public void addCourses(String course){
		courses.add(course);
	}
	public void removeCourses(String course){
		courses.remove(course);
	}
	
	//increase the faculty members num courses
	public void increasenumCourses(){
		numCourses++;
	}
	
	public void deacreaseNumCourses(){
		numCourses--;
	}
	
	//increase num of faculty objects
	public static void increaseNumFaculty(){
		numFaculty++;
	}
	
	
	//getters and setters
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

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
	


	public static int getNumFaculty() {
		return numFaculty;
	}

	public static void setNumFaculty(int numFaculty) {
		FSCfaculty.numFaculty = numFaculty;
	}
	
	
	
	//to string method to print faculty members
	@Override
	public String toString() {
		try {
			String s = String.format("\n   Dr. %s %s (ID: %d)   Rank: %s   Department: %s", getFirstName(), getLastName(), getID(), this.rank, this.department);
			s += "\n   Courses Assigned:\n   ";
			if(courses.isEmpty()){
				s += "---none---\n";
				return s;
			}
			else{
				for (int i = 0; i < this.numCourses; i++) {
				s += this.courses.get(i) + ", ";
			}
			s += "\n";
			return s;
			}
		}
		catch (NullPointerException ex){
			String s = "Caught null pointer";
			return s;
		}
	}

}
