package g8row;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MangaList extends ArrayList<Manga>{
    public void parse(int limit, int offset) throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.mangadex.org/manga?limit=" + limit + "&offset=" + offset);
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
        JSONArray mangaList = mangaResponse.getJSONArray("results");
        for (int i=0;i<mangaList.length();i++) {
            //System.out.println(mangaList.getJSONObject(i).getString("result"));
            JSONObject manga = mangaList.getJSONObject(i);
            JSONObject mangaData = manga.getJSONObject("data");
            String id = mangaData.getString("id");
            String type = mangaData.getString("type");

            JSONObject mangaAttributes;
            JSONObject title;
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

            mangaAttributes = mangaData.getJSONObject("attributes");

            title = mangaAttributes.getJSONObject("title");
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

            MangaAttributes Attributes = new MangaAttributes(title, altTitles, descriptions, isLocked, originalLanguage,
                    lastVolume, lastChapter, publicationDemographic, status, contentRating, createdAt, updatedAt);

            add(new Manga(id, type, Attributes));
        }
    }

    public MangaList() throws IOException {
        ensureCapacity(5);
        parse(5,0);
    }
}
