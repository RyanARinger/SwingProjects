package Lab0;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;
import java.lang.Math;

public class LiningPanel extends JPanel{

    public LiningPanel() { }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();

        double lines = 15.0;

        for(int i = 0; i < lines; i++) {
            int w2 = (int)((i/lines)*w);
            int h2 = (int)((i/lines)*h);

            g.setColor(rColor());
            g.drawLine(0,  h2, w2, h);

            g.setColor(rColor());
            g.drawLine(w-w2, h, w, h2);

            g.setColor(rColor());
            g.drawLine(w, h-h2, w-w2, 0);

            g.setColor(rColor());
            g.drawLine( w2, 0, 0, h-h2);
        }
    }

    private Color rColor(){
        Random rand = new Random();

        int r = Math.abs(rand.nextInt()%6);

        switch(r){
            case 0:
                return Color.RED;
            case 1:
                return Color.ORANGE;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.CYAN;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.MAGENTA;
            default:
                return Color.BLACK;
        }

    }
}

