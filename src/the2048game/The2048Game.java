/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Calvin Cramer
 */
public class The2048Game {

    private static Grid g;
    private static ScoreRecorder r;
    public static String rootGameDir = "C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game";
    public static String scoresDir = "C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game\\scores.txt";
    public static String sequencesDir = "C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game\\Sequences";
    public static SequencesWindow sw;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createDirectories();
        r = new ScoreRecorder("C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game\\scores.txt", true);
        
        createSequenceWindow();
    }
    
    private static void createDirectories() {
        File dir = new File("C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game");
        /*
        boolean isDirectoryCreated = dir.mkdir();
        if (isDirectoryCreated) {
          System.out.println("created root folder");
        } else {
          System.out.println("did not create root folder");
        }
        */
        File sequences = new File("C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game\\Sequences");
        /*
        isDirectoryCreated = sequences.mkdir();
        if (isDirectoryCreated) {
          System.out.println("created sequences folder");
        } else {
          System.out.println("did not create sequences folder");
        }
        */
        
    }
    
    public static void newGame() {
        g.newGame();
    }
    
    public static Grid getGrid() {
        return g;
    }
    
    public static boolean move(int n) {
        if (n < 0 || n > 3) System.out.println("Illegal move: " + n);
        
        //System.out.println(n);
        
        return g.swipe(n);
    }
    
    public static void recordScore() {
        if (g.getScore() <= 0) return;
        
        try {
           r.write("" + g.getScore()); 
        } catch (IOException e){
            System.out.println("Error" + e.toString());
        }
        
    }
    
    public static void createGameWindow() {
        GameWindow gw = new GameWindow();
        g = new Grid(gw);
        gw.setVisible(true);
    }
    
     public static void createSequenceWindow() {
        sw = new SequencesWindow();
        g = new Grid(sw);
        sw.addGrid(g);
        sw.setVisible(true);
     }
}
