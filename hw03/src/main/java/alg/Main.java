/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author fuji
 */
public class Main {
    static int[] array;
    static ArrayList<Integer> roots;
    static ArrayList<Store> paths;
    static int[] inorder;
    static int[] finInorder;
    static int sumBig = Integer.MIN_VALUE;
    static int index = 0;

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
  
        public long nextLong() throws IOException 
        { 
            long ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public double nextDouble() throws IOException 
        { 
            double ret = 0, div = 1; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
  
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
  
            if (c == '.') 
            { 
                while ((c = read()) >= '0' && c <= '9') 
                { 
                    ret += (c - '0') / (div *= 10); 
                } 
            } 
  
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
    
    static class QuickRead{
        BufferedReader reader;
        StringTokenizer str;
        
        public QuickRead(){
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        String other(){
            while (str == null || !str.hasMoreElements()){
                try{
                    str = new StringTokenizer(reader.readLine());
                }catch (IOException e){
                    e.printStackTrace();
                }
                
            }
            return str.nextToken();
        }
        
        int nextInt(){
            return Integer.parseInt(other());
        }
        
    }//vic cest vyplivnout arraylist objektu
        public static ArrayList<Store> maxPathPart(int node, ArrayList<Store> sums){
        if (node > array.length-1){
            return sums;
        }
        ArrayList<Store> right = null;
        ArrayList<Store> left = null;
       if (2*node+1 <= array.length-1 && array[2*node+1]!= 0){
          // System.out.println("leftak" + (2*node+1));
            left = maxPathPart(2*node+1, sums);
       } 
        if (2*node+2 <= array.length-1 && array[2*node+2]!= 0){
           // System.out.println("pravak" + (2*node+2));
            right = maxPathPart(2*node+2, sums);
        } 
        sums = new ArrayList<>();
       // System.out.println("Zde");
       if (left == null && right == null){
            Store e = new Store();
            
            int max_single = Math.max(Math.max(0, 0) + array[node], array[node]);
            int max_top = Math.max(max_single, 0+0+array[node]);
            sumBig = Math.max(sumBig, max_top);
            e.unused = new ArrayList<>();
            e.sumBig = array[node];
            e.index = node;
            e.endl = node;
            e.endr = node;
            sums.add(e);
            boolean add = true;
            for (int jk = 0; jk < paths.size(); jk++){
                if(paths.isEmpty()){
                    paths.add(e);
                    add= false;
                    break;
                } else{
                    if (e.sumBig > paths.get(jk).sumBig){
                        paths = new ArrayList<>();;
                        paths.add(e);
                        add = false;
                    } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                        add = false;
                    }
                }
            } 
            if (add){
                paths.add(e);
            }
            return sums;
       }
       if (left != null && right == null && left.isEmpty()){
            Store e = new Store();
            
            int max_single = Math.max(Math.max(0, 0) + array[node], array[node]);
            int max_top = Math.max(max_single, 0+0+array[node]);
            sumBig = Math.max(sumBig, max_top);
            e.unused = new ArrayList<>();
            e.sumBig = array[node];
            e.index = node;
            e.endl = node;
            e.endr = node;
            boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    }
            boolean add = true;
            for (int jk = 0; jk < paths.size(); jk++){
                if(paths.isEmpty()){
                    paths.add(e);
                    add= false;
                    break;
                } else{
                    if (e.sumBig > paths.get(jk).sumBig){
                        paths = new ArrayList<>();;
                        paths.add(e);
                        add = false;
                    } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                        add = false;
                    }
                }
            } 
            if (add){
                paths.add(e);
            }
        }
              if (left == null && right != null && right.isEmpty()){
            Store e = new Store();
            
            int max_single = Math.max(Math.max(0, 0) + array[node], array[node]);
            int max_top = Math.max(max_single, 0+0+array[node]);
            sumBig = Math.max(sumBig, max_top);
            e.unused = new ArrayList<>();
            e.sumBig = array[node];
            e.index = node;
            e.endl = node;
            e.endr = node;
            boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    }
            boolean add = true;
            for (int jk = 0; jk < paths.size(); jk++){
                if(paths.isEmpty()){
                    paths.add(e);
                    add= false;
                    break;
                } else{
                    if (e.sumBig > paths.get(jk).sumBig){
                        paths = new ArrayList<>();;
                        paths.add(e);
                        add = false;
                    } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                        add = false;
                    }
                }
            } 
            if (add){
                paths.add(e);
            }
        }

            if (left != null && right == null && !left.isEmpty()){
                int iter = 0;
                for (int i = 0; i <  left.size(); i++){
                    int max_single = Math.max(Math.max(left.get(i).sumBig, 0) + array[node], array[node]);
                    int max_top = Math.max(max_single, left.get(i).sumBig+0+array[node]);
                    sumBig = Math.max(sumBig, max_top);
                    Store e = new Store();
                    
                    e.unused = new ArrayList<>();
                    e.unused = (left.get(i).unused);
                    if (e.unused == null){
                        e.unused = new ArrayList<>();
                    }
                    e.sumBig = max_single;
                    e.index = node;

                    e.endl = left.get(i).endl;
                    e.endr = node;
                    if (left.get(i).endl == left.get(i).index){
                        e.endl = left.get(i).endr;
     
                    }else{
                        e.endl = left.get(i).endl;
                    }
                    boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    }
                    boolean add = true;
                    for (int jk = 0; jk < paths.size(); jk++){
                        if(paths.isEmpty()){
                            paths.add(e);
                            add= false;
                            break;
                        } else{
                            if (e.sumBig > paths.get(jk).sumBig){
                                paths = new ArrayList<>();;
                                paths.add(e);
                                add = false;
                            } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                                add = false;
                            }
                        }
                    } 
                    if (add){
                        paths.add(e);
                    }
                    iter++;
            }
            return sums;
        }
        if (left == null && right != null && !right.isEmpty()){
            int iter = 0;
            for (int j = 0; j < right.size(); j++){
                int max_single = Math.max(Math.max(0, right.get(j).sumBig) + array[node], array[node]);
                int max_top = Math.max(max_single, 0+right.get(j).sumBig+array[node]);
                sumBig = Math.max(sumBig, max_top);
                Store e = new Store();
                
                e.unused = new ArrayList<>();
                e.unused = (right.get(j).unused);
                if (e.unused == null){
                    e.unused = new ArrayList<>();
                }
  
                e.sumBig = max_single;
                e.index = node;
   
                e.endl = node;
                if (right.get(j).endr == right.get(j).index){
                    e.endr = right.get(j).endl;
              
                }else{
                    e.endr = right.get(j).endr;
             
                }
                boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    };
                boolean add = true;
                for (int jk = 0; jk < paths.size(); jk++){
                    if(paths.isEmpty()){
                        paths.add(e);
                        add= false;
                        break;
                    } else{
                        if (e.sumBig > paths.get(jk).sumBig){
                            paths = new ArrayList<>();;
                            paths.add(e);
                            add = false;
                        } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                            add = false;
                        }
                    }
                } 
                if (add){
                    paths.add(e);
                }
 
            }
            iter++;
        }
        //System.out.println("Sel " + right.get(0).sumBig +" left " +left.get(0).sumBig);
        int iter = 0;
        sums = new ArrayList<>();
        for (int i = 0; i<left.size(); i++){
            for (int j = 0; j < right.size(); j++){
                if (left.get(i).sumBig == right.get(j).sumBig){
                    int max_single = Math.max(Math.max(left.get(i).sumBig, right.get(j).sumBig) + array[node], array[node]);
                    int max_top = Math.max(max_single, left.get(i).sumBig+right.get(j).sumBig+array[node]);
                    sumBig = Math.max(sumBig, max_top);
                    Store e = new Store();
                    
                    e.unused = new ArrayList<>();
                    e.unused = (right.get(j).unused);
                    e.unused.add(2*node+1);

                    e.sumBig = max_single;
                    e.index = node;

                    e.endl = node;
                    if (right.get(j).endr == right.get(j).index){
                        e.endr = right.get(j).endl;
                  
                    }else{
                        e.endr = right.get(j).endr;
                    
                    }
                    boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    }
                    
                 //   System.out.println("unused r " + sums.get(iter).unused);
                //    System.out.println("endr " + sums.get(iter).endr);
                    
                    
                    Store d = new Store();
                    
                    d.unused = new ArrayList<>();
                    d.unused = (left.get(i).unused);
                    d.unused.add(2*node+2);

                    d.sumBig = max_single;
                    d.index = node;
         
        
                    if (left.get(i).endl == left.get(i).index){
                        d.endl = left.get(i).endr;
                 
                    }else{
                        d.endl = left.get(i).endl;
           
                    }
                    d.endr = node;
                    ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(d);
                            ad=false;
                            break;
                        } else{
                            if (d.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(d);
                                ad = false;
                            } else if(d.sumBig == sums.get(jk).sumBig && d.endl == sums.get(jk).endl && d.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(d);
                    }
                 //   System.out.println("unused l " + sums.get(iter+1).unused);
                 //   System.out.println("endl " + sums.get(iter+1).endl);
                    //merge that shit together and compare to maximum
                    Store merged = new Store();
                    merged.unused = new ArrayList<>();
                    merged.unused.addAll(e.unused);
                    //System.out.println("merged unused " + merged.unused);
                    merged.unused.remove(new Integer(2*node+1));
                    merged.unused.addAll(d.unused);
                    merged.unused.remove(new Integer(2*node+2));
                 //   System.out.println("merged unused " + merged.unused);
            
                    merged.sumBig = sumBig;
                    merged.index = node;
            
                    merged.endl = d.endl;
                    merged.endr = e.endr;
                  //  System.out.println("Sumbig " + sumBig);
                  //  System.out.println("Unused SIZ " + merged.unused.size() + " ITER " + iter);
                  boolean add = true;  
                  for (int jk = 0; jk < paths.size(); jk++){
                    if(paths.isEmpty()){
                          paths.add(merged);
                          add = false;
                          break;
                      } else{
                          if (merged.sumBig > paths.get(jk).sumBig){
                              paths = new ArrayList<>();
                              paths.add(merged);
                              add = false;
                          } else if (merged.sumBig == paths.get(jk).sumBig && merged.endl == paths.get(jk).endl && merged.endr == paths.get(jk).endr){
                              add = false;
                          }
                      }
                  }
                  if (add){
                      paths.add(merged);
                  }
                    

                } else if (left.get(i).sumBig  < right.get(j).sumBig){
                    int max_single = Math.max(Math.max(left.get(i).sumBig, right.get(j).sumBig) + array[node], array[node]);
                    int max_top = Math.max(max_single, left.get(i).sumBig+right.get(j).sumBig+array[node]);
                    sumBig = Math.max(sumBig, max_top);
                 //   System.out.println("sumbbbbig " + max_top);
                    Store e = new Store();
                    
                    e.unused = new ArrayList<>();
                    e.unused = (right.get(j).unused);
                    if (e.unused == null){
                        e.unused = new ArrayList<>();
                    }
                    e.unused.add(2*node+1);
       
                    e.sumBig = max_single;
                    e.index = node;
    
                    e.endl = node;
                    if (right.get(j).endr == right.get(j).index){
                        e.endr = right.get(j).endl;
                 
                    }else{
                        e.endr = right.get(j).endr;
                 
                    }
                                        boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    }
                  //  System.out.println("endr " + sums.get(iter).endr + " sumBig " + max_single);
                    boolean add = true;
                    for (int jk = 0; jk < paths.size(); jk++){
                        if(paths.isEmpty()){
                            paths.add(e);
                            add= false;
                            break;
                        } else{
                            if (e.sumBig > paths.get(jk).sumBig){
                                paths = new ArrayList<>();;
                                paths.add(e);
                                add = false;
                            } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                                add = false;
                            }
                        }
                    } 
                    if (add){
                        paths.add(e);
                    }
                    Store merged = new Store();
                    merged.unused = new ArrayList<>();
                    merged.unused.addAll(e.unused);
                    merged.unused.remove(new Integer(2*node+1));
                    if (left.get(i).unused != null){
                        merged.unused.addAll(left.get(i).unused);
                    }
       
                    merged.sumBig = max_top;
                    merged.index = node;
          
                    merged.endl = e.endl;
                    if (left.get(i).endl == left.get(i).index){
                        merged.endl = left.get(i).endr;
                    }else{
                        merged.endl = left.get(i).endl;
                    }
                    merged.endr = e.endr;
                  add = true;  
                  for (int jk = 0; jk < paths.size(); jk++){
                    if(paths.isEmpty()){
                          paths.add(merged);
                          add = false;
                          break;
                      } else{
                          if (merged.sumBig > paths.get(jk).sumBig){
                              paths = new ArrayList<>();
                              paths.add(merged);
                              add = false;
                          } else if (merged.sumBig == paths.get(jk).sumBig && merged.endl == paths.get(jk).endl && merged.endr == paths.get(jk).endr){
                              add = false;
                          }
                      }
                  }
                  if (add){
                      paths.add(merged);
                  }
                } else if (left.get(i).sumBig  > right.get(j).sumBig){
                    int max_single = Math.max(Math.max(left.get(i).sumBig, right.get(j).sumBig) + array[node], array[node]);
                    int max_top = Math.max(max_single, left.get(i).sumBig+right.get(j).sumBig+array[node]);
                    sumBig = Math.max(sumBig, max_top);
                    Store e = new Store();
                    
                    e.unused = new ArrayList<>();
                    e.unused = (left.get(i).unused);
                    if (e.unused == null){
                        e.unused = new ArrayList<>();
                    }
                    e.unused.add(2*node+2);
      
                    e.sumBig = max_single;
                    e.index = node;
   
                    e.endl = left.get(i).endl;
                    e.endr = node;
                    if (left.get(i).endl == left.get(i).index){
                        e.endl = left.get(i).endr;
                   
                    }else{
                        e.endl = left.get(i).endl;
                
                    }
                                        boolean ad = true;
                    for (int jk = 0; jk < sums.size(); jk++){
                        if (sums.isEmpty()){
                            sums.add(e);
                            ad=false;
                            break;
                        } else{
                            if (e.sumBig > sums.get(jk).sumBig){
                                sums = new ArrayList<>();
                                sums.add(e);
                                ad = false;
                            } else if(e.sumBig == sums.get(jk).sumBig && e.endl == sums.get(jk).endl && e.endr == sums.get(jk).endr){
                                ad = false;
                            }
                        }
                    }
                    if(ad){
                        sums.add(e);
                    }
                  //  System.out.println("endl " + sums.get(iter).endl + " sumBig " + max_single);
                    boolean add = true;
                    for (int jk = 0; jk < paths.size(); jk++){
                        if(paths.isEmpty()){
                            paths.add(e);
                            add= false;
                            break;
                        } else{
                            if (e.sumBig > paths.get(jk).sumBig){
                                paths = new ArrayList<>();;
                                paths.add(e);
                                add = false;
                            } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                                add = false;
                            }
                        }
                    } 
                    if (add){
                        paths.add(e);
                    }
                    Store merged = new Store();
                    merged.unused = new ArrayList<>();
                    merged.unused.addAll(e.unused);
                    merged.unused.remove(new Integer(2*node+2));
                    if (right.get(j).unused != null){
                        merged.unused.addAll(right.get(j).unused);
                    }
            
                    merged.sumBig = max_top;
                    merged.index = node;
               
                    
                    merged.endl = e.endl;
                    merged.endr = e.endr;
                    if (right.get(j).endr == right.get(j).index){
                        merged.endr = right.get(j).endl;
                    }else{
                        merged.endr = right.get(j).endr;
                    }
                  add = true;  
                  for (int jk = 0; jk < paths.size(); jk++){
                    if(paths.isEmpty()){
                          paths.add(merged);
                          add = false;
                          break;
                      } else{
                          if (merged.sumBig > paths.get(jk).sumBig){
                              paths = new ArrayList<>();
                              paths.add(merged);
                              add = false;
                          } else if (merged.sumBig == paths.get(jk).sumBig && merged.endl == paths.get(jk).endl && merged.endr == paths.get(jk).endr){
                              add = false;
                          }
                      }
                  }
                  if (add){
                      paths.add(merged);
                  }
                }
                iter++;
            }
        }
        if (sums.isEmpty()){
            Store e = new Store();
            
            int max_single = Math.max(Math.max(0, 0) + array[node], array[node]);
            int max_top = Math.max(max_single, 0+0+array[node]);
            sumBig = Math.max(sumBig, max_top);
   
            e.unused = new ArrayList<>();
            e.sumBig = array[node];

            e.index = node;
            e.endl = node;
            e.endr = node;
            sums.add(e);
            boolean add = true;
            for (int jk = 0; jk < paths.size(); jk++){
                if(paths.isEmpty()){
                    paths.add(e);
                    add= false;
                    break;
                } else{
                    if (e.sumBig > paths.get(jk).sumBig){
                        paths = new ArrayList<>();;
                        paths.add(e);
                        add = false;
                    } else if (e.sumBig == paths.get(jk).sumBig && e.endl == paths.get(jk).endl && e.endr == paths.get(jk).endr){
                        add = false;
                    }
                }
            } 
            if (add){
                paths.add(e);
            }
        }



        return sums;
        
        
    }
    
    public static ArrayList<Store> maxPathFinder(int node){
        ArrayList<Store> sums = new ArrayList<>();
        sumBig = Integer.MIN_VALUE;
        sums = maxPathPart(node, sums);;
        return sums;
    }
    
    public static void inOrder(int node){
        if (node > array.length-1){
            return;
        }
        inOrder(2*node+1);
        index++;
        inorder[node] = index;
        inOrder(2*node+2);
    }
    

    /**
     * @param args the command line arguments
     */
    //Inorder seznam done
    public static void main(String[] args) throws IOException {
        //QuickRead read = new QuickRead();
        Reader read=new Reader();   
       int nodes = read.nextInt();
        roots = new ArrayList<>();
        array = new int[nodes];
        inorder = new int[nodes];
        finInorder = new int[nodes];
        paths = new ArrayList<>();
        for (int i = 0; i < nodes; i++){
            array[i] = read.nextInt();
        }
        inOrder(0);
        roots.add(0);
        int finalInc = 0;
       // ArrayList<Store> max;
        /*ArrayList<Store> max = maxPathFinder(0);
        System.out.println("Size of " + paths.size());
        System.out.println("Max path is" + paths.get(0).sumBig + " endl " + paths.get(0).endl + " endr " + paths.get(0).endr);*/
        while (!roots.isEmpty()){
            finalInc++;
            ///System.out.println("novy");
          //  System.out.println("rootsize "+roots.size());
           //     System.out.println("Root " + roots.get(i));
                maxPathFinder(roots.get(0));
           // System.out.println("Size of " + paths.size());
            
            // TODO  vyfiltrovat to a pak oznacit jako 0 a upravit ten cyklus nahore a melo by to byti
            // a jak najit to zkurvene minimum?
            if (paths.size() > 1){
                int min = inorder[paths.get(0).index];
                for (int i = 0; i < paths.size(); i++){
                    if (inorder[paths.get(i).index] < min){
                        min = inorder[paths.get(i).index];
                    }
                }
                int siz = paths.size();
                for (int i = siz-1; i >= 0; i--){
                    if (inorder[paths.get(i).index] > min){
                        paths.remove(i);
                    }
                }
                if (paths.size() > 1){
                    int abs = Math.abs(inorder[paths.get(0).endl] - inorder[paths.get(0).endr]);
                    siz = paths.size();
                    for (int i = 0; i < siz; i++){
                        if (Math.abs(inorder[paths.get(i).endl] - inorder[paths.get(i).endr]) > abs){
                            abs = Math.abs(inorder[paths.get(i).endl] - inorder[paths.get(i).endr]);
                        }
                    }
                    siz = paths.size();
                    for (int i = siz-1; i >= 0; i--){
                        if (Math.abs(inorder[paths.get(i).endl] - inorder[paths.get(i).endr]) < abs){
                            paths.remove(i);
                        }
                    }
                    
                }
            //    System.out.println("MAXpath is " +paths.get(0).sumBig+ " size " + paths.size() + " endl " + paths.get(0).endl+" endr "+paths.get(0).endr);
                array[paths.get(0).index]=0;
                siz = roots.size();
                for (int j = siz-1; j >= 0; j--){
                    if (paths.get(0).index == roots.get(j)){
                        roots.remove(j);
                        for (int i : paths.get(0).unused){
                            if (i < nodes-1 &&array[i] != 0){
                                roots.add(i);
                //                System.out.println("Root are " + i);
                            }
                        }
                        break;
                    }
                }
                paths = new ArrayList<>();
                
                sumBig = Integer.MIN_VALUE;
            } else if (paths.size() == 1){
                //this should be done
            //    System.out.println("MAXpath is " +paths.get(0).sumBig+ " size " + paths.size() + " endl " + paths.get(0).endl+" endr "+paths.get(0).endr);
                array[paths.get(0).index] = 0; //null that shit
                int siz = roots.size();
                for (int j = siz-1; j >= 0; j--){
                    if (paths.get(0).index == roots.get(j)){
                        roots.remove(j);
                        for (int i : paths.get(0).unused){
                            if (i < nodes-1 &&array[i] != 0){
                                roots.add(i);
              //                  System.out.println("Root are " + i);
                            }
                        }
                        break;
                    }
                }
                sumBig = Integer.MIN_VALUE;
                paths = new ArrayList<>();
            }
        }
        System.out.println(finalInc);
    }
    

    
}