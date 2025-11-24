package use_case.reorder;

public class ReorderInputData {
    private final int fromIndex;
    private final int toIndex;

    public ReorderInputData(int fromIndex, int toIndex) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }
}
