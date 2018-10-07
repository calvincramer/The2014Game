/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;
import javax.swing.JFileChooser;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Calvin Cramer
 */
public class SequencesWindow 
    extends javax.swing.JFrame 
    implements Window {

    /**
     * Creates new form SequencesWindow
     */
    public SequencesWindow() {
        initComponents();
        
        labelGrid = new JLabel[4][4];
        labelGrid[0][0] = label00;
        labelGrid[1][0] = label10;
        labelGrid[2][0] = label20;
        labelGrid[3][0] = label30;
        labelGrid[0][1] = label01;
        labelGrid[1][1] = label11;
        labelGrid[2][1] = label21;
        labelGrid[3][1] = label31;
        labelGrid[0][2] = label02;
        labelGrid[1][2] = label12;
        labelGrid[2][2] = label22;
        labelGrid[3][2] = label32;
        labelGrid[0][3] = label03;
        labelGrid[1][3] = label13;
        labelGrid[2][3] = label23;
        labelGrid[3][3] = label33;
        
        AbstractBorder line = new TextBubbleBorder(new Color(204, 192, 180), 2, 8, 0);

        for (int a = 0; a <= 3; a++) {
            for (int b = 0; b <= 3; b++) {
                labelGrid[a][b].setBorder(line);
            }
        }
        
        tileColors = new Color[15];
        tileColors[0] = new Color(238, 228, 218); //2 good
        tileColors[1] = new Color(237, 224, 200); //4 good
        tileColors[2] = new Color(242, 177, 121); //8 good
        tileColors[3] = new Color(236, 141,  84); //16 good
        tileColors[4] = new Color(246, 124,  95); //32 good
        tileColors[5] = new Color(234,  89,  55); //64 good
        tileColors[6] = new Color(243, 216, 107); //128 good
        tileColors[7] = new Color(241, 208,  75); //256 good
        tileColors[8] = new Color(237, 200,  80); //512 good
        tileColors[9] = new Color(237, 197,  63); //1024 good
        tileColors[10] =new Color(237, 194,  46); //2048 good
        tileColors[11] =new Color(119, 161,  54); //4096 good
        tileColors[12] =new Color( 45, 179, 136); //8192 good
        tileColors[13] =new Color( 45, 131, 178); //16383 good
        tileColors[14] =new Color( 60,  58,  50); //32768 good
        
        lowNumberColor = new Color(119, 110, 101);
        highNumberColor = Color.WHITE;
        largeFontSize = 44;
        smallFontSize = 32;
        emptyTileColor = new Color(204, 192, 180);
        tileBackgroundColor = emptyTileColor;

        gameThere = false;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = (width / 2) - (this.getWidth() / 2);
        int y = (height / 2) - (this.getHeight() / 2);
        this.setBounds(x, y, this.getWidth(), this.getHeight());
        
        timerTask = new RepeatingTimer();
        timer = new Timer(true);
        
        speed = speedSlider.getValue();
        this.speedSliderMouseDragged(null);
        
        round = 0;
        highestScore = 0;
        averageScore = 0;
        totalOfScores = 0;
        highestTileValue = 0;
        
        sequenceName = "(No Sequence)";
        
        colors = colorsCheckBox.isSelected();
        notMovedCount = 0;
        
        usingSpecialSequence = false;
        advSequence = new AdvancedSequence(The2048Game.getGrid(), TYPE_OF_ADVANCED_SEQUENCE);
        
        specialSequenceCheckBox.setSelected(true);
        this.specialSequenceCheckBoxActionPerformed(null);
        
        doneMoving = true;
        
    }

    private Color getColorFromNumber(Number num) {
        if (colors == false) {
            return tileColors[0];
        }
        Color c;
        switch (num.getValue()) {
            case 2: c = tileColors[0];
                break;
            case 4: c = tileColors[1];
                break;
            case 8: c = tileColors[2];
                break;
            case 16: c = tileColors[3];
                break;
            case 32: c = tileColors[4];
                break;
            case 64: c = tileColors[5];
                break;
            case 128: c = tileColors[6];
                break;
            case 256: c = tileColors[7];
                break;
            case 512: c = tileColors[8];
                break;
            case 1024: c = tileColors[9];
                break;
            case 2048: c = tileColors[10];
                break;
            case 4096: c = tileColors[11];
                break;
            case 8192: c = tileColors[12];
                break;
            case 16383: c = tileColors[13];
                break;
            default: c = tileColors[0];
                break;
        }
        return c;
    }
    
    @Override
    public void add(int x, int y, Number num) {
        String n = "" + num.getValue();
        Color c = getColorFromNumber(num);
        AbstractBorder line = new TextBubbleBorder(c, 2, 8, 0);
        
        JLabel l = labelGrid[y][x];
        l.setText(n);
        l.setBackground(c);
        l.setBorder(line);
        if (num.getValue() >= 1024) l.setFont(new Font("font name", l.getFont().getStyle(), smallFontSize));
        else l.setFont(new Font("font name", l.getFont().getStyle(), largeFontSize));
        
        if (colors == false) {
            l.setForeground(lowNumberColor);
        } else {
            if (num.getValue() >= 8) l.setForeground(highNumberColor);
            else l.setForeground(lowNumberColor);
        }
    }
    
    @Override
    public void remove(int x, int y) {
        AbstractBorder line = new TextBubbleBorder(tileBackgroundColor, 2, 8, 0);
        
        JLabel l = labelGrid[y][x];
        l.setText("");
        l.setBackground(emptyTileColor);
        l.setBorder(line);
    }
    
    @Override
    public void setScoreText(String s) {
        scoreLabel.setText("Score: " + s);
    }
    
    @Override
    public void close() {
        this.setVisible(false);
        this.dispose();
    }
    
    public void addGrid(Grid g) {
        this.g = g;
        advSequence.setGrid(g);
    }
    
    public void setSequence(String s) {
        sequence = s;
        sequenceNameLabel.setText(sequence);
        
    }
    
    public static void nextMove() {
        if (!doneMoving) {
            System.out.println("Not done moving yet!");
            return;
        }
        doneMoving = false;
        
        boolean moved = false; 
                
        if (usingSpecialSequence) {
            moved = The2048Game.move(advSequence.nextMove());
        } else {
            moved = The2048Game.move(reader.nextMove());
        }
        if (!moved) notMovedCount++;
        else notMovedCount = 0;

        if (notMovedCount >= 100) gameOver();
        
        doneMoving = true;
        
    }
    
    public static void gameOver() {
        //make new game
        The2048Game.sw.updateGameOverLabels();
        
        The2048Game.newGame();
        round++;
        
        advSequence.gameOver();
    }
    
 
    
    private void updateGameOverLabels() {
        if (g == null) return;
        
        recentScoresTextArea.insert("" + g.getScore() + "\n", 0);
        if (recentScoresTextArea.getLineCount() > 40) {
            recentScoresTextArea.setText(recentScoresTextArea.getText().substring(0, recentScoresTextArea.getText().length() / 3));
        }
        
        
        if (g.getHighestNumber() != null && g.getHighestNumber().getValue() > highestTileValue) {
            highestTileValue = g.getHighestNumber().getValue();
            highestTileLabel.setText("Highest Tile: " + highestTileValue);
        }
        
        roundLabel.setText("Round: " + round);
        if (g.getScore() > highestScore) {
            highestScore = g.getScore();
            highestScoreLabel.setText("Highest Score: " + highestScore);
        }
        totalOfScores += g.getScore();
        if (round != 0) averageScore = (int) totalOfScores / round;
        else averageScore = g.getScore();
        averageScoreLabel.setText("Average Score: " + averageScore);
        
        //lastScore = g.getScore();
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        label01 = new javax.swing.JLabel();
        label00 = new javax.swing.JLabel();
        label03 = new javax.swing.JLabel();
        label02 = new javax.swing.JLabel();
        label13 = new javax.swing.JLabel();
        label10 = new javax.swing.JLabel();
        label11 = new javax.swing.JLabel();
        label12 = new javax.swing.JLabel();
        label23 = new javax.swing.JLabel();
        label20 = new javax.swing.JLabel();
        label21 = new javax.swing.JLabel();
        label22 = new javax.swing.JLabel();
        label33 = new javax.swing.JLabel();
        label30 = new javax.swing.JLabel();
        label31 = new javax.swing.JLabel();
        label32 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        sequenceTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        recentScoresTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        averageScoreLabel = new javax.swing.JLabel();
        highestTileLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        roundLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        editButton = new javax.swing.JButton();
        sequenceNameLabel = new javax.swing.JLabel();
        highestScoreLabel = new javax.swing.JLabel();
        startStopToggleButton = new javax.swing.JToggleButton();
        stateLabel = new javax.swing.JLabel();
        normalGameButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        numberOfGamesLabel1 = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        speedLabel = new javax.swing.JLabel();
        plusOne = new javax.swing.JButton();
        minusOne = new javax.swing.JButton();
        plusFive = new javax.swing.JButton();
        minusFive = new javax.swing.JButton();
        plusFifty = new javax.swing.JButton();
        minusFifty = new javax.swing.JButton();
        colorsCheckBox = new javax.swing.JCheckBox();
        nextGameButton = new javax.swing.JButton();
        specialSequenceCheckBox = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sequences");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setBackground(new java.awt.Color(187, 173, 160));
        jLayeredPane1.setMaximumSize(new java.awt.Dimension(410, 415));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(410, 415));
        jLayeredPane1.setName(""); // NOI18N
        jLayeredPane1.setOpaque(true);

        label01.setBackground(new java.awt.Color(204, 192, 180));
        label01.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label01.setForeground(new java.awt.Color(51, 51, 51));
        label01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label01.setOpaque(true);

        label00.setBackground(new java.awt.Color(204, 192, 180));
        label00.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label00.setForeground(new java.awt.Color(51, 51, 51));
        label00.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label00.setMaximumSize(new java.awt.Dimension(100, 100));
        label00.setOpaque(true);
        label00.setPreferredSize(new java.awt.Dimension(90, 90));

        label03.setBackground(new java.awt.Color(204, 192, 180));
        label03.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label03.setForeground(new java.awt.Color(51, 51, 51));
        label03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label03.setOpaque(true);

        label02.setBackground(new java.awt.Color(204, 192, 180));
        label02.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label02.setForeground(new java.awt.Color(51, 51, 51));
        label02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label02.setOpaque(true);

        label13.setBackground(new java.awt.Color(204, 192, 180));
        label13.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label13.setForeground(new java.awt.Color(51, 51, 51));
        label13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label13.setOpaque(true);

        label10.setBackground(new java.awt.Color(204, 192, 180));
        label10.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label10.setForeground(new java.awt.Color(51, 51, 51));
        label10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label10.setOpaque(true);

        label11.setBackground(new java.awt.Color(204, 192, 180));
        label11.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label11.setForeground(new java.awt.Color(51, 51, 51));
        label11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label11.setOpaque(true);

        label12.setBackground(new java.awt.Color(204, 192, 180));
        label12.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label12.setForeground(new java.awt.Color(51, 51, 51));
        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setOpaque(true);

        label23.setBackground(new java.awt.Color(204, 192, 180));
        label23.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label23.setForeground(new java.awt.Color(51, 51, 51));
        label23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label23.setOpaque(true);

        label20.setBackground(new java.awt.Color(204, 192, 180));
        label20.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label20.setForeground(new java.awt.Color(51, 51, 51));
        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setOpaque(true);

        label21.setBackground(new java.awt.Color(204, 192, 180));
        label21.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label21.setForeground(new java.awt.Color(51, 51, 51));
        label21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label21.setOpaque(true);

        label22.setBackground(new java.awt.Color(204, 192, 180));
        label22.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label22.setForeground(new java.awt.Color(51, 51, 51));
        label22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label22.setOpaque(true);

        label33.setBackground(new java.awt.Color(204, 192, 180));
        label33.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label33.setForeground(new java.awt.Color(51, 51, 51));
        label33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label33.setOpaque(true);

        label30.setBackground(new java.awt.Color(204, 192, 180));
        label30.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label30.setForeground(new java.awt.Color(51, 51, 51));
        label30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label30.setOpaque(true);

        label31.setBackground(new java.awt.Color(204, 192, 180));
        label31.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label31.setForeground(new java.awt.Color(51, 51, 51));
        label31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label31.setOpaque(true);

        label32.setBackground(new java.awt.Color(204, 192, 180));
        label32.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        label32.setForeground(new java.awt.Color(51, 51, 51));
        label32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label32.setOpaque(true);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label00, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(label01, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label02, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label03, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(label20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label22, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(label30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label31, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label33, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label03, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label02, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label00, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label01, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label22, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label33, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label31, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jLayeredPane1.setLayer(label01, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label00, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label03, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label02, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label23, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label21, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label33, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label30, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label31, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(label32, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        addButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addButton.setText("Make Sequence");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        getContentPane().add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 193, -1));

        openButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        openButton.setText("Open Sequence");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        getContentPane().add(openButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 193, -1));

        sequenceTextArea.setEditable(false);
        sequenceTextArea.setColumns(20);
        sequenceTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sequenceTextArea.setLineWrap(true);
        sequenceTextArea.setRows(5);
        sequenceTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(sequenceTextArea);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 300, 204));

        recentScoresTextArea.setEditable(false);
        recentScoresTextArea.setColumns(10);
        recentScoresTextArea.setRows(5);
        jScrollPane2.setViewportView(recentScoresTextArea);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 469, 230, 143));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Recent Scores:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 432, 166, 26));

        averageScoreLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        averageScoreLabel.setText("Average Score: 0");
        getContentPane().add(averageScoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 291, 26));

        highestTileLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        highestTileLabel.setText("Highest Tile: 0");
        getContentPane().add(highestTileLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 470, 291, 26));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 541, -1, -1));

        scoreLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        scoreLabel.setText("Score: 0");
        getContentPane().add(scoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 464, 162, 26));

        roundLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        roundLabel.setText("Round: 0");
        getContentPane().add(roundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 432, 162, 26));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 444, -1, 175));

        editButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editButton.setText("Edit Sequence");
        editButton.setEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        getContentPane().add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 300, -1));

        sequenceNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sequenceNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sequenceNameLabel.setText("(No Sequence)");
        getContentPane().add(sequenceNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 300, -1));

        highestScoreLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        highestScoreLabel.setText("Highest Score: 0");
        getContentPane().add(highestScoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 410, 291, 26));

        startStopToggleButton.setText("Start");
        startStopToggleButton.setEnabled(false);
        startStopToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStopToggleButtonActionPerformed(evt);
            }
        });
        getContentPane().add(startStopToggleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 156, -1));

        stateLabel.setText("State: not running...");
        getContentPane().add(stateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 144, 23));

        normalGameButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        normalGameButton.setText("Normal Game");
        normalGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalGameButtonActionPerformed(evt);
            }
        });
        getContentPane().add(normalGameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        exitButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 105, -1));

        numberOfGamesLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        numberOfGamesLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfGamesLabel1.setText("Speed");
        getContentPane().add(numberOfGamesLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 520, 50, 26));

        speedSlider.setMaximum(2000);
        speedSlider.setMinimum(1);
        speedSlider.setValue(1);
        speedSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                speedSliderMouseDragged(evt);
            }
        });
        getContentPane().add(speedSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 520, 160, -1));

        speedLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        speedLabel.setText("0 ms");
        getContentPane().add(speedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 520, 90, 26));

        plusOne.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        plusOne.setText("+1");
        plusOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusOneActionPerformed(evt);
            }
        });
        getContentPane().add(plusOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 550, 60, -1));

        minusOne.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        minusOne.setText("-1");
        minusOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusOneActionPerformed(evt);
            }
        });
        getContentPane().add(minusOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 580, 60, -1));

        plusFive.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        plusFive.setText("+5");
        plusFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusFiveActionPerformed(evt);
            }
        });
        getContentPane().add(plusFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, 60, -1));

        minusFive.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        minusFive.setText("-5");
        minusFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusFiveActionPerformed(evt);
            }
        });
        getContentPane().add(minusFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 580, 60, -1));

        plusFifty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        plusFifty.setText("+50");
        plusFifty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusFiftyActionPerformed(evt);
            }
        });
        getContentPane().add(plusFifty, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, 60, -1));

        minusFifty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        minusFifty.setText("-50");
        minusFifty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusFiftyActionPerformed(evt);
            }
        });
        getContentPane().add(minusFifty, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 580, 60, -1));

        colorsCheckBox.setSelected(true);
        colorsCheckBox.setText("Colors");
        colorsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorsCheckBoxActionPerformed(evt);
            }
        });
        getContentPane().add(colorsCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, -1, -1));

        nextGameButton.setText("Override: Next Game");
        nextGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextGameButtonActionPerformed(evt);
            }
        });
        getContentPane().add(nextGameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, 160, -1));

        specialSequenceCheckBox.setText("Use Special Sequence");
        specialSequenceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specialSequenceCheckBoxActionPerformed(evt);
            }
        });
        getContentPane().add(specialSequenceCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, -1));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, 300, 10));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void normalGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalGameButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        timer.cancel();
        The2048Game.createGameWindow();
        this.dispose();
    }//GEN-LAST:event_normalGameButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        SequenceMaker sm = new SequenceMaker(this);
        sm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void startStopToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStopToggleButtonActionPerformed
        if(startStopToggleButton.isSelected()) {
            startStopToggleButton.setText("Stop");
            stateLabel.setText("State: running...");
            addButton.setEnabled(false);
            openButton.setEnabled(false);
            editButton.setEnabled(false);
            
            setNewTimer();
            
            if (!gameThere) this.gameOver();
            gameThere = true;
            
        } else {
            startStopToggleButton.setText("Start");
            stateLabel.setText("State: not running...");
            addButton.setEnabled(true);
            openButton.setEnabled(true);
            editButton.setEnabled(true);
            
            timer.cancel();
        }
        
    }//GEN-LAST:event_startStopToggleButtonActionPerformed

    private void setNewTimer() {
        timerTask = new RepeatingTimer();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, speed, speed);
        
    }
    
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser("C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game\\Sequences");
        int num = chooser.showDialog(this, "OK");
        
        if(num == chooser.APPROVE_OPTION) {
            openSequence(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_openButtonActionPerformed

    public void openSequence(File aFile) {
        //Set label text
        sequenceFile = aFile;
        sequenceName = sequenceFile.getName();
        int n = sequenceName.length();
        for (int i = sequenceName.length() - 1; i > 0; i--) {
            char c = sequenceName.charAt(i);
            if( c == '.' ) {
                n = i;
                break;
            }
        }
        sequenceName = sequenceName.substring(0, n);
        sequenceNameLabel.setText(sequenceName);
        //Set sequence text area

        try {
            Scanner in = new Scanner(sequenceFile);
            sequenceTextArea.setText("");
            while (in.hasNext()) {
                if (sequenceTextArea.getText() == "" || sequenceTextArea.getText() == null) {
                    sequenceTextArea.setText(in.next());
                } else {
                    sequenceTextArea.setText(sequenceTextArea.getText() + " " + in.next());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        sequenceText = sequenceTextArea.getText();
        
        //enable buttons
        editButton.setEnabled(true);
        startStopToggleButton.setEnabled(true);

        reader = new SequenceReader(sequenceFile);
    }
    
    private void speedSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_speedSliderMouseDragged
        speedLabel.setText(speedSlider.getValue() + " ms");
        speed = speedSlider.getValue();
        timer.cancel();
        if ( startStopToggleButton.isSelected() ) setNewTimer();
    }//GEN-LAST:event_speedSliderMouseDragged

    private void plusOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusOneActionPerformed
        // TODO add your handling code here:
        changeSlider(1);
    }//GEN-LAST:event_plusOneActionPerformed

    private void minusOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusOneActionPerformed
        // TODO add your handling code here:
        changeSlider(-1);
    }//GEN-LAST:event_minusOneActionPerformed

    private void plusFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusFiveActionPerformed
        // TODO add your handling code here:
        changeSlider(5);
    }//GEN-LAST:event_plusFiveActionPerformed

    private void minusFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusFiveActionPerformed
        // TODO add your handling code here:
        changeSlider(-5);
    }//GEN-LAST:event_minusFiveActionPerformed

    private void plusFiftyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusFiftyActionPerformed
        // TODO add your handling code here:
        changeSlider(50);
    }//GEN-LAST:event_plusFiftyActionPerformed

    private void minusFiftyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusFiftyActionPerformed
        // TODO add your handling code here:
        changeSlider(-50);
    }//GEN-LAST:event_minusFiftyActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        if (sequenceFile == null) return;
        
        SequenceMaker sm = new SequenceMaker(this);
        String sequenceName = sequenceFile.getName();
            int n = sequenceName.length();
            for (int i = sequenceName.length() - 1; i > 0; i--) {
                char c = sequenceName.charAt(i);
                if( c == '.' ) {
                    n = i;
                    break;
                }
            }
            sequenceName = sequenceName.substring(0, n);
        sm.setName(sequenceName);
        
        try {
            Scanner in = new Scanner(sequenceFile);
            String s = "";
            while(in.hasNext()) {
                s += in.next() + " ";
            }
            s = s.trim();
            sm.setCode(s);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        
        sm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_editButtonActionPerformed

    private void colorsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorsCheckBoxActionPerformed
        // TODO add your handling code here:
        colors = colorsCheckBox.isSelected();
    }//GEN-LAST:event_colorsCheckBoxActionPerformed

    private void nextGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextGameButtonActionPerformed
        // TODO add your handling code here:
        gameOver();
    }//GEN-LAST:event_nextGameButtonActionPerformed

    private void specialSequenceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specialSequenceCheckBoxActionPerformed
        // TODO add your handling code here:
        if (specialSequenceCheckBox.isSelected() == true) {
            openButton.setEnabled(false);
            editButton.setEnabled(false);
            startStopToggleButton.setEnabled(true);
            sequenceNameLabel.setText("Special Sequence");
            sequenceTextArea.setText("");
            usingSpecialSequence = true;
        } else {
            openButton.setEnabled(true);
            if ("(No Sequence)".equals(sequenceName)) {
                editButton.setEnabled(false);
                startStopToggleButton.setEnabled(false);
            } else {
                editButton.setEnabled(true);
                startStopToggleButton.setEnabled(true);
            }
            sequenceNameLabel.setText(sequenceName);
            sequenceTextArea.setText(sequenceText);
            usingSpecialSequence = false;
        }
    }//GEN-LAST:event_specialSequenceCheckBoxActionPerformed

    private void changeSlider(int n) {
        speedSlider.setValue(speedSlider.getValue() + n);
        this.speedSliderMouseDragged(null);
    }
    
    
    public static boolean doneMoving;
    
    private static Color tileBackgroundColor;
    private static Color emptyTileColor;
    private static int smallFontSize;
    private static int largeFontSize;
    private static Color lowNumberColor;
    private static Color highNumberColor;
    private static Color[] tileColors;
    private static JLabel[][] labelGrid;
    
    private static Timer timer;
    private static TimerTask timerTask;
    private static int speed;
    
    private static File sequenceFile;
    private static String sequence;
    private static String sequenceName;
    private static SequenceReader reader;
    private static String sequenceText;
    
    private static boolean gameThere;
    private static boolean colors;
    private Grid g;
    private static int notMovedCount;
    
    private static int round;
    private static int highestScore;
    private static int averageScore;
    private static long totalOfScores;
    private static int highestTileValue;
    
    private static boolean usingSpecialSequence;
    private static AdvancedSequence advSequence;
    
    private static final int TYPE_OF_ADVANCED_SEQUENCE = 2;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel averageScoreLabel;
    private javax.swing.JCheckBox colorsCheckBox;
    private javax.swing.JButton editButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel highestScoreLabel;
    private javax.swing.JLabel highestTileLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel label00;
    private javax.swing.JLabel label01;
    private javax.swing.JLabel label02;
    private javax.swing.JLabel label03;
    private javax.swing.JLabel label10;
    private javax.swing.JLabel label11;
    private javax.swing.JLabel label12;
    private javax.swing.JLabel label13;
    private javax.swing.JLabel label20;
    private javax.swing.JLabel label21;
    private javax.swing.JLabel label22;
    private javax.swing.JLabel label23;
    private javax.swing.JLabel label30;
    private javax.swing.JLabel label31;
    private javax.swing.JLabel label32;
    private javax.swing.JLabel label33;
    private javax.swing.JButton minusFifty;
    private javax.swing.JButton minusFive;
    private javax.swing.JButton minusOne;
    private javax.swing.JButton nextGameButton;
    private javax.swing.JButton normalGameButton;
    private javax.swing.JLabel numberOfGamesLabel1;
    private javax.swing.JButton openButton;
    private javax.swing.JButton plusFifty;
    private javax.swing.JButton plusFive;
    private javax.swing.JButton plusOne;
    private javax.swing.JTextArea recentScoresTextArea;
    private javax.swing.JLabel roundLabel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel sequenceNameLabel;
    private javax.swing.JTextArea sequenceTextArea;
    private javax.swing.JCheckBox specialSequenceCheckBox;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JToggleButton startStopToggleButton;
    private javax.swing.JLabel stateLabel;
    // End of variables declaration//GEN-END:variables
}
