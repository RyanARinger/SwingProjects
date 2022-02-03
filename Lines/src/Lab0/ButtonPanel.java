package Lab0;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/*
This is a panel which is used to store the button which will pause and resume the animator thread
 */
public class ButtonPanel extends JPanel{

    private JButton dpp; //draw pause pause
    private JButton cs; //color switch
    private JButton lu; //lines up
    private JButton ld; //lines down
    private JButton ds; //design switch
    private JLabel count;
    private Animator an;
    private boolean paused;
    private AnimatedLiningPanel pan;

    public ButtonPanel(Animator animator, AnimatedLiningPanel p){
        this.dpp = new JButton("Draw");
        this.cs = new JButton("Color Mode");
        this.lu = new JButton("+");
        this.ld = new JButton("-");
        this.ds = new JButton();
        this.count= new JLabel();
        this.paused = false;
        this.an = animator;
        this.pan = p;

        this.count.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());
        this.setRest(this.dpp);
        this.setRest(this.cs);
        this.setRest(this.lu);
        this.setRest(this.ld);
        this.setRest(this.ds);
        this.designLabel();
        this.changeCount();

        this.dpp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                an.pauseToggle();
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                an.pauseToggle();
                pauseToggleThread();
                toggleText();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setEnter(dpp);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setEnter(dpp);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setRest(dpp);
            }
        });

        this.cs.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                colorModeSwitch();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setEnter(cs);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setEnter(cs);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setRest(cs);
            }
        });

        this.lu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                incrementLines();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setEnter(lu);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setEnter(lu);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setRest(lu);
            }
        });
        this.ld.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                decrementLines();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setEnter(ld);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setEnter(ld);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setRest(ld);
            }
        });

        this.ds.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                designToggle();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setEnter(ds);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setEnter(ds);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setRest(ds);
            }
        });

        this.add(this.ds);
        this.add(this.cs);
        this.add(this.dpp);

        this.add(this.ld);
        this.add(this.count);
        this.add(this.lu);
    }
    private void setEnter(JButton b){
        b.setBackground(Color.DARK_GRAY);
        b.setForeground(Color.LIGHT_GRAY);
    }
    private void setRest(JButton b){
        b.setBackground(Color.LIGHT_GRAY);
        b.setForeground(Color.DARK_GRAY);
    }

    private void toggleText(){
        if(this.paused){
            this.dpp.setText("Resume");
            this.paused = false;
        }
        else{
            this.dpp.setText("Stop");
            this.paused = true;
        }
    }
    private void pauseToggleThread(){
        an.pauseToggle();
    }
    private void colorModeSwitch(){
        this.pan.colorSwitch();
    }
    private void incrementLines(){
        this.pan.incrementLines();
        this.changeCount();
    }
    private void decrementLines(){
        this.pan.decrementLines();
        this.changeCount();
    }
    private void changeCount(){
        this.count.setText(this.pan.getLines());
    }

    private void designToggle(){
        this.pan.changeDesign();
        this.designLabel();
    }
    private void designLabel(){
        if(this.pan.getBoxes() == 0){
            this.ds.setText("Boxes");
        }
        else if(this.pan.getBoxes() == 1){
            this.ds.setText("Wings");
        }
        else{
            this.ds.setText("Flopper");
        }
    }
}