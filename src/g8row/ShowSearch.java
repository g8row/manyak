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
        GridBagConstraints bagConstraints = new GridBagConstraints();
        showPopular = new GButton("Show Popular Manga");
        //showPopular.setPreferredSize();
        bagConstraints.fill = GridBagConstraints.CENTER;
        add(searchBox, bagConstraints);
        bagConstraints.fill = GridBagConstraints.PAGE_END;
        bagConstraints.gridy = 5;
        add(showPopular, bagConstraints);
    }
}
