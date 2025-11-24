package use_case.remove_marker;

import org.jxmapviewer.viewer.GeoPosition;
import use_case.itinerary.ItineraryDataAccessInterface;

import java.util.List;

public class RemoveMarkerInteractor implements RemoveMarkerInputBoundary {
    private final ItineraryDataAccessInterface itineraryDataAccessInterface;
    private final RemoveMarkerOutputBoundary removeMarkerPresenter;

    public RemoveMarkerInteractor(ItineraryDataAccessInterface itineraryDataAccessInterface,
                                  RemoveMarkerOutputBoundary removeMarkerPresenter) {
        this.itineraryDataAccessInterface = itineraryDataAccessInterface;
        this.removeMarkerPresenter = removeMarkerPresenter;
    }

    @Override
    public void execute(RemoveMarkerInputData inputData) {
        int index = inputData.getIndex();
        List<GeoPosition> stops = itineraryDataAccessInterface.getStops();
        if (index < 0 || index >= stops.size()) {
            removeMarkerPresenter.prepareFailView("No marker selected to remove.");
            return;
        }

        itineraryDataAccessInterface.removeStop(index);
        removeMarkerPresenter.prepareSuccessView(new RemoveMarkerOutputData(index, itineraryDataAccessInterface.getStops()));
    }
}
