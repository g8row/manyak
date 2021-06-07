package g8row;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.registry.infomodel.LocalizedString;
import java.util.ArrayList;

public class MangaList {
    ArrayList<Manga> mangaArray;
    public void parse(String line){
        JSONObject mangaResponse = new JSONObject(line);
        
        JSONArray mangaList = mangaResponse.getJSONArray("results");
        System.out.println(mangaList.getJSONObject(0).getString("result"));
        JSONObject manga = mangaList.getJSONObject(0);
        JSONObject mangaData = manga.getJSONObject("data");
        String id = mangaData.getString("id");
        String type = mangaData.getString("type");
        
        JSONObject mangaAttributes = mangaData.getJSONObject("attributes");
        JSONArray altTitles = mangaAttributes.getJSONArray("altTitles");//you need to make this into an javax.xml.registry.infomodel.LocalizedString array
        JSONObject description = mangaAttributes.getJSONObject("description");//you need to make this into an javax.xml.registry.infomodel.LocalizedString array
        boolean isLocked = mangaAttributes.getBoolean("isLocked");
        JSONObject links = mangaAttributes.getJSONObject("links");//i think this gotta be a map?
        String lastVolume = mangaAttributes.getString("lastVolume");//make it nullable(aka it can be null)
        String publicationDemographic = mangaAttributes.getString("publicationDemographic");//make it nullable(aka it can be null)
        String status = mangaAttributes.getString("status");//make it nullable(aka it can be null)
        //Integer year = mangaAttributes.getInt("year");//make it nullable(aka it can be null)
        String contentRating = mangaAttributes.getString("contentRating");//make it nullable(aka it can be null)
        JSONArray tags = mangaAttributes.getJSONArray("tags");//you gotta make a tag class
        int version = mangaAttributes.getInt("version");
        String createdAt = mangaAttributes.getString("createdAt");
        String updatedAt = mangaAttributes.getString("updatedAt");
        
        JSONArray relationships = manga.getJSONArray("relationships");//gotta make a relationship class with id and type and make it an array
        
        int limit = mangaResponse.getInt("limit");
        int offset = mangaResponse.getInt("offset");
        int total = mangaResponse.getInt("total");
        mangaArray.add(new Manga(id,type));
    }
    public MangaList(String line){
        mangaArray = new ArrayList<>();
        parse(line);
    }
}
