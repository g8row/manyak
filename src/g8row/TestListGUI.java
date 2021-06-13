package g8row;

import designs.GButton;
import designs.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TestListGUI extends JPanel {
    int page=0;
    int onScreen=5;
    int numButtons;

    public void showList(MangaList mangaList, int offset){
        for(int i = offset; i < onScreen*(page+1); i++){
            Manga manga = mangaList.mangaArray.get(i);
            GButton mangaB = new GButton(manga.mangaAttributes.title);
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
    }

    public TestListGUI(MangaList mangaList){
        numButtons = mangaList.mangaArray.size()+1;
        setLayout(new GridLayout(numButtons,1));
        setPreferredSize(new Dimension(600,600));
        showList(mangaList,0);
        GButton prev = new GButton("Prev Page");
        prev.setBold();
        GButton next = new GButton("Next Page");
        next.setBold();
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                page--;
                if(page!=0){
                    add(prev);
                }else{
                    setLayout(new GridLayout(--numButtons,1));
                }
                showList(mangaList,page*onScreen);
                add(next);
                revalidate();
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeAll();
                    page++;

                    if(page==1){
                        setLayout(new GridLayout(++numButtons,1));
                    }
                    add(prev);
                    if(mangaList.mangaArray.size()<(page+1)*onScreen) {
                        mangaList.parse(5, page * onScreen);
                    }
                    showList(mangaList,page*onScreen);
                    add(next);
                    revalidate();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(next);
        revalidate();
    }
}
