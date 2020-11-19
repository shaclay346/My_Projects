//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
package fscchickfila;

//this is the queueNode class

//these objects will be put in the queue linked Lists
public class HungryPerson {

    private String firstName;
    private String lastName;
    private int numItems;
    
    private int enterTime; 
    private boolean orderTimeSet = false;
    private int orderTime;

    private MenuItem[] itemsOrdered;

    private ChickfilAOrder order;
    private HungryPerson next;
	
	//constructor
    public HungryPerson(String firstName, String lastName, int enterTime, int numItems) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enterTime = enterTime;
        this.numItems = numItems;
        this.orderTime = -1;
        this.orderTimeSet = false;
        this.next = null;
    }

    //getters and setters
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

    public int getEnterTime() {
        return enterTime;
    }

    public MenuItem[] getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(MenuItem[] itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    
    
    public boolean isOrderTimeSet() {
        return orderTimeSet;
    }
    public void setOrderTimeSet(boolean orderTimeSet) {
        this.orderTimeSet = orderTimeSet;
    }
    
    
    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setEnterTime(int enterTime) {
        this.enterTime = enterTime;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    public ChickfilAOrder getOrder() {
        return order;
    }

    public void setOrder(ChickfilAOrder order) {
        this.order = order;
    }

    public HungryPerson getNext() {
        return next;
    }

    public void setNext(HungryPerson next) {
        this.next = next;
    }

}