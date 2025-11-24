package use_case.search;

public class SearchInputData {

    private final String locationName;

    public SearchInputData(String locationName) {
        this.locationName = locationName;
    }

    String getLocationName() {
        return locationName;
    }


}
