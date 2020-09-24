package fsctelecom;

//actual LL class
public class FSCTelecomAccounts {
	private Student head;

	public FSCTelecomAccounts() {
		this.head = null;
	}

	//insert method
	public void insert(String fName, String lName, int id, String number, double balance) {
		head = insert(head, fName, lName, id, number, balance);
	}
	//method to insert the Student node at the correct postition of the Linked List
	private Student insert(Student head, String fName, String lName, int id, String number, double balance) {
		// IF there is no list, newNode will be the first node, so just return it
		if (head == null || head.getID() > id) {
			//int ID, String firstName, String lastName, String phoneNumber, double balance, Student next
			head = new Student(id, fName, lName, number, balance, head);
			return head;
		}
		// ELSE, we have a list. Insert the new node at the correct location
		else {
			// We need to traverse to the correct insertion location...so we need a help ptr
			Student helpPtr = head;
			// Traverse to correct insertion point
			while (helpPtr.getNext() != null) {
				if (helpPtr.getNext().getID() > id) {
					break; // we found our spot and should break out of the while loop
				}
				helpPtr = helpPtr.getNext();
			}
			// Now make the new node. Set its next to point to the successor node.
			// And then make the predecessor node point to the new node
			Student newNode = new Student(id, fName, lName, number, balance, helpPtr.getNext());
			helpPtr.setNext(newNode);
		}
		// Return head
		return head;
	}


	//method to search the Linked list based on ID
	public Student search(int id) {
		return search(head, id);
	}
	
	private Student search(Student head, int id) {
		// TYPE YOUR CODE BELOW
		//create help Pointer 
		Student helpPointer = head;
		//if the Student was found in the Linked List then return that node
		while (helpPointer != null) {
			if (helpPointer.getID() == id) {
				return helpPointer;
			}
			helpPointer = helpPointer.getNext();
		}//if no student with that ID is found then return null
		return null;
	}

	
	public void delete(int id) {
		head = delete(head, id);
	}

	private Student delete(Student head, int id) {
		Student hp = head;

		//empty set 
		if (head == null) {
			// System.out.println("This list is empty. ");
			return null;
		}
		else { //list is not empty
			if (head.getID() == id) {
				head = head.getNext();
			}
			else {
				while (hp.getNext() != null) {
					if (hp.getNext().getID() == id) {
						hp.setNext(hp.getNext().getNext());
						break;
					}
					hp = hp.getNext();
				}
			}
		}
		return head;
	}
	
	
	
	
	//getter and setter for head
	public Student getHead() {
		return head;
	}

	public void setHead(Student head) {
		this.head = head;
	}
}
