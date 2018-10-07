/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

/**
 *
 * @author Calvin Cramer
 */
public interface Window {
    public void setScoreText(String s);
    
    public void remove(int x, int y);
    
    public void add(int x, int y, Number num);
    
    public void close();
    
}
