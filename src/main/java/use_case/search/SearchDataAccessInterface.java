package use_case.search;

import entity.Location;

import java.io.IOException;

public interface SearchDataAccessInterface {

    boolean existsByName(String locationName) throws IOException, InterruptedException;

    void save(Location location);

    Location get(String locationName) throws IOException, InterruptedException;

    void setCurrentLocation(String locationName);

    String getCurrentLocationName();

    java.util.List<Location> searchSuggestions(String query,
                                               Double minLon,
                                               Double minLat,
                                               Double maxLon,
                                               Double maxLat,
                                               int limit) throws IOException, InterruptedException;

    Location reverse(double latitude, double longitude) throws IOException, InterruptedException;
}
