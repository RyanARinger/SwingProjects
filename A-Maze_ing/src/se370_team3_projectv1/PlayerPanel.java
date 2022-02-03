package se370_team3_projectv1;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.Component;
import java.awt.Color;

/*
this JPanel was meant for using the wizard .png file and was meant to exist transparent above the maze

...

it didn't work
 */

public class PlayerPanel extends JPanel{
    
    private WizPanel[][] tiles;
    private int mazeSize;
    private int xc;
    private int yc;
    private int PanelSize;
    private GridBagConstraints gbc;

    public PlayerPanel(int s, int ps){
        this.mazeSize = s;
        this.PanelSize = ps;
        this.tiles = new WizPanel[s][s];
        this.xc = 0;
        this.yc = 0;
        this.gbc = new GridBagConstraints();
//        this.setSize(this.PanelSize, this.PanelSize);
        this.setLayout(new GridBagLayout());

        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++){
                this.tiles[i][j] = new WizPanel();
            }
        }
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++){
                this.addStuff(this, this.tiles[i][j], this.gbc, i, j, 1, 1);
            }
        }
        this.setPlayer(this.xc, this.yc);
    }

    public void setPlayer(int x, int y){
        this.tiles[x][y].setWiz(true);
        this.tiles[this.xc][this.yc].setWiz(false);
        this.xc = x;
        this.yc = y;
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
