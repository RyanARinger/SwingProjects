package Lab0;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Container;
import java.awt.Component;
import java.util.Random;

public class main {
    public static void main(String[] args) {
        System.out.println(true);
        JFrame application = new JFrame();
        AnimatedLiningPanel panel = new AnimatedLiningPanel();
        Animator a = new Animator(panel);
//        JButton jb = new JButton("Pause/Play");
        GridBagConstraints gbc = new GridBagConstraints();
        ButtonPanel bp = new ButtonPanel(a, panel);
//        jb.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                a.pauseToggle();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//            }
//        });


        application.setLayout(new GridBagLayout());

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(800, 800);
        application.setTitle("Lining Art");
        application.setVisible(true);

        addStuff(application, panel, gbc, 0,1,1,10);
        addStuff(application, bp, gbc, 0,0,0,0);
//        addStuff(application, jb,gbc, 0,0,0,1);
        a.start();
//        Random r = new Random();
//        System.out.println(r.nextInt()%15);

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
