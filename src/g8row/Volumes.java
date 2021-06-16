package g8row;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

class Chapter implements Comparable<Chapter>{
    String chapter;
    int count;
    String title;
    String volume;
    String translatedLanguage;
    String hash;
    ArrayList<String> data;
    ArrayList<String> dataSaver;
    String uploader;
    int version;
    String createdAt;
    String updatedAt;
    String publishAt;
    public Chapter(String chapter, int count) {
        this.chapter = chapter;
        this.count = count;
    }

    public void addAttributes(String title, String volume, String translatedLanguage, String hash,
                              ArrayList<Object> data, ArrayList<Object> dataSaver, String uploader, int version,
                              String createdAt, String updatedAt, String publishAt) {

        this.title = title;
        this.volume = volume;
        this.translatedLanguage = translatedLanguage;
        this.hash = hash;
        this.data = new ArrayList<>();
        for (Object entry:data){
            this.data.add((String)entry);
        }
        this.dataSaver = new ArrayList<>();
        for (Object entry:dataSaver){
            this.dataSaver.add((String)entry);
        }
        this.uploader = uploader;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.publishAt = publishAt;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapter='" + chapter + '\n' +
                ", count=" + count + '\n' +
                ", title='" + title + '\n' +
                ", volume='" + volume + '\n' +
                ", translatedLanguage='" + translatedLanguage + '\n' +
                ", hash='" + hash + '\n' +
                ", data=" + data + '\n' +
                ", dataSaver=" + dataSaver + '\n' +
                ", uploader='" + uploader + '\n' +
                ", version=" + version + '\n' +
                ", createdAt='" + createdAt + '\n' +
                ", updatedAt='" + updatedAt + '\n' +
                ", publishAt='" + publishAt + '\n' +
                '}';
    }

    @Override
    public int compareTo(Chapter o) {

        return Double.compare(Double.parseDouble(this.chapter), Double.parseDouble(o.chapter));
    }
}

class Volume{
    String volume;
    int count;
    ArrayList<Chapter> chapters;

    public Volume(String volume, int count, ArrayList<Chapter> chapters) {
        this.chapters = chapters;
        this.volume = volume;
        this.count = count;
        Collections.sort(chapters);
    }

    public void parseChapters(Manga manga, String language) throws IOException {
        Chapter chapter = chapters.get(0);
        //for(Chapter chapter: chapters) {
            BufferedReader reader;
            HttpURLConnection connection;
            StringBuilder responseContent = new StringBuilder();
            String link = "https://api.mangadex.org/chapter?manga=" + manga.id + "&volume=" + volume + "&chapter=" + chapter.chapter + "&translatedLanguage[]=" + language;
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);

            if (connection.getResponseCode() > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            }

            JSONObject chapterResponse = new JSONObject(responseContent.toString());
            JSONArray results = chapterResponse.getJSONArray("results");
            JSONObject chapterResult = results.getJSONObject(0);
            JSONObject dataResult = chapterResult.getJSONObject("data");
            JSONObject attributes = dataResult.getJSONObject("attributes");
            String title = attributes.getString("title");
            String volume = attributes.getString("volume");
            String translatedLanguage = attributes.getString("translatedLanguage");
            String hash = attributes.getString("hash");
            ArrayList<Object> data = (ArrayList<Object>) attributes.getJSONArray("data").toList();

            ArrayList<Object> dataSaver = (ArrayList<Object>) attributes.getJSONArray("dataSaver").toList();
            String uploader;
            try {
                uploader = attributes.getString("uploader");
            }catch (JSONException e){
                uploader = null;
            }
            int version = attributes.getInt("version");
            String createdAt = attributes.getString("createdAt");
            String updatedAt = attributes.getString("updatedAt");
            String publishAt = attributes.getString("publishAt");

            chapter.addAttributes(title,volume,translatedLanguage,hash,data,dataSaver,uploader,version,createdAt,updatedAt,publishAt);
            reader.close();
        //}
    }
    @Override
    public String toString() {
        return "Volume{" +
                "volume='" + volume + '\'' +
                ", \ncount=" + count +
                ", \nchapters=" + chapters +
                '}';
    }
}

class Volumes extends ArrayList <Volume>{
    Manga manga;
    String language;
    public Volumes(Manga manga, String language) throws IOException {
        this.manga = manga;
        this.language = language;
        BufferedReader reader;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
        String link = "https://api.mangadex.org/manga/" + manga.id + "/aggregate?translatedLanguage[]=en";
        URL url = new URL(link);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(500);
        connection.setReadTimeout(500);

        if (connection.getResponseCode() > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
        }
        reader.close();
        JSONObject volumesResponse = new JSONObject(responseContent.toString());
        JSONObject volumes = volumesResponse.getJSONObject("volumes");
        for(Map.Entry<String,Object> entry: volumes.toMap().entrySet()){
            HashMap volume = (HashMap) entry.getValue();
            HashMap<String,Object> chapters = (HashMap<String,Object>) volume.get("chapters");
            ArrayList<Chapter> chapters1 = new ArrayList<>();
            for(Map.Entry<String,Object> entry1: chapters.entrySet()){
                HashMap chapter = (HashMap) entry1.getValue();
                Chapter chapter1 = new Chapter((String) chapter.get("chapter"),(int) chapter.get("count"));
                chapters1.add(chapter1);
            }
            Volume volume1 = new Volume((String) volume.get("volume"),(Integer) volume.get("count"),chapters1);
            add(volume1);
        }
    }
    public void parseVolumes() throws IOException {
        for (Volume volume: this){
            volume.parseChapters(manga,language);
        }
    }
}