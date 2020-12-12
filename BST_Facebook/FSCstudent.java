//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
package fscbook;

//tree node class 
public class FSCstudent {

	private int ID;
	private String firstName;
	private String lastName;
	private String department;
	
	private int numFriends;
	private FSCfriends myFriends;
	
	private FSCstudent right;
	private FSCstudent left;
	
	
	 // CONSTRUCTOR
	public FSCstudent(int ID, String firstName, String lastName, String department) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.numFriends = 0;
		this.myFriends = new FSCfriends();
		left = right = null;
	}
	
	//methods to increase and decrease the number of friends someone has
	public void increaseNumFriends(){
		this.numFriends++;
	}
	
	public void decreaseNumFriends(){
		this.numFriends--;
	}
    



	//getters and setters
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getNumFriends() {
		return numFriends;
	}

	public void setNumFriends(int numFriends) {
		this.numFriends = numFriends;
	}

	public FSCfriends getMyFriends() {
		return myFriends;
	}

	public void setMyFriends(FSCfriends myFriends) {
		this.myFriends = myFriends;
	}

	public FSCstudent getRight() {
		return right;
	}

	public void setRight(FSCstudent right) {
		this.right = right;
	}

	public FSCstudent getLeft() {
		return left;
	}

	public void setLeft(FSCstudent left) {
		this.left = left;
	}
}
