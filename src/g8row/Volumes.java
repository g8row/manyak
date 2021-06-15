package g8row;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

class Chapter implements Comparable<Chapter>{
    String chapter;
    int count;

    public Chapter(String chapter, int count) {
        this.chapter = chapter;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapter='" + chapter + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(Chapter o) {
        return this.chapter.compareTo(o.chapter);
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

    @Override
    public String toString() {
        return "Volume{" +
                "volume='" + volume + '\'' +
                ", \ncount=" + count +
                ", \nchapters=" + chapters +
                '}';
    }
}

class Volumes{
    BufferedReader reader;
    HttpURLConnection connection;
    StringBuilder responseContent = new StringBuilder();
    ArrayList <Volume> volumes;
    public Volumes(Manga manga, String language) throws IOException {
        volumes = new ArrayList<>();

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
            this.volumes.add(volume1);
        }
        System.out.println(this.volumes);
    }
}