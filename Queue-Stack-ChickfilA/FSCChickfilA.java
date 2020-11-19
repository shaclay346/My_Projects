//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
//Program 4: FSCchickfila
//I will practice academic and personal integrity and excellence of character and expect the same from others.
package fscchickfila;

//main class

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FSCChickfilA {

	//makes the order Object for a Hungry Person Object
	public static HungryPerson makeOrder(HungryPerson person, Scanner in) {
		MenuItem[] items = new MenuItem[person.getNumItems()];

		//loop over their number of items
		for (int i = 0; i < person.getNumItems(); i++) {
			//read in
			String itemName = in.next();
			int itemQuantity = in.nextInt();
			//use method to get price based on itemName
			double price = getPrice(itemName);

			//create the item objecct and save it into an array
			items[i] = new MenuItem(itemName, itemQuantity, price);

		}
		//set this array in the person object
		person.setItemsOrdered(items);
		
		//return the person object
		return person;
	}



	//method that recieves the integer representing time and returns a string for that time
	public static String getTime(int time) {
		int hours = 12;
		
		//ineger divide by 60 to get the hours
		if (time >= 60) {
			hours = time / 60;
		}
		//mod the integer to get the minutes
		int minutes = time % 60;

		String min = "";
		//if the minutes is less than ten add a zero before it
		if (minutes < 10) {
			//cast to a string with a zero
			min = "0" + Integer.toString(minutes);
		}
		else {
			min = Integer.toString(minutes);
		}
		
		String realTime = "";
		
		if (hours == 12) {
			realTime = Integer.toString(hours) + ":" + min + " PM:";
		}
		else {
			realTime = " " + Integer.toString(hours) + ":" + min + " PM:";
		}
		return realTime;
	}


	//based on the read in string for an item name return the appropriate price
	public static double getPrice(String itemName) {
		switch (itemName) {
			case "Chicken_Sandwich":
				return 3.05;
			case "Chicken_Sandwich_Combo":
				return 5.95;
			case "Chicken_Sandwich_Spicy":
				return 3.29;
			case "Chicken_Sandwich_Combo_Spicy":
				return 6.79;
			case "Nuggets_8_Piece":
				return 3.05;
			case "Nuggets_12_Piece":
				return 4.45;
			case "Nuggets_8_Piece_Combo":
				return 5.95;
			case "Nuggets_12_Piece_Combo":
				return 8.59;
			case "Grilled_Chicken_Sandwich":
				return 4.39;
			case "Grilled_Chicken_Sandwich_Combo":
				return 7.19;
			case "Waffle_Fries_Small":
				return 1.55;
			case "Waffle_Fries_Medium":
				return 1.65;
			case "Waffle_Fries_Large":
				return 1.85;
			case "Milkshake_Small":
				return 2.75;
			case "Milkshake_Large":
				return 3.15;
			case "Water_Dasani":
				return 1.59;
			case "Soft_Drink_Small":
				return 1.35;
			case "Soft_Drink_Medium":
				return 1.59;
			case "Soft_Drink_Large":
				return 1.85;
			default:
				return 0.00;
		}
	}



	//adds the customer to the shortest possible line
	public static void addToShortestLine(ChickfilAQueue[] lines, int currentTime, HungryPerson person, PrintWriter output) {
		boolean added = false;

		
		//if a line is empty add the person to the first empty line we see then break
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].isEmpty()) {
				added = true;

				//reset their enter time to know when to start processing their order
				person.setEnterTime(person.getEnterTime() + 1);

				output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line %d.", getTime(currentTime), person.getFirstName(), person.getLastName(), (i + 1));

				//enqueue them into the line
				lines[i].enqueue(person);
				
				//person.setNext(null);

				break;
			}
		}
		//if they were not being added to the front of the line we have some comparing to do
		if (added == false) {
			//if all lines are equal length add to line 1
			if (lines[0].lineLength() == lines[1].lineLength() && lines[0].lineLength() == lines[2].lineLength()) {
				output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 1.", getTime(currentTime), person.getFirstName(), person.getLastName());
				lines[0].enqueue(person);
				person.setNext(null);
			}
			//lines 1 and 2 are equal and less than 3 add to line 1
			else if (lines[0].lineLength() == lines[1].lineLength() && lines[0].lineLength() < lines[2].lineLength()) {
				output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 1.", getTime(currentTime), person.getFirstName(), person.getLastName());
				lines[0].enqueue(person);
				person.setNext(null);
			}
			//lines 2 and 3 are the same length and less than line 1, add to line 2
			else if (lines[1].lineLength() == lines[2].lineLength() && lines[1].lineLength() < lines[0].lineLength()) {
				output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 2.", getTime(currentTime), person.getFirstName(), person.getLastName());
				lines[1].enqueue(person);
			}
			//lines 1 and 3 equal and less than line 2, add to line 1
			else if (lines[0].lineLength() == lines[2].lineLength() && lines[0].lineLength() < lines[1].lineLength()) {
				output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 1.", getTime(currentTime), person.getFirstName(), person.getLastName());
				lines[0].enqueue(person);
			}
			else {
				//line 1 < line 2 < and line 1< line 3
				if (lines[0].lineLength() < lines[1].lineLength() && lines[0].lineLength() < lines[2].lineLength()) {
					output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 1.", getTime(currentTime), person.getFirstName(), person.getLastName());
					lines[0].enqueue(person);
				}
				//line 2 < line 1 & line 2 < line 3
				else if (lines[1].lineLength() < lines[0].lineLength() && lines[1].lineLength() < lines[2].lineLength()) {
					output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 2.", getTime(currentTime), person.getFirstName(), person.getLastName());
					lines[1].enqueue(person);
				}
				//line 3 is the shortest line
				else {
					output.printf("\n%s  %s %s arrived at FSC Chick-fil-A and entered Order Line 3.", getTime(currentTime), person.getFirstName(), person.getLastName());
					lines[2].enqueue(person);
				}
			}
		}
	}

	//making the receipt object taht will be added to the stack
	public static ChickfilAOrder createReceipt(HungryPerson person, int currentTime, int j, PrintWriter output, int line) {

		//loop through this persons array of items ordered and create a total by adding their quantity of each item
		//and that items price
		double total = 0;
		for (int i = 0; i < person.getItemsOrdered().length; i++) {
			total += person.getItemsOrdered()[i].getItemQuantity() * person.getItemsOrdered()[i].getPrice();
		}
		//now we can creat the rceipt object and call the static method to increase the order number 
		ChickfilAOrder receipt = new ChickfilAOrder(person.getItemsOrdered(), person.getNumItems(), total, ChickfilAOrder.getNumOrders(), currentTime);
		ChickfilAOrder.increaseNumOrders();

		output.printf("\n%s  %s %s received all items, paid $%.2f, and is "
				+ "exiting Order Line %d (leaving FSC Chick-fil-A).", getTime(currentTime), person.getFirstName(), person.getLastName(), total, line);
		return receipt;
	}

	//simply loop while the stack is not empty and pop and print each node
	public static void printReceipts(ReceiptStack rStack, PrintWriter output) {
		while (!rStack.isEmpty()) {
			output.print(rStack.pop());
		}
	}

	//this method handles reading in the customers info as well as calls appropriate methods to handle all of the queues
	//this method also calls methods to create the stack nodes and returns the stackLL
	public static ReceiptStack handleCustomers(int numCustomers, ChickfilAQueue[] lines, ChickfilAQueue outsideLine, Scanner in, PrintWriter output, ReceiptStack rStack) {
		int countCustomers = 0;
		int currentTime = 0;
		int numOrders = 0;

		while (true) {
			//if our number of customers is less than the numCustomers for the day, read in their info
			if (countCustomers < numCustomers) {
				int enterTime = in.nextInt();
				String firstName = in.next();
				String lastName = in.next();
				int numItems = in.nextInt();

				//create HungryPerson object
				HungryPerson temp = new HungryPerson(firstName, lastName, enterTime, numItems);
				
				//create the customers order array
				temp = makeOrder(temp, in);

				//add the customer to the outside line
				outsideLine.enqueue(temp);
			}

			//loop over the lines each day to determine if someone new is at the front of the line
			for (int i = 0; i < lines.length; i++) {
				//if a line/queue is not empty
				if (!lines[i].isEmpty()) {
					//if the person at the front of the line has not had their order time set
					if (lines[i].peek().isOrderTimeSet() == false) {
						HungryPerson person = lines[i].peek();
						output.printf("\n%s  %s %s is at the front of Order Line %d and is now placing order.", getTime(currentTime), person.getFirstName(), person.getLastName(), i + 1);

						//set this persons order times
						person.setOrderTime(currentTime + person.getNumItems());
						
						//change their boolean to signify their order time has been set
						person.setOrderTimeSet(true);
					}//if the current time equals the person at the front of the lines order time
					if (currentTime == lines[i].peek().getOrderTime() && lines[i].peek().isOrderTimeSet() == true) {
						//handle their order and make their stackNode object and then dequeue them
						ChickfilAOrder receipt = createReceipt(lines[i].dequeue(), currentTime, numOrders++, output, (i + 1));
						rStack.push(receipt);
					}
				}
			}

			//if multiple people have the same enter time this accounts for dequeueing everyone
			//that has the same enter time
			if (!outsideLine.isEmpty()) {
				if (currentTime == outsideLine.peek().getEnterTime()) {
					while (!outsideLine.isEmpty()) {
						//if the current time equals the front of the outside lines enter time then dequeue and add them to
						//the shortest line
						if (currentTime == outsideLine.peek().getEnterTime()) {
							addToShortestLine(lines, currentTime, outsideLine.dequeue(), output);
						}
						else {
							break;
						}
					}
				}
			}

			//break once every line is empty
			if (outsideLine.isEmpty() && lines[0].isEmpty() && lines[1].isEmpty() && lines[2].isEmpty()) {
				break;
			}
			
			currentTime++;
			countCustomers++;
		}
		//return the stack of receipt objects
		return rStack;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// OPEN FILES
		// Input File:
		File inputFile = new File("FSCChickfilA.in");
		if (!inputFile.exists()) {
			System.out.println("Input file, " + inputFile + ", does not exist.");
			System.exit(0);
		}
		// Output File:
		File outputFile = new File("FSCChickfilA.out");

		// Make Scanner variable to read from input file, and 
		// make Printwriter variable to print to output
		Scanner in = new Scanner(inputFile);
		PrintWriter output = new PrintWriter(outputFile);

		int days = in.nextInt();
		//for loop to loop the correct number of days
		for (int i = 1; i < days + 1; i++) {
			output.print("**********\n");
			output.printf("Day %d:\n", i);
			output.print("**********\n");

			//read in number of customers for the day
			int numCustomers = in.nextInt();

			//create the 4 queues and the stack for that day
			ChickfilAQueue line1 = new ChickfilAQueue();
			ChickfilAQueue line2 = new ChickfilAQueue();
			ChickfilAQueue line3 = new ChickfilAQueue();

			//save the queues that correlate to lines 1,2,3 into an array
			ChickfilAQueue[] lines = new ChickfilAQueue[3];
			lines[0] = line1;
			lines[1] = line2;
			lines[2] = line3;

			ChickfilAQueue outsideLine = new ChickfilAQueue();

			ReceiptStack rStack = new ReceiptStack();
			
			//call method to handle the queues and customers for that day
			//the receipt stack/stackLL will be returned here
			rStack = handleCustomers(numCustomers, lines, outsideLine, in, output, rStack);
			
			//call method to print receipts from this day
			output.printf("\n\n*** Day %d:  FSC Chick-fil-A Order Report ***:\n",i);
			output.print("\nFSC Chick-fil-A Received the following orders:\n");
			printReceipts(rStack, output);
			output.println("\n\n");
		}
		//close io
		in.close();
		output.close();
	}
}
