/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

import java.util.ArrayList;
import java.util.Random;

public class AdvancedSequence {
    
    public AdvancedSequence(Grid g, int type) {
        this.g = g;
        this.type = type;
        
        numMovesUp = 0;
        numMovesRight = 0;
        numMovesDown = 0;
        numMovesLeft = 0;
    }
    
    public void setGrid(Grid g) {
        this.g = g;
    }
    
    public int nextMove() {
        if (g == null) throw new java.util.MissingResourceException("NO GRID", "Grid", null);
        int move = 0;
        
        switch(type) {
            case 0: move = sequence0(); break;
            case 1: move = sequence1(); break;
            case 2: move = sequence2(); break;
                
        }
        
        lastMove = move;
        
        return move;
    }
    
    public void gameOver() {
        //reset cerain fields
        numMovesUp = 0;
        numMovesRight = 0;
        numMovesDown = 0;
        numMovesLeft = 0;
    }
    
    //==================
    //==================================================
    //======================================================================================
    // sequences
    //======================================================================================
    //==================================================
    //==================
    
    /**
     * A sequence that matches the highest possible tiles with a preference of going right and down 
     * If it can't move right or down, it will move up or right randomly
     * @return the move direction
     */
    public int sequence0() {
        ArrayList<Group> matches = this.getMatches();
        
        
       //select best pair
        if (matches.isEmpty()) {
            
            
            boolean canMoveDown = this.canGoDown();
            boolean canMoveLeft = this.canGoLeft();
            
            if (canMoveDown && canMoveLeft) {
                int n = r.nextInt(2);
                if (n == 1) {
                    return 2; //down
                } else {
                    return 3; //left
                }
            } else if (canMoveDown) {
                return 2; //down
            } else if (canMoveLeft) {
                return 3; //left
            } else {
                return r.nextInt(2); //0 or 1 = up or right
            }

        } else {
            Group greatestGroup = matches.get(0);

            for (Group gr : matches) {
                if (greatestGroup == null) greatestGroup = gr;
                if (gr.getNum1().getValue() > greatestGroup.getNum1().getValue()) greatestGroup = gr;
            }
            
            return greatestGroup.getDirection();
        }
        
    }
    
    /**
     * Tries to keep the highest tile in a corner
     *  If highest tile isn't in corner, will try to get highest in a corner
     * @return 
     */
    public int sequence1() {
        
        return r.nextInt(4);
    }
    
