package g8row;

import designs.BackButton;
import designs.GButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class ShowVolumes extends JPanel{
    public Volumes volumes;
    JScrollPane jScrollPane;
    JComboBox<String> jComboBox;

    public ShowVolumes(Manga manga) throws IOException {
        JPanel volumesPanel = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        volumes = new Volumes(manga,"en");
        //jScrollPane = new JScrollPane();
        volumes.parseVolumes();
        jComboBox = new JComboBox<>();
        jComboBox.setEditable(true);
        jComboBox.setSelectedItem("Select a volume:");
        //jComboBox.addItem("Select a volume:");
        jComboBox.setEditable(false);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel jPanel;
                try {
                    remove(jScrollPane);
                }catch (Exception ex){
                    //ex.printStackTrace();
                }
                jPanel = new JPanel(new GridLayout(volumes.get(jComboBox.getSelectedIndex() ).chapters.size(), 1));
                for (Chapter chapter : volumes.get(jComboBox.getSelectedIndex()).chapters) {
                    GButton jButton;
                    if(chapter.title!=null) {
                        jButton = new GButton(chapter.chapter + ": " + chapter.title);
                    }else {
                        jButton = new GButton(chapter.chapter);
                    }
                    jButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JPanel parent = (JPanel) getParent();
                            CardLayout layout = (CardLayout) parent.getLayout();
                            try {
                                parent.add(new ShowPages(chapter),"chapter");
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            layout.show(parent,"chapter");
                            revalidate();
                        }
                    });
                    jPanel.add(jButton);
                }
                jScrollPane = new JScrollPane(jPanel);
                volumesPanel.add(jScrollPane, BorderLayout.CENTER);
                revalidate();
            }
        });

        for(Volume volume: volumes) {
            jComboBox.addItem("Volume " + volume.volume);
        }

        volumesPanel.add(jComboBox, BorderLayout.NORTH);

        BackButton backButtonPanel = new BackButton();
        add(backButtonPanel,BorderLayout.NORTH);
        backButtonPanel.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "manga");
                layout.removeLayoutComponent(getParent());
                revalidate();
            }
        });
        add(volumesPanel);
    }
}
