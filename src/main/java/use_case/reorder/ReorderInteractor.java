package use_case.reorder;

import org.jxmapviewer.viewer.GeoPosition;
import java.util.List;

public class ReorderInteractor implements ReorderInputBoundary {
    private final ReorderDataAccessInterface reorderDataAccess;
    private final ReorderOutputBoundary reorderPresenter;

    public ReorderInteractor(ReorderDataAccessInterface reorderDataAccess, ReorderOutputBoundary reorderPresenter) {
        this.reorderDataAccess = reorderDataAccess;
        this.reorderPresenter = reorderPresenter;
    }

    @Override
    public void execute(ReorderInputData inputData) {
        int fromIndex = inputData.getFromIndex();
        int toIndex = inputData.getToIndex();
        List<String> names = reorderDataAccess.getStopNames();
        List<GeoPosition> stops = reorderDataAccess.getStops();
        int size = stops.size();
        if (fromIndex < 0 || fromIndex >= size || toIndex < 0 || toIndex >= size) {
            reorderPresenter.prepareFailView("Cannot move marker in that direction.");
            return;
        }

        GeoPosition movedStop = stops.remove(fromIndex);
        stops.add(toIndex, movedStop);
        String movedName = names.remove(fromIndex);
        names.add(toIndex, movedName);

        reorderPresenter.prepareSuccessView(new ReorderOutputData(fromIndex, toIndex, names, stops));
    }
}
