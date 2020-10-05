//Shane Claycomb
//3-14-2019
//shaclay346@gmail.com
//CSC 2290 
//Program 4: FSCregistar
//I will practice academic and personal integrity and excellence of character and expect the same from others.
package fscregistrar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class FSCregistrar {

	//opens a course in a gradebook
	public static void openCourse(Scanner in, PrintWriter output, FSCcourse[] courses, int numCourses) {
		String courseNum = in.next();
		int initialCap = in.nextInt();
		boolean trigger = true;

		String num = courseNum.substring(4);
		int numOfCourse = Integer.parseInt(num);
		//create the course
		FSCcourse tempCourse = new FSCcourse(courseNum, initialCap);

		//checks if the course number is already open 
		//every time I loop over the courses Array I will be using a try catch to keep from 
		//getting a null pointer exception as I added each course at the index of that classes course num
		for (int i = 0; i < courses.length; i++) {
			try {
				if (courseNum.equals(courses[i].getCourseNumber())) {
					output.print("\n   Error: Cannot open course . Course is already open in the system.\n\n");
					trigger = false;
				}
			} catch (NullPointerException ex) {
				continue;
			}
		}

		//if the course is not open yet, then it will add it
		if (trigger) {
			//FSCcourse newCourse = new FSCcourse(courseNum, initialCap);
			//add this newly created course to the array of Course objects
			output.printf("\n   Success: Course %s is now open with a maximum capacity of %d students.\n\n", courseNum, initialCap);
			courses[numOfCourse] = tempCourse;
			System.out.println(numOfCourse);
			System.out.println(courses[numOfCourse].getCourseNumber());
			System.out.println(courses[numOfCourse].getMaxNumStudents());
			//FSCcourse.increaseNumCourses();

		}//end of for loop

	}//end of method openCourse

	//adds the amount of students that can be in a course
	public static void expandCourse(Scanner in, PrintWriter output, FSCcourse[] courses) {
		String courseNum = in.next();
		int newMaxCap = in.nextInt();
		boolean triggerCap = true;
		boolean trigger = true;
		int index = 0;

		//loops over the courses
		for (int i = 0; i < courses.length; i++) {
			try {
				//once the course number is found we epand that course and
				//print the success message 
				if (courseNum.equals(courses[i].getCourseNumber())) {
					index = i;
					//make sure the new capacity is larger than the previous 
					if (courses[i].getMaxNumStudents() < newMaxCap) {
						courses[i].setMaxNumStudents(newMaxCap);
						output.print("\n   Success: Course " + courseNum + " has been expanded to hold " + newMaxCap + " students.\n\n");
						//set the trigger to false so the error message won't display 
						trigger = false;
						triggerCap = false;
					}
					//if the capacity isn't larger print an error message
					else if (courses[i].getMaxNumStudents() >= newMaxCap) {
						output.printf("\n   Error: Cannot expand course %s.", courseNum);
						output.printf("\n          New capacity (%d) is less than or equal to current capac"
								+ "ity (%d).\n\n", newMaxCap, courses[index].getMaxNumStudents());
					}
				}
			}//end of for loop
			catch (NullPointerException ex) {
				continue;
			}
		}
		
		
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				try {
					if (courseNum.equals(courses[i].getCourseNumber())) {
						trigger = false;
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			if (trigger) {
				output.print("\n   Error: Cannot expand course " + courseNum + ". Course is not"
						+ " currently open in the system.\n\n");
			}
		}

	}//end of method expandCourse

	//do this method later after the add students and faculty and stuff
	public static void deleteCourse(Scanner in, PrintWriter output, FSCcourse[] courses, FSCstudent[] students, ArrayList<FSCfaculty> Faculty) {
		String courseNum = in.next();

		int indexFac = 0;
		int indexCourse = 0;
		String holder;
		FSCfaculty facHolder;
		boolean trigger = true;
		boolean courseFound = false;

		//error message when the course is not found 
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				try {
					//loops over the course array. skips the error message if the course is found 
					if (courseNum.equals(courses[i].getCourseNumber())) {
						courseFound = true;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			//if the course is not found then an error message is printed
			if (courseFound == false) {
				System.out.println("THIS MESSAGE ");
				output.print("\n   Error: Cannot delete course. Course is not currently open in the system.\n\n");
				trigger = false;
			}
		}

		//finding the index of the course
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				try {
					if (courseNum.equals(courses[i].getCourseNumber())) {
						indexCourse = i;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
		}

		//for loop to remove the class from the assigned faculty member
		if (trigger) {
			for (int j = 0; j < FSCfaculty.getNumFaculty(); j++) {
				//loop over the faculty members array list of courses
				for (int k = 0; k < Faculty.get(j).getNumCourses(); k++) {
					if (Faculty.get(j).getCourses().get(k).equals(courseNum)) {
						output.printf("\n   Course assignment has been removed from the teaching "
								+ "schedule of Dr. %s %s.", Faculty.get(j).getFirstName(), Faculty.get(j).getLastName());
						System.out.println("REMOVE FAC");
						Faculty.get(j).removeCourses(courseNum);
						Faculty.get(j).deacreaseNumCourses();
					}
				}
			}
		}

		//now removing the course from all students who have that course
		if (trigger) {
			//looping over the students
			for (int j = 0; j < FSCstudent.getNumStudents(); j++) {
				//looping over the students ArrayList of courses
				for (int k = 0; k < students[j].getNumCourses() + 1; k++) {
					try {
						if (students[j].getCourses().get(k).equals(courseNum)) {
							students[j].deleteCourse(courseNum);
							students[j].dereaseNumCourse();
							output.printf("\n   Course has been removed from the schedule of "
									+ "%s %s (ID: %d).", students[j].getFirstName(), students[j].getLastName(), students[j].getID());
						}
					} catch (IndexOutOfBoundsException ex) {
						continue;
					}
				}
			}
			//erase the index of the class from the courses array
			courses[indexCourse] = null;
			output.printf("\n   Success: Course %s has been deleted from the system.\n\n", courseNum);
			//decrease number of students
			FSCcourse.decreaseNumCourses();
		}
	}//end of delete course method 
	
	
	//creates objects of type FSC faculty 
	public static void addFaculty(Scanner in, PrintWriter output, ArrayList<FSCfaculty> Faculty, int numFaculty) {
		int id = in.nextInt();
		String firstName = in.next();
		String lastName = in.next();

		String rank1 = in.next();
		String rank2 = in.next();
		String rank = rank1 + " " + rank2;

		String department1 = in.next();
		String department2 = in.next();
		String department = department1 + " " + department2;

		//creates faculty objects with given variables 
		FSCfaculty f = new FSCfaculty(rank, department, id, firstName, lastName);
		output.printf("\n   Success: Dr. %s %s (%s) successfully enrolled in the system.\n\n", firstName, lastName, rank);
		//add them to the Faculty array list and increase number of faculty members
		Faculty.add(f);
		FSCfaculty.increaseNumFaculty();

	}//end of add Faculty method

	
	//assign faculty member to teach a course
	public static void assignFaculty(Scanner in, PrintWriter output, ArrayList<FSCfaculty> Faculty, FSCcourse[] courses) {
		int ID = in.nextInt();
		String courseNum = in.next();

		int idx = 0;
		boolean trigger = true;
		boolean courseFound = false;
		boolean idFound = false;

		FSCfaculty holder;
		FSCfaculty teacher = new FSCfaculty();


		//loop over the courses array and if it is found save the index and skip the 
		//error message and then check if another faculty has that course
		//then assign it
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				try {
					//check if the course is open and save that index in the course array if it is open
					if (courseNum.equals(courses[i].getCourseNumber())) {
						courseFound = true;
						idx = i;
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			//error message if no course is found 
			if (courseFound == false) {
				output.printf("\n   Error: cannot assign faculty to the course. The given course number (%s) is not currently open for registration.\n\n", courseNum);
				trigger = false;
			}
		}

		//check if faculty ID exists
		if (trigger) {
			//loop over the faculty array list
			for (int i = 0; i < FSCfaculty.getNumFaculty(); i++) {
				//set a boolean to true if the fac ID is found
				if (Faculty.get(i).getID() == ID) {
					idFound = true;
					break;
				}
			}
			//if the faculty ID is not found, print an error
			if (idFound == false) {
				output.printf("\n   Error: cannot assign faculty to the course. The given faculty ID number (%d) was not found in the system.\n\n", ID);
				trigger = false;
			}
		}

		//if no errors are present continue
		if (trigger) {
			//loop over the courses array
			for (int i = 0; i < courses.length; i++) {
				try {
					//loop over the course if an instructor is assigned
					if (courses[idx].getInstructorID() != 0) {
						//find which faculty member is already assigned
						for (int j = 0; j < FSCfaculty.getNumFaculty(); j++) {
							if (Faculty.get(j).getID() == courses[idx].getInstructorID()) {
								teacher = Faculty.get(j);
							}
						}
						//then find the faculty member trying to be assigned
						for (int j = 0; j < FSCfaculty.getNumFaculty(); j++) {
							holder = Faculty.get(j);
							if (holder.getID() == ID) {
								//print the error that a teacher already has the class assigned
								output.printf("\n   Error: cannot assign Dr. %s %s to %s.", holder.getFirstName(), holder.getLastName(), courseNum);
								output.printf(" The given course has already been assigned to Dr. %s %s\n\n", teacher.getFirstName(), teacher.getLastName());
								trigger = false;
								break;
							}
						}
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
		}

		//if no errors present enter
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				try {
					//loop over the courses and set the instructor ID
					if (courseNum.equals(courses[i].getCourseNumber())) {
						courses[i].setInstructorID(ID);
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			//add the fauclty to the course and add the class to their array list of courses
			for (int i = 0; i < FSCfaculty.getNumFaculty(); i++) {
				if (Faculty.get(i).getID() == ID) {
					Faculty.get(i).addCourses(courseNum);
					Faculty.get(i).increasenumCourses();
					output.printf("\n   Success: Dr. %s %s (ID %d) has been assigned to %s.\n\n", Faculty.get(i).getFirstName(), Faculty.get(i).getLastName(), Faculty.get(i).getID(), courseNum);
				}
			}
		}
	}//end of assign faculty method

	//adds a student to the gradebook
	public static void enrollStudent(Scanner in, PrintWriter output, FSCstudent[] students) {
		int id = in.nextInt();
		String firstName = in.next();
		String lastName = in.next();
		boolean trigger = true;
		int numStudents = FSCstudent.getNumStudents();

		//loop over the students array to find out if the student is enrolled already 
		if (trigger) {
			for (int i = 0; i < numStudents; i++) {
				try {
					if (id == students[i].getID()) {
						output.print("\n   Error: cannot enroll student. Student already exists in the system.\n\n");
						trigger = false;
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
		}

		//check if the max number of studetns has been reached
		if (trigger) {
			if (FSCstudent.getNumStudents() == 6) {
				output.printf("\n   Error: cannot enroll student %s %s (%d). FSC Registrar has "
						+ "already reached its maximum capacity of students.\n\n", firstName, lastName, id);
				trigger = false;
			}
		}
		//if no errors enter here
		if (trigger) {
			//create the student object
			FSCstudent tempStu = new FSCstudent(id, firstName, lastName);
			students[FSCstudent.getNumStudents()] = tempStu;

			//save the student objects into a temporary array to avoid null pointer error
			FSCstudent[] tempArray = new FSCstudent[numStudents + 1];

			for (int i = 0; i < numStudents + 1; i++) {
				tempArray[i] = students[i];
			}

			//used to sort the IDs
			Arrays.sort(tempArray, Comparator.comparing(FSCstudent::getID));

			//add the sorted aray back into the students array
			for (int i = 0; i < tempArray.length; i++) {
				students[i] = tempArray[i];
			}
			output.printf("\n   Success: %s %s (%d) successfully enrolled in "
					+ "the system.\n\n", firstName, lastName, id);
			FSCstudent.increaseNumStudents();
		}
	}//end of enroll student

	
	//adds a course to the schedule of the desired student
	public static void courseAdd(Scanner in, PrintWriter output, FSCstudent[] students, FSCcourse[] courses) {
		int ID = in.nextInt();
		String courseNum = in.next();

		int index = 0;
		int stuIdx = 0;

		FSCcourse holder;
		boolean trigger = true;
		boolean idFound = false;
		boolean courseFound = false;

		//check if student ID is enrolled in the system
		if (trigger) {
			//loop over the students and find the student who has this ID number
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					idFound = true;
					break;
				}
			}
			//if the student is not found in the system print an error
			if (idFound == false) {
				System.out.println("STOPPED AT ID NOT ENROLLED");
				output.print("\n   Error: cannot add course. Student ID " + ID + " is not enrolled in the system.\n\n");
				trigger = false;
			}
		}

		//searches over the courses array
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				//try catch to stop null pointer exception
				try {
					//If the class being added is open it will skip the error message
					if (courseNum.equals(courses[i].getCourseNumber())) {
						courseFound = true;
						index = i;
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			//if the course is not found, displays the message that it isn't open
			if (courseFound == false) {
				output.print("\n   Error: cannot add course. Course Number " + courseNum + " is not open for enrollment.\n\n");
				trigger = false;
			}
		}

		//loops over the students and checks if this student has the max number of courses
		if (trigger) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getNumCourses() == 6) {
					output.printf("\n   Error: cannot add course. Student ID %d already has six registered courses.\n\n", ID);
					trigger = false;
					break;
				}
			}
		}

		//checks if the class is full
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				//looping over the classes
				try {
					if (courseNum.equals(courses[i].getCourseNumber())) {
						index = i;
					}
					//checking if the courses max number of students equals the current number of students
				} catch (NullPointerException ex) {
					continue;
				}
			}
			if (courses[index].getMaxNumStudents() == courses[index].getNumStudents()) {
				System.out.println("STOPPED AT COURSE IS AT MAX CAPACITY");
				//if the course is full
				output.print("\n   Error: cannot add course. Course Number " + courseNum + " has already reached maximum capacity.\n\n");
				trigger = false;
			}
		}

	//find the student by ID number
		if (trigger) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					stuIdx = i;
					break;
				}
			}
			////loops over the students courses and checks if the already have this class
			for (int i = 0; i < students[stuIdx].getNumCourses(); i++) {
				if (courseNum.equals(students[stuIdx].getCourses().get(i))) {
					output.printf("\n   Error: cannot add course. Student ID %d has already registered for %s\n\n", ID, courseNum);
					trigger = false;
				}
			}
		}

		//adds the course to th correctstudents Array List and increases that students num courses
		if (trigger) {
			for (int j = 0; j < FSCstudent.getNumStudents(); j++) {
				if (students[j].getID() == ID) {
					students[j].addCourses(courseNum);
					students[j].addNumCourse();
					break;
				}
			}
			//loops over the courses and adds the student to the course roster array list
			for (int k = 0; k < courses.length; k++) {
				try {
					if (courseNum.equals(courses[k].getCourseNumber())) {
						courses[k].increaseNumStudents();
						courses[k].addRoster(ID);
						output.printf("\n   Success: Course Number %s has been added to the schedule of Student ID %d.\n\n", courseNum, ID);
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
		}
	}//end of courseAdd method

	public static void courseDrop(Scanner in, PrintWriter output, FSCstudent[] students, FSCcourse[] courses, ArrayList<FSCfaculty> Faculty, int numStudents) {
		int ID = in.nextInt();
		String courseNum = in.next();

		int idx = 0;
		int indexClass = 0;
		boolean trigger = true;
		boolean idFound = false;
		boolean courseFound = false;

		//loop over the students ID and if no ID matches this one print and error
		if (trigger) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					idx = i;
					idFound = true;
					break;
				}
			}//prints the error trigger is now false
			if (idFound == false) {
				output.printf("\n   Error: cannot drop course. Student ID %d is not enrolled in the system.\n\n", ID);
				trigger = false;
			}
		}

		//determines if the course is open
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				//try catch to stop null pointer exception
				try {
					//If the class being added is open it will skip the error message
					if (courseNum.equals(courses[i].getCourseNumber())) {
						courseFound = true;
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			//if the course is not found, displays the message that it isn't open
			if (courseFound == false) {
				output.print("\n   Error: cannot drop course. Course Number " + courseNum + " is not an open course in the system.\n\n");
				trigger = false;
			}
		}

		//ifno error is present continue to the course drop
		if (trigger) {
			//loop over the studetns array list of courses
			for (int j = 0; j < students[idx].getNumCourses(); j++) {
				//if the course asking to be deleted is found
				String holder = students[idx].getCourses().get(j);
				if (holder.equals(courseNum)) {
					//execute the dropping of the course
					students[idx].deleteCourse(courseNum);
					output.printf("\n    Success: Course %s dropped from the schedule of Student ID "
							+ "%d.\n\n", courseNum, ID);
					students[idx].dereaseNumCourse();
				}
			}
			//decreases the number of students in the class that the studnt was dropped
			for (int i = 0; i < courses.length; i++) {
				//try catch to stop null pointer exception
				try {
					//also removes their ID from the roster array list
					if (courseNum.equals(courses[i].getCourseNumber())) {
						for (int j = 0; j < courses[i].getRoster().size(); j++) {
							if (courses[i].getRoster().get(j) == ID) {
								courses[i].decreaseNumStudents();
								courses[i].getRoster().remove(j);
							}
						}
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
		}
	}//end of course drop method

	public static void deleteStudent(Scanner in, PrintWriter output, FSCstudent[] students, FSCcourse[] courses) {
		int ID = in.nextInt();
		int stuIdx = 0;

		boolean trigger = true;
		boolean studentFound = false;

		//loops over the number of students and if no student is found in the system
		if (trigger) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					studentFound = true;
					break;
				}
			}//prints the error trigger is now false
			if (studentFound == false) {
				output.printf("\n   Error: cannot delete student. Student ID %d is not enrolled in the system.\n\n", ID);
				trigger = false;
			}
		}
		

		//determine if the student is enrolled in courses
		if (trigger) {
			//check if the num courses is > 0
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID && students[i].getNumCourses() > 0) {
					//save the index of the student
					stuIdx = i;
					output.printf("\n   Student ID %d is currently enrolled in courses.", ID);
				}
			}
			//loop over the students courses array list print that each one is being deleted
			for (int i = 0; i < students[stuIdx].getNumCourses(); i++) {
				output.printf("\n   ...removed from the course roster of %s.", students[stuIdx].getCourses().get(i));
			}
			
			//loop over the courses
			for (int j = 0; j < courses.length; j++) {
				try {
					//loop over each courses roster
					for (int k = 0; k < courses[j].getRoster().size(); k++) {
						//remove the student ID from the roster of this course
						if (courses[j].getRoster().get(k) == ID) {
							courses[j].removeRoster(k);
							courses[j].decreaseNumStudents();
						}
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			
//			//this index might be the one giving the error
			for (int i = 0; i < students.length; i++) {
				try{
				System.out.println("STUDENTS ID: " + students[i].getID());
				}
				catch(NullPointerException ex){
					System.out.println("null");
					continue;
				}
			}
			
			//loop over the students and find the student to be deleted
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					//once the student is found move everyone behind them in the array up
					for (int j = stuIdx; j < FSCstudent.getNumStudents() ; j++) {
						if (j + 1 < FSCstudent.getNumStudents()) {
							students[j] = students[j + 1];
						}
					}
				}
			}
			//and set that student to null
			//decrease the number of students
			FSCstudent.decreaseNumStudents();
			students[FSCstudent.getNumStudents()] = null;	
		}
		output.printf("\n   Success: Student ID %d has been deleted from the system.\n\n", ID);

	}//end of deleteStudent method 


	public static void printDetailsCourse(Scanner in, PrintWriter output, FSCcourse[] courses, ArrayList<FSCfaculty> Faculty, FSCstudent[] students) {
		String courseNum = in.next();

		int facID = 0;
		boolean trigger = true;
		boolean courseFound = false;
		int idx = 0;

		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				//try catch to stop null pointer exception
				try {
					//If the class being added is open it will skip the error message
					if (courseNum.equals(courses[i].getCourseNumber())) {
						courseFound = true;
						break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			//if the course is not found, displays the message that it isn't open
			if (courseFound == false) {
				output.print("\n   Error: cannot print course details. Course Number " + courseNum + " is not open for enrollment.\n\n");
				trigger = false;
			}
		}

		//loop over the courses array and find the course we want to print
		if (trigger) {
			for (int i = 0; i < courses.length; i++) {
				try {
					//save the index of the course and print its information
					if (courseNum.equals(courses[i].getCourseNumber())) {
						idx = i;
						courseFound = true;
						output.printf("\n   Course Number:                  %s", courseNum);
						//if there is no instructor assigned, if statement to print unassigned 
						if (courses[i].getInstructorID() == 0) {
							output.print("\n   Course Instructor:              unassigned");
						}
						else {
							//if course has been assigned to an instructor
							//get the courses instructor ID and then loop over faculty and print that faculty member
							facID = courses[i].getInstructorID();
							for (int j = 0; j < FSCfaculty.getNumFaculty(); j++) {
								if (Faculty.get(j).getID() == facID) {
									output.printf("\n   Course Instructor:              Dr. %s %s", Faculty.get(j).getFirstName(), Faculty.get(j).getLastName());
								}
							}
						}
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}

			
			if (courses[idx].getNumStudents() > 0) {
				//if there are students registered print the roster
				output.printf("\n   Number of Students Registered:  %d", courses[idx].getNumStudents());
				output.print("\n   Course Roster:");
				for (int i = 0; i < courses[idx].getRoster().size(); i++) {
					int id = courses[idx].getRoster().get(i);
					for (int j = 0; j < FSCstudent.getNumStudents(); j++) {
						if(id == students[j].getID()){
							output.printf("\n      %s %s (ID: %d)", students[j].getFirstName(), students[j].getLastName(), students[j].getID());
						}
					}
				}
				output.print("\n\n");
			}
			//else print 0 students registered
			else{
				output.printf("\n   Number of Students Registered:  %d\n\n", courses[idx].getNumStudents());
			}
		}
	}//end of print details course method
	

	//prints a students information
	public static void printDetailsStudent(Scanner in, PrintWriter output, FSCstudent[] students, int numStudents) {
		int ID = in.nextInt();

		boolean trigger = true;
		boolean isFound = false;

		//loops over the students and prints the student with the input read ID by calling the FSCstudent toString method
		if (trigger) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					isFound = true;
					break;
				}
				
			}//error message if no student is found
			if (isFound == false) {
				output.printf("\n   Error: cannot print student details. Student ID %d is not enrolled in the system.\n\n", ID);
				trigger = false;
			}
		}
		//if the student exist in the system cal the to string method
		if (trigger) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() == ID) {
					output.print(students[i]);
					break;
				}
			}
			output.print("\n\n");
		}
	}//end of method

	
	public static void printCourses(PrintWriter output, FSCcourse[] courses, int numCourses) {
		if (numCourses > 0) {
			
			//if there are courses entered loop over them and print them using FSCcourse to string 
			for (int i = 0; i < courses.length; i++) {
				try {
					if (courses[i] != null) {
						output.print(courses[i]);
						//break;
					}
				} catch (NullPointerException ex) {
					continue;
				}
			}
			output.print("\n\n");
		}
		//if no courses are open
		else {
			output.print("\n   Error: there are no open courses in the system.\n\n");
		}
	}

	
	public static void printStudents(PrintWriter output, FSCstudent[] students, int numStudents) {
		//if there are students entered print their info
		if (FSCstudent.getNumStudents() > 0) {
			for (int i = 0; i < FSCstudent.getNumStudents(); i++) {
				if (students[i].getID() != 0) {
					output.printf("\n   %-1s %1s %-11s   %s %d", "Name:", students[i].getFirstName(), students[i].getLastName(), "ID:", students[i].getID());
				}
				//if else to either print no courses entered or print all of their courses
				if (students[i].getNumCourses() == 0 && students[i].getID() != 0) {
					output.print("\n       Courses Registered: ---none---");
				}
				else if (students[i].getCourses().size() > 0 && students[i].getID() != 0) {
					output.print("\n      Courses Registered: ");
					for (int j = 0; j < students[i].getCourses().size(); j++) {
						output.print("" + students[i].getCourses().get(j) + ", ");
					}
				}
			}
			output.print("\n\n");
		}
		//if there are no students in the gradebook
		else if (FSCstudent.getNumStudents() == 0) {
			output.print("\n   Error: there are no students enrolled in the system.\n\n");
		}
	}

	//just loops over th faculty and prints them using the faculty to string
	public static void printFaculty(PrintWriter output, ArrayList<FSCfaculty> Faculty, int numFaculty) {
		for (int i = 0; i < numFaculty; i++) {
			output.print(Faculty.get(i));
			//System.out.println(Faculty.get(i));
		}
		output.print("\n");
	}

	public static void main(String[] args) throws FileNotFoundException {
		String command; // used to save the command read from input file

		File inputFile = new File("FSCregistrar.in");
		if (!inputFile.exists()) {
			System.out.println("Input file, " + inputFile + ", does not exist.");
			System.exit(0);
		}
		// Output File:
		File outputFile = new File("Registartest.out");

		// Make Scanner variable to read from input file, and 
		// make Printwriter variable to print to output
		Scanner in = new Scanner(inputFile);
		PrintWriter output = new PrintWriter(outputFile);

		//read the max number of courses 
		//and max number of students 
		int maxNumCourses = in.nextInt();
		int maxNumStudents = in.nextInt();
		//count num commands
		int counter = 0;
		int numCommands = in.nextInt();

		//create the courses and students arrau
		FSCcourse[] courses = new FSCcourse[5000];
		FSCstudent[] students = new FSCstudent[maxNumStudents];

		//faculty array list
		ArrayList<FSCfaculty> Faculty = new ArrayList<>();

		int numStudents = 0;
		int numCourses = 0;
		int numFaculty = 0;

		//i used a do while instead of for loop
		//just so I could keep my main the same as last project
		do {
			command = in.next();
			counter++;

			output.print("COMMAND: " + command);
			System.out.println("\nCommand: " + command);
			//should probably be an array list

			// Each command calls a method
			if (command.equals("OPENCOURSE") == true) {
				openCourse(in, output, courses, numCourses);

			}

			// expand course
			else if (command.equals("EXPANDCOURSE") == true) {
				expandCourse(in, output, courses);
			}

			// delete course
			else if (command.equals("DELETECOURSE") == true) {
				deleteCourse(in, output, courses, students, Faculty);

			}

			//add faculty
			else if (command.equals("ADDFACULTY") == true) {
				addFaculty(in, output, Faculty, numFaculty);
				numFaculty++;
			}
		
			
			else if (command.equals("ASSIGNFACULTY") == true) {
				assignFaculty(in, output, Faculty, courses);
			}

			else if (command.equals("ENROLLSTUDENT") == true) {
				enrollStudent(in, output, students);
			}

			else if (command.equals("COURSEADD") == true) {
				courseAdd(in, output, students, courses);
				numCourses++;
			}

			
			else if (command.equals("COURSEDROP") == true) {
				courseDrop(in, output, students, courses, Faculty, numStudents);
			}

			else if (command.equals("DELETESTUDENT") == true) {
				deleteStudent(in, output, students, courses);
				numStudents--;
			}

			else if (command.equals("PRINTDETAILSCOURSE") == true) {
				printDetailsCourse(in, output, courses, Faculty, students);
			}

			else if (command.equals("PRINTDETAILSSTUDENT") == true) {
				printDetailsStudent(in, output, students, numStudents);
			}

			else if (command.equals("PRINTCOURSES") == true) {
				printCourses(output, courses, numCourses);
			}

			else if (command.equals("PRINTSTUDENTS") == true) {
				printStudents(output, students, numStudents);
			}

			else if (command.equals("PRINTFACULTY") == true) {
				printFaculty(output, Faculty, numFaculty);
			}
		//once the number of commands has been reached the program exits
		} while (counter < numCommands == true);
		

		// Close input and output
		in.close();
		output.close();
	}

}
