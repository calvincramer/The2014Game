/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the2048game;

public class Tile {
    public Tile(int y, int x, int n) {
        this.y = y;
        this.x = x;
        this.n = n;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getValue() {
        return n;
    }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ") " + n;
    }

    public boolean equals(Tile other) {
        if (other == null) return false;
        return (this.y == other.getY() 
                && this.x == other.x 
                && this.n == other.getValue() );
    }
    
    private int y;
    private int x;
    private int n;
}