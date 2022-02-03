package bargraphv2;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Container;
import java.awt.Component;

public class BarGraphV2 {
    public static void main(String[] args) {
        // Seed random number or initialize scanner

        // Constant Variables

        // Declare all Variables Here
        JFrame frame = new JFrame("BAR GRAPH"); // the frame
        BarChart chart = new BarChart(); // the bar graph
        JButton button = new JButton("Redraw"); // the JButton
        GridBagConstraints gbc = new GridBagConstraints(); // for formatting


        // Input or initialize values Here
        frame.setSize(450, 500); // window size
        frame.setLayout(new GridBagLayout()); // setting layout to a gridbaglayout
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x button

        // Process/Calculations Here
        button.addMouseListener(new MouseListener() { // make button do something
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
                chart.reDraw(); // resets the BarChart
                frame.repaint(); // reloads the JFrame
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

        // addstuff is a function that will take a container and place the passed component in the container
        // following the restrictions set in place by the preceding variables
        // x and y are coordinates
        // wx and wy are the weight variable that determine the dominance of each component
        addStuff(frame, chart, gbc, 0,0,100,100); // add the chart
        addStuff(frame, button, gbc, 0,1,0,5); // add the button

        // Output Located Here
        frame.setVisible(true); // Power *on*

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
