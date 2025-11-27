package use_case.search;

import entity.Location;

import java.io.IOException;

public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessObj;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(SearchDataAccessInterface userDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary) {
        this.searchDataAccessObj = userDataAccessInterface;
        this.searchPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        final String locationName = searchInputData.getLocationName();
        try {
            if (!searchDataAccessObj.existsByName(locationName)) {
                searchPresenter.prepareFailView(locationName + ": Location does not exist.");
            }
            else {
                // get location data from OSM
                final Location location = searchDataAccessObj.get(searchInputData.getLocationName());

                final SearchOutputData searchOutputData = new SearchOutputData(location.getName(),
                        location.getLatitude(), location.getLongitude());
                searchPresenter.prepareSuccessView(searchOutputData);
            }
        } catch (IOException e) {
            searchPresenter.prepareFailView("Network error while searching for location: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            searchPresenter.prepareFailView("Request interrupted while searching for location.");
        } catch (Exception e) {
            searchPresenter.prepareFailView("Unexpected error: " + e.getMessage());
        }
    }

    @Override
    public void executeSuggestions(SearchSuggestionsInputData inputData) {
        try {
            java.util.List<Location> locations = searchDataAccessObj.searchSuggestions(
                    inputData.getQuery(),
                    inputData.getMinLon(),
                    inputData.getMinLat(),
                    inputData.getMaxLon(),
                    inputData.getMaxLat(),
                    inputData.getLimit());

            java.util.List<SearchSuggestionDto> suggestions = new java.util.ArrayList<>();
            for (Location location : locations) {
                suggestions.add(new SearchSuggestionDto(
                        location.getName(),
                        location.getLatitude(),
                        location.getLongitude()));
            }
            searchPresenter.presentSuggestions(new SearchSuggestionsOutputData(suggestions));
        } catch (IOException | InterruptedException e) {
            searchPresenter.presentSuggestions(new SearchSuggestionsOutputData(java.util.Collections.emptyList()));
        }
    }

    @Override
    public void reverseLookup(ReverseLookupInputData inputData) {
        try {
            Location location = searchDataAccessObj.reverse(inputData.getLatitude(), inputData.getLongitude());
            SearchSuggestionDto dto = new SearchSuggestionDto(
                    location.getName(),
                    location.getLatitude(),
                    location.getLongitude());
            searchPresenter.presentReverseLookup(new ReverseLookupOutputData(dto));
        } catch (IOException | InterruptedException e) {
            String fallbackName = String.format("%.5f, %.5f", inputData.getLatitude(), inputData.getLongitude());
            SearchSuggestionDto dto = new SearchSuggestionDto(fallbackName, inputData.getLatitude(), inputData.getLongitude());
            searchPresenter.presentReverseLookup(new ReverseLookupOutputData(dto));
        }
    }
}
