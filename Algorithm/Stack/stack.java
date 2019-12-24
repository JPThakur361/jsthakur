
import java.io.*;

class Stack{
     int stack[] =new int[5];
     int top=0;
     
    public void push(int data)
    { 
        if(top==4)
        {
             System.out.println("Stack is full");
             
        }
        else{
        
        stack[top]=data;
        top=top+1;
        }        
     
    }
    
       public int peek(){
            int data=0;
            data=stack[top-1];
            return data;
       }
       
        public int pop(){
         if(isEmpty())
         {
              return top;
         }
          else{
                int data=0;
            data =stack[top];
            stack[top]=0;
            top =top-1;
            
            
            return data;
          }
        }
            public void show()
            {
                 for(int n:stack)
                 {
                      System.out.println(n+" ");
                 }
            }
         public int size(){
              return top;
         }   
       
        public boolean isEmpty()
        {
             
             return top<=0;
            
        }
               
}                
                
public class Runner{
     public static void main(String [] args)
     {
          Stack s =new Stack();
          s.push(10);
          s.push(12);
          s.push(130);
          s.push(3);
         
          s.pop();
   System.out.println(s.size());
          s.show();
     }
}
