package alg;
 
import java.io.IOException;
import java.util.LinkedList;
 
public class Main
{
    static Node root;
    static LinkedList<Integer> nodesToRemove;
    static int height;
    static int rotCount;
    static int consolCount;
    static boolean done;
     
    public static int max(int a, int b)
    {
        return a > b ? a : b;
    }
     
    public static Node findMinNode(Node n)
    {
        if(n == null) return null;
        Node ret = n;
        if(n.left != null)
        {
            ret = findMinNode(n.left);
        }
        return ret;
    }
     
    public static Node findMaxNode(Node n)
    {
        if(n == null) return null;
        Node ret = n;
        if(n.right != null)
        {
            ret = findMaxNode(n.right);
        }
        return ret;
    }
     
    public static void inoPrint(Node n, int depth)
    {
        if(n != null)
        {
            inoPrint(n.left, depth + 1);
            System.out.printf("%d (%d).. ", n.deleted ? -1*n.value : n.value, depth);
            inoPrint(n.right, depth + 1);
        }
    }
     
    public static void preoPrint(Node n, int depth)
    {
        if(n != null)
        {
            System.out.printf("%d (%d).. ", n.deleted ? -1*n.value : n.value, depth);
            preoPrint(n.left, depth + 1);
            preoPrint(n.right, depth + 1);
        }
    }
     
    public static void postoPrint(Node n, int depth)
    {
        if(n != null)
        {
            postoPrint(n.left, depth + 1);
            postoPrint(n.right, depth + 1);
            System.out.printf("%d (%d).. ", n.deleted ? -1*n.value : n.value, depth);
        }
    }
     
    public static void delBelowPrint(Node n)
    {
        if(n != null)
        {
            delBelowPrint(n.left);
            System.out.printf("%d...... ", n.deletedBelow);
            delBelowPrint(n.right);
        }
    }
     
    public static void delBelowUpdate(Node n)
    {
        if(n != null)
        {
            int delBelLeft = 0;
            int delBelRight = 0;
            if(n.left != null)
                delBelLeft = n.left.deletedBelow;
            if(n.right != null)
                delBelRight = n.right.deletedBelow;
            n.deletedBelow = max(delBelLeft, delBelRight);
            n.deletedBelow += n.deleted ? 1 : 0;
        }
    }
     
    public static void updateHeight(Node n)
    {
        if(n != null)
        {
            int heightLeft = 0;
            int heightRight = 0;
            if(n.left != null)
                heightLeft = n.left.height;
            if(n.right != null)
                heightRight = n.right.height;
            n.height = max(heightLeft, heightRight) + 1;
        }
    }
     
    public static int balance(Node n)
    {
        if(n != null)
        {
            int heightLeft = 0;
            int heightRight = 0;
            if(n.left != null)
                heightLeft = n.left.height;
            if(n.right != null)
                heightRight = n.right.height; 
            return heightLeft - heightRight;
        }
        return 0;
    }
     
    public static Node leftRotation(Node n)
    {
        if(n != null && n.right != null)
        {
            rotCount++;
            Node rightNode = n.right; 
            Node rightNodesLeftSubTree = rightNode.left;
            rightNode.left = n; 
            n.right = rightNodesLeftSubTree; 
 
            delBelowUpdate(n);
            delBelowUpdate(rightNode);
 
            updateHeight(n);
            updateHeight(rightNode);
            return rightNode; 
        }
        return null;
    }
     
    public static Node rightRotation(Node n)
    { 
        if(n != null && n.left != null)
        {
            rotCount++;
            Node leftNode = n.left;
            Node leftNodesRightSubTree = leftNode.right; 
            leftNode.right = n; 
            n.left = leftNodesRightSubTree; 
 
            delBelowUpdate(n);
            delBelowUpdate(leftNode);
 
            updateHeight(n);
            updateHeight(leftNode);
            return leftNode; 
        }
        return null;
    } 
   
