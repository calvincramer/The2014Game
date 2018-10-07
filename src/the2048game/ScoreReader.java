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
public class ScoreReader {
    
    public ScoreReader() {
        try {
            File inFile = new File(The2048Game.scoresDir);
            in = new Scanner(inFile);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    
    public int[] getTopScores(int numberOfScores) {
        ArrayList<Integer> scores = new ArrayList<Integer>();
        while (in.hasNextInt()) {
            int n = in.nextInt();
            if (scores.isEmpty()) {
                scores.add(n);
            } else {
                int index = 0;
                while(index < scores.size() && n < scores.get(index) ) {
                    index++;
                }
                scores.add(index, n);
            }
            
        }
        int[] nums = new int[scores.size()];
        for (int a = 0; a < scores.size(); a++) {
            nums[a] = scores.get(a);
        }

        return nums;
    }
    
    private Scanner in;
}
