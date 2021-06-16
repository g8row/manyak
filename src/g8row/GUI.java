package g8row;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    CardLayout cardLayout;
    public static final int WIDTH = 600, HEIGHT = 600;


    public GUI(MangaList mangaList) throws IOException {
        setTitle("Manyak");
        ImageIcon icon = new ImageIcon("src/designs/manyak-icon.jpg");
        setIconImage(icon.getImage());
        this.mangaList = mangaList;
        create();
        revalidate();
    }

    public GUI (){
        setTitle("Manyak");
        setLayout(new BorderLayout());
        JPanel jPanel = new JPanel(new BorderLayout());
        setContentPane(jPanel);
        ImageIcon icon = new ImageIcon("src/designs/manyak-icon.jpg");
        setIconImage(icon.getImage());
        setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH) / 2, ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        setVisible(true);
        JLabel jLabel = new JLabel(icon);
        jLabel.setBorder(new EmptyBorder(100, 100, 100, 100));
        jPanel.add(jLabel,BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void create() throws IOException {
        cardLayout = new CardLayout();
        gui.setLayout(cardLayout);
        //JPanel menu = new TestListGUI(mangaList);
        JPanel volumes = new ShowChapters(mangaList.get(0));
        //gui.add(menu, "menu");
        gui.add(volumes, "volumes");
        setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH) / 2, ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        setVisible(true);
        //cardLayout.show(gui,"menu");
        cardLayout.show(gui,"volumes");
        setContentPane(gui);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
