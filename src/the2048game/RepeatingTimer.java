/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Calvin Cramer
 */
public class RepeatingTimer extends TimerTask{
 
    @Override
    public void run() {
        //System.out.println("beep");
        SequencesWindow.nextMove();
    }
    
    public static void main(String args[]) {
        TimerTask timerTask = new RepeatingTimer();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 1 * 500);
        
        System.out.println("Task Begin");
        
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("Task End");
    }
}
