package interface_adapter.remove_marker;

import use_case.remove_marker.RemoveMarkerInputBoundary;
import use_case.remove_marker.RemoveMarkerInputData;

public class RemoveMarkerController {
    private final RemoveMarkerInputBoundary removeMarkerInputBoundary;

    public RemoveMarkerController(RemoveMarkerInputBoundary removeMarkerInputBoundary) {
        this.removeMarkerInputBoundary = removeMarkerInputBoundary;
    }

    public void removeAt(int index) {
        removeMarkerInputBoundary.execute(new RemoveMarkerInputData(index));
    }
}
