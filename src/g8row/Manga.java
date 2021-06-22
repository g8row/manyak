package g8row;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.*;

class Descriptions extends TreeMap<String,Object>{
    JSONObject descriptionsJSON;
    public Descriptions(JSONObject descriptionsJSON) {
        this.descriptionsJSON = descriptionsJSON;
        for(Map.Entry<String,Object> temp: descriptionsJSON.toMap().entrySet()){
            String value = "<p style=\"font-family:tahoma\">";
            value = value + temp.getValue();
            value = value.replace("[spoiler]", "");
            value = value.replace("[/spoiler]", "");
            value = value.replace("[*]","");
            value = value.replace("[hr]","<p style=\"font-family:tahoma\">");
            value = value.replace('[','<');
            value = value.replace(']','>');
            value = value.replace("url", "a href");



            if(value.lastIndexOf("Portuguese (BR) / Português (BR)")>-1){
                put("br","<p style=\"font-family:tahoma\">" + value.substring(value.lastIndexOf("Portuguese (BR) / Português (BR)")+32) + "</p>");
                value = value.substring(0,value.lastIndexOf("Portuguese (BR) / Português (BR)"));
            }
            if(value.lastIndexOf("PL")>-1){
                put("pl","<p style=\"font-family:tahoma\">" + value.substring(value.lastIndexOf("PL")+2) + "</p>");
                value = value.substring(0,value.lastIndexOf("PL"));
            }
            value = value + "</p>";
            //System.out.println(value);
            put(temp.getKey(),value);
        }
    }

    /*@Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(String key:keySet()){
            str.append(key).append(' ');
        }
        return str.toString();
    }

     */
}
class Links {
    int al;
    String ap;
    int kt;
    int mu;
    String nu;
    int mal;
    URL raw;
    URL engtl;
}
class Relationship {

}

class Tags{

}
class MangaAttributes{

    Map<String,String> altTitles;
    public Descriptions descriptions;
    boolean isLocked;
    Links links;
    String originalLanguage;
    String lastVolume;
    String lastChapter;
    String publicationDemographic;
    String status;
    int year;
    String contentRating;
    Tags tags;
    Date createdAt;
    Date updatedAt;
    Relationship[] relationships;
    public MangaAttributes(JSONArray altTitles, Descriptions descriptions, boolean isLocked, String originalLanguage, String lastVolume, String lastChapter,
                          String publicationDemographic, String status, String contentRating, String createdAt, String updatedAt) {
        parseArray(altTitles,this.altTitles=new HashMap<>());
        this.descriptions = descriptions;
        this.isLocked = isLocked;
        //this.links=links;
        this.originalLanguage = originalLanguage;
        this.lastVolume = lastVolume;
        this.lastChapter = lastChapter;
        this.publicationDemographic = publicationDemographic;
        this.status = status;
        //this.year=year;
        this.contentRating = contentRating;
        //this.tags = tags;
        this.createdAt = Date.from(Instant.parse(createdAt));
        this.updatedAt = Date.from(Instant.parse(updatedAt));
        //this.relationships = relationships;
    }

    public void parseArray(JSONArray array, Map<String,String> map){
        for (int i = 0;i<array.length();i++) {
            JSONObject titleObj = array.getJSONObject(i);
            String lang = titleObj.toString().substring(2, 4);
            String title = titleObj.toString().substring(7, titleObj.toString().length() - 2);
            map.put(title,lang);
        }
    }

    @Override
    public String toString() {
        return
                //", altTitles=" + altTitles +
                //", description='" + descriptions + '\'' +
                //", isLocked=" + isLocked +
                //", links=" + links +
                "Original Language: " + originalLanguage +
                ", Last Volume: " + lastVolume +
                ", Last Chapter:" + lastChapter +
                ", Demographic: " + publicationDemographic +
                ", Status: " + status +
                ", Year: " + year +
                ", Content Rating: " + contentRating +
                //", Tags: " + tags +
                ", Date Created: " + createdAt +
                ", Last updated: " + updatedAt
                //", relationships=" + Arrays.toString(relationships) +
                ;
    }
}

