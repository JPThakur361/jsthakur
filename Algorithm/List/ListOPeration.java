class Node{
	 int data;
	 Node next;
}

class LinkedList{
	 Node head;
     
    public void insert(int data){
	    Node node =new Node();
	    node.data=data;
	    node.next=null;
	    head=node;
    }
   }
class Runner{
	 public static void main(String []args){
		  LinkedList l =new LinkedList();
		  l.insert(19);
		  l.insert(20);

		}
}
