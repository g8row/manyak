package designs;

import javax.swing.*;
import java.awt.*;

public class BackButton extends JPanel {
    public JButton backButton;
    //public static final int WIDTH =3
    public BackButton (){
        backButton = new GButton();
        backButton.setIcon(new ImageIcon("src/designs/back-arrow.png"));
        setLayout(new BorderLayout());
        add(backButton,BorderLayout.WEST);
    }
}
