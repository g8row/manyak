package g8row;

import designs.GButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ShowList extends JPanel {
    int page=0;
    int onScreen=5;
    int numButtons;

    public void showList(ArrayList<Manga> list, int offset){
        for(int i = offset; i < onScreen*(page+1); i++){
            Manga manga = list.get(i);
            GButton mangaB = new GButton(manga.title);
            mangaB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        manga.parseMangaAttributes();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JPanel parent = (JPanel) getParent();
                    CardLayout layout = (CardLayout)parent.getLayout();
                    parent.add(new ShowManga(manga),"manga");
                    layout.show(parent,"manga");
                    revalidate();
                }
            });
            add(mangaB);
        }
    }

    public ShowList(Popular popular){
        numButtons = popular.size()+1;
        setLayout(new GridLayout(numButtons,1));
        setPreferredSize(new Dimension(600,600));
        showList(popular,0);
        JPanel buttons = new JPanel(new GridLayout(1,2));
        GButton prev = new GButton("Prev Page");
        prev.setBold();
        GButton next = new GButton("Next Page");
        next.setBold();
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                buttons.removeAll();
                page--;
                if(page!=0){
                    buttons.add(prev);
                }
                showList(popular,page*onScreen);
                buttons.add(next);
                add(buttons);
                revalidate();
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeAll();
                    buttons.removeAll();
                    page++;
                    buttons.add(prev);
                    if(popular.size()<(page+1)*onScreen) {
                        popular.parse(5, page * onScreen);
                    }
                    showList(popular,page*onScreen);
                    buttons.add(next);
                    add(buttons);
                    revalidate();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        buttons.add(next);
        add(buttons);
        revalidate();
    }
    public ShowList(Search search){
        numButtons = search.size()+1;
        setLayout(new GridLayout(numButtons,1));
        setPreferredSize(new Dimension(600,600));
        showList(search,0);
        JPanel buttons = new JPanel(new GridLayout(1,2));
        GButton prev = new GButton("Prev Page");
        prev.setBold();
        GButton next = new GButton("Next Page");
        next.setBold();
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                buttons.removeAll();
                page--;
                if(page!=0){
                    buttons.add(prev);
                }
                showList(search,page*onScreen);
                buttons.add(next);
                add(buttons);
                revalidate();
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    removeAll();
                    buttons.removeAll();
                    page++;
                    buttons.add(prev);

                    showList(search,page*onScreen);
                    buttons.add(next);
                    add(buttons);
                    revalidate();
            }
        });
        buttons.add(next);
        add(buttons);
        revalidate();
    }
}
