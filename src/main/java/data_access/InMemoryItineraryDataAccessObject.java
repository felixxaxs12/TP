package data_access;

import org.jxmapviewer.viewer.GeoPosition;
import use_case.itinerary.ItineraryDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple in-memory implementation of {@link ItineraryDataAccessInterface} so
 * itinerary-focused use cases can share a single, canonical list of stops
 * without coupling to the Swing view.  Replace this with a persistent
 * implementation if the itinerary should survive application restarts.
 */
public class InMemoryItineraryDataAccessObject implements ItineraryDataAccessInterface {
    private final List<GeoPosition> stops = new ArrayList<>();

    @Override
    public List<GeoPosition> getStops() {
        return new ArrayList<>(stops);
    }

    @Override
    public void addStop(GeoPosition position) {
        stops.add(position);
    }

    @Override
    public GeoPosition removeStop(int index) {
        return stops.remove(index);
    }

    @Override
    public List<GeoPosition> reorderStops(int fromIndex, int toIndex) {
        GeoPosition pos = stops.remove(fromIndex);
        stops.add(toIndex, pos);
        return getStops();
    }
}
