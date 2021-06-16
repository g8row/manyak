package g8row;

import javax.swing.*;
import java.io.IOException;

public class ShowChapters extends JPanel {
    public Volumes volumes;
    public ShowChapters(Manga manga) throws IOException {
        volumes = new Volumes(manga,"en");

    }
}
