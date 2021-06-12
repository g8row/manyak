package g8row;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class Main {

    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        MangaList list = new MangaList();
        gui.dispose();
        gui = new GUI(list);
        //System.out.println(list.mangaArray.get(1).mangaAttributes.descriptions);
        //System.out.println(list.mangaArray.get(0).mangaAttributes.descriptions.get("fr"));
        /*for(int i = 0;i<list.mangaArray.size();i++) {
            //System.out.println(list.mangaArray.get(i));
            System.out.println(list.mangaArray.get(i).id);
            System.out.println(list.mangaArray.get(i).fileNames.get(0));
        }*/
    }
}
