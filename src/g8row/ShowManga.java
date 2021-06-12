package g8row;

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
        buttons = new JPanel();
        for (String key : manga.mangaAttributes.descriptions.keySet()) {
            if (manga.mangaAttributes.descriptions.get(key) != null) {
                JButton jButton = new JButton(key);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jTextPane.setText("");
                        jTextPane.setPreferredSize(new Dimension(400, 400));
                        jTextPane.setContentType("text/html");
                        jTextPane.setText(null);
                        HTMLDocument doc = (HTMLDocument) jTextPane.getStyledDocument();
                        try {
                            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), (String) manga.mangaAttributes.descriptions.get(key));
                        } catch (BadLocationException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                buttons.add(jButton);
            }
        }
        JScrollPane jScrollPane = new JScrollPane(jTextPane);
        jScrollPane.setPreferredSize(new Dimension(400, 400));
        add(buttons);
        add(jScrollPane);
        JButton backButton = new JButton("Back");
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
