package interface_adapter.itinerary;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.util.List;

public class ItineraryState {
    private List<String> stopNames;
    private List<GeoPosition> stops;
    private List<List<GeoPosition>> routeSegments;
    private String errorMessage;

    public ItineraryState() {
        this.stopNames = new ArrayList<>();
        this.stops = new ArrayList<>();
        this.routeSegments = new ArrayList<>();
    }

    public ItineraryState(ItineraryState copy) {
        this.stopNames = new ArrayList<>(copy.getStopNames());
        this.stops = new ArrayList<>(copy.getStops());
        this.routeSegments = new ArrayList<>();
        for (List<GeoPosition> segment : copy.getRouteSegments()) {
            this.routeSegments.add(new ArrayList<>(segment));
        }
        this.errorMessage = copy.getErrorMessage();
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
}
