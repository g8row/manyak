package g8row;

import designs.BackButton;
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
    JPanel jPanel;

    public void showNewPage(ArrayList<Manga> list, int offset){
        for(int i = offset; i < onScreen*(page+1); i++){
            try {
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
                        CardLayout layout = (CardLayout) parent.getLayout();
                        parent.add(new ShowManga(manga), "manga");
                        layout.show(parent, "manga");
                        revalidate();
                    }
                });
                jPanel.add(mangaB);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ShowList(MangaList mangaList){
        setLayout(new BorderLayout());
        jPanel = new JPanel();
        numButtons = mangaList.size()+1;
        jPanel.setLayout(new GridLayout(numButtons,1));
        jPanel.setPreferredSize(new Dimension(600,600));
        showNewPage(mangaList,0);
        JPanel buttons = new JPanel(new GridLayout(1,2));
        GButton prev = new GButton("Prev Page");
        prev.setBold();
        GButton next = new GButton("Next Page");
        next.setBold();
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel.removeAll();
                buttons.removeAll();
                page--;
                if(page!=0){
                    buttons.add(prev);
                }
                showNewPage(mangaList,page*onScreen);
                buttons.add(next);
                jPanel.add(buttons);
                revalidate();
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jPanel.removeAll();
                    buttons.removeAll();
                    page++;
                    buttons.add(prev);
                    if(mangaList.size()<(page+1)*onScreen) {
                        if(mangaList.titleSearch.equals("")) {
                            mangaList.parse(5, page * onScreen);
                        }else{
                            mangaList.parse(5, page * onScreen, mangaList.titleSearch);
                        }
                    }

                    showNewPage(mangaList,page*onScreen);

                    buttons.add(next);
                    jPanel.add(buttons);
                    buttons.revalidate();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        buttons.add(next);

        jPanel.add(buttons);
        BackButton backButton = new BackButton();
        backButton.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "search");
                layout.removeLayoutComponent(getParent());
                revalidate();
            }
        });
        add(backButton, BorderLayout.NORTH);
        add(jPanel, BorderLayout.CENTER);
    }
}
