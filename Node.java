package vbs3;

public class Node{

    //Should have two attributes: data and reference

    // Superclass of all classes
    // Integer data; and so on
    User data; // Data type depends on the type of data you want to store be it Integer or Student Integers
    Node next; // next --> reference attribute

    //Constructor (Should be 2 constructors)
    public Node(User o){ 
    //Only 1 parameter because we know it is the last node,
    // then we already know the next of the last node is always null.

        data = o;
        next = null;
        //(This will be the last node)
    }
    
    //Constructing a Node that is NOT the last node,
    public Node(User o, Node next){
        data = o;
        this.next = next;
    }
}