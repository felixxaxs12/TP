package app;

import data_access.InMemoryItineraryDataAccessObject;
import data_access.OSMDataAccessObject;
import data_access.RoutingDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.itinerary.ItineraryViewModel;
import interface_adapter.generate_route.GenerateRouteController;
import interface_adapter.generate_route.GenerateRoutePresenter;
import interface_adapter.remove_marker.RemoveMarkerController;
import interface_adapter.remove_marker.RemoveMarkerPresenter;
import interface_adapter.reorder.ReorderController;
import interface_adapter.reorder.ReorderPresenter;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import use_case.generate_route.GenerateRouteInputBoundary;
import use_case.generate_route.GenerateRouteInteractor;
import use_case.generate_route.GenerateRouteOutputBoundary;
import use_case.itinerary.ItineraryDataAccessInterface;
import use_case.remove_marker.RemoveMarkerInputBoundary;
import use_case.remove_marker.RemoveMarkerInteractor;
import use_case.remove_marker.RemoveMarkerOutputBoundary;
import use_case.reorder.ReorderInputBoundary;
import use_case.reorder.ReorderInteractor;
import use_case.reorder.ReorderOutputBoundary;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.SearchView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;
import java.net.http.HttpClient;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final HttpClient client = HttpClient.newHttpClient();
    final OSMDataAccessObject osmDataAccessObject = new OSMDataAccessObject(client);
    final RoutingDataAccessObject routingDataAccessObject = new RoutingDataAccessObject(client);
    final ItineraryDataAccessInterface itineraryDataAccessObject = new InMemoryItineraryDataAccessObject();


    private SearchViewModel searchViewModel;
    private ItineraryViewModel itineraryViewModel;
    private SearchView searchView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        itineraryViewModel = new ItineraryViewModel();
        searchView = new SearchView(searchViewModel, itineraryViewModel, itineraryDataAccessObject);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(
                osmDataAccessObject, searchOutputBoundary);

        SearchController searchController = new SearchController(searchInteractor);
        searchView.setSearchController(searchController);

        searchView.setOsmDataAccessObject(osmDataAccessObject);

        final RemoveMarkerOutputBoundary removeOutputBoundary = new RemoveMarkerPresenter(itineraryViewModel);
        final RemoveMarkerInputBoundary removeInteractor = new RemoveMarkerInteractor(itineraryDataAccessObject,
                removeOutputBoundary);
        searchView.setRemoveMarkerController(new RemoveMarkerController(removeInteractor));

        final ReorderOutputBoundary reorderOutputBoundary = new ReorderPresenter(itineraryViewModel);
        final ReorderInputBoundary reorderInteractor = new ReorderInteractor(itineraryDataAccessObject,
                reorderOutputBoundary);
        searchView.setReorderController(new ReorderController(reorderInteractor));

        final GenerateRouteOutputBoundary generateRoutePresenter = new GenerateRoutePresenter(itineraryViewModel);
        final GenerateRouteInputBoundary generateRouteInteractor = new GenerateRouteInteractor(itineraryDataAccessObject,
                routingDataAccessObject, generateRoutePresenter);
        searchView.setGenerateRouteController(new GenerateRouteController(generateRouteInteractor));

        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("trip planner");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(searchView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
