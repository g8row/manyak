package g8row;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class Main {

    public static void main(String[] args) throws IOException {

        MangaList list = new MangaList();
        GUI gui = new GUI(list.mangaArray);
        for(int i = 0;i<list.mangaArray.size();i++) {
            //System.out.println(list.mangaArray.get(i));
            System.out.println(list.mangaArray.get(i).id);
            System.out.println(list.mangaArray.get(i).fileNames.get(0));
        }
    }
}
