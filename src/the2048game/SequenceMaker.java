/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package the2048game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;

/**
 *
 * @author Calvin Cramer
 */
public class SequenceMaker extends javax.swing.JFrame {

    /**
     * Creates new form SequenceMaker
     * @param w the main window
     */
    public SequenceMaker(SequencesWindow w) {
        initComponents();
        sw = w;

        sequenceDir = "C:\\Users\\Calvin Cramer\\Documents\\Programs\\Program Files\\2048 Game\\Sequences";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = (width / 2) - (this.getWidth() / 2);
        int y = (height / 2) - (this.getHeight() / 2);
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        codeArea = new javax.swing.JTextArea();
        doneButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        rightButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        leftButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setTitle("Create a Sequence");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Name: ");

        codeArea.setEditable(false);
        codeArea.setColumns(20);
        codeArea.setLineWrap(true);
        codeArea.setRows(5);
        codeArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(codeArea);

        doneButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        doneButton.setText("Done");
        doneButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doneButtonMouseClicked(evt);
            }
        });

        upButton.setText("U");
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        rightButton.setText("R");
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightButtonActionPerformed(evt);
            }
        });

        downButton.setText("D");
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        leftButton.setText("L");
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete Word");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(doneButton, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(leftButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(upButton)
                                    .addComponent(downButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rightButton)
                                        .addGap(107, 107, 107))
                                    .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(upButton)
                        .addGap(18, 22, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(downButton)
                            .addComponent(deleteButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(leftButton)
                            .addComponent(rightButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void doneButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doneButtonMouseClicked
        // TODO add your handling code here:
        if (nameTextField.getText().equals("")) {
             JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "No Name Given", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (codeArea.getText().equals("")) {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "No Code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String name = nameTextField.getText().toUpperCase();
        String code = codeArea.getText();
        ScoreRecorder r = new ScoreRecorder(sequenceDir + "\\" + name + ".txt", false);

        try {
            r.write(code);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        this.setVisible(false);
        sw.setVisible(true);
        this.dispose();
        sw.openSequence(new File(sequenceDir + "\\" + name + ".txt"));
    }//GEN-LAST:event_doneButtonMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.setVisible(false);
        sw.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        // TODO add your handling code here:
        codeArea.setText(codeArea.getText() + " UP");
    }//GEN-LAST:event_upButtonActionPerformed

    private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightButtonActionPerformed
        // TODO add your handling code here:
        codeArea.setText(codeArea.getText() + " RIGHT");
    }//GEN-LAST:event_rightButtonActionPerformed

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        // TODO add your handling code here:
        codeArea.setText(codeArea.getText() + " DOWN");
    }//GEN-LAST:event_downButtonActionPerformed

    private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftButtonActionPerformed
        // TODO add your handling code here:
        codeArea.setText(codeArea.getText() + " LEFT");
    }//GEN-LAST:event_leftButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        int n = 0;
        for (int i = codeArea.getText().length() - 1; i > 0; i--) {
            if (codeArea.getText().charAt(i) == ' ') {
                n = i;
                break;
            }
        }
        codeArea.setText(codeArea.getText().substring(0, n));
    }//GEN-LAST:event_deleteButtonActionPerformed

    public void setName(String name) {
        nameTextField.setText(name);
    }
    
    public void setCode(String s) {
        codeArea.setText(s);
    }
 
    private static String sequenceDir;
    private static SequencesWindow sw;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea codeArea;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JButton downButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton leftButton;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton rightButton;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
}
