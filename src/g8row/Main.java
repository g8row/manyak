package g8row;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        String line;
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();
	    System.out.println("manyak online");
        URL url = new URL("https://api.mangadex.org/manga");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        System.out.println(connection.getResponseCode());

        if(connection.getResponseCode() > 299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            line = reader.readLine();
            //while ((line = reader.readLine())!=null){
                responseContent.append(line);
            //}
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            line = reader.readLine();
            //while ((line = reader.readLine())!=null){
            responseContent.append(line);
            //}
            reader.close();
        }
        /*HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.mangadex.org/manga")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
        System.out.println(responseContent);*/
        MangaList list = new MangaList(responseContent.toString());
        System.out.print(list.mangaArray.get(0));
    }
}