    /**
     * Calculates the benefit of swiping each direction by doing many tests on the grid
     * @return 
     */
    public int sequence2() {
        int[] scores = { 0, 0, 0, 0 };
        
        System.out.println(g.toString());

        SimpleGrid sg = new SimpleGrid();

        //tests
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //the score that will be gained from moving in that direction
        //works
        final float GAIN_IN_SCORE_MULTIPLIER = 1.0f;
            int beforeScore;
            int afterScore;
            
            for (int i = 0; i < 4; i++) { // for each direction
                sg.setGrid(g);
                beforeScore = sg.getScore();
                sg.swipe(i);
                afterScore = sg.getScore();
                scores[i] += (afterScore - beforeScore) * GAIN_IN_SCORE_MULTIPLIER;
            }
            /*
            System.out.println("Gain in up score: " + scores[0]);
            System.out.println("Gain in right score: " + scores[1]);
            System.out.println("Gain in down score: " + scores[2]);
            System.out.println("Gain in left score: " + scores[3]);
            
            
            if (scores[0] != scores[2]) {
                System.out.println("UP AND DOWN SCORES AREN'T EQUAL!: " + scores[0] + "   " + scores[2]);
            }
            if (scores[1] != scores[3]) {
                System.out.println("RIGHT AND LEFT SCORES AREN'T EQUAL!: " + scores[1] + "   " + scores[3]);
            }
            */
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //if moving will cause the highest tile in a corner to move out of that corner
        
            sg.setGrid(g);
            Tile[] highest = sg.getHighestTiles();

            /*
            Tile ht = highest[0];
            System.out.println(ht.toString());
            System.out.println((sg.willMove(ht, UP) ? "can" : "cannot" ) + " move up");
            System.out.println((sg.willMove(ht, RIGHT) ? "can" : "cannot" ) + " move right");
            System.out.println((sg.willMove(ht, DOWN) ? "can" : "cannot" ) + " move down");
            System.out.println((sg.willMove(ht, LEFT) ? "can" : "cannot" ) + " move left");
            */
            for (Tile t : highest) {
                
                if (sg.get(t.getX(), t.getY()).getValue() != 0 && isInCorner(t)) {
                    for (int i = 0; i < 4; i++) { //for each direction
                        if (!sg.willMove(t, i)) {
                            scores[i] += 2048; //weighting?
                        } else {
                            scores[i] -= 2048;
                        }
                    }
                } else {
                    for (int i = 0; i < 4; i++) { //for each direction
                        sg.setGrid(g);
                        sg.swipe(i);
                        if (isInCorner(t)) {
                            scores[i] += 2048; // weighting?
                        }
                    }
                }
            }
                
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
        //the number of matches made 
        //works
        final int MATCHES_MADE_MULTIPLIER = 8;
            sg.setGrid(g);
            int tilesBefore = sg.getNumFiledSpaces();
            int tilesAfter;
            
            for (int i = 0; i < 4; i++) { // for each direction
                sg.setGrid(g);
                sg.swipe(i);
                tilesAfter = sg.getNumFiledSpaces();
                scores[i] += (tilesBefore - tilesAfter) * MATCHES_MADE_MULTIPLIER;
                //System.out.println((tilesBefore - tilesAfter) + " matches made going direction " + i);
            }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
        //the matches it will make after it slides that direction
        //doesnt't work, figure out what to actually test here
        final float NEXT_MOVE_MATCHES_MULTIPLIER = 0.00f; //
            int[] matchesScores = new int[4];
            for (int x = 0; x < 4; x++) { //for each direction
                sg.setGrid(g); // reset grid
                sg.swipe(x);
                ArrayList<Group> matches = sg.getMatches();
                int sumScoreOfMatches = 0;
                for(Group group : matches) {
                    sumScoreOfMatches += group.getNum1().getValue() + group.getNum2().getValue();
                }
                matchesScores[x] = sumScoreOfMatches;
            }
            
            //System.out.println("Next move matches score moving up: " + matchesScores[0]);
            //System.out.println("Next move matches score moving right: " + matchesScores[1]);
            //System.out.println("Next move matches score moving down: " + matchesScores[2]);
            //System.out.println("Next move matches score moving left: " + matchesScores[3]);
            
            scores[0] += matchesScores[0] * NEXT_MOVE_MATCHES_MULTIPLIER;
            scores[1] += matchesScores[1] * NEXT_MOVE_MATCHES_MULTIPLIER;
            scores[2] += matchesScores[2] * NEXT_MOVE_MATCHES_MULTIPLIER;
            scores[3] += matchesScores[3] * NEXT_MOVE_MATCHES_MULTIPLIER;   

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //the amount of tiles a direction moves
        
            int[] tilesMoved = new int[4];
            for(int i = 0; i < 4; i++) { //for each direction
                sg.setGrid(g);
                tilesMoved[i] = sg.swipeAndCountMoves(i);
                
            }
            
            System.out.println("Tiles going to be moved up: " + tilesMoved[0]);
            System.out.println("Tiles going to be moved right: " + tilesMoved[1]);
            System.out.println("Tiles going to be moved down: " + tilesMoved[2]);
            System.out.println("Tiles going to be moved left: " + tilesMoved[3]);
            
            
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //the change in snakiness?
             
            sg.setGrid(g);
            int totalSnakiness = this.computeSnakiness(sg);
            System.out.println("Snakiness:" + totalSnakiness);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //choose the best direction based off of scores

        //disqualify any directions that wont move any tiles
        for (int i = 0; i < 4; i++) { //for each direction
            sg.setGrid(g);
            if (!sg.swipe(i)) {
                System.out.println("Moving: " + i + " WONT move anything");
                scores[i] = -999999999; //because some score may be negative
            } else {
                System.out.println("Moving: " + i + " WILL move stuff");
            }
        }
        
        System.out.println("0 Up: " + scores[0]);
        System.out.println("1 Right: " + scores[1]);
        System.out.println("2 Down: " + scores[2]);
        System.out.println("3 Left: " + scores[3]);
        
        int greatestIndex = 0;

        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > scores[greatestIndex]) greatestIndex = i;
        }
        
        ArrayList<Integer> greatestIndecies = new ArrayList<>();
        greatestIndecies.add(greatestIndex);
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] == scores[greatestIndecies.get(0)] && i != greatestIndecies.get(0)) {
                greatestIndecies.add(i);
            }
        } // greatestIndecies new holds the greatest indexes
        
        int moving;
        if (greatestIndecies.size() > 1) {
            moving = greatestIndecies.get(r.nextInt(greatestIndecies.size()));
        } else {
            moving = greatestIndecies.get(0);
        }
        
        System.out.println("Moving: " + moving);
        System.out.println();
        
        //increment moving fields
        switch(moving) {
            case 0: numMovesUp++; break;
            case 1: numMovesRight++; break;
            case 2: numMovesDown++; break;
            case 3: numMovesLeft++; break;
        }
        
        
        
        return moving;
    }
    //==================
    //==================================================
    //======================================================================================
    // helper methods
    //======================================================================================
    //==================================================
    //==================
    
    private boolean canGoUp() {
        if (g == null) throw new java.util.MissingResourceException("NO GRID", "Grid", null);
        
        //for each column
        for (int x = 0; x <= 3; x++) {
            //for rows 1 to 3 from the bottom
            for (int y = 3; y >= 1; y--) {
                if (g.get(x, y).getValue() != 0 && g.get(x, y - 1).getValue() == 0)
                    return true;
            }
        }
        return false;
    }
    
    private boolean canGoRight() {
        if (g == null) throw new java.util.MissingResourceException("NO GRID", "Grid", null);
        
        //for each row
        for (int y = 0; y <= 3; y++) {
            //for columns 0 - 2 from the left
            for (int x = 0; x <= 2; x++) {
                if (g.get(x, y).getValue() != 0 && g.get(x + 1, y).getValue() == 0)
                    return true;
            }
        }
        return false;
    }
    
    private boolean canGoDown() {
        if (g == null) throw new java.util.MissingResourceException("NO GRID", "Grid", null);
        
        //row each column
        for (int x = 0; x <= 3; x++) {
            //for rows 0 - 2 from the top
            for (int y = 0; y <= 2; y++) {
                if (g.get(x, y).getValue() != 0 && g.get(x, y + 1).getValue() == 0)
                    return true;
            }
        }
        return false;
    }
    
    private boolean canGoLeft() {
        if (g == null) throw new java.util.MissingResourceException("NO GRID", "Grid", null);
        
        //for each row
        for (int y = 0; y <= 3; y++) {
            //for columns 1 - 3 from the right
            for (int x = 3; x >= 1; x--) {
                if (g.get(x, y).getValue() != 0 && g.get(x - 1, y).getValue() == 0)
                    return true;
            }
        }
        return false;
    }
    
    private boolean isInCorner(int x, int y) {
        return ((x == 0 && y == 0) ||
                (x == 3 && y == 0) ||
                (x == 3 && y == 3) ||
                (x == 0 && y == 3));
    }
    
    private boolean isInCorner(Tile t) {
        return isInCorner(t.getX(), t.getY());
    }
    
    private int getSumOfAllTiles() {
        int sum = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                sum += g.get(x, y).getValue();
            }
        }
        return sum;
    }
    
    private ArrayList<Group> getMatches() {
        ArrayList<Group> matches = new ArrayList<>();
        
        //left - right
        for (int y = 0; y <= 3; y++) {
            for (int x = 0; x <= 2; x++) {
                if (g.get(x, y).getValue() != 0) {
                    
                    for (int p = x + 1; p <= 3; p++) {
                        if ( g.get(p, y).getValue() != 0 && g.get(p, y).hasSameValue(g.get(x, y)) ) {
                            matches.add( new Group(g.get(x, y), g.get(p, y), 3) );
                            
                            //System.out.println("(" + x + ", " + y + ") - (" + p + ", " + y + ") right");
                            
                        } else if ( g.get(p, y).getValue() != g.get(x, y).getValue()) {
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
                if (g.get(x, y).getValue() != 0) {
                    
                    for (int p = y + 1; p <= 3; p++) {
                        if ( g.get(x, p).getValue() != 0 && g.get(x, p).hasSameValue(g.get(x, y)) ) {
                            matches.add( new Group(g.get(x, y), g.get(x, p), 2) );
                            
                            //System.out.println("(" + x + ", " + y + ") - (" + x + ", " + p + ") down");
                            
                        } else if ( g.get(x, p).getValue() != g.get(x, y).getValue()) {
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
    
    private int computeSnakiness(SimpleGrid sg) {
        int total = 0;
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (sg.get(x, y).getValue() != 0) 
                    total += this.snakiness(new Tile(y, x, sg.get(x, y).getValue()) , null, sg);
            }
        }
        
        
        return total;
        
    }
    
    private int snakiness(Tile t, boolean[][] moved, SimpleGrid sg) {
        Tile[] neighbors = sg.getNeighbors(t);
        if (neighbors.length == 0) return t.getValue();
        
        if (moved == null) moved = new boolean[4][4];
        
        Tile next = null;
        for (Tile tile : neighbors) {
            if (moved[ tile.getY() ][ tile.getX() ] == false) {
                moved[ tile.getY() ][ tile.getX() ] = true;
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
        else return(t.getValue() + snakiness(next, moved, sg));
    }
    
    

    //==================
    //==================================================
    //======================================================================================
    // fields
    //======================================================================================
    //==================================================
    
    private final int type;
    private Grid g;
    private static final Random r = new Random();
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private int lastMove;
    
    private int numMovesUp;
    private int numMovesRight;
    private int numMovesDown;
    private int numMovesLeft;
}
