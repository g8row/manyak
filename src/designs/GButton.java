package designs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GButton extends JButton {
    public GButton(){
        setText("button");
        setBackground(new Color(120, 82, 255));
        setForeground(Color.WHITE);
        setFont(new Font("Tahoma", Font.PLAIN, 12));
        setBorder(new RoundedBorder(5));
    }
    public GButton(String text){
        setText(text);
        setBackground(new Color(120, 82, 255));
        setForeground(Color.WHITE);
        setFont(new Font("Tahoma", Font.PLAIN, 12));
        LineBorder lineBorder = new LineBorder(new Color(120, 82, 255),2,true);
        setBorder(lineBorder);
    }
    public void setBold(){
        setFont(new Font("Tahoma", Font.BOLD, 12));

    }
}
