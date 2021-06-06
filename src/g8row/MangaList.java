package g8row;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.registry.infomodel.LocalizedString;
import java.util.ArrayList;

public class MangaList {
    ArrayList<Manga> mangaList;
    public static void parse(String line){
        JSONObject mangaResponse = new JSONObject(line);
        
        JSONArray mangaList = mangaResponse.getJSONArray("results");
        System.out.println(mangaList.getJSONObject(0).getString("result"));
        JSONObject manga = mangaList.getJSONObject(0);
        int id = manga.getInt("id");
        String type = manga.getString("type");
        
        JSONObject mangaAttributes = manga.getJSONObject("attributes");
        JSONObject altTitles = mangaAttributes.getJSONObject("altTitles");//you need to make this into an javax.xml.registry.infomodel.LocalizedString array
        JSONObject description = mangaAttributes.getJSONObject("description");//you need to make this into an javax.xml.registry.infomodel.LocalizedString array
        boolean isLocked = mangaAttributes.getBoolean("isLocked");
        JSONObject links = mangaAttributes.getJSONObject("links");//i think this gotta be a map?
        String lastVolume = mangaAttributes.getString("lastVolume");//make it nullable(aka it can be null)
        String publicationDemographic = mangaAttributes.getString("publicationDemographic");//make it nullable(aka it can be null)
        String status = mangaAttributes.getString("status");//make it nullable(aka it can be null)
        String year = mangaAttributes.getString("year");//make it nullable(aka it can be null)
        String contentRating = mangaAttributes.getString("contentRating");//make it nullable(aka it can be null)
        JSONObject tags = mangaAttributes.getJSONObject("tags");//you gotta make a tag class
        int version = mangaAttributes.getInt("version");
        String createdAt = mangaAttributes.getString("createdAt");
        String updatedAt = mangaAttributes.getString("updatedAt");
        
        JSONObject relationships = manga.getJSONObject("relationships");//gotta make a relationship class with id and type and make it an array
        
        int limit = manga.getInt("limit");
        int offset = manga.getInt("offset");
        int total = manga.getInt("total");
    }
}
