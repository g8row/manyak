package g8row;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MangaList {
    ArrayList<Manga> mangaList;
    public static void parse(String line){
        JSONObject mangaResponse = new JSONObject(line);
        JSONArray mangaList = mangaResponse.getJSONArray("results");

        System.out.println(mangaList.getJSONObject(0).getString("result"));
    }
}
