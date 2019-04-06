/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author fuji
 */
public class Main {
    static int length;
    static int width;
    static int counter = 0;
    static ArrayList<Integer> globCounter;
    

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
        
        //loading size
        length = read.nextInt();
        width = read.nextInt();
        
        //global counter
        globCounter = new ArrayList<>();
        //loading matrix
        int matrix[][] = new int[length][width];
        //creating graph of sectors
        int sectors[][] = new int[length][width];
        //helping graph
        boolean visited[][] = new boolean[length][width];
        //array of prices
        ArrayList<Integer> prices = new ArrayList<>();
        
        //loading start;
        int start[] = new int[2];
        start[0] = read.nextInt();
        start[1] = read.nextInt();
        start[0]+=-1;
        start[1]+=-1;
        
        //loading end
        int end[] = new int[2];
        end[0] = read.nextInt();
        end[1] = read.nextInt();
        end[0]+=-1;
        end[1]+=-1;
        //intialize matrix
        for (int i = 0; i < length; i++){
           for (int j = 0; j < width; j++){
               matrix[i][j] = read.nextInt();
           }    
        }
        //intializes sectors matrix
        for (int i = 0; i < length; i++){
               sectors[i] = matrix[i].clone();   
        }

              //This function counts values of sectors and STORES IT into SECTORS matrix
        long startTime = System.currentTimeMillis();
 //count policka v sektoru a put it to prices list
        for (int i = 0; i < length; i++){
           for (int j = 0; j < width; j++){
               if (visited[i][j] !=  true){
                   //Udelam bfs pro ten uzel
                   counter = 0;
                   countSectorsRecursion(i, j, visited, matrix, sectors);                   
                   prices.add(counter);
               }
           }
        }
        //create sectors matrix
        //fill the sectors matrix from prices list
        int ind = 0;
        visited = new boolean[length][width];
        for (int i = 0; i < length; i++){
           for (int j = 0; j < width; j++){
               if (visited[i][j] !=  true){
                   int temp = prices.get(ind);
                   createSectorsMatrix(i, j, visited, matrix, sectors, temp);                   
                   ind++;
               }
           }
        }
        //countSectors(visited, matrix, sectors);  
    //    long endTime = System.currentTimeMillis();

    //    long timeElapsed = endTime - startTime;

     //   System.out.println("Execution time in milliseconds: " + timeElapsed);
        
     //   startTime = System.currentTimeMillis();
        //lets do finding best path
         //idea - udelat si dva zasobniky curRecStack a plusOneStack , (n a n+1) a do curRecStack davat jen pokud to bude n a 
        //do plusOneStack pokud n+1 a kdyz soucasny bude prazdny tak dam vsechny prvky z plus1 do soucasneho, plusone pole dam jako prazdne
        // a pojedu zase od soucasneho a pokud najdu stejny pocet rekonfiguraci tak aktualizuji nejlepsi pocet konfiguraci a az se vyprazdni tak stopnu algoritmus
        visited = new boolean[length][width];
        LinkedList<Integer> bestPath = findBestPath(sectors, start, end, visited, matrix);
        System.out.println(bestPath.poll() + " " + bestPath.poll());
     //   endTime = System.currentTimeMillis();

     //   timeElapsed = endTime - startTime;

