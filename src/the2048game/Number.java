/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

import java.util.Random;

/**
 *
 * @author Calvin Cramer
 */
public class Number {
    
    private int value;
    private boolean hasChangedThisRound;
    private boolean hasMovedThisRound;
    
    /**
     * Creates a number with a value;
     * @param value 
     */
    public Number(int value) {
        this.value = value;
        hasChangedThisRound = false;
        hasMovedThisRound = false;
    }
    
    /**
     * Creates a new number with a random value of 2 or 4
     */
    public Number() {
        Random r = new Random();
        hasChangedThisRound = false;
        hasMovedThisRound = false;
        int n = r.nextInt(2);
        if (n == 0)
            value = 2;
        else 
            value = 4;
    }
    
    public int getValue() {
        return value;
    }
    
    public boolean hasSameValue(Number otherNumber) {
        return this.value == otherNumber.getValue();
    }
    
    public void setChanged(boolean b) {
        hasChangedThisRound = b;
    }
    
    public boolean getChanged() {
        return hasChangedThisRound;
    }
    
    public void setMoved(boolean b) {
        hasMovedThisRound = b;
    }
    
    public boolean getMoved() {
        return hasMovedThisRound;
    }
    
    @Override
    public String toString() {
        return "" + value;
    }
}
