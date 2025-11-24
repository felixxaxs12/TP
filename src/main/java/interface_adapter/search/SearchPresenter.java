package interface_adapter.search;

import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData response) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setLocationName(response.getLocationName());
        searchState.setLatitude(response.getLatitude());
        searchState.setLongitude(response.getLongitude());
        searchState.setSearchError(null);
        this.searchViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(error);
        searchViewModel.firePropertyChange();
    }
}
