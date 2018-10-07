/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 *
 * @author Calvin Cramer
 */
public class SequenceReader {
    public SequenceReader(File aFile) {
        f = aFile;
        words = new ArrayList<>();
        try {
            in = new Scanner(f);
            while (in.hasNext()) {
                words.add(in.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        index = 0;
    }
    
    /**
     * Return the next move in the file
     * @return -1 if invalid move read, 0 if up, 1 if right, 2 if down, 3 if left
     */
    public int nextMove() {
        
        String word = nextMoveWord();
        int n;
        
        switch (word) {
            case "UP": n = 0; break;
            case "RIGHT": n = 1; break;
            case "DOWN": n = 2; break;
            case "LEFT": n = 3; break;
            default: n = -1; break;
        }
        
        return n;

        
    }
    
    private String nextMoveWord() {
        int n = index;
        incrementIndex();
        return words.get(n);
    }
    
    private void incrementIndex() {
        index++;
        if (index >= words.size()) index = 0;
    }
    
    private File f;
    private Scanner in;
    private ArrayList<String> words;
    private int index;
}
