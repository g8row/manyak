package designs;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

public class BackButton extends JPanel {
    public JButton backButton;
    //public static final int WIDTH =3
    public BackButton (){
        backButton = new JButton();
        //backButton.setBackground(Color.WHITE);
        backButton.setBorder(new EmptyBorder(5,5,5,5));
        backButton.setIcon(new ImageIcon("src/designs/back-arrow.png"));
        setLayout(new BorderLayout());
        add(backButton,BorderLayout.WEST);
    }
}
