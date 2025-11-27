package interface_adapter.search;

import use_case.search.ReverseLookupOutputData;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;
import use_case.search.SearchSuggestionDto;
import use_case.search.SearchSuggestionsOutputData;

public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData response) {
        final SearchState searchState = new SearchState(searchViewModel.getState());
        searchState.setLocationName(response.getLocationName());
        searchState.setLatitude(response.getLatitude());
        searchState.setLongitude(response.getLongitude());
        searchState.setSearchError(null);
        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SearchState searchState = new SearchState(searchViewModel.getState());
        searchState.setSearchError(error);
        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChange();
    }

    @Override
    public void presentSuggestions(SearchSuggestionsOutputData outputData) {
        final SearchState searchState = new SearchState(searchViewModel.getState());
        java.util.List<SearchSuggestionViewData> suggestions = new java.util.ArrayList<>();
        for (SearchSuggestionDto dto : outputData.getSuggestions()) {
            suggestions.add(new SearchSuggestionViewData(dto.getName(), dto.getLatitude(), dto.getLongitude()));
        }
        searchState.setSuggestions(suggestions);
        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChange("suggestions");
    }

    @Override
    public void presentReverseLookup(ReverseLookupOutputData outputData) {
        final SearchState searchState = new SearchState(searchViewModel.getState());
        SearchSuggestionDto dto = outputData.getResolvedLocation();
        searchState.setResolvedClickLocation(
                new SearchSuggestionViewData(dto.getName(), dto.getLatitude(), dto.getLongitude()));
        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChange("reverse");
    }
}
