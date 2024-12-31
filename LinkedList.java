package vbs3;

public class LinkedList
{
    // int returns the value
    // Integer returns the address

    // A linked List must always be able to access the first node/ head  
    Node first, last, current;
    Node sorted;

    public LinkedList(){
        first = last = null; // An empty linked list is null on its first and last node 
    }

    public void insertAtFront(User o){ // (Use second constructor)
        if(first == null){ // The list is empty
            first = last = new Node(o); // first node is also the last node
        } else if ( first == last){ // if list got one node
            first = new Node(o);
            last = first;
        } 
        else{ // list has node(s)
            first = new Node(o, first);
        }
    }

    public void insertAtSecond(User o){ // (Use second constructor)
        if(first == null){ // The list is empty
            first = last = new Node(o); // first node is also the last node
        } else if ( first == last){ // if list got one node
            first.next = new Node(o);
            last = first.next;
        } 
        else{ // list has node(s)
            first.next = new Node(o, first.next);
                // create new temp node to retain the orginal first node
                Node n = new Node(o);
                n.next = first.next;
                
                first.next = n;

            // first.next = new Node(o);
            // 
        }
    }

    public void insertAtBack(User o){ // (Use first constructor)
        if(first == null){ // The list is empty
            first = last = new Node(o);
        } else{ // list has node(s)
            last = last.next = new Node(o); //the new node will next to the current last node |  ... -> oldLast -> newLast
        }
    }

    public int size(){
        int count = 0;
        Node curr = first; // go to the head/ first node

         while (curr != null){ // loop until the next of the last node (null)
             count++;
             curr = curr.next;
         }

        return count;
    }

    public boolean isEmpty(){
        if (first == null){ //yeah
            return true;
        } else {
            return false;
        }
    }

    public User removeAtFront(){
        User remove; // 

        if (first == null){ // there is no data in the list
            remove = null;
        } 
        else if (first == last){ // there is only one data in the list
            remove = first.data; 
            first = last = null; 
        }
        else { // there is more than one node in the list
            remove = first.data;
            if(first.next == last){ // if there are two nodes in the list
                first = last;
            } else { // if there are more than two nodes in the list
                first = first.next;
            }
        }
        return remove; //wakarimasen
    }

    public User removeFromSecond(){
        User remove; 

        if (first == null){ // there is no data in the list
            remove = null;
        } 
        else if (first == last){ // there is only one data in the list
            remove = first.data; 
            first = last = null; 
        }
        else { // there is more than one node in the list
            Node second = first.next;
            remove = second.data;
            if(first.next == last){ // if there are two nodes in the list
                first = last;
            } else { // if there are more than two nodes in the list
                first.next = first.next.next;
            }
        }
        return remove;
    }
    

    public User removeAtBack(){
        User remove = null;
        Node previous = null;

        // there is no data in the list
        if (first == null){
            remove = null;
        } else if (first == last ){// there is only one data in the list
            remove = last.data;
            first = last = null;
        } else { //there are more than one data in the list
            current = first;
            if(last == first.next){//there are two data in the list
                last = first;
            } else{
                current = first;
                while (current.next != null){
                    previous = current;
                    current = previous.next;
                }
                remove = last.data;
                last = previous;
                previous.next = null;
            }
        }
        return remove;
    }

// IMPORTANT ACCESSORS

    public User getFirst(){
        current = first; // importante
        if (first == null){ // list is empty
            return null;
        } else {
            return current.data;
        }
    }

    public User getNext(){ // to traverse the linkedList (go next and so on)
        
        if (current != last){ // while the address of the first node is not equal to the last
            current = current.next; // go to the node next to the current node
            return current.data;
        } else{
            return null; // reach last node
        }
    }
}