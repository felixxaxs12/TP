package interface_adapter.search;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

public class SearchState {
    private String locationName;
    private double latitude;
    private double longitude;
    private String searchError;
    private List<String> stopNames = new ArrayList<>();
    private List<GeoPosition> stops = new ArrayList<>();
    private List<List<GeoPosition>> routeSegments = new ArrayList<>();
    private String errorMessage;
    private List<SearchSuggestionViewData> suggestions = new ArrayList<>();
    private SearchSuggestionViewData resolvedClickLocation;

    public SearchState() {}

    public SearchState(SearchState copy) {
        this.locationName = copy.getLocationName();
        this.latitude = copy.getLatitude();
        this.longitude = copy.getLongitude();
        this.searchError = copy.getSearchError();
        this.stopNames = new ArrayList<>(copy.getStopNames());
        this.stops = new ArrayList<>(copy.getStops());
        this.routeSegments = new ArrayList<>();
        for (List<GeoPosition> segment : copy.getRouteSegments()) {
            this.routeSegments.add(new ArrayList<>(segment));
        }
        this.errorMessage = copy.getErrorMessage();
        this.suggestions = new ArrayList<>(copy.getSuggestions());
        this.resolvedClickLocation = copy.getResolvedClickLocation();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public List<String> getStopNames() {
        return new ArrayList<>(stopNames);
    }

    public void setStopNames(List<String> stopNames) {
        this.stopNames = new ArrayList<>(stopNames);
    }

    public List<GeoPosition> getStops() {
        return new ArrayList<>(stops);
    }

    public void setStops(List<GeoPosition> stops) {
        this.stops = new ArrayList<>(stops);
    }

    public List<List<GeoPosition>> getRouteSegments() {
        List<List<GeoPosition>> copy = new ArrayList<>();
        for (List<GeoPosition> segment : routeSegments) {
            copy.add(new ArrayList<>(segment));
        }
        return copy;
    }

    public void setRouteSegments(List<List<GeoPosition>> routeSegments) {
        List<List<GeoPosition>> copy = new ArrayList<>();
        for (List<GeoPosition> segment : routeSegments) {
            copy.add(new ArrayList<>(segment));
        }
        this.routeSegments = copy;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<SearchSuggestionViewData> getSuggestions() {
        return new ArrayList<>(suggestions);
    }

    public void setSuggestions(List<SearchSuggestionViewData> suggestions) {
        this.suggestions = new ArrayList<>(suggestions);
    }

    public SearchSuggestionViewData getResolvedClickLocation() {
        return resolvedClickLocation;
    }

    public void setResolvedClickLocation(SearchSuggestionViewData resolvedClickLocation) {
        this.resolvedClickLocation = resolvedClickLocation;
    }
}
