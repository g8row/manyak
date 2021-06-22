package g8row;

import designs.BackButton;
import designs.GButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ShowManga extends JPanel {
    Manga manga;

    public JLabel coverArt(){
        JLabel jLabel = new JLabel(new ImageIcon(manga.cover.getScaledInstance(100, 150, Image.SCALE_FAST)));
        jLabel.setBorder(new LineBorder(new Color(120, 82, 255),2,true));
        return jLabel;

    }
    public JPanel titlePanel(){
        JPanel jPanel = new JPanel(new BorderLayout());
        //jPanel.setPreferredSize(new Dimension(300,200));
        jPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel title = new JLabel(manga.title);

        title.setFont(new Font("Tahoma", Font.BOLD, 17));
        JLabel moreInfo = new JLabel("<html><p style=\"width:300px\">"+manga.mangaAttributes.toString()+"</p></html>");
        //moreInfo.setPreferredSize(new Dimension(250,200));
        moreInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jPanel.add(title,BorderLayout.NORTH);
        jPanel.add(moreInfo, BorderLayout.CENTER);
        return jPanel;
    }
    public JPanel descriptionsPanel(){
        JTextPane jTextPane;
        JPanel buttons;
        JPanel descriptions = new JPanel(new BorderLayout());
        descriptions.setBorder(new EmptyBorder(10, 10, 10, 10));
        jTextPane = new JTextPane();
        jTextPane.setContentType("text/html");
        buttons = new JPanel(new GridLayout(1,manga.mangaAttributes.descriptions.keySet().size()));
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
        descriptions.add(buttons, BorderLayout.NORTH);
        descriptions.add(jScrollPane, BorderLayout.CENTER);
        return descriptions;
    }

    public ShowManga(Manga manga) {
        this.manga = manga;
        setLayout(new BorderLayout());
        JPanel mangaPanel = new JPanel(new BorderLayout());
        BackButton backButtonPanel = new BackButton();
        JPanel top = new JPanel(new BorderLayout());
        top.add(coverArt(),BorderLayout.WEST);
        top.add(titlePanel(), BorderLayout.CENTER);
        mangaPanel.add(top,BorderLayout.NORTH);
        mangaPanel.add(descriptionsPanel(),BorderLayout.CENTER);
        backButtonPanel.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "menu");
                layout.removeLayoutComponent(getParent());
                revalidate();
            }
        });
        GButton read = new GButton("Start Reading");
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                try {
                    parent.add(new ShowVolumes(manga),"volumes");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                layout.show(parent,"volumes");
                revalidate();
            }
        });
        mangaPanel.add(read, BorderLayout.SOUTH);
        add(backButtonPanel, BorderLayout.NORTH);
        add(mangaPanel, BorderLayout.CENTER);
        revalidate();
    }
}
