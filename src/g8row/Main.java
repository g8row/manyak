package g8row;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        Popular list = new Popular();
        gui.dispose();
        gui = new GUI(list);
        /*
        int WIDTH = 600, HEIGHT = 600;
        JFrame jFrame = new JFrame();
        jFrame.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH) / 2, ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jFrame.setContentPane(new ShowSearch());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        */
    }
}
