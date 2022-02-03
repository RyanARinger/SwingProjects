package se370_team3_projectv1;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
/*
This is a custom JLabel that uses selected fonts and colors
 */
public class MessageLabel extends JLabel{
    private final Font RESTINGVERANDA = new Font("Unispace", 1, 40);
    private final Color RESTINGCOLOR = Color.decode("#222a35");
    private final LineBorder RESTINGBORDER = new LineBorder(RESTINGCOLOR);

    public MessageLabel(String s){
        super(s, SwingConstants.CENTER);

        this.setFont(RESTINGVERANDA);
        this.setForeground(RESTINGCOLOR);
//        this.setBorder(RESTINGBORDER);
    }
}
