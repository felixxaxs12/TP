package interface_adapter.generate_route;

import interface_adapter.itinerary.ItineraryState;
import interface_adapter.itinerary.ItineraryViewModel;
import use_case.generate_route.GenerateRouteOutputBoundary;
import use_case.generate_route.GenerateRouteOutputData;

public class GenerateRoutePresenter implements GenerateRouteOutputBoundary {

    private final ItineraryViewModel itineraryViewModel;

    public GenerateRoutePresenter(ItineraryViewModel itineraryViewModel) {
        this.itineraryViewModel = itineraryViewModel;
    }

    @Override
    public void prepareSuccessView(GenerateRouteOutputData outputData) {
        ItineraryState newState = new ItineraryState(itineraryViewModel.getState());
        newState.setRouteSegments(outputData.getSegments());
        newState.setErrorMessage(null);
        itineraryViewModel.setState(newState);
        itineraryViewModel.firePropertyChange("route");
    }

    @Override
    public void prepareFailView(String error) {
        ItineraryState newState = new ItineraryState(itineraryViewModel.getState());
        newState.setRouteSegments(newState.getRouteSegments());
        newState.setErrorMessage(error);
        itineraryViewModel.setState(newState);
        itineraryViewModel.firePropertyChange("error");
    }
}
