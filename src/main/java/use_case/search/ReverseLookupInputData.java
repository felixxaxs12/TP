package use_case.search;

public class ReverseLookupInputData {
    private final double latitude;
    private final double longitude;

    public ReverseLookupInputData(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
