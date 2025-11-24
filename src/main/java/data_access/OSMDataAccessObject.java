package data_access;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import entity.Location;
import use_case.search.SearchDataAccessInterface;

public class OSMDataAccessObject implements SearchDataAccessInterface {

    private final HttpClient client;

    public OSMDataAccessObject(HttpClient client) {
        this.client = client;
    }

    @Override
    public boolean existsByName(String locationName) throws IOException, InterruptedException {
        JSONArray array = getNominatimInfoHelper(locationName);
        return !array.isEmpty();
    }

    @Override
    public Location get(String locationName) throws IOException, InterruptedException {
        JSONArray array = getNominatimInfoHelper(locationName);
        JSONObject obj = array.getJSONObject(0);
        String name = obj.getString("display_name");
        double lat = obj.getDouble("lat");
        double lon = obj.getDouble("lon");
        return new Location(name, lat, lon);
    }

    @Override
    public void save(Location location) {
        // TODO document why this method is empty
    }

    @Override
    public void setCurrentLocation(String locationName) {
        // TODO document why this method is empty
    }

    @Override
    public String getCurrentLocationName() {
        return "";
    }

    public Location reverse(double latitude, double longitude) throws IOException, InterruptedException {
        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat="
                + latitude + "&lon=" + longitude + "&zoom=18&addressdetails=0";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "TripPlanner/1.0 (207 5-6)")
                .GET()
                .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Reverse geocode failed: " + response.statusCode());
        }

        JSONObject obj = new JSONObject(response.body());
        String name = obj.optString("display_name", String.format("%.5f, %.5f", latitude, longitude));
        double lat = obj.optDouble("lat", latitude);
        double lon = obj.optDouble("lon", longitude);
        return new Location(name, lat, lon);
    }

    public java.util.List<Location> searchSuggestions(String query, Double minLon, Double minLat, Double maxLon, Double maxLat, int limit) throws IOException, InterruptedException {
        if (query == null || query.isBlank()) return java.util.Collections.emptyList();
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://nominatim.openstreetmap.org/search?q=" + encoded + "&format=json&limit=" + limit + "&addressdetails=0";
        if (minLon != null && minLat != null && maxLon != null && maxLat != null) {
            url += "&viewbox=" + minLon + "," + minLat + "," + maxLon + "," + maxLat + "&bounded=0"; // bounded=0 biases results towards viewbox
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "TripPlanner/1.0 (207 5-6)")
                .GET()
                .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Search request failed: " + response.statusCode());
        }

        JSONArray arr = new JSONArray(response.body());
        java.util.List<Location> out = new java.util.ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            String name = o.optString("display_name", null);
            double lat = o.optDouble("lat", Double.NaN);
            double lon = o.optDouble("lon", Double.NaN);
            if (name != null && !Double.isNaN(lat) && !Double.isNaN(lon)) {
                out.add(new Location(name, lat, lon));
            }
        }
        return out;
    }

    private JSONArray getNominatimInfoHelper(String locationName) throws IOException, InterruptedException {
        String url = "https://nominatim.openstreetmap.org/search?q="
                + java.net.URLEncoder.encode(locationName, StandardCharsets.UTF_8)
                + "&format=json&limit=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "TripPlanner/1.0 (207 5-6)")
                .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONArray(response.body());
    }
}
