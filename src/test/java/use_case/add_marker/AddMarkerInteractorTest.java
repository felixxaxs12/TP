package use_case.add_marker;

import entity.Location;
import entity.Marker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

class AddMarkerInteractorTest {

    @Test
    void successTest() {
        // Arrange
        AddMarkerInputData inputData = new AddMarkerInputData(43.6532, -79.3832);

        // Create a DAO that simulates empty storage (marker doesn't exist yet)
        AddMarkerDataAccessInterface successDAO = new AddMarkerDataAccessInterface() {
            private final List<Marker> markers = new ArrayList<>();

            @Override
            public boolean exists(Location location) {
                for (Marker marker : markers) {
                    if (marker.getLocation().getLatitude() == location.getLatitude() &&
                            marker.getLocation().getLongitude() == location.getLongitude()) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void save(Marker marker) {
                markers.add(marker);
            }

            @Override
            public List<Marker> getAllMarkers() {
                return markers;
            }
        };

        // Create a presenter to verify the success view is called
        AddMarkerOutputBoundary successPresenter = new AddMarkerOutputBoundary() {
            @Override
            public void prepareSuccessView(AddMarkerOutputData outputData) {
                assertEquals(43.6532, outputData.getLatitude());
                assertEquals(-79.3832, outputData.getLongitude());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        AddMarkerInputBoundary interactor = new AddMarkerInteractor(successDAO, successPresenter);

        // Act
        interactor.execute(inputData);

        // Assert: Verify the marker was actually saved in the DAO
        assertEquals(1, successDAO.getAllMarkers().size());
        assertEquals(43.6532, successDAO.getAllMarkers().get(0).getLatitude());
    }

    @Test
    void failureMarkerExistsTest() {
        // Arrange
        double lat = 40.7128;
        double lon = -74.0060;
        AddMarkerInputData inputData = new AddMarkerInputData(lat, lon);

        // Create a DAO that simulates a marker already existing at this location
        AddMarkerDataAccessInterface failDAO = new AddMarkerDataAccessInterface() {
            @Override
            public boolean exists(Location location) {
                // Simulate that the specific location already exists
                return location.getLatitude() == lat && location.getLongitude() == lon;
            }

            @Override
            public void save(Marker marker) {
                fail("DAO save should not be called when marker exists.");
            }

            @Override
            public List<Marker> getAllMarkers() {
                return new ArrayList<>();
            }
        };

        // Create a presenter to verify the fail view is called
        AddMarkerOutputBoundary failPresenter = new AddMarkerOutputBoundary() {
            @Override
            public void prepareSuccessView(AddMarkerOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("A marker already exists at this location", error);
            }
        };

        AddMarkerInputBoundary interactor = new AddMarkerInteractor(failDAO, failPresenter);

        // Act
        interactor.execute(inputData);
    }
}