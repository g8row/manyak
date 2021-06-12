package g8row;



import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.*;

class Descriptions extends HashMap<String,Object>{
    JSONObject descriptionsJSON;
    public Descriptions(JSONObject descriptionsJSON) {
        this.descriptionsJSON = descriptionsJSON;
        for(Map.Entry<String,Object> temp: descriptionsJSON.toMap().entrySet()){
            String value = (String) temp.getValue();
            value = value.replace('[','<');
            value = value.replace(']','>');
            value = value.replace("url", "a href");
            value = value.replace("<spoiler>", "");
            value = value.replace("</spoiler>", "");
            if(value.lastIndexOf("Portuguese (BR) / Português (BR)")>-1){
                put("br",value.substring(value.lastIndexOf("Portuguese (BR) / Português (BR)")+32));
                value = value.substring(0,value.lastIndexOf("Portuguese (BR) / Português (BR)"));
            }
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
	String title;
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
    public MangaAttributes(JSONObject title, JSONArray altTitles, Descriptions descriptions, boolean isLocked, String originalLanguage, String lastVolume, String lastChapter,
                          String publicationDemographic, String status, String contentRating, String createdAt, String updatedAt) {
        this.title = parseTitle(title);
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
    public String parseTitle(JSONObject titleObj){
        return titleObj.toString().substring(7, titleObj.toString().length() - 2);
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
        return "MangaAttributes{" +
                "title='" + title + '\'' +
                ", altTitles=" + altTitles +
                ", description='" + descriptions + '\'' +
                ", isLocked=" + isLocked +
                ", links=" + links +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", lastVolume='" + lastVolume + '\'' +
                ", lastChapter='" + lastChapter + '\'' +
                ", publicationDemographic='" + publicationDemographic + '\'' +
                ", status='" + status + '\'' +
                ", year=" + year +
                ", contentRating='" + contentRating + '\'' +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", relationships=" + Arrays.toString(relationships) +
                '}';
    }
}
public class Manga {
    String id;
    String type;
    MangaAttributes mangaAttributes;
    BufferedImage cover;


    public Manga(String id, String type, MangaAttributes mangaAtributes) throws IOException {
        this.id = id;
        this.type = type;
        this.mangaAttributes = mangaAtributes;
        cover = getCover();
    }

    public BufferedImage getCover() throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();

        String link ="https://api.mangadex.org/cover?limit=1&manga[]="+id+"";
        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if(connection.getResponseCode() > 299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            while ((line = reader.readLine())!=null){
                responseContent.append(line);
            }
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine())!=null){
                responseContent.append(line);
            }
            reader.close();
        }
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
