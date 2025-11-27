package use_case.search;

public class ReverseLookupOutputData {
    private final SearchSuggestionDto resolvedLocation;

    public ReverseLookupOutputData(SearchSuggestionDto resolvedLocation) {
        this.resolvedLocation = resolvedLocation;
    }

    public SearchSuggestionDto getResolvedLocation() {
        return resolvedLocation;
    }
}
