package fsctelecom;
//Shane Claycomb
//9-26-2020
//shaclay346@gmail.com
//CSC 3280 section: 02
//Program 2: FSCTelecom
//I will practice academic and personal integrity and excellence of character and expect the same from others.

import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FSCTelecom {

	// FINAL (CONSTANT) variables
	public static final double ratePerMinute = 0.05;
	public static final double smsRate = 0.02;
	public static final double dataRatePerKB = (1.0 / 1024.0) / 100.0;

	public static void addAccount(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		// Note that this method should use the search() or findNode() method of the Linked List class
		// Note that this method should use the insert() method of the Linked List class
		//read in paremeters
		int ID = in.nextInt();
		String fName = in.next();
		String lName = in.next();
		String number = in.next();
		double balance = 20;

		//send the read in info to the Telecom accounts class to add them to the link list
		accounts.insert(fName, lName, ID, number, balance);
		//increase number of students 
		Student.increaseNumStudents();

		//print out who was added
		output.printf("\n\tName:          %s %s", fName, lName);
		output.printf("\n\tStudent ID: %10d", ID);
		output.printf("\n\tPhone Number: %11s", number);
		output.print("\n\tBalance:       $20.00\n\n");
	}

	public static void makeCall(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		int id = in.nextInt();
		String number = in.next();
		int minutes = in.nextInt();

		//read in paremeters above and then using the ID as the data value
		//call the search method from the linkedList class that will return a Student object
		Student s = accounts.search(id);
		double cost = minutes * ratePerMinute;

		//if no student is found in search method null is returned, in this case print an error
		if (s == null) {
			output.print("\n\tCannot perform MAKECALL. Account does not exist in FSC Telecom System.\n\n");
		}//if they have insufficent balance then also print an error
		else if (s.getBalance() == 0.0 || s.getBalance() < 0.05) {//might need or balance < 0.05
			output.print("\n\tCannot perform MAKECALL. Account has insufficient balance.\n\n");
		}
		else {//else print the info that the call was successful
			if (s.getBalance() >= cost) {
				output.printf("\n\tName:           %s %s", s.getFirstName(), s.getLastName());
				output.printf("\n\tPhone Number:   %s", s.getPhoneNumber());
				output.printf("\n\tNumber Called:  %s", number);
				output.printf("\n\tCall Duration:  %d minutes", minutes);
				output.printf("\n\tPrev Balance:   $%.2f", s.getBalance());
				output.printf("\n\tCall Cost:      $%.2f", cost);
				s.setBalance(s.getBalance() - cost);
				output.printf("\n\tNew Balance:    $%.2f\n\n", s.getBalance());
			}//else statement if they did not have the full balance to complete the call
			else {//if balance of node is less than cost of call
				//calculate how long they can be on the call for
				double length = Math.floor(s.getBalance() / ratePerMinute);
				minutes = (int) length;
				//make a new cost variable
				double price = length * ratePerMinute;
				//print info
				output.printf("\n\tName:           %s %s", s.getFirstName(), s.getLastName());
				output.printf("\n\tPhone Number:   %s", s.getPhoneNumber());
				output.printf("\n\tNumber Called:  %s", number);
				output.printf("\n\tCall Duration:  %.0f", length);
				output.printf("\n\tPrev Balance:   $%.2f", s.getBalance());
				output.printf("\n\tCall Cost:      $%.2f", price);
				s.setBalance(s.getBalance() - price);
				output.printf("\n\tNew Balance:    $%.2f", s.getBalance());
				output.print("\n\t***Call terminated due to low balance.\n\n");
			}
			//save the student objects array for call length and numbers called into local arrays
			int[] duration = s.getCallDuration();
			String[] numbersCalled = s.getCalledNumbers();

			//loop to shift everything to the right after something is added
			for (int i = (duration.length - 1); i > 0; i--) {
				duration[i] = duration[i - 1];
				numbersCalled[i] = numbersCalled[i - 1];
			}//add the new call to index 0 of the arrays
			duration[0] = minutes;
			numbersCalled[0] = number;

			//now that they have been changed reset the arrays
			s.setCallDuration(duration);
			s.setCalledNumbers(numbersCalled);
		}
	}

	public static void sendText(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		//read paremeters
		int id = in.nextInt();
		String number = in.next();

		//same as makeCall method, search for the node we want by ID if null is returned print nothing
		Student s = accounts.search(id);
		if (s == null) {
			output.print("\n\tCannot perform SENDTEXT. Account does not exist in FSC Telecom System.\n\n");
		}//if the balance is too low to make the call
		else if (s.getBalance() == 0.0 || s.getBalance() < smsRate) {
			output.print("\n\tCannot perform SENDTEXT. Account has zero balance.\n\n");
		}
		else {//found in system and have balance
			output.printf("\n\tName:           %s %s", s.getFirstName(), s.getLastName());
			output.printf("\n\tPhone Number:   %s", s.getPhoneNumber());
			output.print("\n\tNumber Texted:  " + number);
			output.printf("\n\tPrev Balance:   $%.2f", s.getBalance());
			output.printf("\n\tText Cost:      $%.2f", smsRate);
			s.setBalance(s.getBalance() - smsRate);
			output.printf("\n\tNew Balance:    $%.2f\n\n", s.getBalance());

			//save the objects array for numbers texted into a local array
			String[] numbersTexted = s.getTextedNumbers();

			//loop to shift all elements to the right
			for (int i = (numbersTexted.length - 1); i > 0; i--) {
				numbersTexted[i] = numbersTexted[i - 1];
			}//insert new number at index 0
			numbersTexted[0] = number;

			//now that a change has been made reset the array
			s.setTextedNumbers(numbersTexted);
		}
	}

	public static void useData(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		// Note that this method should use the search() or findNode() method of the Linked List class
		int id = in.nextInt();
		int KB = in.nextInt();
		//read in info and search for the student as well as creat the double for
		//cost of the data use
		Student s = accounts.search(id);
		double dataCost = Math.ceil(KB * dataRatePerKB * 100000.0) / 100000.0;
		dataCost = ((int) (dataCost * 100)) / 100.0;
		
		if (s == null) {//if no student matching that ID is found in the Linked List
			output.print("\n\tCannot perform USEDATA. Account does not exist in FSC Telecom System.\n\n");
		}
		else if (s.getBalance() < dataCost) {//if the student doesn't have enough balance print an error
			output.print("\n\tCannot perform USEDATA. Account does not have enough balance.\n\n");
		}
		else {//else print the info of the transaction
			output.printf("\n\tName:           %s %s", s.getFirstName(), s.getLastName());
			output.printf("\n\tPhone Number:   %s", s.getPhoneNumber());
			output.printf("\n\tKilobytes:      %d", KB);
			output.printf("\n\tPrev Balance:   $%.2f", s.getBalance());
			output.printf("\n\tData Cost:      $%.2f", dataCost);
			s.setBalance(s.getBalance() - dataCost);
			output.printf("\n\tNew Balance:    $%.2f\n\n", s.getBalance());
		}
	}

	public static void recharge(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		// Note that this method should use the search() or findNode() method of the Linked List class
		int id = in.nextInt();
		double amount = in.nextDouble();
		Student s = accounts.search(id);
		//search for the student and if they are found then print out the info and set their new balane
		if (s == null) {//of no student is found = error
			output.print("\n\tCannot perform RECHARGE. Account does not exist in FSC Telecom System.\n\n");
		}
		else {//add the new balance if the student is found 
			output.printf("\n\tName:            %s %s", s.getFirstName(), s.getLastName());
			output.printf("\n\tPhone Number:    %s", s.getPhoneNumber());
			output.printf("\n\tRecharge Amount: $%.2f", amount);
			s.setBalance(s.getBalance() + amount);
			output.printf("\n\tNew Balance:     $%.2f\n\n", s.getBalance());
		}
	}

	public static void deleteAccount(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		// Note that this method should use the search() or findNode() method of the Linked List class
		// Note that this method should use the delete() method of the Linked List class
		int id = in.nextInt();

		//search for the student to be deleted
		Student s = accounts.search(id);
		if (s == null) {
			output.print("\n\tCannot perform DELETEACCOUNT. Account does not exist in FSC Telecom System.\n\n");
		}
		else {//if the student matching that ID was found print their info
			output.printf("\n\tName:           %s %s", s.getFirstName(), s.getLastName());
			output.printf("\n\tStudent ID:  %10d", s.getID());
			output.printf("\n\tPhone Number:   %s", s.getPhoneNumber());
			output.printf("\n\tBalance:        $%.2f", s.getBalance());
			//call the Linked List method to delete objects
			accounts.delete(id); //they will be removed by the FSCTelecomAccounts class
			output.print("\n\t***Account has been deleted.\n\n");
			//decrease the number od students
			Student.decreaseNumStudents();;
		}
	}

	public static void search(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		// read in info and search for the student object 
		int id = in.nextInt();
		Student s = accounts.search(id);
		if (s == null) {//if no student is found
			output.print("\n\tCannot perform SEARCH. Account does not exist in FSC Telecom System.\n\n");
		}
		else {//if they are found print their info
			output.printf("\n\tName:          %s %s", s.getFirstName(), s.getLastName());
			output.printf("\n\tStudent ID: %10d", s.getID());
			output.printf("\n\tPhone Number: %11s", s.getPhoneNumber());
			output.printf("\n\tBalance:       $%.2f\n\n", s.getBalance());
		}

	}

	public static void displayDetails(FSCTelecomAccounts accounts, Scanner in, PrintWriter output) {
		// Note that this method should use the search() or findNode() method of the Linked List class
		int id = in.nextInt();
		Student s = accounts.search(id);
		if (s == null) {//if no student object is found 
			output.print("\n\tCannot perform DISPLAYDETAILS. Account does not exist in FSC Telecom System.\n\n");
		}
		else {//if they are found print all info
			//get the arrays into local arrays
			int[] duration = s.getCallDuration();
			String[] numbersCalled = s.getCalledNumbers();
			//print info
			output.printf("\n\tName:          %s %s", s.getFirstName(), s.getLastName());
			output.printf("\n\tStudent ID: %10d", s.getID());
			output.printf("\n\tPhone Number:  %s", s.getPhoneNumber());
			output.print("\n\tCalled Numbers and Duration:");
			if (duration[0] == 0) {
				output.print("\n        (user has not made any texts yet)");
			}
			//loop through their numbers called and call durtion array
			for (int i = 0; i < duration.length; i++) {
				if (duration[i] != 0) {
					output.printf("\n\t\t%10s (%d)", numbersCalled[i], duration[i]);
				}
			}
			//get the texted numbers array and loop through it to print the numbers they texted
			String[] numbers = s.getTextedNumbers();
			output.print("\n\tTexted Numbers:");
			if (numbers[0] == null) {
				output.print("\n\t\t(user has not made any texts yet)");
			}//print the numbers they texted
			for (int i = 0; i < numbers.length; i++) {
				if (numbers[i] != null) {
					output.printf("\n\t\t%s", numbers[i]);
				}
			}
			output.println("\n");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		/// Variables needed for program
		String command; // used to save the command read from input file

		// OPEN FILES
		// Input File:
		File inputFile = new File("FSCTelecom.in");
		if (!inputFile.exists()) {
			System.out.println("Input file, " + inputFile + ", does not exist.");
			System.exit(0);
		}
		// Output File:
		File outputFile = new File("Test.out");

		// Make Scanner variable to read from input file, and 
		// make Printwriter variable to print to output
		Scanner in = new Scanner(inputFile);
		PrintWriter output = new PrintWriter(outputFile);

		// Make linked-list
		FSCTelecomAccounts accounts = new FSCTelecomAccounts();
		// ^ ^ ^ ^ ^
		// | | | | |
		// | | | | |     "accounts" is the reference variable that points
		//               to your linked-list of Student objects

		// MAIN DO/WHILE LOOP:
		//    - We read commands and then process the commands by calling
		//      the appropriate methods.
		do {
			command = in.next();
			output.print("Command: " + command);
			// ADDACCOUNT
			if (command.equals("ADDACCOUNT") == true) {
				addAccount(accounts, in, output);
			}

			// MAKECALL
			else if (command.equals("MAKECALL") == true) {
				makeCall(accounts, in, output);
			}

			// SENDTEXT
			else if (command.equals("SENDTEXT") == true) {
				sendText(accounts, in, output);
			}

			// USEDATA
			else if (command.equals("USEDATA") == true) {
				useData(accounts, in, output);
			}

			// RECHARGE
			else if (command.equals("RECHARGE") == true) {
				recharge(accounts, in, output);
			}

			// DELETEACCOUNT
			else if (command.equals("DELETEACCOUNT") == true) {
				deleteAccount(accounts, in, output);
			}

			// SEARCH
			else if (command.equals("SEARCH") == true) {
				search(accounts, in, output);
			}

			// DISPLAYDETAILS
			else if (command.equals("DISPLAYDETAILS") == true) {
				displayDetails(accounts, in, output);
			}

			// Command QUIT: Quit the Program
			else if (command.equals("QUIT") == true) {
				output.print(".");
				output.println("\n\tExiting the FSC Telecom System...");
				output.println("\tGoodbye.");
			}

			// Invalid Command
			else {
				System.out.println("Invalid Command: input invalid.");
			}

		} while (command.equals("QUIT") != true);

		// Close input and output
		in.close();
		output.close();
	}

}
