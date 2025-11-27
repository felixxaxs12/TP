package use_case.search;

public class SearchSuggestionsInputData {
    private final String query;
    private final Double minLon;
    private final Double minLat;
    private final Double maxLon;
    private final Double maxLat;
    private final int limit;

    public SearchSuggestionsInputData(String query, Double minLon, Double minLat, Double maxLon, Double maxLat, int limit) {
        this.query = query;
        this.minLon = minLon;
        this.minLat = minLat;
        this.maxLon = maxLon;
        this.maxLat = maxLat;
        this.limit = limit;
    }

    public String getQuery() {
        return query;
    }

    public Double getMinLon() {
        return minLon;
    }

    public Double getMinLat() {
        return minLat;
    }

    public Double getMaxLon() {
        return maxLon;
    }

    public Double getMaxLat() {
        return maxLat;
    }

    public int getLimit() {
        return limit;
    }
}
