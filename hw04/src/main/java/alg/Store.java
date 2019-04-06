/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg;

/**
 *
 * @author fuji
 */
public class Store implements Comparable<Store>{
    int i;
    int j;
    int distance;
    int reconfig;
    
    
    
    public Store(int i, int j, int distance, int reconfig) {
        this.i = i;
        this.j = j;
        this.distance = distance;
        this.reconfig = reconfig;
    }
    

    @Override
    public int compareTo(Store comparesTo) {
        int compDistance = comparesTo.distance;
        return this.distance - compDistance;
    }
    



}
