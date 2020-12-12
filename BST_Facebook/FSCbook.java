//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
//Program 4: FSCchickfila
//I will practice academic and personal integrity and excellence of character and expect the same from others.
package fscbook;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FSCbook {

	//method to add to the BST tree
	public static void add(Scanner in, PrintWriter output, FSCbookBST book) {
		//read in info
		int ID = in.nextInt();
		String first = in.next();
		String last = in.next();
		String department = in.next();
		FSCstudent found = book.searchbyID(ID);
		if (found != null) {
			//error
			output.print("\tCannot Perform ADD Command:\n");
			output.printf("\t\tID %d, %s %s - already a member of FSCbook.\n\n", found.getID(), found.getFirstName(), found.getLastName());
		}
		else {
			output.printf("\t%s %s (ID %d), from the %s department, joined FSCbook.\n\n", first, last, ID, department);

			//create new student object and insert them into the tree
			FSCstudent temp = new FSCstudent(ID, first, last, department);

			//insert them into the tree 
			book.insert(temp);
		}
	}

	//findID
	public static void findID(Scanner in, PrintWriter output, FSCbookBST book) {
		int ID = in.nextInt();

		//save a temporary student y searching the BST by ID
		FSCstudent temp = book.searchbyID(ID);

		//if they are not found print an error
		if (temp == null) {
			output.printf("\tID %d was not found in FSCbook.\n\n", ID);
		}
		else {//otherwise print their info
			output.printf("\tFound:  ID %d, %s %s (%s Department)\n\n", temp.getID(), temp.getFirstName(), temp.getLastName(), temp.getDepartment());
		}
	}

	//search the BST by name
	public static void findName(Scanner in, PrintWriter output, FSCbookBST book) {
		String firstName = in.next();
		String lastName = in.next();

		//search the tree for them and save them
		FSCstudent temp = book.searchByName(firstName, lastName);

		//error if they were not found
		if (temp == null) {
			output.printf("\t%s %s was not found in FSCbook.\n\n", firstName, lastName);
		}
		//else they were found and print them 
		else {
			output.printf("\tFound:  ID %d, %s %s (%s Department)\n\n", temp.getID(), temp.getFirstName(), temp.getLastName(), temp.getDepartment());
		}
	}

	public static void friend(Scanner in, PrintWriter output, FSCbookBST book) {
		boolean flag = true;
		//read in both names
		String firstName1 = in.next();
		String lastName1 = in.next();

		String firstName2 = in.next();
		String lastName2 = in.next();

		//search the tree for both of them
		FSCstudent temp1 = book.searchByName(firstName1, lastName1);
		FSCstudent temp2 = book.searchByName(firstName2, lastName2);

		//if either are null display an error message
		if (temp1 == null || temp2 == null) {
			flag = false;
			output.print("\tCannot Perform FRIEND Command:\n");

			if (temp1 == null) {
				output.printf("\t\t%s %s - this student is not in FSCbook.\n", firstName1, lastName1);
			}
			if (temp2 == null) {
				output.printf("\t\t%s %s - this Student is not in FSCbook.\n", firstName2, lastName2);
			}
			output.println();
		}

		//check if they are already friends by seeing if they are in eachothers linkedlists
		if (flag) {
			if (temp1.getMyFriends().search(temp2.getID()) != null) {
				flag = false;
				output.print("\tCannot Perform FRIEND Command:\n");
				output.printf("\t\t%s %s and %s %s are already friends.\n\n", firstName1, lastName1, firstName2, lastName2);
			}
		}

		if (flag) {
			//if there was no error add them to each others friends lists
			output.printf("\t%s %s and %s %s are now friends.\n\n", firstName1, lastName1, firstName2, lastName2);
			temp1.getMyFriends().insert(temp2.getID());
			temp2.getMyFriends().insert(temp1.getID());

			//increment their number of friends
			temp1.increaseNumFriends();
			temp2.increaseNumFriends();
		}
	}

	public static void unfriend(String firstName1, String lastName1, String firstName2, String lastName2, PrintWriter output, FSCbookBST book) {
		boolean flag = true;

		//search the tree for those students
		FSCstudent temp1 = book.searchByName(firstName1, lastName1);

		FSCstudent temp2 = book.searchByName(firstName2, lastName2);

		//if either were not found print an error
		if (temp1 == null || temp2 == null) {
			flag = false;
			output.print("\tCannot Perform UNFRIEND Command:\n");
			if (temp1 == null) {
				output.printf("\t\t%s %s - this student is not in FSCbook.\n", firstName1, lastName1);
			}
			if (temp2 == null) {
				output.printf("\t\t%s %s - this Student is not in FSCbook.\n", firstName2, lastName2);
			}
			output.println();
		}

		if (flag) {
			//if neither of them are friends print an error
			//checked by searching their linked lists 
			if (temp1.getMyFriends().search(temp2.getID()) == null) {
				flag = false;
				output.print("\tCannot Perform UNFRIEND Command:\n");
				output.printf("\t%s %s and %s %s are not currently friends.\n\n", firstName1, lastName1, firstName2, lastName2);
			}
		}

		if (flag) {
			//remove the other from both friends lists
			temp1.getMyFriends().delete(temp2.getID());
			temp2.getMyFriends().delete(temp1.getID());

			output.printf("\t%s %s and %s %s are no longer friends.\n\n", temp1.getFirstName(), temp1.getLastName(), temp2.getFirstName(), temp2.getLastName());
			
			//decrement their number of friends
			temp1.decreaseNumFriends();
			temp2.decreaseNumFriends();
		}
	}

	public static void unfriendNoPrint(String firstName1, String lastName1, String firstName2, String lastName2, FSCbookBST book) {
		//search the tree for those students
		FSCstudent temp1 = book.searchByName(firstName1, lastName1);

		FSCstudent temp2 = book.searchByName(firstName2, lastName2);

		//delete them from each others LL
		temp1.getMyFriends().delete(temp2.getID());
		temp2.getMyFriends().delete(temp1.getID());

		//decrement their number of friends
		temp1.decreaseNumFriends();
		temp2.decreaseNumFriends();
	}

	public static void delete(Scanner in, PrintWriter output, FSCbookBST book) {
		//read in name first and last then loop over their linked list and from there based on that ID remove each of their friends
		String firstName = in.next();
		String lastName = in.next();

		//save the student being deleted into an object after searching the tree for them
		FSCstudent stuToDelete = book.searchByName(firstName, lastName);

		//if the student to be deleted is not found
		if (stuToDelete == null) {
			output.print("\tCannot Perform DELETE Command:\n");
			output.printf("\t\t%s %s was not found in FSCbook.\n\n", firstName, lastName);
		}
		else {
			FSCfriend hp = stuToDelete.getMyFriends().getHead();
			//loop through the linked list of the person we are deleting and remove all their friends
			while (hp != null) {
				FSCstudent friend = book.searchbyID(hp.getID());

				//call the unfriend method to remove them from each others LL
				unfriendNoPrint(stuToDelete.getFirstName(), stuToDelete.getLastName(), friend.getFirstName(), friend.getLastName(), book);
				
				hp = hp.getNext();
			}

			//once all friends have been deleted, remove them from the tree 
			book.delete(stuToDelete.getID());

			//make sure they are deleted from everyones LL
			//book.removeAllFriends(stuToDelete.getID());
			output.printf("\t%s %s has been removed from FSCbook.\n\n", firstName, lastName);
		}
	}

	public static void printFriends(Scanner in, PrintWriter output, FSCbookBST book) {
		String firstName = in.next();
		String lastName = in.next();
		boolean trigger = true;

		//search for the student we are printing 
		FSCstudent temp = book.searchByName(firstName, lastName);

		//if no one is found print an error
		if (temp == null) {
			trigger = false;
			output.print("\tCannot Perform PRINTFRIENDS Command:");
			output.printf("\n\t\t%s %s was not found in FSCbook.\n\n", firstName, lastName);
		}

		if (trigger) {
			//if they don't have any friends print an error
			if (temp.getNumFriends() == 0) {
				trigger = false;
				output.printf("\t%s %s has no friends.\n\n", firstName, lastName);
			}
		}
		//otherwise print their friends 
		if (trigger) {
			output.printf("\tFriends for ID %d, %s %s (%s Department):", temp.getID(), temp.getFirstName(), temp.getLastName(), temp.getDepartment());
			//print their number of friends
			output.printf("\n\t\tThere are a total of %d friends(s).", temp.getNumFriends());
			
			//call a method from the LL class to print their friends 
			temp.getMyFriends().printAllFriends(book, output);
		}
	}

	public static void printMembers(FSCbookBST book, PrintWriter output) {
		//if the tree is empty print an error
		if (book.isEmpty()) {
			output.print("\tCannot Perform PRINTMEMBERS Command:\n");
			output.print("\t\tThere are currently no members of FSCbook.\n\n");
		}
		else {//else call a custom method in the BST class to print all nodes inorder
			output.print("\tMembers of FSCbook:\n");
			//call a method from the BST to print all of the members and their IDs
			book.printMembers(output);
			output.println();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		// OPEN FILES
		// Input File:
		File inputFile = new File("FSCbook.in");
		if (!inputFile.exists()) {
			System.out.println("Input file, " + inputFile + ", does not exist.");
			System.exit(0);
		}
		// Output File:
		File outputFile = new File("test.out");

		// Make Scanner variable to read from input file, and 
		// make Printwriter variable to print to output
		Scanner in = new Scanner(inputFile);
		PrintWriter output = new PrintWriter(outputFile);

		int k = in.nextInt();
		FSCbookBST tree = new FSCbookBST();
		//for loop to loop the correct number of days
		for (int i = 0; i < k; i++) {
			String command = in.next();
			output.println(command + " Command");

			//switch to call individual methods
			switch (command) {
				case "ADD":
					add(in, output, tree);
					break;
				case "FINDID":
					findID(in, output, tree);
					break;
				case "FINDNAME":
					findName(in, output, tree);
					break;
				case "FRIEND":
					friend(in, output, tree);
					break;
				case "UNFRIEND":
					String firstName1 = in.next();
					String lastName1 = in.next();

					String firstName2 = in.next();
					String lastName2 = in.next();

					unfriend(firstName1, lastName1, firstName2, lastName2, output, tree);
					break;
				case "PRINTFRIENDS":
					printFriends(in, output, tree);
					break;
				case "PRINTMEMBERS":
					printMembers(tree, output);
					break;
				default:
					delete(in, output, tree);
			}
		}
		//close io
		in.close();
		output.close();
	}
}