      //  System.out.println("Execution time in milliseconds: " + timeElapsed);
        
    }
    
     static void countSectorsRecursion(int i, int j ,  boolean [][]visited, int[][] matrix, int[][] sectors){
   
        //set the node as visited
        visited[i][j] = true;
        counter++;
        //todo left
        if (j-1 >= 0){
            if (matrix[i][j-1] != matrix[i][j] || visited[i][j-1] == true){
            }else{
                countSectorsRecursion(i, j-1, visited, matrix, sectors);
            }
            
        }
        //todo right
        if (j+1 < width){
            if (matrix[i][j+1] != matrix[i][j]|| visited[i][j+1] == true){
            }else{
                countSectorsRecursion(i, j+1, visited, matrix, sectors);
            }
        }
        //todo up
        if (i+1 < length){
            if (matrix[i+1][j] != matrix[i][j]|| visited[i+1][j] == true){
            }else{
                countSectorsRecursion(i+1, j, visited, matrix, sectors);
            }
        }
        //todo down
        if (i-1 >= 0){
            if (matrix[i-1][j] != matrix[i][j]|| visited[i-1][j] == true){
            } else{
                countSectorsRecursion(i-1, j, visited, matrix, sectors);
            }
        }
        
        
        
    }
    
        static void createSectorsMatrix(int i, int j ,  boolean [][]visited, int[][] matrix, int[][] sectors, int temp){
   
        //set the node as visited
        visited[i][j] = true;
        sectors[i][j] = temp;
        //todo left
        if (j-1 >= 0){
            if (matrix[i][j-1] != matrix[i][j] || visited[i][j-1] == true){
            }else{
                sectors[i][j-1] = temp;
                createSectorsMatrix(i, j-1, visited, matrix, sectors, temp);
            }
            
        }
        //todo right
        if (j+1 < width){
            if (matrix[i][j+1] != matrix[i][j]|| visited[i][j+1] == true){
            }else{
                sectors[i][j+1] = temp;
                createSectorsMatrix(i, j+1, visited, matrix, sectors, temp);
            }
        }
        //todo up
        if (i+1 < length){
            if (matrix[i+1][j] != matrix[i][j]|| visited[i+1][j] == true){
            }else{
                sectors[i+1][j] = temp;
                createSectorsMatrix(i+1, j, visited, matrix, sectors, temp);
            }
        }
        //todo down
        if (i-1 >= 0){
            if (matrix[i-1][j] != matrix[i][j]|| visited[i-1][j] == true){
            } else{
                sectors[i-1][j] = temp;
                createSectorsMatrix(i-1, j, visited, matrix, sectors, temp);
            }
        }
    }
    
    /*
    *count values of sectors in SECTORS matrix
    */
    static void countSectors(boolean [][]visited, int[][] matrix, int[][] sectors){
        int col[] = {0, -1, 1, 0};
        int row[] = {-1, 0, 0, 1};
        //que for BFS
        LinkedList<Coords> que = new LinkedList<>();
        //counter represent number of dilku v sectors
        counter = 0;
        //linked list for storing all NODES and their indexes -> we will put them value that will be stored in allSums list
        LinkedList<Coords> allNodes = new LinkedList<>();
        //linked list for STORING all size of sectors
        LinkedList<Integer> allSums = new LinkedList<>();
        //projdeme cele pole
       for (int ik = 0; ik < length; ik++){
           for (int jk = 0; jk < width; jk++){
               if (visited[ik][jk] == false){
                   
                    counter = 0;
                    Coords start = new Coords(ik, jk);
                     que.push(start);
                     allNodes.push(start);
                     
                     //projdeme BFS 
                     while (!que.isEmpty()){
                         //navysime pocet prvku
                         counter++;
                         Coords now = que.poll();
                         //oznacime jako visited
                         visited[now.i][now.j] = true;
                         for (int in = 0; in < 4; in++) {
                             int rows = now.i + row[in]; 
                             int cols = now.j + col[in]; 
                             if (rows >= 0 && cols >= 0 && rows < length && cols < width && visited[rows][cols]== false&&matrix[now.i][now.j]==matrix[rows][cols]){
                                 Coords put = new Coords(rows, cols);
                                 //oznacime jako visited
                                 visited[rows][cols] = true;
                                 //dame pryc z queue
                                 que.push(put);
                                 //ale vlozime node - > chceme mit vsechny nodes
                                 allNodes.push(put);
                             }
                         }

                     }
                     //vlozime velikost te oblasti
                     allSums.push(counter);
               }
           }
       }
       //dokud list oblasti neni prazdny
       while (!allSums.isEmpty()){
           int s = allSums.poll();
           //vezmeme prvni oblast
           for (int is = 0; is < s; is++){
               Coords temp = allNodes.poll();
               //do vsech prvku v oblasti ulozime hodnotu oblasti
               sectors[temp.i][temp.j] = s;
           }
       }
       //done


   
    }
        
        //idea - udelat si dve prioritni fronty curRecStack a plusOneStack , (n a n+1) a do curRecStack davat jen pokud to bude n a 
        //do plusOneStack pokud n+1 a kdyz soucasny bude prazdny tak dam vsechny prvky z plus1 do soucasneho, plusone pole dam jako prazdne
        // a pojedu zase od soucasneho a pokud najdu stejny pocet rekonfiguraci tak aktualizuji nejlepsi pocet konfiguraci a az se vyprazdni tak stopnu algoritmus
    static LinkedList<Integer> findBestPath(int [][]sectors, int[]start, int end[], boolean[][] visited, int[][] matrix){        
        LinkedList<Integer> bestPath = new LinkedList<>();
        //statring, distance 0, reconfig 0
        Store st = new Store(start[0], start[1], 0, 0);
        //current reconfig
        int curRec = 0;
        //green stack
        PriorityQueue<Store> curRecStack = new PriorityQueue<>();
        //red stack
        PriorityQueue<Store> plusOneStack = new PriorityQueue<>();
        curRecStack.add(st);
        
        //final reconfig and final moves
        int solutionPointReconfig = -1;
        int solutionPointMoves = -1;
        
        //do bfs from start
        while (!curRecStack.isEmpty()){
            Store currPoint = curRecStack.poll();
            
            //if unvisited
            if (currPoint.i >= 0 && currPoint.j >= 0 && currPoint.i < length && currPoint.j < width && visited[currPoint.i][currPoint.j] == false){
                //visited as true
                visited[currPoint.i][currPoint.j] = true;
                //check if current is goal
                if (currPoint.i == end[0] && currPoint.j == end[1]){
                    if (solutionPointReconfig == -1){
                        solutionPointReconfig = currPoint.reconfig;
                        solutionPointMoves = currPoint.distance;
                    } else{
                        //if is there better - reasign
                        if (currPoint.reconfig < solutionPointReconfig){
                            solutionPointReconfig = currPoint.reconfig;
                            solutionPointMoves = currPoint.distance;
                        }
                        if (currPoint.reconfig == solutionPointReconfig){
                            if (currPoint.distance < solutionPointMoves){
                                solutionPointMoves = currPoint.distance;
                            }
                        }
                    }
                }
                int col[] = {0, -1, 1, 0};
                int row[] = {-1, 0, 0, 1};
            //    ArrayList<Store> CurSortByDistance = new ArrayList<Store>();
            //    ArrayList<Store> PlusOneSortByDistance = new ArrayList<Store>();
                for (int i = 0; i < 4; i++){
                    int rows = currPoint.i + row[i];
                    int cols = currPoint.j + col[i];
                    //if visited - continue;
                    if (rows >= 0 && cols >= 0 && rows < length && cols < width && visited[rows][cols] == true){
                        continue;
                    }
                    
                    //if unvisited
                    if (rows >= 0 && cols >= 0 && rows < length && cols < width && visited[rows][cols] == false){ 
                        //count reconfig
                        int reconfig = currPoint.reconfig;
                        //if we increase reconfig by 1 marked plusOne as true
                        boolean plusOne = false;
                        if (Math.abs(sectors[currPoint.i][currPoint.j] - sectors[rows][cols]) > Math.min(sectors[currPoint.i][currPoint.j], sectors[rows][cols])){                
                            reconfig++;
                            plusOne = true;
                        }                    
                        Store neighbor = new Store(rows, cols, currPoint.distance + 1, reconfig);
                        //if reconfig is increased add it to plusonestack
                        //else put it to currentstack
                        if (plusOne){
                           // PlusOneSortByDistance.add(neighbor);
                            plusOneStack.add(neighbor);
                        }else{
                           // CurSortByDistance.add(neighbor);
                            curRecStack.add(neighbor);
                        }  
                    }
                    
                }
                
            }
            //No need to switch our stacks and check n+1 reconfig, if we have already found a solution with reconfig = n
            if (solutionPointReconfig != -1){
                continue;
            }   
            
            if (curRecStack.isEmpty() && !plusOneStack.isEmpty()){
                curRec++;
                //presypat vse z plusOneStack do curRec stack (neoznacovat jako visited)
                //swapStacks(curRecStack, plusOneStack);
                curRecStack = plusOneStack;
                plusOneStack = new PriorityQueue<>();
            }
       
    }
    bestPath.add(solutionPointReconfig);
    bestPath.add(solutionPointMoves);
    return bestPath; 
     
    }
}