    public static Node insert(Node n, int value)
    {
        if(n == null)
        {
            Node newNode = new Node(value, 1);
            return newNode;
        }
        if(value < n.value)
            n.left = insert(n.left, value);
        else if(value > n.value)
            n.right = insert(n.right, value);
        else if(value == n.value)
        {
            //System.out.println("It's already here - undelete " + n.value);
            n.deleted = false;
            delBelowUpdate(n);
            return n;
        }
         
        delBelowUpdate(n);
        updateHeight(n);
        int balance = balance(n);
         
        if(balance > 1 && value < n.left.value) 
        {
            //System.out.println("insert single right");
            n = rightRotation(n);
        }
 
        else if(balance < -1 && value > n.right.value) 
        {
          //  System.out.println("insert single left");
            n = leftRotation(n);
        }
         
        else if(balance < -1 && value < n.right.value)
        { 
            //System.out.println("insert double right left");
            n.right = rightRotation(n.right); 
            n = leftRotation(n);
        } 
         
        else if(balance > 1 && value > n.left.value)
        { 
            //System.out.println("insert double left right");
            n.left = leftRotation(n.left); 
            n = rightRotation(n);
        } 
        return n;
    }
     
    public static void delete(Node n, int value)
    {
        if(n != null)
        {
            if(value < n.value)
                delete(n.left, value);
            else if(value > n.value)
                delete(n.right, value);
            else if(value == n.value)
            {
                n.deleted = true;
            }
               
            delBelowUpdate(n);
        }
    }
     
    public static Node remove(Node n, int val)
    {
        if(n != null)
        {
            if(val < n.value) n.left = remove(n.left, val);
            else if(val > n.value) n.right = remove(n.right, val);
            else if(n.value == val)
            {
                if(n.left == null && n.right == null)
                {
                    n = null;
                    return n;
                }
                else if(n.left != null)
                {
                    Node y = findMaxNode(n.left);
                    int newval = y.value;
                    n.value = y.value;
                    n.deleted = false;
                    n.left = remove(n.left, y.value);
                    n.value = newval;
                    //return n;   //???
                }
                else if(n.left == null && n.right != null)
                {
                    Node y = findMinNode(n.right);
                    int newval = y.value;
                    n.deleted = false;
                    n.value = y.value;
                    // y.deleted = false; //ma to tam byt? DEBUG
                    n.right = remove(n.right, y.value);
                    n.value = newval;
                    //return n;   ///???
                }
            }
 
            delBelowUpdate(n);
            updateHeight(n);
            int balance = balance(n);
            int balanceLeft = 0;
            int balanceRight = 0;
            if(n.left != null) balanceLeft = balance(n.left);
            if(n.right != null) balanceRight = balance(n.right);
 
             
            //System.out.println("BALANCE after remove");
            if(balance > 1 && balanceLeft >= 0) 
            {
                //System.out.printf("delete single right on %d\n", n.value);
                n = rightRotation(n);
            }
 
            else if(balance < -1 && balanceRight <= 0) 
            {
                //System.out.printf("delete single left on %d\n", n.value);
                n = leftRotation(n);
            }
 
            else if(balance < -1 && balanceRight > 0)
            { 
                //System.out.printf("delete double right left on %d\n", n.value);
                n.right = rightRotation(n.right); 
                n = leftRotation(n);
            } 
 
            else if(balance > 1 && balanceLeft < 0)
            { 
                //System.out.printf("delete double left right on %d\n", n.value);
                n.left = leftRotation(n.left); 
                n = rightRotation(n);
            }
        }
        return n;
    }
    
    public static void consolidation(Node n)
    {
        if(n != null)
        {
           // System.out.printf("%d.. ", n.value);
            if(n.deletedBelow <= 0)
            {
                //delBelowUpdate(n);
                //n.deletedBelow = 0;
                //n.deleted = false;  //ma to tam byt? DEBUG
                return;
            }
            consolidation(n.left); 
            consolidation(n.right);
            //System.out.printf("Consolidation - now on %d\n", n.value);
            //delBelowUpdate(n);
            if(n.deleted)
            {
                //nodesToRemove.add(n.value);
                //n.deleted = false; //ma to tam byt? DEBUG
                root = remove(root, n.value);
            }
            n.deletedBelow = 0;  
        }
    }
    
