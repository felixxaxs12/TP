package interface_adapter.generate_route;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import use_case.generate_route.GenerateRouteOutputBoundary;
import use_case.generate_route.GenerateRouteOutputData;

public class GenerateRoutePresenter implements GenerateRouteOutputBoundary {

    private final SearchViewModel searchViewModel;

    public GenerateRoutePresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(GenerateRouteOutputData outputData) {
        SearchState newState = new SearchState(searchViewModel.getState());
        newState.setRouteSegments(outputData.getSegments());
        newState.setErrorMessage(null);
        searchViewModel.setState(newState);
        searchViewModel.firePropertyChange("route");
    }

    @Override
    public void prepareFailView(String error) {
        SearchState newState = new SearchState(searchViewModel.getState());
        newState.setErrorMessage(error);
        searchViewModel.setState(newState);
        searchViewModel.firePropertyChange("error");
    }
}
