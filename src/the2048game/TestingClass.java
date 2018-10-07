package the2048game;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Calvin Cramer
 */
public class TestingClass {
    
    public static SimpleGrid sg;
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long startNano = System.nanoTime();
        
        Random r = new Random();
        sg = new SimpleGrid();
        sg.add(0, 0, 64);
        sg.add(1, 0, 32);
        sg.add(2, 0, 16);
        sg.add(3, 0, 8);
        sg.add(3, 1, 4);
        sg.add(2, 1, 2);
        
        System.out.println(sg.toString());
        
        Tile greatestSnakiness = null;
        int greatestValue = 0;
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Number n = sg.get(x, y);
                if (n != null && n.getValue() != 0) {
                    Tile t = new Tile(y, x, n.getValue());
                    int value = snakiness(t, null);
                    if (value > greatestValue) {
                        greatestValue = value;
                        greatestSnakiness = t;
                    }
                }
            }
        }
        
        if (greatestSnakiness != null) {
            System.out.println("Greatest snakiness tile: " + greatestSnakiness.toString());
            System.out.println(snakiness(greatestSnakiness, null));
        }
        
        long end = System.currentTimeMillis();
        long endNano = System.nanoTime();
        System.out.println("Time elapsed: " + (end - start));
        System.out.println("Time elapsed in nano: " + (endNano - startNano) + " == " + ((endNano - startNano) / 1000000000.0) );

    }
    
    public static int snakiness(Tile t, Tile lastTile) { // acount for sqaures of same value! dont stack overflow error!
        Tile[] neighbors = sg.getNeighbors(t);
        if (neighbors.length == 0) return t.getValue();
        
        Tile next = null;
        for (Tile tile : neighbors) {
            if (!tile.equals(lastTile)) {
                if (tile.getValue() >= t.getValue()) {
                    if (next == null) {
                        next = tile;
                    } else if (tile.getValue() < next.getValue()) {
                        next = tile;
                    }
                }
            }
            
            
        }
        
        if (next == null) return t.getValue();
        else return(t.getValue() + snakiness(next, t));
    }
    
    public static int snakinessWithLogging(Tile t, Tile lastTile) {
        Tile[] neighbors = sg.getNeighbors(t);
        if (neighbors.length == 0) {
            System.out.println("Stopping at " + t.toString());
            return t.getValue();
        }
        
        Tile next = null;
        for (Tile tile : neighbors) {
            if (!tile.equals(lastTile)) {
                if (tile.getValue() >= t.getValue()) {
                    if (next == null) {
                        next = tile;
                    } else if (tile.getValue() < next.getValue()) {
                        next = tile;
                    }
                }
            }
            
            
        }
        
        if (next == null) {
            System.out.println("Stopping at " + t.toString());
            return t.getValue();
        } else {
            System.out.println("Going to " + next.toString() + "   From " + t.toString());
            return(t.getValue() + snakinessWithLogging(next, t));
        }
    }
    
    
    
}
