package g8row;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI extends JFrame{
    final JPanel gui;
    CardLayout cardLayout;
    public static final int WIDTH = 600, HEIGHT = 600;

    public GUI (){
        setTitle("Manyak");
        ImageIcon icon = new ImageIcon("src/designs/manyak-icon.jpg");
        setIconImage(icon.getImage());
        cardLayout = new CardLayout();
        gui = new JPanel(cardLayout);
        ShowSearch search = new ShowSearch();
        search.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!search.searchBar.getText().equals("")) {
                    try {
                        create(new MangaList(search.searchBar.getText()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        search.showPopular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    create(new MangaList());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        gui.add(search,"search");
        cardLayout.show(gui,"search");
        setContentPane(gui);
        setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH) / 2, ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void create(MangaList mangaList) throws IOException {
        JPanel menu = new ShowList(mangaList);
        gui.add(menu, "list");
        cardLayout.show(gui,"list");
    }
}
