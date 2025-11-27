package interface_adapter.search;

import use_case.search.ReverseLookupInputData;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;
import use_case.search.SearchSuggestionsInputData;

public class SearchController {

    private final SearchInputBoundary searchUseCaseInteractor;

    public SearchController(SearchInputBoundary searchUseCaseInteractor) {
        this.searchUseCaseInteractor = searchUseCaseInteractor;
    }

    public void execute(String locationName) {
        final SearchInputData searchInputData = new SearchInputData(
                locationName);

        searchUseCaseInteractor.execute(searchInputData);
    }

    public void requestSuggestions(String query,
                                   Double minLon,
                                   Double minLat,
                                   Double maxLon,
                                   Double maxLat,
                                   int limit) {
        searchUseCaseInteractor.executeSuggestions(
                new SearchSuggestionsInputData(query, minLon, minLat, maxLon, maxLat, limit));
    }

    public void reverseLookup(double latitude, double longitude) {
        searchUseCaseInteractor.reverseLookup(new ReverseLookupInputData(latitude, longitude));
    }
}
