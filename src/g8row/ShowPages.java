package g8row;

import designs.GButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowPages extends JPanel {
    Chapter chapter;
    int page=0;
    JScrollPane jScrollPane;
    public ShowPages (Chapter chapter) throws IOException {
        jScrollPane = new JScrollPane(showPage(chapter));
        setLayout(new BorderLayout());

        JPanel buttons = new JPanel(new GridLayout(1,3));

        GButton prevButton = new GButton("<==");
        GButton backButton = new GButton("Back");
        GButton nextButton = new GButton("==>");

        buttons.add(prevButton);
        buttons.add(backButton);
        buttons.add(nextButton);

        add(buttons, BorderLayout.SOUTH);

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page>0){
                    remove(jScrollPane);
                    try{
                        page--;
                        add(jScrollPane = new JScrollPane(showPage(chapter)),BorderLayout.CENTER);
                        revalidate();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    revalidate();
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page<chapter.data.size()){
                    remove(jScrollPane);
                    try{
                        page++;
                        add(jScrollPane = new JScrollPane(showPage(chapter)),BorderLayout.CENTER);
                        revalidate();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    revalidate();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "volumes");
                layout.removeLayoutComponent(getParent());
                revalidate();
            }
        });

        add(jScrollPane,BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
    public JLabel showPage(Chapter chapter) throws IOException {
        String link = "https://uploads.mangadex.org/data/" + chapter.hash + "/" + chapter.data.get(page);
        System.out.println(link);
        URL url = new URL(link);
        BufferedImage page = ImageIO.read(url);
        return new JLabel(new ImageIcon(page));
    }
}
