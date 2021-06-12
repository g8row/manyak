package g8row;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestListGUI extends JPanel {
    public TestListGUI(MangaList mangaList){
        //JPanel temp = new JPanel(new GridLayout(mangaList.mangaArray.size(),1));
        //temp.setPreferredSize(new Dimension(600,mangaList.mangaArray.size()*100));
        setLayout(new GridLayout(mangaList.mangaArray.size(),1));
        setPreferredSize(new Dimension(600,600));
        //System.out.println(mangaList.mangaArray);
        for(Manga manga:mangaList.mangaArray){
            JButton mangaB = new JButton(manga.mangaAttributes.title);
            mangaB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel parent = (JPanel) getParent();
                    CardLayout layout = (CardLayout)parent.getLayout();
                    parent.add(new ShowManga(manga),"manga");
                    layout.show(parent,"manga");
                    revalidate();
                }
            });
            add(mangaB);
        }
        revalidate();
        //add(temp);
    }
}
