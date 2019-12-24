
import java.io.*;

class Runner{
      public static void main(String [] args)
      {
           Queue q =new Queue();
           q.enqueue(10);
           q.enqueue(11);
           
           q.dequeue();
           q.show();
      }
}
class Queue{
     
      int queue[] =new int[5];
       int front;
      int size;
      int reare; 
      public void enqueue(int data)
      {
           if(!isFull())
           {
           queue[reare]=data;
           reare=(reare+1)%5;
           size=size+1;
           }
           else{
                System.out.println("queue is full");
           }
      }
      public int dequeue(){
           int data=0;
          if(!isEmpty())
          {
           
            data=queue[front];
            front=(front+1)%5;
            size=size-1;
          }
           
          
          else
               System.out.println("queue is isEmpty");
           return data;
      }
      public void show()
      {
           for(int i=0;i<size;i++){
                System.out.println(queue[front+i]+" ");
           }
           System.out.println();
           for(int n:queue)
           {
                System.out.println(n+" ");
                
           }
      }
      
       public int getSize()
       {
            return size;
       }
      public boolean isEmpty()
      {
           return getSize()==0;
      }
      public boolean isFull(){
           return getSize()==5;
      }
}
