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
    String titleSearch = "";

    public void parse(int limit, int offset) throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.mangadex.org/manga?limit=" + limit + "&offset=" + offset);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(200);
        connection.setReadTimeout(200);

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
            JSONObject manga = mangaList.getJSONObject(i);
            JSONObject mangaData = manga.getJSONObject("data");
            String id = mangaData.getString("id");
            String type = mangaData.getString("type");
            String title = mangaData.getJSONObject("attributes").getJSONObject("title").toString();
            title = title.substring(7, title.length() - 2);
            add(new Manga(id, type, title));
        }
    }
    public void parse(int limit, int offset, String titleSearch) throws IOException {
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.mangadex.org/manga?limit=" + limit + "&offset=" + offset + "&title={" + titleSearch+"}");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(200);
        connection.setReadTimeout(200);

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
            String title = mangaData.getJSONObject("attributes").getJSONObject("title").toString();
            title = title.substring(7, title.length() - 2);

            add(new Manga(id, type, title));
        }
    }
    public MangaList(){
        ensureCapacity(5);
        try {
            parse(5,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MangaList(String titleSearch){
        this.titleSearch = titleSearch;
        ensureCapacity(5);
        try {
            parse(5,0, titleSearch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
