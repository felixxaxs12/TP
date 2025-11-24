package interface_adapter.reorder;

import use_case.reorder.ReorderInputBoundary;
import use_case.reorder.ReorderInputData;

public class ReorderController {
    private final ReorderInputBoundary reorderInputBoundary;

    public ReorderController(ReorderInputBoundary reorderInputBoundary) {
        this.reorderInputBoundary = reorderInputBoundary;
    }

    public void move(int fromIndex, int toIndex) {
        reorderInputBoundary.execute(new ReorderInputData(fromIndex, toIndex));
    }
}
