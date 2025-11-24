package use_case.search;

public class SearchOutputData {

    private final String locationName;
    private final double latitude;
    private final double longitude;

    public SearchOutputData(String locationName, double lat, double lon) {
        this.locationName = locationName;
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
