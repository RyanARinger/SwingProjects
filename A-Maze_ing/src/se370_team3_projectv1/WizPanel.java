package se370_team3_projectv1;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;

/*
this class isnt used, it is meant to hold an imageIcon and paint the wizard .png file
it is the base unit for the PlayerPanel class but since the PlayerPanel is not implemented, neither is this
 */

public class WizPanel extends JPanel{

    private ImageIcon wiz; // for card image file
    private boolean wizHere;

    public WizPanel(){ //constructor takes a file name for the card image
        super();
        this.wiz = new ImageIcon("wizStandRight.png");
        this.wizHere = false;
        Image im = this.wiz.getImage(); //store the image in Java
//        im = im.getScaledInstance(65, 100, Image.SCALE_SMOOTH); //scale down the image by 1/10 (cards only)
//        this.wiz = new ImageIcon(im); //reassign ImageIcon to smaller image
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g); //parent constructor
        this.setOpaque(false);
        if(this.wizHere) {
            this.wiz.paintIcon(this, g, 0, 0); //each ImageIcon gets its own panel, so (0,0) coordinate
        }

    }

    public ImageIcon getWizII(){ //not needed after all
        return this.wiz;
    }

    public void setWiz(boolean w){
        this.wizHere = w;
        this.repaint();
    }

}
