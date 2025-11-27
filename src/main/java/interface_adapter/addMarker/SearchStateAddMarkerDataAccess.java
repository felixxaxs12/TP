package interface_adapter.addMarker;

import entity.Location;
import entity.Marker;
import interface_adapter.search.SearchViewModel;
import org.jxmapviewer.viewer.GeoPosition;
import use_case.add_marker.AddMarkerDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapts the add-marker gateway to the {@link SearchViewModel} state so the interactor can work
 * with the current itinerary without depending on Swing components.
 */
public class SearchStateAddMarkerDataAccess implements AddMarkerDataAccessInterface {

    private final SearchViewModel searchViewModel;

    public SearchStateAddMarkerDataAccess(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public boolean exists(Location location) {
        for (GeoPosition gp : searchViewModel.getState().getStops()) {
            if (Double.compare(gp.getLatitude(), location.getLatitude()) == 0
                    && Double.compare(gp.getLongitude(), location.getLongitude()) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(Marker marker) {
        final var current = new interface_adapter.search.SearchState(searchViewModel.getState());
        List<String> names = new ArrayList<>(current.getStopNames());
        names.add(marker.getLocation().getName());
        current.setStopNames(names);

        List<GeoPosition> stops = new ArrayList<>(current.getStops());
        stops.add(new GeoPosition(marker.getLatitude(), marker.getLongitude()));
        current.setStops(stops);
        current.setErrorMessage(null);
        searchViewModel.setState(current);
    }

    @Override
    public List<Marker> getAllMarkers() {
        List<Marker> markers = new ArrayList<>();
        List<String> names = searchViewModel.getState().getStopNames();
        List<GeoPosition> stops = searchViewModel.getState().getStops();
        for (int i = 0; i < stops.size(); i++) {
            GeoPosition gp = stops.get(i);
            String name = i < names.size() ? names.get(i) : "";
            markers.add(new Marker(new Location(name, gp.getLatitude(), gp.getLongitude())));
        }
        return markers;
    }
}

