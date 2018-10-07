/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author Calvin Cramer
 */
public class ScoreRecorder {
    private String path;
    private boolean wantsToAppend = false;
    
    public ScoreRecorder(String path, boolean appends) {
        this.path = path;
        wantsToAppend = appends;
    }
    
    public void write(String s) throws IOException {
        FileWriter writer = new FileWriter(path, wantsToAppend);
        PrintWriter printer = new PrintWriter(writer);
        
        printer.println(s);
        printer.close();
    }
}
