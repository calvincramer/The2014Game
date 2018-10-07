/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author Calvin Cramer
 */
public class UserInputListener implements KeyListener{
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key >= 37 && key <= 40) {
            int n = 0;
            if (key == 38) n = 0; //up
            if (key == 39) n = 1; //right
            if (key == 40) n = 2; //down
            if (key == 37) n = 3; //left
            The2048Game.move(n);
        } else {
            int n = 0;
            switch (key) {
                case 87: n = 0; break;
                case 68: n = 1; break;
                case 83: n = 2; break;
                case 65: n = 3; break;
            }
            The2048Game.move(n);
        }
    }
    @Override
    public void keyReleased(KeyEvent arg0) {
        //System.out.println("key released: " + arg0.getKeyCode());
    }
    
    @Override
    public void keyTyped(KeyEvent arg0) {
        //System.out.print(arg0.getKeyChar());
    }
    
}