    public static int consolNew(Node n)
    {
        int retleft;
        int retright;
        if(n != null)
        {
            if(n.deletedBelow <= 0) return 0;
            retleft = consolNew(n.left);
            if(retleft != 0) return retleft;
            retright = consolNew(n.right);
            if(retright != 0) return retright;
            
            if(n.deleted)
            {
                return n.value;
            }
        }
        return 0;
    }
    
    public static Node remove(Node n)
    {
        if(n != null)
        {
            if(n.deletedBelow <= 0)
                return n;
            n.left = remove(n.left);
            n.right = remove(n.right);
            if(n.deleted)
            {
                done = false;
                if(n.left == null && n.right == null)
                {
                    n = null;
                    return n;
                }
                else if(n.left != null)
                {
                    Node y = findMaxNode(n.left);
                    int newval = y.value;
                    n.deleted = false;
                    n.left = remove(n.left, y.value);
                    n.value = newval;
                }
                else if(n.left == null && n.right != null)
                {
                    Node y = findMinNode(n.right);
                    int newval = y.value;
                    n.deleted = false;
                    n.right = remove(n.right, y.value);
                    n.value = newval;
                }
            }
            //n.deletedBelow = 0;
            updateHeight(n);
            int balance = balance(n);
            int balanceLeft = 0;
            int balanceRight = 0;
            if(n.left != null) balanceLeft = balance(n.left);
            if(n.right != null) balanceRight = balance(n.right);
 
            if(balance > 1 && balanceLeft >= 0) 
            {
                n = rightRotation(n);
            }
 
            else if(balance < -1 && balanceRight <= 0) 
            {
                n = leftRotation(n);
            }
 
            else if(balance < -1 && balanceRight > 0)
            { 
                n.right = rightRotation(n.right); 
                n = leftRotation(n);
            } 
 
            else if(balance > 1 && balanceLeft < 0)
            { 
                n.left = leftRotation(n.left); 
                n = rightRotation(n);
            }
        }
        return n;
    }
    
    public static Node clear(Node n)
    {
        if(n != null)
        {
            n.left = clear(n.left);
            n.right = clear(n.right);
            n.deletedBelow = 0;
        }
        return n;
    }
    
    public static void main(String[] args) throws IOException
    {
        root = null;
        height = -1;
        rotCount = 0;
        consolCount = 0;
        Reader rdr = new Reader();
 
        int operations = rdr.nextInt();
         
        for (int i = 0; i < operations; i++)
        {
            int value = rdr.nextInt();
            if(value > 0)
            {
                //System.out.printf("insert %d\n", value);
                root = insert(root, value);
            }
            else if(value < 0)
            {
                //System.out.printf("delete %d\n", -1*value);
                delete(root, value*(-1));
            }
             
            /*inoPrint(root, 0);
            System.out.println("");
            delBelowPrint(root);
            System.out.println("");*/
             
            if(root != null)
            {
                //System.out.printf("root height: %d ...%d/2 = %d\n", root.height - 1, root.height - 1, (root.height - 1)/2);
                //System.out.printf("%d >= %d ???\n", root.deletedBelow, 1 + (root.height-1)/2);
                
                //podminka pro konsolidaci
                if(root.deletedBelow >= 1 + (root.height-1)/2)
                {
                    consolCount++;
                    //System.out.printf("%d >= %d --->>> HERE COMES CONSOLIDATION\n", root.deletedBelow, 1 + (root.height-1)/2);

                    //consolidation(root);

                   /* done = false;
                    while(!done)
                    {
                        done = true;
                        root = remove(root);
                    }*/
                    
                    while(true)
                    {
                        int remval = consolNew(root);
                        if(remval == 0) break;
                        //System.out.printf("remove %d\n", remval);
                        root = remove(root, remval);
                    }
                   
                    //clear(root);
                    
                   /* inoPrint(root, 0);
                    System.out.println("");
                    delBelowPrint(root);
                    System.out.println("");*/ 
                }
            }
            //else System.out.printf("root height: -1\n");
            //System.out.println("------------------------");
        }
 
        if(root != null)
        {
            height = root.height - 1;
        }
        System.out.printf("%d %d %d\n", height, rotCount, consolCount);
    }
}