package g8row;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class GUI {
    JFrame gui;
    JPanel temp;
    public static final int WIDTH = 400, HEIGHT=400;
    public GUI (ArrayList<Manga> mangaList){
        create();
        showManga(mangaList);
    }

    public void showManga(ArrayList<Manga> mangaList){
        BufferedImage image;

        try {
            URL url = new URL ("https://uploads.mangadex.org/covers/" + mangaList.get(0).id +'/'+ mangaList.get(0).fileNames.get(1));
            image = ImageIO.read(url);
            temp.add(new JLabel(new ImageIcon(image.getScaledInstance(100,100,Image.SCALE_FAST))));
            temp.revalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void create(){
        gui = new JFrame();
        temp = new JPanel(new FlowLayout());
        gui.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-WIDTH)/2,((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        gui.setVisible(true);
        gui.setContentPane(temp);
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
