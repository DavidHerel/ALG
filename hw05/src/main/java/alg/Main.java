/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author fuji
 */
public class Main {
        static Bst binaryTree;
        static int allKeys = 0;
        static boolean helpTemp = false;
        static Bst subTree = null;
        static LinkedList<Bst> queueOfRoots;
        static boolean root = true;
    
        static class Reader 
            { 
        final private int BUFFER_SIZE = 1 << 16; 
        private DataInputStream din; 
        private byte[] buffer; 
        private int bufferPointer, bytesRead; 
  
        public Reader() 
        { 
            din = new DataInputStream(System.in); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public Reader(String file_name) throws IOException 
        { 
            din = new DataInputStream(new FileInputStream(file_name)); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public String readLine() throws IOException 
        { 
            byte[] buf = new byte[64]; // line length 
            int cnt = 0, c; 
            while ((c = read()) != -1) 
            { 
                if (c == '\n') 
                    break; 
                buf[cnt++] = (byte) c; 
            } 
            return new String(buf, 0, cnt); 
        } 
  
        public int nextInt() throws IOException 
        { 
            int ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do
            { 
                ret = ret * 10 + c - '0'; 
            }  while ((c = read()) >= '0' && c <= '9'); 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  

  
        private void fillBuffer() throws IOException 
        { 
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE); 
            if (bytesRead == -1) 
                buffer[0] = -1; 
        } 
  
        private byte read() throws IOException 
        { 
            if (bufferPointer == bytesRead) 
                fillBuffer(); 
            return buffer[bufferPointer++]; 
        } 
  
        public void close() throws IOException 
        { 
            if (din == null) 
                return; 
            din.close(); 
        } 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Reader read=new Reader(); 
        
        //loading numberOfInputs
        int numberOfInputs = read.nextInt();
        
        int operation;
        int startingKey;
        int endingKey;
        binaryTree = null;
        
        //taking input
        for (int i = 0; i < numberOfInputs; i++){
            operation = read.nextInt();

            if (operation == 25){
                //it is I - Insert
                startingKey = read.nextInt();
                endingKey = read.nextInt();
          //      System.out.println("INSERT");
            //    System.out.println(" Start input " + startingKey + " end input" + endingKey);
                binaryTree = insert(binaryTree, startingKey, endingKey);                
            //    printInorder(binaryTree, 0);
      //          System.out.println("---------------------");
            }
             else if (operation == 20){
                //it is D - delete
                startingKey = read.nextInt();
                endingKey = read.nextInt();
            //    System.out.println("DELETE");
            //    System.out.println(" Start delete " + startingKey + " end delete" + endingKey);
                delete(binaryTree, startingKey, endingKey);
         //       printInorder(binaryTree, 0);
     //           System.out.println("---------------------");
            }
            
        }
        printInorder(binaryTree, 0);
        //projdu strom znovu a budu printit
        printEverything(binaryTree);
        System.out.println("");
    }
    
    static void printInorder(Bst n, int depth){
        if (n == null){
            return;
        }
        printInorder(n.left, depth+1);
        n.depth = depth;
       // System.out.println(n.startKey + " " + n.endKey + " :: " + depth);
        printInorder(n.right, depth+1);
    }
    
    static private void printEverything(Bst n){
        int depth = depth(n);
        System.out.println(allKeys +" " + depth);
        printDepths(n);
    }
    
    static int depth(Bst n){
        if (n == null){
            return -1;
        } else {
            allKeys += (n.endKey+1 - n.startKey);
            int left = depth(n.left);
            int right = depth(n.right);
            
            if (left > right){             
                return (left+1);
            } else{
                return (right+1);
            }
        }
    }
    
    static void printDepths(Bst n){
        LinkedList<Bst> q = new LinkedList<>();
        int current = n.depth;
        q.add(n);
        int keys = 0;
        Bst temp = null;
        while (q.size() > 0){
            temp = q.remove();
            if (!helpTemp){
                if (current != temp.depth){
                    current = temp.depth;
                    helpTemp = true;
                    System.out.print(keys);
                    keys = (temp.endKey+1 - temp.startKey);
                }else{
                    keys += (temp.endKey+1 - temp.startKey);
                }
            }else if (helpTemp){
                if (current != temp.depth){
                    current = temp.depth;
                    System.out.print(" "+keys);
                    keys = (temp.endKey+1 - temp.startKey);
                }else{
                    keys += (temp.endKey+1 - temp.startKey);
                }
            }
            
            if (temp.left != null){
                q.add(temp.left);
            }
            if (temp.right != null){
                q.add(temp.right);
            }
        }if (temp != null){
            System.out.print(" "+keys);
        }

    }
    
    static private void leftMerge(Bst n){
        if (n == null){
            return;
        } else{
            if (n.left == null){
                return;
            }
            Bst p = TreeMax(n.left);
            if (p.endKey+1 == n.startKey){
                int tmp = p.startKey;
                binaryTree = deleteNode(binaryTree, p.startKey, p.endKey);
                n.startKey = tmp;
            //    System.out.println("leftMerge startkey " + n.startKey);
                return;
            }
        }
    }
    
    static private void rightMerge(Bst n){
        if (n == null){
            return;
        } else{
            if (n.right == null){
                return;
            }
            Bst p = TreeMin(n.right);
            if (n.endKey+1 == p.startKey){
                int tmp = p.endKey;
                binaryTree = deleteNode(binaryTree, p.startKey, p.endKey);
                n.endKey = tmp;
           //     System.out.println("rightMerge endkey " + n.endKey);
                return;
            }
        }
    }
    
    static private Bst insert(Bst n, int startingKey, int endingKey){
        // if bst is empty
        if (n == null){
            n = new Bst(startingKey, endingKey);
      //      System.out.println("n je null " + n.startKey + " " + n.endKey);
            return n;
        }
        
        //does nothing
        else if (n.startKey <= startingKey && endingKey <= n.endKey){
     //       System.out.println("DOES NOTHING 1 " + n.startKey + " " + n.endKey);
        }
        
        // 2 insert in L or R subtree
        else if (endingKey < n.startKey-1){
            n.left = insert(n.left, startingKey, endingKey);
     //       System.out.println("Insert 2L " + n.startKey + " " + n.endKey);
        }else if(n.endKey+1 < startingKey){
            n.right = insert(n.right, startingKey, endingKey);
     //       System.out.println("Insert 2R " + n.startKey + " " + n.endKey);
        }
        
        //3 expand the streak in the node
        else if (startingKey < n.startKey || n.endKey < endingKey){
            if(startingKey < n.startKey){
                delete(n.left, startingKey, n.startKey-1);
                n.startKey = startingKey;
                leftMerge(n);
      //          System.out.println("Insert 3L " + n.startKey + " " + n.endKey);
            }
            if (n.endKey < endingKey){
                delete(n.right, n.endKey+1, endingKey);
                n.endKey = endingKey;
                rightMerge(n);
        //        System.out.println("Insert3R " + n.startKey + " " + n.endKey);
            }
        }
        return n;      
    }
    static Bst deleteNode(Bst n, int startingKey, int endingKey){
        if(n==null){
            return n;
        }
        //recur down
        if(startingKey < n.startKey && endingKey < n.endKey){
            n.left = deleteNode(n.left, startingKey, endingKey);
        }else if(endingKey > n.endKey && startingKey > n.startKey){
            n.right = deleteNode(n.right, startingKey, endingKey);
            //if they are same
        }else{
            if (n.left == null){
                return n.right;
            }else if (n.right == null){
                return n.left;
            }
        }
        return n;
    }
   
    static Bst deleteTree(Bst n, int startingKey, int endingKey){
        if (n == null){
            return null;
        }
        //recur down
        if(startingKey < n.startKey && endingKey < n.endKey){
            n.left = deleteTree(n.left, startingKey, endingKey);
        }else if(endingKey > n.endKey && startingKey > n.startKey){
            n.right = deleteTree(n.right, startingKey, endingKey);
            //if they are same
        }else{
            return null;
        }
        return n;
        
    }
    
    
    static void delete(Bst n, int startingKey, int endingKey){
        //0
        if (n == null){
       //     System.out.println("DEL 0 ");
            return;
        }
        //1L
        //continue left or right
        else if (endingKey < n.startKey){
            delete(n.left, startingKey, endingKey);
      //      System.out.println("DEL 1L " + startingKey + " "+endingKey);
            return;
        }else if(n.endKey < startingKey){
            delete(n.right, startingKey, endingKey);
    //        System.out.println("DEL 1R " + startingKey + " "+endingKey);
            return;
        }
        //2L
        //delete inside node
        else if(endingKey < n.endKey && ((startingKey == n.startKey)||(startingKey == n.startKey-1))){
            n.startKey = endingKey+1;
     //       System.out.println("DEL 2L " + startingKey + " "+endingKey);
            return;
        }else if(n.startKey < startingKey && ((endingKey == n.endKey)||(endingKey == n.endKey+1))){
            n.endKey = startingKey-1;
     //       System.out.println("DEL 2R " + startingKey + " "+endingKey);
            return;
        }
        //delete
        //3L
        else if(startingKey < n.startKey-1 && endingKey < n.endKey){
            
            n.startKey = endingKey+1;
            delete(n.left, startingKey, n.startKey-2);
    //        System.out.println("DEL 3L " + startingKey + " "+endingKey);
            return;
        }else if (n.startKey<startingKey && n.endKey+1 < endingKey){
            
            
            n.endKey = startingKey-1;
            delete(n.right, n.endKey+2, endingKey);
      //      System.out.println("DEL 3R " + startingKey + " "+endingKey);
            return;
        }
        //4L
        else if (n.startKey < startingKey && endingKey < n.endKey){
            if(startingKey - n.startKey <= n.endKey-endingKey){
                n.left = insert(n.left, n.startKey, startingKey-1);
                n.startKey = endingKey+1;
     //           System.out.println("DEL 4L " + startingKey + " "+endingKey);
                return;
            }else if(startingKey - n.startKey > n.endKey-endingKey){
                n.right = insert(n.right, endingKey+1, n.endKey);
                n.endKey = startingKey-1;
      //          System.out.println("DEL 4R " + startingKey + " "+endingKey);
                return;
            }
        }
        //5
        else if (startingKey <= leftTreeMin(n) && rightTreeMax(n)<=endingKey){
            binaryTree = deleteTree(binaryTree, n.startKey, n.endKey);
     //       System.out.println("DEL 5 " + startingKey + " "+endingKey);
            return;
        }
        //6
        else if (startingKey <= leftTreeMin(n) && endingKey < rightTreeMax(n)){
            if (n.left != null){
                binaryTree = deleteTree(binaryTree, n.left.startKey, n.left.endKey);
            } else{
     //           System.out.println("NULL v 6L");
            }
            
            delete(n.right, n.endKey+2, endingKey);
            binaryTree = deleteNode(binaryTree, startingKey, endingKey);
     //       System.out.println("DEL 6L " + startingKey + " "+endingKey);
            return;
        }else if (leftTreeMin(n) < startingKey && rightTreeMax(n) <= endingKey){
            if (n.right != null){
                binaryTree = deleteTree(binaryTree, n.right.startKey, n.right.endKey);
            }else{
     //           System.out.println("NULL v 6R");
            }
            delete(n.left, startingKey, n.startKey-2);
            binaryTree = deleteNode(binaryTree, startingKey, endingKey);
    //        System.out.println("DEL 6R " + startingKey + " "+endingKey);
            return;
        }
        //7
        else if(leftTreeMin(n) < startingKey && endingKey < rightTreeMax(n)){
   //         System.out.println("DEL 7");
            //leftTrim
   //         System.out.println("left Trim");
           // leftTrim(n.left, startingKey);
           queueOfRoots = new LinkedList<>();
           root = true;
           subTree = null;
           n.left = leftTrim(n.left, startingKey);
            //shift between these 
            if (n.left != null){
                Bst y = TreeMax(n.left);
                int tmpS = y.startKey;
                int tmpE = y.endKey;                
                binaryTree = deleteNode(binaryTree, tmpS, tmpE);
                n.startKey = tmpS;
                n.endKey = tmpE;
            }

            //righttrim
    //        System.out.println("right Trim");
            //rightTrim(n.right, endingKey);
            queueOfRoots = new LinkedList<>();
            root = true;
            subTree = null;
            n.right = rightTrim(n.right, endingKey);
            return;
        }
        
    }
    
    static int leftTreeMin(Bst n){
        while (n.left != null){
            n = n.left;
        }
        return n.startKey;
    }
    
    static int rightTreeMax(Bst n){
        while (n.right != null){
            n = n.right;
        }
        return n.endKey;
    }
    
    static Bst TreeMin(Bst n){
        while (n.left != null){
            n = n.left;
        }
        return n;
    }
    
    static Bst TreeMax(Bst n){
        while (n.right != null){
            n = n.right;
        }
        return n;
    }
    
        static Bst leftTrim(Bst n, int Val){
        if (n == null){
            return null;
        }
        if (n.startKey >= Val){
            binaryTree = deleteNode(binaryTree, n.startKey, n.endKey);
            root = true;
        }else{
            if (n.endKey >= Val){
                n.endKey = Val-1;
            }
            subTree = insert(subTree, n.startKey, n.endKey);
            if (root){
                queueOfRoots.add(n);
                root = false;
            }
        } 
        n. left = leftTrim(n.left, Val);
        n.right = leftTrim(n.right, Val);
      //  System.out.println("Left tree subtree return");
        //spojim je - k nejpravejsimu dam jako praveho potomka dalsi podstrom

        return subTree;
    }
        
    static Bst rightTrim(Bst n, int Val){
        if (n == null){
            return null;
        }
        if (n.endKey <= Val){
            binaryTree = deleteNode(binaryTree, n.startKey, n.endKey);
            root = true;
        }else{
          
             if (n.startKey <= Val){
                n.startKey = Val+1;
            }
             subTree = insert(subTree, n.startKey, n.endKey);
            if (root){
                queueOfRoots.add(n);
                root = false;
            }
        } 
     //   System.out.println("Right tree subtree return");
        n.left = rightTrim(n.left, Val);
        n.right = rightTrim(n.right, Val);
       
        return subTree;
    }
    
    public Bst insertToRight(Bst n){
        if (n == null){
            return null;
        }
        n = TreeMax(n);
        if(n==null){
            return null;
        }
        n.right = queueOfRoots.remove();
        insertToRight(n);
        return n;
    }
    

}
