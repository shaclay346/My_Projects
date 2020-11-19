package fscchickfila;
//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02

public class MenuItem {

    //make an object with the item name and price and then make an array of them that goes in the order class
    private String itemName;
    private int itemQuantity;
    private double price;

	//constructor
    public MenuItem(String itemName, int itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }
   
	//constructor
    public MenuItem(String itemName, int itemQuantity, double price) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price = price;
    }

	
	//getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    
      
    private final String[] menu = {
        "Chicken_Sandwich",
        "Chicken_Sandwich_Combo",
        "Chicken_Sandwich_Spicy",
        "Chicken_Sandwich_Combo_Spicy",
        "Nuggets_8_Piece",
        "Nuggets_12_Piece",
        "Nuggets_8_Piece_Combo",
        "Nuggets_12_Piece_Combo",
        "Grilled_Chicken_Sandwich",
        "Grilled_Chicken_Sandwich_Combo",
        "Waffle_Fries_Small",
        "Waffle_Fries_Medium",
        "Waffle_Fries_Large",
        "Milkshake_Small",
        "Milkshake_Large",
        "Water_Dasani",
        "Soft_Drink_Small",
        "Soft_Drink_Medium",
        "Soft_Drink_Large"};
    private final String[] prices = {
        "$3.05",
        "$5.95",
        "$3.29",
        "$6.79",
        "$3.05",
        "$4.45",
        "$5.95",
        "$8.59",
        "$4.39",
        "$7.19",
        "$1.55",
        "$1.65",
        "$1.85",
        "$2.75",
        "$3.15",
        "$1.59",
        "$1.35",
        "$1.59",
        "$1.85"};
   
    
}


