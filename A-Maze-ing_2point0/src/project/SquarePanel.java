package project;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;

public class SquarePanel extends JPanel{
    private final Color WC = Color.DARK_GRAY;   //Wall color
    private final Color SC = Color.decode("#e7e6e6");   //space color
    private final Color PC = Color.decode("#222a35");     //Player Color #222a35
    private final Color GC = Color.decode("#66B962");  //Gate Color
    private final Color VC = Color.decode("#FF7676");    //Visited Color
    private final Color HC = Color.decode("#66B962");
    private boolean touched; //is touched
    private boolean visited; //has been visited
    private boolean openRight; //opens right
    private boolean openLeft; //opens left
    private boolean openUp; //opens up
    private boolean openDown; //opens down
    private boolean playerHere; //player is here
    private boolean gate; //final tile
    private boolean hint; //is part of hint
    private final int LINESIZE; //length of line to be drawn

    public SquarePanel(int s){
        super();

        //everything false
        this.gate = false;
        this.touched = false;
        this.playerHere = false;
        this.visited = false;
        this.openRight = false;
        this.openLeft = false;
        this.openUp = false;
        this.openDown = false;
        this.hint = false;
        this.LINESIZE = s;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        this.setPreferredSize(new Dimension(1,1));

        g.setColor(this.WC);
        g2d.setStroke(new BasicStroke(2f));

        if(!this.openUp){ //if up is open
            g2d.draw(new Line2D.Double(0,0,this.LINESIZE,0));
        }
        if(!this.openDown){//if donw is open
            g2d.draw(new Line2D.Double(0,this.LINESIZE,this.LINESIZE,this.LINESIZE));
        }
        if(!this.openRight){//if right is open
            g2d.draw(new Line2D.Double(this.LINESIZE,0,this.LINESIZE,this.LINESIZE));
        }
        if(!this.openLeft){//if left is open
            g2d.draw(new Line2D.Double(0,0,0,100));
        }




        if(this.touched){ //part of path
            g2d.setStroke(new BasicStroke(10));
            g2d.setColor(this.VC);
            g2d.draw(new Line2D.Double((double)this.LINESIZE/2, ((double)this.LINESIZE/2), (double)this.LINESIZE/2, ((double)this.LINESIZE/2)));
        }
        if(this.hint){ //part of hint
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(this.WC);
            g2d.draw(new Line2D.Double((double)this.LINESIZE/2, ((double)this.LINESIZE/2), (double)this.LINESIZE/2, ((double)this.LINESIZE/2)));
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(this.HC);
            g2d.draw(new Line2D.Double((double)this.LINESIZE/2, ((double)this.LINESIZE/2), (double)this.LINESIZE/2, ((double)this.LINESIZE/2)));
        }

        if(this.gate){ //is final tile
            g2d.setStroke(new BasicStroke(this.LINESIZE - 10));
            g2d.setColor(this.GC);
            g2d.draw(new Line2D.Double((double)this.LINESIZE/2, ((double)this.LINESIZE/2), (double)this.LINESIZE/2, ((double)this.LINESIZE/2)));
        }
        if(this.playerHere){ //player is here
            g2d.setStroke(new BasicStroke(this.LINESIZE - 20));
            g2d.setColor(this.PC);
            g2d.draw(new Line2D.Double((double)this.LINESIZE/2, ((double)this.LINESIZE/2), (double)this.LINESIZE/2, ((double)this.LINESIZE/2)));
        }

    }

    public void setToSpace(){
        this.setBackground(this.SC);
        this.playerHere = false;
        this.repaint();
    }
    public void setToPlayer(){
        this.playerHere = true;
        this.repaint();
    }
    public void removePlayer(){
        this.setBackground(this.SC);
        this.playerHere = false;
        this.touched = true;
        this.repaint();
    }
    public void setToGate(){
        this.setBackground(this.SC);
        this.gate = true;
        this.repaint();
    }

    public boolean visited(){
        return this.visited;
    }
    public void visit(){
        this.visited = true;
    }
    public void unVisit(){
        this.visited = false;
    }
    public void openLeftBorder(){
        this.openLeft = true;
        this.repaint();
    }
    public void openRightBorder(){
        this.openRight = true;
        this.repaint();
    }
    public void openTopBorder(){
        this.openUp = true;
        this.repaint();
    }
    public void openBottomBorder(){
        this.openDown = true;
        this.repaint();
    }
    public void hintTile(){
        this.hint = true;
        this.repaint();
    }
    public void unHint(){
        this.hint = false;
        this.repaint();
    }
    public void touch(){
        this.touched = true;
        this.repaint();
    }
    public void unTouch(){
        this.touched = false;
        this.repaint();
    }

    public boolean getLeft(){
        return this.openLeft;
    }
    public boolean getRight(){
        return this.openRight;
    }
    public boolean getUp(){
        return this.openUp;
    }
    public boolean getDown(){
        return this.openDown;
    }

}
