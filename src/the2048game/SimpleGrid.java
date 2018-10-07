package the2048game;
import java.util.ArrayList;
import java.util.Random;

/**
 * A simple version of Grid that doesn't support game overs. To be used for sequences. 
 * @author Calvin Cramer
 */
public class SimpleGrid {
    
    private int size;
    private Random r;
    private Number[][] grid;

    private int score;


    
    
    public SimpleGrid() {
        
        score = 0;
        r = new Random();
        size = 4;
        grid = new Number[size][size];

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

        return moved;
    }

    /**
     * counts the number of tiles moved
     * @param direction
     * @return 
     */
    public int swipeAndCountMoves(int direction) {
        int numMoves = 0;
        
        if (direction == 0) {
            //up
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (grid[y][x] != null) {
                        boolean done = false;
                        for (int j = y; j > 0 && !done; j--) {
                            if (grid[j - 1][x] == null) {
                                grid[j - 1][x] = grid[j][x];
                                
                                if (grid[j - 1][x].getMoved() == false) {
                                    grid[j - 1][x].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[j][x] = null;
                            } else if (grid[j - 1][x].hasSameValue(grid[j][x]) && grid[j - 1][x].getChanged() == false && grid[j][x].getChanged() == false) {
                                grid[j - 1][x] = new Number(grid[j - 1][x].getValue() * 2);
                                grid[j - 1][x].setChanged(true);
                                
                                if (grid[j][x].getMoved() == false) {
                                    grid[j][x].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[j][x] = null;
                                score += grid[j - 1][x].getValue();
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
                                
                                if (grid[k][x + 1].getMoved() == false) {
                                    grid[k][x + 1].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[k][x] = null;
                            } else if (grid[k][x + 1].hasSameValue(grid[k][x]) && grid[k][x + 1].getChanged() == false && grid[k][x].getChanged() == false) {
                                grid[k][x + 1] = new Number(grid[k][x + 1].getValue() * 2);
                                grid[k][x + 1].setChanged(true);
                                
                                if (grid[k][x].getMoved() == false) {
                                    grid[k][x].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[k][x] = null;
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
                                
                                if (grid[j + 1][x].getMoved() == false) {
                                    grid[j + 1][x].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[j][x] = null;
                            } else if (grid[j + 1][x].hasSameValue(grid[j][x]) && grid[j + 1][x].getChanged() == false && grid[j][x].getChanged() == false) {
                                grid[j + 1][x] = new Number(grid[j + 1][x].getValue() * 2);
                                grid[j + 1][x].setChanged(true);
                                
                                if (grid[j][x].getMoved() == false) {
                                    grid[j][x].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[j][x] = null;
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
                                
                                if (grid[k][x - 1].getMoved() == false) {
                                    grid[k][x - 1].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[k][x] = null;
                            } else if (grid[k][x - 1].hasSameValue(grid[k][x]) && grid[k][x - 1].getChanged() == false && grid[k][x].getChanged() == false) {
                                grid[k][x - 1] = new Number(grid[k][x - 1].getValue() * 2);
                                grid[k][x - 1].setChanged(true);
                                
                                if (grid[k][x].getMoved() == false) {
                                    grid[k][x].setMoved(true);
                                    numMoves++;
                                }
                                
                                grid[k][x] = null;
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
        removeMovedTags();

        return numMoves;
    }
    
    private void removeMovedTags() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[y][x] != null) {
                    grid[y][x].setMoved(false);
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
        if (x < 0 || y < 0 || x > 3 || y > 3) return new Number(0);
        
        if (grid[y][x] == null) return new Number(0);
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
    
    public void setGrid(Grid g) {
        for(int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Number n = g.get(x, y);
                if (n == null) grid[y][x] = null;
                else grid[y][x] = n;
            }
        }
        score = g.getScore();
        
    }
    
    
    /**
     * NEED TO MAKE SURE WORKS PROPERLY
     * @return 
     */
    public ArrayList<Group> getMatches() {
        ArrayList<Group> matches = new ArrayList<>();
        
        //left - right
        for (int y = 0; y <= 3; y++) {
            for (int x = 0; x <= 2; x++) {
                if (this.get(x, y).getValue() != 0) {
                    
                    for (int p = x + 1; p <= 3; p++) {
                        if ( this.get(p, y).getValue() != 0 && this.get(p, y).hasSameValue(this.get(x, y)) ) {
                            Group g = new Group(this.get(x, y), this.get(p, y), 3);
                            matches.add(g);
                            
                            //System.out.println("(" + x + ", " + y + ") - (" + p + ", " + y + ") right");
                            
                        } else if ( this.get(p, y).getValue() != this.get(x, y).getValue()) {
                            break;
                        } else {
                            //do nothing if null (or zero)
                        }
                    }
                }
            }
        }
        //up - down
        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 2; y++) {
                if (this.get(x, y).getValue() != 0) {
                    
                    for (int p = y + 1; p <= 3; p++) {
                        if ( this.get(x, p).getValue() != 0 && this.get(x, p).hasSameValue(this.get(x, y)) ) {
                            Group g = new Group(this.get(x, y), this.get(x, p), 2);
                            matches.add(g);
                            
                            //System.out.println("(" + x + ", " + y + ") - (" + x + ", " + p + ") down");
                            
                        } else if ( this.get(x, p).getValue() != this.get(x, y).getValue()) {
                            break;
                        } else {
                            //do nothing if null (or zero)
                        }
                    }
                }
            }
        }
        return matches;
    }
    
    public int getNumFiledSpaces() {
        int n = 0;
        
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                if (grid[y][x] != null) n++;
            }
            
        }
        return n;
    }
    
    public int getNumEmptySpaces() {
        int n = 0;
        
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++) {
                if (grid[y][x] == null) n++;
            }
            
        }
        return n;
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
    
    public boolean willMove(int x, int y, int direction) {
        //works
        if (grid[y][x] == null) throw new NullPointerException("The coordinate (" + y + ", " + x + ") is null");
        if (direction < 0 || direction > 3) throw new IllegalArgumentException("field [direction] must be within 0 to 3 inclusive");
        

        boolean canMakePair = true;
        
        switch (direction) {
            case 0://up
                for (int row = y - 1; row >= 0; row--) {
                    if (grid[row][x] == null) return true;
                    else if (grid[row][x].getValue() == grid[y][x].getValue() && canMakePair) return true;
                    else {
                        canMakePair = false;
                        if (grid[row + 1][x].getValue() == grid[row][x].getValue()) return true;
                    }
                }
            break;
            case 1://right
                for (int col = x + 1; col < 4; col++) {
                    if (grid[y][col] == null) return true;
                    else if (grid[y][col].getValue() == grid[y][x].getValue() && canMakePair) return true;
                    else {
                        canMakePair = false;
                        if (grid[y][col - 1].getValue() == grid[y][col].getValue()) return true;
                    }
                }
            break;
            case 2://down
                for (int row = y + 1; row < 4; row++) {
                    if (grid[row][x] == null) return true;
                    else if (grid[row][x].getValue() == grid[y][x].getValue() && canMakePair) return true;
                    else {
                        canMakePair = false;
                        if (grid[row - 1][x].getValue() == grid[row][x].getValue()) return true;
                    }
                }
            break;
            case 3://left
                for (int col = x - 1; col >= 0; col--) {
                    if (grid[y][col] == null) return true;
                    else if (grid[y][col].getValue() == grid[y][x].getValue() && canMakePair) return true;
                    else {
                        canMakePair = false;
                        if (grid[y][col + 1].getValue() == grid[y][col].getValue()) return true;
                    }
                }
            break;
        }
        return false;
    }
    
    public boolean willMove(Tile t, int direction) {
        return willMove(t.getX(), t.getY(), direction);
    }
    
    public Tile[] getNeighbors(Tile t) {
        if (t == null) throw new IllegalArgumentException("field t is null");
        
        int y = t.getY();
        int x = t.getX();
        int n = t.getValue();        
        
        Tile[] tiles = new Tile[4];
        tiles[0] = new Tile(y - 1, x, this.get(x, y - 1).getValue());
        tiles[1] = new Tile(y, x + 1, this.get(x + 1, y).getValue());
        tiles[2] = new Tile(y + 1, x, this.get(x, y + 1).getValue());
        tiles[3] = new Tile(y, x - 1, this.get(x - 1, y).getValue());

        ArrayList<Tile> neighbors = new ArrayList<>();
        
        for (Tile obj : tiles ) {
            if (obj.getX() >= 0 && obj.getX() <= 3 && obj.getY() >= 0 && obj.getY() <= 3) {
                neighbors.add(obj);
            }
        }
        
        Tile[] neigh = new Tile[neighbors.size()];
        return neighbors.toArray(neigh);
                
    }
    //////////////////
    

}
