package use_case.search;

import entity.Location;

import java.io.IOException;

public interface SearchDataAccessInterface {

    boolean existsByName(String locationName) throws IOException, InterruptedException;

    void save(Location location);

    Location get(String locationName) throws IOException, InterruptedException;

    void setCurrentLocation(String locationName);

    String getCurrentLocationName();
}
