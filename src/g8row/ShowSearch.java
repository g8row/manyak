package g8row;

import designs.GButton;

import javax.swing.*;
import java.awt.*;

public class ShowSearch extends JPanel {
    JPanel searchBox;
    JTextField searchBar;
    JButton searchButton;
    JButton showPopular;
    public ShowSearch() {
        searchBox = new JPanel(new BorderLayout());
        searchBar = new JTextField();
        searchBar.setColumns(10);
        searchButton = new GButton("Search");
        searchButton.setPreferredSize(new Dimension(50,50));
        searchBox.add(searchBar,BorderLayout.WEST);
        searchBox.add(searchButton,BorderLayout.EAST);
        setLayout(new GridBagLayout());

        showPopular = new GButton("Show Popular Manga");
        add(searchBox,SwingConstants.CENTER);
    }
}