public class Manga {
    String id;
    String type;
    MangaAttributes mangaAttributes;
    BufferedImage cover;
    String title;

    public Manga(String id, String type, String title) throws IOException {

        this.id = id;
        this.type = type;
        this.title=title;
        cover = getCover();
    }


    public void parseMangaAttributes() throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.mangadex.org/manga/" + id);
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
        JSONObject mangaData = mangaResponse.getJSONObject("data");
        JSONObject mangaAttributes = mangaData.getJSONObject("attributes");

        JSONArray altTitles;
        JSONObject description;
        boolean isLocked;
        String originalLanguage;
        //JSONObject links;//i think this gotta be a map?
        String lastVolume;//make it nullable(aka it can be null)
        String lastChapter;//make it nullable(aka it can be null)
        String publicationDemographic;//make it nullable(aka it can be null)
        String status;//make it nullable(aka it can be null)
        int year;//make it nullable(aka it can be null)
        String contentRating;//make it nullable(aka it can be null)
        //JSONArray tags;//you gotta make a tag class
        String createdAt;
        String updatedAt;
        //JSONArray relationships;//gotta make a relationship class with id and type and make it an array

        altTitles = mangaAttributes.getJSONArray("altTitles");
        description = mangaAttributes.getJSONObject("description");
        Descriptions descriptions = new Descriptions(description);
        try{
            isLocked = mangaAttributes.getBoolean("isLocked");
        }catch (JSONException e){
            isLocked = false;
        }
        originalLanguage = mangaAttributes.getString("originalLanguage");
        //links = mangaAttributes.getJSONObject("links");
        try {
            lastVolume = mangaAttributes.getString("lastVolume");
        } catch (JSONException e) {
            lastVolume = null;
        }
        try {
            lastChapter = mangaAttributes.getString("lastChapter");
        } catch (JSONException e) {
            lastChapter = null;
        }
        try {
            publicationDemographic = mangaAttributes.getString("publicationDemographic");
        } catch (JSONException e) {
            publicationDemographic = null;
        }
        try {
            status = mangaAttributes.getString("status");
        } catch (JSONException e) {
            status = null;
        }
        try {
            year = mangaAttributes.getInt("year");
        } catch (JSONException | NumberFormatException e) {
            year = 0;
        }
        try {
            contentRating = mangaAttributes.getString("contentRating");
        } catch (JSONException e) {
            contentRating = null;
        }
        //tags = mangaAttributes.getJSONArray("tags");
        int version;
        version = mangaAttributes.getInt("version");
        createdAt = mangaAttributes.getString("createdAt");
        updatedAt = mangaAttributes.getString("updatedAt");
        //relationships = manga.getJSONArray("relationships");
        //int limit = mangaResponse.getInt("limit");
        //int offset = mangaResponse.getInt("offset");

        this.mangaAttributes = new MangaAttributes(altTitles, descriptions, isLocked, originalLanguage,
                lastVolume, lastChapter, publicationDemographic, status, contentRating, createdAt, updatedAt);
    }

    public BufferedImage getCover() throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();

        String link ="https://api.mangadex.org/cover?limit=1&manga[]="+id+"";
        URL url = new URL(link);
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
        JSONArray mangaArray = mangaResponse.getJSONArray("results");
        String fileName = mangaArray.getJSONObject(0).getJSONObject("data").getJSONObject("attributes").getString("fileName");
        url = new URL("https://uploads.mangadex.org/covers/" + id + '/' + fileName+".256.jpg");
        return ImageIO.read(url);
    }


    @Override
    public String toString() {
        return "Manga{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", mangaAtributes=" + mangaAttributes +
                '}';
    }
}
