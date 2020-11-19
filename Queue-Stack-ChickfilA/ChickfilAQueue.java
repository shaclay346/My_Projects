//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
package fscchickfila;

//queueLL class 

public class ChickfilAQueue {

	private HungryPerson front;
	private HungryPerson back;
	private String name;   //name like line 1 or line 2
	private HungryPerson next;

	public ChickfilAQueue() {
		front = null;
		back = null;
	}

	public boolean isEmpty() {
		return front == null;
	}

	
	//method to return the length of a queue
	public int lineLength() {
		HungryPerson temp = front;
		if (temp == null) {
			return 0;
		}
		else {
			int length = lineLength(temp);
			return length;
		}
	}

	private int lineLength(HungryPerson front) {
		int counter = 0;
		HungryPerson hp = front;
		while (hp != null) {
			counter++;
			hp = hp.getNext();
		}
		return counter;
	}

	// QueueNode | enqueue(QueueNode, QueueNode, int)
	public void enqueue(HungryPerson person) {
		if (isEmpty()) {
			front = back = enqueue(front, back, person);
		}
		else {
			back = enqueue(front, back, person);
		}

	}

	private HungryPerson enqueue(HungryPerson front, HungryPerson back, HungryPerson person) {
		// Make a new QueueNode with "data" as the data value
		//set the nodes next to be null when adding them to a new queue
		person.setNext(null);
		// Now, if the list is empty, return the reference for temp
		// and save this reference into both "front" and "back"
		// Why? Since this is the only node in the queue, it will be the front and back node
		if (isEmpty()) {
			return person;
		} // ELSE, the queue is not empty. We need to insert temp at the back of the queue.
		// So save the address of the new node into the next of back.
		// Then, make back "traverse" one node over, so it now points to the new back node.
		// Finally, return the updated address of back.
		else {
			back.setNext(person);
			back = back.getNext();
			return back;
		}
	}

	//
	// QueueNode | dequeue()
	//
	public HungryPerson dequeue() {
		HungryPerson temp = front;
		front = dequeue(front);
		if (front == null) {
			back = null;
		}
		return temp;
	}
	//
	// QueueNode | dequeue(QueueNode)
	//

	private HungryPerson dequeue(HungryPerson front) {
		front = front.getNext();
		return front;
	}

	//
	// int | peek()
	//
	public int peekEnterTime() {
		// Invoke the peek method with front as a parameter
		int enterTime = peekEnterTime(front);
		//int frontValue = peek(front);

		// return topValue
		return enterTime;
	}
	//
	// int | peek(QueueNode)
	//

	private int peekEnterTime(HungryPerson front) {
		// Return the data value of the front node.
		// You can see that we do NOT dequeue. We are only returning the data value.
		///return front.getData();
		return front.getEnterTime();
	}

	public HungryPerson peek() {
		HungryPerson temp = front;
		return temp;
	}

}
