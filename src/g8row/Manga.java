package g8row;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.Date;
import java.util.Map;

class Descriptions {

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
class MangaAtributes{
    Map<String,String> altTitles;
    boolean isLocked;
    Links links;
    String originalLanguage;
    int lastVolume;
    int lastChapter;
    String publicationDemographic;
    String status;
    int year;
    String contentRating;
    Tags tags;
    Date createdAt;
    Date updatedAt;
    Relationship[] relationships;
}
public class Manga {
    String id;
    String type;
    String title;
    public Manga(String id, String type, String title, Map<String, String> altTitles, boolean isLocked, Links links, String originalLanguage, int lastVolume, int lastChapter, String publicationDemographic, String status, int year, String contentRating, Tags tags, Date createdAt, Date updatedAt, Relationship[] relationships) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public Manga() {
    }


}
