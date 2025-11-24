package interface_adapter.reorder;

import interface_adapter.itinerary.ItineraryState;
import interface_adapter.itinerary.ItineraryViewModel;
import use_case.reorder.ReorderOutputBoundary;
import use_case.reorder.ReorderOutputData;

import java.util.ArrayList;
import java.util.List;

public class ReorderPresenter implements ReorderOutputBoundary {

    private final ItineraryViewModel itineraryViewModel;

    public ReorderPresenter(ItineraryViewModel itineraryViewModel) {
        this.itineraryViewModel = itineraryViewModel;
    }

    @Override
    public void prepareSuccessView(ReorderOutputData outputData) {
        ItineraryState current = itineraryViewModel.getState();
        ItineraryState newState = new ItineraryState(current);
        List<String> names = new ArrayList<>(newState.getStopNames());
        int from = outputData.getFromIndex();
        int to = outputData.getToIndex();
        if (from >= 0 && from < names.size()) {
            String name = names.remove(from);
            names.add(to, name);
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
