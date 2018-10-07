/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author Calvin Cramer
 */
public class Grid {
    
    private int size;
    private Random r;
    private Number[][] grid;
    private Window window;
    private int score;
    private int moves;
    private int state; //0 for sequences, 1 for normal game, 2 for unknown
    
    
    public Grid(Window w) {
        this.window = w;
        
        if (window instanceof SequencesWindow) state = 0;
        else if (window instanceof GameWindow) state = 1;
        else state = 2;
        
        r = new Random();
        size = 4;
        score = 0;
        grid = new Number[size][size];
        
        if (state != 0) {
            addRandom();
            addRandom();
        }
        
        updateDisplay();
        moves = 0;
    }
    
    /**
     * Swipes number across grid
     * @param direction 0 for up, 1 for right, 2 for down, 3 for left
     * @return boolean true if anything moved
     */
    public boolean swipe(int direction) {
        boolean moved = false;
        
        if (direction == 0) {
            //up
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (grid[y][x] != null) {
                        boolean done = false;
                        for (int j = y; j > 0 && !done; j--) {
                            if (grid[j - 1][x] == null) {
                                grid[j - 1][x] = grid[j][x];
                                grid[j][x] = null;
                                moved = true;
                            } else if (grid[j - 1][x].hasSameValue(grid[j][x]) && grid[j - 1][x].getChanged() == false && grid[j][x].getChanged() == false) {
                                grid[j - 1][x] = new Number(grid[j - 1][x].getValue() * 2);
                                grid[j - 1][x].setChanged(true);
                                grid[j][x] = null;
                                score += grid[j - 1][x].getValue();
                                moved = true;
                            } else {
                                done = true;
                            }
                        }

                    }
                }
            }
        } else if (direction == 1) {
            //right
            for (int k = 0; k < size; k++) {
                for (int i = size - 1; i >= 0; i--) {
                    if (grid[k][i] != null) {
                        boolean done = false;
                        for (int x = i; x + 1 < size && !done; x++) {
                            if (grid[k][x + 1] == null) {
                                grid[k][x + 1] = grid[k][x];
                                grid[k][x] = null;
                                moved = true;
                            } else if (grid[k][x + 1].hasSameValue(grid[k][x]) && grid[k][x + 1].getChanged() == false && grid[k][x].getChanged() == false) {
                                grid[k][x + 1] = new Number(grid[k][x + 1].getValue() * 2);
                                grid[k][x + 1].setChanged(true);
                                grid[k][x] = null;
                                moved = true;
                                score += grid[k][x + 1].getValue();
                            } else {
                                done = true;
                            }
                        }

                    }
                }
            }
        } else if (direction == 2) {
            //down
            for (int x = 0; x < size; x++) {
                for (int y = size - 1; y >= 0; y--) {
                    if (grid[y][x] != null) {
                        boolean done = false;
                        for (int j = y; j + 1 < size && !done; j++) {
                            if (grid[j + 1][x] == null) {
                                grid[j + 1][x] = grid[j][x];
                                grid[j][x] = null;
                                moved = true;
                            } else if (grid[j + 1][x].hasSameValue(grid[j][x]) && grid[j + 1][x].getChanged() == false && grid[j][x].getChanged() == false) {
                                grid[j + 1][x] = new Number(grid[j + 1][x].getValue() * 2);
                                grid[j + 1][x].setChanged(true);
                                grid[j][x] = null;
                                moved = true;
                                score += grid[j + 1][x].getValue();
                            } else {
                                done = true;
                            }
                        }

                    }
                }
            }
        } else if (direction == 3) {
            //left
            for (int k = 0; k < size; k++) {
                for (int i = 0; i < size; i++) {
                    if (grid[k][i] != null) {
                        boolean done = false;
                        for (int x = i; x > 0 && !done; x--) {
                            if (grid[k][x - 1] == null) {
                                grid[k][x - 1] = grid[k][x];
                                grid[k][x] = null;
                                moved = true;
                            } else if (grid[k][x - 1].hasSameValue(grid[k][x]) && grid[k][x - 1].getChanged() == false && grid[k][x].getChanged() == false) {
                                grid[k][x - 1] = new Number(grid[k][x - 1].getValue() * 2);
                                grid[k][x - 1].setChanged(true);
                                grid[k][x] = null;
                                moved = true;
                                score += grid[k][x - 1].getValue();
                            } else {
                                done = true;
                            }
                        }

                    }
                }
            }
        } else {
            System.out.println("Invalid movement");
        }  
        
        removeChangedTags();
        updateDisplay();
        if(moved) addRandom();
        else return false;

        return true;
    }
    
    private void updateDisplay() {
        window.setScoreText("" + score);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[y][x] == null) {
                    window.remove(x, y);
                } else {
                    window.add(x, y, grid[y][x]);
                }
            }
        }
        
    }
    
    private void removeChangedTags() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[y][x] != null) {
                    grid[y][x].setChanged(false);
                }
            }
        }
    }
    
    public void addRandom() {
        if (!hasSpace()) {
            if (!checkForGameOver()) {
                gameOver();
            }
        }
        Number n = new Number();
        boolean done = false;
        
        while (!done)
        {
            int x = r.nextInt(size);
            int y = r.nextInt(size);

            if (grid[x][y] == null) {
                grid[x][y] = n;
                done = true;
            }
        
        }
        updateDisplay();
        
        if (!hasSpace()) {
            if (!checkForGameOver()) {
                gameOver();
            }
        }
    }
    
    private void clearBoard() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                window.remove(x, y);
                grid[y][x] = null;
            }
        }
    }
    
    private boolean checkForGameOver() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y <= 2; y++) {
                if (grid[y][x].getValue() == grid[y + 1][x].getValue()) return true;
            }
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x <= 2; x++) {
                if (grid[y][x].getValue() == grid[y][x + 1].getValue()) return true;
            }
        }
        return false;
    }
    
    /**
     * Game over by no more moves
     */
    public void gameOver() {
        if (state == 1) {
            //Collect score and store it
            The2048Game.recordScore();

            //Ask if play again
            Component frame = new JFrame();
            int n = JOptionPane.showConfirmDialog(frame , "Score: " + score + "\nPlay again?", "GAME OVER!", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                clearBoard();
                addRandom();
                addRandom();
            } else {
                window.close();
                System.exit(0);
            }

            window.setScoreText("0");
        } else if (state == 0) {
            SequencesWindow.gameOver();
        }
        
    }
    
    /**
     * User pressed "new game" button
     */
    public void newGame() {
        if (state == 1) The2048Game.recordScore();
        
        clearBoard();
        addRandom();
        addRandom();
        window.setScoreText("0");
        score = 0;
    }
    
    public boolean hasSpace() {
        boolean space = false;
        
        for (int y = 0; !space && y < size; y++) {
            for (int x = 0; !space && x < size; x++) {
                if (grid[y][x] == null) {
                    space = true;
                }
            }
        }
        
        return space;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (grid[y][x] == null) {
                    s += " _ ";
                } else {
                    s += " " + grid[y][x].getValue() + " ";
                }
            }
            s += "\n";
        }
        return s;
    }
    
    /**
     * for debugging
     * @param x coord
     * @param y coord
     * @param n 
     */
    public void add(int x, int y, int n) {
        grid[y][x] = new Number(n);
    }

    public int getScore() {
        return score;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return the number at the specified place, and a Number of 0 if null
     */
    public Number get(int x, int y) {
        //if (grid[y][x] == null) return new Number(0);
        return grid[y][x];
    }
    
    public Number getHighestNumber() {
        Number highest = grid[0][0];
        
        for (int y = 0; y <= 3; y++) {
            for (int x = 0; x <= 3; x++) {
                if (grid[y][x] != null && grid[y][x].getValue() > highest.getValue()) {
                    highest = grid[y][x];
                }
            }
        }
        
        if (highest == null) return new Number(0);
        return highest;
    }
    
    public Grid createCopy() {
        Grid g = new Grid(null);
        for(int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                g.add(x, y, this.get(x, y).getValue());
            }
        }
        return g;
    }
    
    public Tile[] getHighestTiles() {
        if (grid == null) throw new java.util.MissingResourceException("NO GRID", "Grid", null);
        
        Number n = this.get(0, 0);
        ArrayList<Tile> highestNumbers = new ArrayList<>();
        highestNumbers.add(new Tile(0, 0, 0)); //dummy tile
        
        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 3; y++) {
                n = this.get(x, y);
                if (n != null && n.getValue() > highestNumbers.get(0).getValue()) {
                    highestNumbers.clear();
                    highestNumbers.add(new Tile(y, x, n.getValue()));
                } else if (n != null && n.getValue() == highestNumbers.get(0).getValue()) {
                    highestNumbers.add(new Tile(y, x, n.getValue()));
                }
                    
            }
        }
        return highestNumbers.toArray(new Tile[1]);
    }
    
    public Tile[] getNeighbors(Tile t) {
        if (t == null) throw new IllegalArgumentException("field t is null");
        
        int y = t.getY();
        int x = t.getX();
        int n = t.getValue();        
        
        Tile[] tiles = new Tile[4];
        tiles[0] = new Tile(y - 1, x, n);
        tiles[1] = new Tile(y, x + 1, n);
        tiles[2] = new Tile(y + 1, x, n);
        tiles[3] = new Tile(y, x - 1, n);

        ArrayList<Tile> neighbors = new ArrayList<>();
        
        for (Tile obj : tiles ) {
            if (obj.getX() >= 0 && obj.getX() <= 3 && obj.getY() >= 0 && obj.getY() <= 3) {
                neighbors.add(obj);
            }
        }
        
        Tile[] neigh = new Tile[neighbors.size()];
        return neighbors.toArray(neigh);
                
    }
    //end of class
}
