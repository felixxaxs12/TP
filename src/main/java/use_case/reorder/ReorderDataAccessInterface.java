package use_case.reorder;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

/**
 * Gateway for retrieving the current itinerary stops for the reorder use case.
 * This keeps the interactor independent from any concrete storage (view model,
 * in-memory list, etc.).
 */
public interface ReorderDataAccessInterface {

    List<String> getStopNames();

    List<GeoPosition> getStops();
}
