package interface_adapter.remove_marker;

import interface_adapter.itinerary.ItineraryState;
import interface_adapter.itinerary.ItineraryViewModel;
import use_case.remove_marker.RemoveMarkerOutputBoundary;
import use_case.remove_marker.RemoveMarkerOutputData;

import java.util.ArrayList;
import java.util.List;

public class RemoveMarkerPresenter implements RemoveMarkerOutputBoundary {

    private final ItineraryViewModel itineraryViewModel;

    public RemoveMarkerPresenter(ItineraryViewModel itineraryViewModel) {
        this.itineraryViewModel = itineraryViewModel;
    }

    @Override
    public void prepareSuccessView(RemoveMarkerOutputData outputData) {
        ItineraryState current = itineraryViewModel.getState();
        ItineraryState newState = new ItineraryState(current);

        List<String> names = new ArrayList<>(newState.getStopNames());
        int idx = outputData.getRemovedIndex();
        if (idx >= 0 && idx < names.size()) {
            names.remove(idx);
        }
        newState.setStopNames(names);
        newState.setStops(outputData.getStops());
        newState.setErrorMessage(null);
        itineraryViewModel.setState(newState);
        itineraryViewModel.firePropertyChange("stops");
    }

    @Override
    public void prepareFailView(String error) {
        ItineraryState state = itineraryViewModel.getState();
        ItineraryState newState = new ItineraryState(state);
        newState.setErrorMessage(error);
        itineraryViewModel.setState(newState);
        itineraryViewModel.firePropertyChange("error");
    }
}
