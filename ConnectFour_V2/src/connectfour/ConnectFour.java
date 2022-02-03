/* 
 * Author: Ryan Ringer
 * Created on: July 2nd, 2019 at 2:28am
 * File: ConnectFour.java
 * Purpose: Implementing a four in a row game
 */

package connectfour;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ConnectFour {

    public static void main(String[] args) {
        // Seed random number or initialize scanner


        // Constant Variables
        final int WSIZE = 800;
        final int HSIZE = 600;
        
        final int NUMLINES = 4;
        final int NUMTILES = NUMLINES * NUMLINES;
        final int YWIN = (NUMLINES*1);
        final int RWIN = (NUMLINES*10);
        
        // Declare all Variables Here
        JFrame window;
        JPanel board, hello, goodbye, panel;
        SquareButton[] tiles;
        GridBagConstraints g = new GridBagConstraints();
        boolean done = false;
        boolean p1 = false;
        int redTiles = 0;
        int yellowTiles = 0;
        int rt, yt, c;
        Color p1C, p2C;
        String message1 = "Hello";
        String message2 = "Welcome to Connect";
        String message3 = new String();
        JLabel box1, box2;
        CardLayout cl = new CardLayout();
        WordButton exit = new WordButton("EXIT");
        WordButton play = new WordButton("PLAY");
        
        // Input or initialize values Here
        window = new JFrame("Four in a Row");
        window.setSize(WSIZE, HSIZE);
        panel = new JPanel(cl);
        board = new JPanel(new GridBagLayout());
        hello = new JPanel(new GridBagLayout());
        goodbye = new JPanel(new GridBagLayout());
        box1 = new JLabel(message1, SwingConstants.CENTER);
        box2 = new JLabel(message2, SwingConstants.CENTER);
        tiles = new SquareButton[NUMTILES];
        g.insets = new Insets(5,5,5,5);
        
        box1.setFont(new Font("Veranda", 1, 40));
        box1.setForeground(Color.LIGHT_GRAY);
        box1.setText(message1);
        box2.setFont(new Font("Veranda", 1, 40));
        box2.setForeground(Color.LIGHT_GRAY);
        box2.setText(message2);
        
        addStuff(hello, box1, g, 0,0,0,0);
        addStuff(hello, box2, g, 0,1,0,0);
        addStuff(hello, play, g, 0,2,0,0);
        board.setBackground(Color.DARK_GRAY);
        goodbye.setBackground(Color.DARK_GRAY);
        hello.setBackground(Color.DARK_GRAY);
        
        for (int i = 0; i < NUMTILES; i++) {
            tiles[i] = new SquareButton();
        }
        
        for (int i = 0; i < NUMLINES; i++) {
            for (int j = 0; j < NUMLINES; j++) {
                addStuff(board, tiles[(i*NUMLINES) + j], g, i, j, 1, 1);
            }
        }
        
        p1C = tiles[0].getAc1();
        p2C = tiles[0].getAc2();
        panel.add(hello, "First");
        panel.add(board, "Second");
        window.add(panel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.next(panel);
            }
        });
        
        // Process/Calculations Here
        
        while(!done){
            rt = 0;
            yt = 0;
            c = 0;
            for (int i = 0; i < NUMLINES; i++) {
                for (int j = 0; j < NUMLINES; j++) {
                    c += tiles[(i*NUMLINES)+j].getValue();
                    
                }
                if(c == YWIN){
                    message3 = "Yellow Wins!";
                    done = true;
                }
                else if(c == RWIN){
                    message3 = "Red Wins!";
                    done = true;
                }
                c = 0;
            }
            c = 0;
            for (int i = 0; i < NUMLINES; i++) {
                for (int j = 0; j < NUMLINES; j++) {
                    c += tiles[(j*NUMLINES)+i].getValue();
                    
                }
                if(c == YWIN){
                    message3 = "Yellow Wins!";
                    done = true;
                }
                else if(c == RWIN){
                    message3 = "Red Wins!";
                    done = true;
                }
                c = 0;
            }
            c = 0;
            for (int i = 0; i < NUMLINES; i++) {
                int j = i;
                c += tiles[(i*NUMLINES) + j].getValue();
                
                if(c == YWIN){
                    message3 = "Yellow Wins!";
                    done = true;
                }
                else if(c == RWIN){
                    message3 = "Red Wins!";
                    done = true;
                }
            }
            c = 0;
            
            for (int i = 0; i < NUMLINES; i++) {
                c += tiles[(NUMLINES -1) * (i+1)].getValue();
                if(c == YWIN){
                    message3 = "Yellow Wins!";
                    done = true;
                }
                else if(c == RWIN){
                    message3 = "Red Wins!";
                    done = true;
                }
            }
            c = 0;
            for (int i = 0; i < NUMTILES; i++) {
                if(!tiles[i].getLocked()){
                    tiles[i].setP1(p1);
                }
                else{
                    c++;
                }
            }
            if(c == NUMTILES){
                done = true;
                message3 = "Draw";
            }
            
            for (int i = 0; i < NUMTILES; i++) {
                if(tiles[i].getBackground().equals(p1C)){
                    rt++;
                }
                if(rt > redTiles){
                    p1 = false;
                    redTiles = rt;
                }
            }
            
            for (int i = 0; i < NUMTILES; i++) {
                
                if(tiles[i].getBackground().equals(p2C)){
                    yt++;
                }
                if(yt > yellowTiles){
                    p1 = true;
                    yellowTiles = yt;
                }
            }
            
            
            
        }
        
        g.anchor = GridBagConstraints.CENTER;
        box1.setText(message3);
        box1.setFont(new Font("Veranda", 1, 40));
        box1.setForeground(Color.LIGHT_GRAY);
        addStuff(goodbye, box1, g, 0,0,0,0);
        addStuff(goodbye, exit, g, 0,1,0,0);
        panel.add(goodbye, "Last");
        cl.next(panel);
        // Output Located Here


        // Exit
    }
    public static void addStuff(Container c, Component l, GridBagConstraints gbc, int x, int y, int wx, int wy){
        gbc.gridx = x;
        gbc.gridy = y;
        
        gbc.weightx = wx;
        gbc.weighty = wy;
        gbc.fill = GridBagConstraints.BOTH;
        
        c.add(l, gbc);
    }
}