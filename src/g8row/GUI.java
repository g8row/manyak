package g8row;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class GUI extends JFrame{
    final JPanel gui = new JPanel();
    MangaList mangaList;
    CardLayout cardLayout = new CardLayout();
    public static final int WIDTH = 600, HEIGHT = 600;

    public GUI(MangaList mangaList) {
        this.mangaList = mangaList;
        create();
        revalidate();
    }



    public void create() {
        gui.setLayout(cardLayout);
        JPanel menu = new TestListGUI(mangaList);
        gui.add(menu, "menu");
        setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH) / 2, ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        setVisible(true);
        cardLayout.show(gui,"menu");
        setContentPane(gui);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
