package connectfour;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;


public class SquareButton extends JPanel{
    
    private final ArrayList<ActionListener> actionListeners;
    private final Color RESTINGCOLOR = Color.GRAY;
    private final Color HOVERCOLOR = Color.CYAN;
    private final Color ACTIVECOLOR1 = Color.YELLOW;
    private final Color ACTIVECOLOR2 = Color.RED;
    private boolean locked = false;
    private boolean p1;
    private int value = 0;
    
    
    public SquareButton(){
        super();
        actionListeners = new ArrayList();
        this.p1 = false;
        
        this.setBackground(RESTINGCOLOR);
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                performAction();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SquareButton.this.setColor();
            }
        });
     
    }
    
    public void addActionListener(ActionListener al){
        actionListeners.add(al);
    }
    public void performAction(){
        for (int i = 0; i < actionListeners.size(); i++) {
            actionListeners.get(i).actionPerformed(new ActionEvent(this,0,"Click"));
        }
    }
    
    public void enter(){
        this.setBackground(HOVERCOLOR);
    }
    
    public void exit(){
        this.setBackground(RESTINGCOLOR);
    }
    
    public void setP1(boolean p){
        this.p1 = p;
    }
    
    public boolean getP1(){
        return this.p1;
    }
    
    public void setColor(){
        if(!locked){
            if (this.p1){
                this.setBackground(ACTIVECOLOR1);
                 this.value = 1;
            }
            else{
                this.setBackground(ACTIVECOLOR2);
                this.value = 10;
            }
            this.locked = true;
        }
    }
    
    public Color getAc1(){
        return ACTIVECOLOR1;
    }
    
    public Color getAc2(){
        return ACTIVECOLOR2;
    }
    
    public void lock(){
        this.locked = true;
    }
    
    public boolean getLocked(){
        return this.locked;
    }
    
    public void setValue(int v){
        this.value = v;
    }
    
    public int getValue(){
        return this.value;
    }
}
