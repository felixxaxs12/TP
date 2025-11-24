package use_case.itinerary;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

/**
 * Gateway for persisting the user's ordered itinerary in a source that is
 * independent of the Swing UI.  The remove, reorder, and generate-route
 * interactors all depend on this interface so they can work with the same
 * canonical list of stops without knowing whether it is stored in memory,
 * on disk, or elsewhere.
 */
public interface ItineraryDataAccessInterface {
    List<GeoPosition> getStops();

    void addStop(GeoPosition position);

    GeoPosition removeStop(int index);

    List<GeoPosition> reorderStops(int fromIndex, int toIndex);
}
