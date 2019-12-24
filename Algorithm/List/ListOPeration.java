/*package whatever //do not write package name here */

 
import java.io.*;
class Node{
     int data;
     Node next;
     
}
class LinkedList{
     Node head;
     public void insert(int data)
     {
          Node node =new Node();
          node.data =data;
          node.next =null;
           if(head==null)
           {
                head=node;
           }
           else{
                Node n =head;
                while(n.next!=null)
                {
                     n=n.next;
                }
                n.next=node;
           }
         
     }
     public void insertAtStart(int data)
     {
          Node node = new Node();
          node.data=data;
          node.next=null;
          node.next=head;
          head=node;
          
     }
     public void insertAt(int index,int data)
     {
          Node node =new Node();
          node.data=data;
          node.next =null;
          if(index==0)
          {
               insertAtStart(data);
          }
          else
          {
              Node n =head;
              for(int i=0;i<index-1;i++)
              {
                   n=n.next;
              }
              node.next=n.next;
              n.next=node;
          }
     }  
        public void deleteAt(int index)
        {
             if(index==0)
             {
                  head=head.next;
             }
             else{
                  Node n =head;
                  Node n1=null;
                  for(int i=0;i<index-1;i++)
                  {
                       n =n.next;
                  }
                  n1=n.next;
                  n.next=n1.next;
                  
                  System.out.println("n1"+n1.data);
                  
             }
     }
     public void show()
     {
          Node node =head;
          while(node.next!=null)
          {
               System.out.print(node.data+" ");
               node =node.next;
          }
         System.out.print(node.data+" ");
          
     }
     
}
class Runner{
     public static void main(String args[])
     {
          LinkedList l =new LinkedList();
          l.insert(10);
          l.insert(20);
          l.insert(30);
          l.insert(40);
          l.insertAtStart(22);
          l.insertAt(1,23);
          l.insertAt(3,12);
          l.deleteAt(4);
          l.show();
     }
}
