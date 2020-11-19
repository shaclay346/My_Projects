//Shane Claycomb
//1235640
//shaclay346@gmail.com
//CSC 3280 section: 02
package fscchickfila;

//stack LL class

import java.io.PrintWriter;

public class ReceiptStack {

    private ChickfilAOrder top;

    // CONSTRUCTOR
    public ReceiptStack() {
        top = null;
    }

   
     
    //
    // boolean | isEmpty()
    //
    public boolean isEmpty() {
        return top == null;
    }

    //
    // void | PrintStack()
    //
    public void PrintStack(PrintWriter output) {
        PrintStack(top, output);
    }
    //
    // void | PrintStack(StackNode)
    //

    private void PrintStack(ChickfilAOrder top, PrintWriter output) {
        // We need to traverse...so we need a help ptr
        ChickfilAOrder helpPtr = top;
        // Traverse to correct insertion point
        while (helpPtr != null) {
            // Print the data value of the node
            output.print(helpPtr);
            // Step one node over
            helpPtr = helpPtr.getNext();
        }
    }

    //
    // boolean | search(int)
    //
    public boolean search(int data) {
        return search(top, data);
    }
    //
    // boolean | search(StackNode, int)
    //

    private boolean search(ChickfilAOrder p, int data) {
        // To search, we must traverse. Therefore, we need helpPtr.
        ChickfilAOrder helpPtr = p;
        while (helpPtr != null) {
//            if (helpPtr.getData() == data) {
                return true;
            //}
           // helpPtr = helpPtr.getNext(); // step one node over		
        }
        return false;
    }

    //
    // void | push(int)
    //
    public void push(ChickfilAOrder receipt) {
        top = push(top, receipt);
    }
    //
    // StackNode | push(StackNode, int)
    //

    private ChickfilAOrder push(ChickfilAOrder top, ChickfilAOrder receipt) {
        // Make a new StackNode with "data" as the data value
        // and set the "next" of this new node to the same address as top
        // * This is the same as addToFront() method for Linked Lists.
        // top = new Chickfil[AOrder(data, top);
		ChickfilAOrder temp = top;
		
        top = receipt;
		top.setNext(temp);
        // Now, return the newly updated top.
        return top;
    }

    //
    // StackNode | pop()
    //
    public ChickfilAOrder pop() {
        // Save a reference to the current top node (because we will change where top points to)
        ChickfilAOrder temp = top;

        // Now, invoke the pop method with top as a parameter.
        // This method will return a new top node.
        top = pop(top);

        // Finally, return temp, which is the previous top node that we just "popped" off the list.
        return temp;
    }
    //
    // StackNode | pop(StackNode)
    //

    private ChickfilAOrder pop(ChickfilAOrder top) {
        // Set top equal to the next node.
        // This will make top point to the 2nd node instead of the first node.
        top = top.getNext();

        // return the address/reference of the new top node
        return top;
    }

    //
    // int | peek()
    //
    public int peek() {
        // Invoke the peek method with top as a parameter
        int topValue = peek(top);

        // return topValue
        return topValue;
    }
    //
    // int | peek(StackNode)
    //

    private int peek(ChickfilAOrder top) {
        // Return the data value of the top node.
        // You can see that we do NOT pop. We are only returning the data value.
        // return top.getData();
        return 0;
    }
}