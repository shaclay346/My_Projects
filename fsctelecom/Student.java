package fsctelecom;

//LLnode class
public class Student {
	private int ID;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private double balance;
	
	private String[] calledNumbers = new  String[10];
	private int[] callDuration = new int[10];
	
	private String[] textedNumbers = new String[10];
	
	private static int numStudents;
	private Student next;

	public Student(int ID, String firstName, String lastName, String phoneNumber, double balance) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		next = null;
	}
	
	public Student(int ID, String firstName, String lastName, String phoneNumber, double balance, Student next) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.next = next;
		//I think these need to go here, but maybe not
		//String[] calledNumbers = new String[10];
		//int[] callDuration = new int[10];
		//String[] textedNumbers = new String[10];
	}

	public Student(int ID) {
		this.ID = ID;
	}
	
	
//method to increase the number of students
	public static void increaseNumStudents(){
		numStudents++;
	}
	
	
	@Override
	public String toString(){
		String s = "";
		
		
		
		return s;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String[] getCalledNumbers() {
		return calledNumbers;
	}

	public void setCalledNumbers(String[] calledNumbers) {
		this.calledNumbers = calledNumbers;
	}

	public int[] getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int[] callDuration) {
		this.callDuration = callDuration;
	}

	public String[] getTextedNumbers() {
		return textedNumbers;
	}

	public void setTextedNumbers(String[] textedNumbers) {
		this.textedNumbers = textedNumbers;
	}

	public static int getNumStudents() {
		return numStudents;
	}

	public static void setNumStudents(int numStudents) {
		Student.numStudents = numStudents;
	}

	public Student getNext() {
		return next;
	}

	public void setNext(Student next) {
		this.next = next;
	}	
}//end of class
