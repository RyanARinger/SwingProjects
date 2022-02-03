package bargraphv2;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;

import java.util.Random;

public class BarChart extends JPanel {

    private final int NUMBARS = 10; // number of bars in chart
    private final int HWL; // height/width limiter
    private Color[] colors; // array for random colors
    private int[] vals; // array for random array heights

    public BarChart(){
        colors = new Color[this.NUMBARS]; // initialize color array;
        vals = new int[this.NUMBARS]; // initialize value array
        this.HWL = 10 + this.NUMBARS * 40;
        this.reDraw(); // for initial drawing of chart
        this.setBackground(Color.GRAY);

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g); // parent constructor to achieve transparency
        Graphics2D g2d = (Graphics2D)g; // Graphics2D object

        Color color;
        int value;

        for(int i = 0; i<= this.NUMBARS; i++){ // horizontal lines
            g2d.draw(new Line2D.Double(10 , 10 + (i*40), this.HWL, 10 + (i*40)));
        }
        for(int i = 0; i<= this.NUMBARS; i++) { // vertical lines
            g2d.draw(new Line2D.Double(10 + (i * 40), 10, 10 + (i * 40), this.HWL));
        }

        g2d.setStroke(new BasicStroke(10f)); // "Thickness be 10f"

        for(int i = 0; i < this.NUMBARS; i++){ // painting the bars of the chart
            value = this.vals[i];
            color = this.colors[i];

            g2d.setColor(color);

            // x1 and x2 traverse left to right as the bars are printed
            // y1 is set to the bottom of the grid
            // y2 is variable depending on its random value
            g2d.draw(new Line2D.Double(30 + (i * 40),405,30 + (i * 40),415-value ));

        }

        this.repaint(); // repaints the panel each time this method is called;
    }

    public void reDraw(){
        Random rand = new Random(); // initialize random number generator

        Color color; // temporary color variable
        int val; // temporary value variable
        int v; // used for picking a random color

        for(int i = 0; i < this.NUMBARS; i++){ // loops NUMBARS times
            v = (rand.nextInt() % 6); // get next random number
            if(v < 0){ // if number is negative
                v *= -1; // make positive
            }
            v += 1; // avoid zero
//            System.out.println("Color = " + v);

            color = switch (v) { // enhanced switch for assigning a random color using v
                case 1 -> Color.BLUE;
                case 2 -> Color.RED;
                case 3 -> Color.GREEN;
                case 4 -> Color.CYAN;
                case 5 -> Color.YELLOW;
                case 6 -> Color.MAGENTA;
                default -> Color.BLACK;
            };

            colors[i] = color; // place color in array
            val = rand.nextInt()%400; // get random bar size variable
            if(val < 0){ // if number is negative
                val *= -1; // make positive
            }
            if(val < 10){ // experienced formatting errors between 10f thickness and values less than ten
                val += 10;
            }
            vals[i] = val; // place value in array
//            System.out.println("Value = " + val);
        }
    }
}