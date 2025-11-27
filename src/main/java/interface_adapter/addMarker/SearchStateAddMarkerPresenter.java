package interface_adapter.addMarker;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import use_case.add_marker.AddMarkerOutputBoundary;
import use_case.add_marker.AddMarkerOutputData;

/**
 * Presenter that updates the {@link SearchViewModel} when markers are added so the view can react
 * via property change events without depending on entities or use-case classes.
 */
public class SearchStateAddMarkerPresenter implements AddMarkerOutputBoundary {

    private final SearchViewModel searchViewModel;

    public SearchStateAddMarkerPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(AddMarkerOutputData outputData) {
        // Data was written into the SearchState by the data access object; fire change for the UI.
        SearchState updated = new SearchState(searchViewModel.getState());
        updated.setErrorMessage(null);
        searchViewModel.setState(updated);
        searchViewModel.firePropertyChange("stops");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        SearchState state = new SearchState(searchViewModel.getState());
        state.setErrorMessage(errorMessage);
        searchViewModel.setState(state);
        searchViewModel.firePropertyChange("error");
    }
}

