package se370_team3_projectv1;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Insets;

import java.util.ArrayList;

/*
This JPanel uses an arraylist for words an an arrayList for buttons
The contents of these arraylists are added to the panel in the order they were
passed into the object
 */

public class MenuPanel extends JPanel{

    private ArrayList<JLabel> words;
    private ArrayList<WordButton> buttons;
    private GridBagConstraints gbc;


    public MenuPanel(){
        super();

        this.words = new ArrayList<>(); //for words
        this.buttons = new ArrayList<>(); //for buttons
        this.gbc = new GridBagConstraints();
        this.setBackground(Color.decode("#e7e6e6"));
        this.setLayout(new GridBagLayout());
        this.gbc.insets = new Insets(10,10,10,10);
        this.gbc.anchor = GridBagConstraints.CENTER;
    }

    public void add(String s){ //adds a string to the words list
        MessageLabel l = new MessageLabel(s);
        this.words.add(l);
        this.refreshPanel();
    }
    public void add(WordButton b){ //adds a WordButton to the buttons list
        this.buttons.add(b);
        this.refreshPanel();
    }
    public void wipePanel(){ //Clear the panel
        this.removeAll();
        this.words = new ArrayList<JLabel>();
        this.buttons = new ArrayList<WordButton>();
    }
    public void refreshPanel(){ //adds the words to the panel, then adds the buttons to the panel
        for(int i = 0; i < this.words.size(); i++){
            this.addStuff(this, this.words.get(i), this.gbc, 0, i, 0, 0);
        }
        for(int i = 0; i < this.buttons.size(); i++){
            this.addStuff(this, this.buttons.get(i), this.gbc, 0, i + this.words.size(), 0, 0);
        }
        this.revalidate();
        this.repaint();
    }
    private void addStuff(Container c, Component l, GridBagConstraints gbc, int x, int y, int wx, int wy){
        gbc.gridx = x;
        gbc.gridy = y;

        gbc.weightx = wx;
        gbc.weighty = wy;
        gbc.fill = GridBagConstraints.BOTH;

        c.add(l, gbc);
    }
}
