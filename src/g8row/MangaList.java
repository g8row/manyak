package g8row;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MangaList {
    ArrayList<Manga> mangaArray;

    public void parse() throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.mangadex.org/manga?limit=5");
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
        JSONArray mangaList = mangaResponse.getJSONArray("results");
        for (int i=0;i<mangaList.length();i++) {
            //System.out.println(mangaList.getJSONObject(i).getString("result"));
            JSONObject manga = mangaList.getJSONObject(i);
            JSONObject mangaData = manga.getJSONObject("data");
            String id = mangaData.getString("id");
            String type = mangaData.getString("type");

            JSONObject mangaAttributes;
            JSONObject title;
            JSONArray altTitles;//you need to make this into an javax.xml.registry.infomodel.LocalizedString array
            JSONObject description;//you need to make this into an javax.xml.registry.infomodel.LocalizedString array
            boolean isLocked;
            String originalLanguage;
            JSONObject links;//i think this gotta be a map?
            String lastVolume = null;//make it nullable(aka it can be null)
            String lastChapter = null;//make it nullable(aka it can be null)
            String publicationDemographic = null;//make it nullable(aka it can be null)
            String status = null;//make it nullable(aka it can be null)
            int year = 0;//make it nullable(aka it can be null)
            String contentRating = null;//make it nullable(aka it can be null)
            JSONArray tags;//you gotta make a tag class
            String createdAt;
            String updatedAt;
            JSONArray relationships;//gotta make a relationship class with id and type and make it an array

            mangaAttributes = mangaData.getJSONObject("attributes");

            title = mangaAttributes.getJSONObject("title");
            altTitles = mangaAttributes.getJSONArray("altTitles");
            description = mangaAttributes.getJSONObject("description");
            isLocked = mangaAttributes.getBoolean("isLocked");
            originalLanguage = mangaAttributes.getString("originalLanguage");
            links = mangaAttributes.getJSONObject("links");
            try {
                lastVolume = mangaAttributes.getString("lastVolume");
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            try {
                lastChapter = mangaAttributes.getString("lastChapter");
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            try {
                publicationDemographic = mangaAttributes.getString("publicationDemographic");
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            try {
                status = mangaAttributes.getString("status");
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            try {
                year = mangaAttributes.getInt("year");
            } catch (JSONException e) {
                //e.printStackTrace();
            } catch (NumberFormatException n) {
                //n.printStackTrace();
            }
            try {
                contentRating = mangaAttributes.getString("contentRating");
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            tags = mangaAttributes.getJSONArray("tags");
            int version;
            version = mangaAttributes.getInt("version");
            createdAt = mangaAttributes.getString("createdAt");
            updatedAt = mangaAttributes.getString("updatedAt");
            relationships = manga.getJSONArray("relationships");
            int limit = mangaResponse.getInt("limit");
            int offset = mangaResponse.getInt("offset");

            MangaAttributes Attributes = new MangaAttributes(title, altTitles, description, isLocked, originalLanguage,
                    lastVolume, lastChapter, publicationDemographic, status, contentRating, createdAt, updatedAt);

            mangaArray.add(new Manga(id, type, Attributes));
        }
    }

    public MangaList() throws IOException {
        mangaArray = new ArrayList<>();
        parse();
    }
}
