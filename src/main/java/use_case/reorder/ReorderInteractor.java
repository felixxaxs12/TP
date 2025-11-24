package use_case.reorder;

import use_case.itinerary.ItineraryDataAccessInterface;

public class ReorderInteractor implements ReorderInputBoundary {
    private final ItineraryDataAccessInterface itineraryDataAccessInterface;
    private final ReorderOutputBoundary reorderPresenter;

    public ReorderInteractor(ItineraryDataAccessInterface itineraryDataAccessInterface,
                             ReorderOutputBoundary reorderPresenter) {
        this.itineraryDataAccessInterface = itineraryDataAccessInterface;
        this.reorderPresenter = reorderPresenter;
    }

    @Override
    public void execute(ReorderInputData inputData) {
        int fromIndex = inputData.getFromIndex();
        int toIndex = inputData.getToIndex();
        int size = itineraryDataAccessInterface.getStops().size();
        if (fromIndex < 0 || fromIndex >= size || toIndex < 0 || toIndex >= size) {
            reorderPresenter.prepareFailView("Cannot move marker in that direction.");
            return;
        }

        reorderPresenter.prepareSuccessView(new ReorderOutputData(fromIndex, toIndex,
                itineraryDataAccessInterface.reorderStops(fromIndex, toIndex)));
    }
}
