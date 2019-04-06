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
public class Bst {
    int startKey;
    int endKey;
    Bst left;
    Bst right;
    Bst parent;
    int depth;

    public Bst(int startKey, int endKey) {
        this.startKey = startKey;
        this.endKey = endKey;
        parent = left = right = null;
        depth = 0;
        
    }


}
