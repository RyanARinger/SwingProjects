package Lab0;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class AnimatedLiningPanel extends JPanel{
    private int w;
    private int h;
    private int counter;
    private int timer;
    private int colorMode;
    private int boxes;
//    private int side;
    private double totalLines;

    public AnimatedLiningPanel() {
        this.w = 0;
        this.h = 0;
        this.counter = 1;
        this.totalLines = 30.0;
        this.colorMode = 0;
        this.boxes = 0;
        this.timer = (int)(2500 / this.totalLines);
        this.setBackground(Color.BLACK);
//        this.side = 0;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        w = this.getWidth();
        h = this.getHeight();

        if(this.colorMode == 1){
            g.setColor(rColor());
        }
        else if(this.colorMode == 2){
            g.setColor(Color.WHITE);
        }
        else if(this.colorMode == 3){
            g.setColor(Color.RED);
        }
        else if(this.colorMode == 4){
            g.setColor(Color.ORANGE);
        }
        else if(this.colorMode == 5){
            g.setColor(Color.BLUE);
        }
        else if(this.colorMode == 6){
            g.setColor(Color.CYAN);
        }
        else if(this.colorMode == 7){
            g.setColor(Color.GREEN);
        }
        else if(this.colorMode == 8){
            g.setColor(Color.MAGENTA);
        }
        if(this.boxes == 2) {
            for (int i = 0; i < this.counter; i++) {
                int w2 = (int) (((i % totalLines) / totalLines) * w);
                int h2 = (int) (((i % totalLines) / totalLines) * h);

//                System.out.println(i + ": ( " + w2 + " , " + h2 + " )");

                if (this.colorMode == 0) {
                    g.setColor(rColor());
                }


                g.drawLine(0, h2, w2, h);
                if (i > this.totalLines) {
                    g.drawLine(w2, h, w, h - h2);
                }
                if (i > this.totalLines * 2) {
                    g.drawLine(w, h - h2, w - w2, 0);
                }
                if (i > this.totalLines * 3) {
                    g.drawLine(w - w2, 0, 0, h2);
                }
            }
        }
        else {
            for (int i = 0; i < this.counter; i++) {
                int w2 = (int) ((i / totalLines) * w);
                int h2 = (int) ((i / totalLines) * h);
                if (this.colorMode == 0) {
                    g.setColor(rColor());
                }

                if (this.boxes == 0) {
                    // boxes
                    g.drawLine(0, h2, w2, h);
                    g.drawLine(w2, h, w, h - h2);
                    g.drawLine(w, h - h2, w - w2, 0);
                    g.drawLine(w - w2, 0, 0, h2);
                } else {
                    // wings
                    g.drawLine(0, h2, w2, h);
                    g.drawLine(w - w2, h, w, h2);
                    g.drawLine(w, h - h2, w - w2, 0);
                    g.drawLine(w2, 0, 0, h - h2);
                }
            }
        }

    }

    public void increaseCounter(){
        this.counter++;
        if(this.boxes == 2) {
            if (this.counter > (this.totalLines * 4)) {
                this.counter = 1;
            }
        }
        else{
            if(this.counter > this.totalLines){
                this.counter = 1;
            }
        }
//        System.out.println(this.counter);
    }
    private Color rColor(){ // retrieves a random color
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
    public int getTimer(){
        return this.timer;
    }
    public void colorSwitch(){
        this.colorMode++;
        if(this.colorMode > 8){
            this.colorMode = 0;
        }
    }
    public void incrementLines(){
        if(this.totalLines < 150){
            this.totalLines++;
            this.timer = (int)(2500/this.totalLines);
        }
    }
    public void decrementLines(){
        if(this.totalLines > 1){
            this.totalLines--;
            this.timer = (int)(2500/this.totalLines);
        }
    }
    public String getLines(){
        return Integer.toString((int)(this.totalLines));
    }
    public void changeDesign(){
        this.boxes ++;
        if(this.boxes > 2){
            this.boxes = 0;
        }
        System.out.println(boxes);
    }
    public int getBoxes(){
        return this.boxes;
    }
}

