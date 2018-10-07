package the2048game;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.border.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Calvin Cramer
 */
public class GameWindow 
    extends javax.swing.JFrame 
    implements Window {

    /**
     * Creates new form GameWindows
     */
    public GameWindow() {
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
        
        backgroundColor = new Color(250, 248, 239);
        componentBackgroundColor = new Color(187, 173, 160);
        tileBackgroundColor = new Color(204, 192, 180);
        unimportantTextColor = new Color(238, 228, 213);
        importantTextColor = backgroundColor;
        emptyTileColor = tileBackgroundColor;
        lowNumberColor = new Color(119, 110, 101);
        highNumberColor = Color.WHITE;
        buttonColor = new Color(237, 153, 91);
        
        largeFontSize = 44;
        smallFontSize = 32;
        
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
        
        scoreLabelHeader.setBackground(unimportantTextColor);
        scoreLabel.setBackground(importantTextColor);
        getContentPane().setBackground(backgroundColor);
        jLayeredPane1.setBackground(componentBackgroundColor);
        jLayeredPane2.setBackground(componentBackgroundColor);
        
        for(int a = 0; a <= 3; a++) {
            for(int b = 0; b <= 3; b++) {
                labelGrid[a][b].setBackground(tileBackgroundColor);
            }
        }
        
        setRoundedCorners();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = (width / 2) - (this.getWidth() / 2);
        int y = (height / 2) - (this.getHeight() / 2);
        this.setBounds(x, y, this.getWidth(), this.getHeight());
        
        this.addKeyListener(new UserInputListener());
    }
    
    @Override
    public void setScoreText(String s) {
        scoreLabel.setText(s);
    }

    private void setRoundedCorners() {
        
        AbstractBorder line = new TextBubbleBorder(tileBackgroundColor, 2, 8, 0);

        for (int a = 0; a <= 3; a++) {
            for (int b = 0; b <= 3; b++) {
                labelGrid[a][b].setBorder(line);
            }
        }
        
        AbstractBorder buttonLine = new TextBubbleBorder(buttonColor, 2, 8, 0);
        newGameButton.setBorder(buttonLine);
        quitButton.setBorder(buttonLine);
        sequencesButton.setBorder(buttonLine);
        highScoresButton.setBorder(buttonLine);
        
    }
    
    private Color getColorFromNumber(Number num) {
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
    public void remove(int x, int y) {
        AbstractBorder line = new TextBubbleBorder(tileBackgroundColor, 2, 8, 0);
        
        JLabel l = labelGrid[y][x];
        l.setText("");
        l.setBackground(emptyTileColor);
        l.setBorder(line);
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
        if (num.getValue() >= 8) l.setForeground(highNumberColor);
        else l.setForeground(lowNumberColor);
    }
    
    @Override
    public void close() {
        this.setVisible(false);
        this.dispose();
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
        jLayeredPane2 = new javax.swing.JLayeredPane();
        scoreLabelHeader = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        newGameButton = new javax.swing.JLabel();
        sequencesButton = new javax.swing.JLabel();
        quitButton = new javax.swing.JLabel();
        highScoresButton = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("2048 Game");
        setBackground(new java.awt.Color(102, 0, 102));
        setForeground(new java.awt.Color(102, 0, 255));
        setResizable(false);

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

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {label00, label01, label02, label03, label10, label11, label12, label13, label20, label21, label22, label23, label30, label31, label32, label33});

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

        jLayeredPane1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {label00, label01, label02, label03, label10, label11, label12, label13, label20, label21, label22, label23, label30, label31, label32, label33});

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

        jLayeredPane2.setBackground(new java.awt.Color(187, 173, 160));
        jLayeredPane2.setMaximumSize(new java.awt.Dimension(173, 105));
        jLayeredPane2.setOpaque(true);

        scoreLabelHeader.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        scoreLabelHeader.setForeground(new java.awt.Color(238, 228, 213));
        scoreLabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabelHeader.setText("SCORE");

        scoreLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        scoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("0");

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scoreLabelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreLabelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane2.setLayer(scoreLabelHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(scoreLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(new java.awt.Color(250, 248, 239));

        newGameButton.setBackground(new java.awt.Color(237, 153, 91));
        newGameButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        newGameButton.setForeground(new java.awt.Color(255, 255, 255));
        newGameButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newGameButton.setText("NEW GAME");
        newGameButton.setOpaque(true);
        newGameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newGameButtonMouseClicked(evt);
            }
        });

        sequencesButton.setBackground(new java.awt.Color(237, 153, 91));
        sequencesButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sequencesButton.setForeground(new java.awt.Color(255, 255, 255));
        sequencesButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sequencesButton.setText("SEQUENCES");
        sequencesButton.setOpaque(true);
        sequencesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sequencesButtonMouseClicked(evt);
            }
        });

        quitButton.setBackground(new java.awt.Color(237, 153, 91));
        quitButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        quitButton.setForeground(new java.awt.Color(255, 255, 255));
        quitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quitButton.setText("QUIT");
        quitButton.setOpaque(true);
        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitButtonMouseClicked(evt);
            }
        });

        highScoresButton.setBackground(new java.awt.Color(237, 153, 91));
        highScoresButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        highScoresButton.setForeground(new java.awt.Color(255, 255, 255));
        highScoresButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highScoresButton.setText("HIGHSCORES");
        highScoresButton.setOpaque(true);
        highScoresButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                highScoresButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sequencesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(highScoresButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {highScoresButton, sequencesButton});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {newGameButton, quitButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sequencesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(highScoresButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {highScoresButton, newGameButton, quitButton, sequencesButton});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitButtonMouseClicked
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_quitButtonMouseClicked

    private void highScoresButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoresButtonMouseClicked
        // TODO add your handling code here:
        HighscoresWindow hsw = new HighscoresWindow(this.getX() + this.getWidth() + 10, this.getY());
        
        hsw.setVisible(true);
    }//GEN-LAST:event_highScoresButtonMouseClicked

    private void sequencesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sequencesButtonMouseClicked
        // TODO add your handling code here:
        SequencesWindow sw = new SequencesWindow();
        this.setVisible(false);
        sw.setVisible(true);
    }//GEN-LAST:event_sequencesButtonMouseClicked

    private void newGameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newGameButtonMouseClicked
        // TODO add your handling code here:
        
        Component frame = new JFrame();
        int n = JOptionPane.showConfirmDialog(frame , "Are you sure you want to restart?", "New Game", JOptionPane.YES_NO_OPTION);
        
        if (n == JOptionPane.YES_OPTION) {
            The2048Game.newGame();
        }

    }//GEN-LAST:event_newGameButtonMouseClicked



    private static Color tileBackgroundColor;
    private static Color backgroundColor;
    private static Color componentBackgroundColor;
    private static Color unimportantTextColor;
    private static Color importantTextColor;
    private static Color emptyTileColor;
    private static Color[] tileColors;
    private static int smallFontSize;
    private static int largeFontSize;
    private static Color lowNumberColor;
    private static Color highNumberColor;
    private static Color buttonColor;
    private static JLabel[][] labelGrid;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel highScoresButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JLabel newGameButton;
    private javax.swing.JLabel quitButton;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel scoreLabelHeader;
    private javax.swing.JLabel sequencesButton;
    // End of variables declaration//GEN-END:variables
}
