//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
package fscchickfila;

//stack Node class

//import static fscchickfila.FSCChickfilA.getTime;
public class ChickfilAOrder {

	private MenuItem[] items4Order;
	private int numItems;
	//probably needs to be a double but he fucked up the PDF
	private double totalPrice;
	private int orderNumber;
	private int timeCompleted;
	private ChickfilAOrder next;

	private static int numOrders = 274952;

	

	//constructor
	public ChickfilAOrder(MenuItem[] items4Order, int numItems, double totalPrice, int orderNumber, int timeCompleted) {
		this.items4Order = items4Order;
		this.numItems = numItems;
		this.totalPrice = totalPrice;
		this.orderNumber = orderNumber;
		this.timeCompleted = timeCompleted;
		this.next = null;
	}

	//method to increase the order number for each receipt
	public static void increaseNumOrders() {
		numOrders++;
	}

	//another method to print the time for the stack printing output because it needs to be printed without the ':' this time
	static String time(int time) {
		int hours = 12;
		if (time >= 60) {
			hours = time / 60;
		}
		int minutes = time % 60;
		String min = "";
		if (minutes < 10) {
			//cast to a string with a zero
			min = "0" + Integer.toString(minutes);
		}
		else {
			min = Integer.toString(minutes);
		}
		String realTime = "";
		if (hours == 12) {
			realTime = Integer.toString(hours) + ":" + min + " PM";
		}
		else {
			realTime = " " + Integer.toString(hours) + ":" + min + " PM";
		}

		return realTime;
	}

	@Override
	public String toString() {
		String s = "";

		s += String.format("\nOrder Number:  %d", this.orderNumber);
		//IF there's a problem it's here
		s += String.format("\n\tTime Completed:  %s\n\tItems:  ", time(this.timeCompleted));
		s += "\n\t| NAME                           | PRICE      | QUANTITY   |";
		for (int i = 0; i < this.items4Order.length; i++) {
			s += String.format("\n\t%s %-30s %s $%-5.2f     %s%2d          |", "|", this.items4Order[i].getItemName(), "|", this.items4Order[i].getPrice(), "|", this.items4Order[i].getItemQuantity());
			 //s += String.format("\n\t|  %-30s %s$%-5.2f     |   %d        |", this.items4Order[i].getItemName(), "|", this.items4Order[i].getPrice(), this.items4Order[i].getItemQuantity());
		}
		s += String.format("\n\tTotal Price:  $%.2f", this.totalPrice);
		return s;
	}

	
	//getters and setters
	public MenuItem[] getItems4Order() {
		return items4Order;
	}

	public void setItems4Order(MenuItem[] items4Order) {
		this.items4Order = items4Order;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getTimeCompleted() {
		return timeCompleted;
	}

	public void setTimeCompleted(int timeCompleted) {
		this.timeCompleted = timeCompleted;
	}
	public ChickfilAOrder getNext() {
		return next;
	}
	public void setNext(ChickfilAOrder next) {
		this.next = next;
	}
	public static int getNumOrders() {
		return numOrders;
	}
	public static void setNumOrders(int numOrders) {
		ChickfilAOrder.numOrders = numOrders;
	}
}
