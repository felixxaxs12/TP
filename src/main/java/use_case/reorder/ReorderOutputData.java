package use_case.reorder;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

public class ReorderOutputData {
    private final int fromIndex;
    private final int toIndex;
    private final List<GeoPosition> stops;

    public ReorderOutputData(int fromIndex, int toIndex, List<GeoPosition> stops) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.stops = stops;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public List<GeoPosition> getStops() {
        return stops;
    }
}
