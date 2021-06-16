package g8row;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

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
        JPanel menu = new TestListGUI(mangaList);
        gui.add(menu, "menu");
        setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH) / 2, ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        setVisible(true);
        cardLayout.show(gui,"menu");
        setContentPane(gui);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
