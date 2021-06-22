package g8row;

import designs.GButton;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowPages extends JPanel {
    int page=0;
    JScrollPane jScrollPane;
    public ShowPages (Chapter chapter) throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.mangadex.org/at-home/server/" + chapter.id);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(500);
        connection.setReadTimeout(500);

        if(connection.getResponseCode() > 299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            while ((line = reader.readLine())!=null){
                responseContent.append(line);
            }
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine())!=null){
                responseContent.append(line);
            }
        }
        reader.close();
        JSONObject mangaResponse = new JSONObject(responseContent.toString());
        chapter.setAtHomeLocation(mangaResponse.getString("baseUrl"));
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
        String link = chapter.atHomeLocation + "/data/" + chapter.hash + "/" + chapter.data.get(page);
        URL url = new URL(link);
        BufferedImage page = ImageIO.read(url);
        return new JLabel(new ImageIcon(page));
    }
}
