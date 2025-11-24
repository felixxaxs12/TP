package use_case.generate_route;

import org.jxmapviewer.viewer.GeoPosition;
import use_case.itinerary.ItineraryDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class GenerateRouteInteractor implements GenerateRouteInputBoundary {
    private final ItineraryDataAccessInterface itineraryDataAccessInterface;
    private final GenerateRouteRoutingDataAccessInterface routingDataAccessInterface;
    private final GenerateRouteOutputBoundary generateRoutePresenter;

    public GenerateRouteInteractor(ItineraryDataAccessInterface itineraryDataAccessInterface,
                                   GenerateRouteRoutingDataAccessInterface routingDataAccessInterface,
                                   GenerateRouteOutputBoundary generateRoutePresenter) {
        this.itineraryDataAccessInterface = itineraryDataAccessInterface;
        this.routingDataAccessInterface = routingDataAccessInterface;
        this.generateRoutePresenter = generateRoutePresenter;
    }

    @Override
    public void execute(GenerateRouteInputData inputData) {
        List<GeoPosition> stops = itineraryDataAccessInterface.getStops();
        if (stops.size() < 2) {
            generateRoutePresenter.prepareFailView("Add at least two stops to compute a full route.");
            return;
        }

        String profile = inputData.getProfile();
        List<List<GeoPosition>> segments = new ArrayList<>();
        for (int i = 0; i < stops.size() - 1; i++) {
            GeoPosition a = stops.get(i);
            GeoPosition b = stops.get(i + 1);
            try {
                List<GeoPosition> segment = routingDataAccessInterface.getRoute(a, b, profile);
                if (segment != null && !segment.isEmpty()) {
                    segments.add(segment);
                } else {
                    List<GeoPosition> straight = new ArrayList<>();
                    straight.add(a);
                    straight.add(b);
                    segments.add(straight);
                }
            } catch (Exception e) {
                List<GeoPosition> straight = new ArrayList<>();
                straight.add(a);
                straight.add(b);
                segments.add(straight);
            }
        }

        if (segments.isEmpty()) {
            generateRoutePresenter.prepareFailView("No route found.");
        } else {
            generateRoutePresenter.prepareSuccessView(new GenerateRouteOutputData(segments));
        }
    }
}
