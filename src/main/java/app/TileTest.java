package app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TileTest {
    public static void main(String[] args) throws Exception {
        String url = "https://tile.openstreetmap.org/14/4578/5979.png";
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "MyMapApp/1.0 (me@example.com)")
                .GET()
                .build();

        HttpResponse<byte[]> resp = client.send(req, HttpResponse.BodyHandlers.ofByteArray());
        System.out.println("status: " + resp.statusCode());
        System.out.println("length: " + resp.body().length);
    }
}
