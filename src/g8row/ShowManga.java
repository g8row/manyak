package g8row;

import designs.GButton;
import designs.RoundedBorder;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ShowManga extends JPanel {
    public ShowManga(Manga manga) {
        JTextPane jTextPane;
        JPanel buttons;

        add(new JLabel(new ImageIcon(manga.cover.getScaledInstance(100, 150, Image.SCALE_FAST))));
        jTextPane = new JTextPane();
        jTextPane.setContentType("text/html");
        buttons = new JPanel();
        for (String key : manga.mangaAttributes.descriptions.keySet()) {
            if (manga.mangaAttributes.descriptions.get(key) != null) {
                GButton descB = new GButton(key);
                descB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jTextPane.setText("");
                        HTMLDocument doc = (HTMLDocument) jTextPane.getStyledDocument();
                        try {
                            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), (String) manga.mangaAttributes.descriptions.get(key));
                        } catch (BadLocationException | IOException ex) {
                            ex.printStackTrace();
                        }
                        jTextPane.setCaretPosition(0);
                    }
                });
                buttons.add(descB);
            }
        }
        HTMLDocument doc = (HTMLDocument) jTextPane.getStyledDocument();
        try {
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), (String) manga.mangaAttributes.descriptions.get("en"));
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
        JScrollPane jScrollPane = new JScrollPane(jTextPane);
        jScrollPane.setPreferredSize(new Dimension(400, 400));
        add(buttons);
        add(jScrollPane);
        GButton backButton = new GButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "menu");
                layout.removeLayoutComponent(getParent().getParent());
                revalidate();
            }
        });
        add(backButton);
        revalidate();

        revalidate();
    }
}
