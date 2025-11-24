package use_case.search;

import entity.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class SearchInteractorTest {

    @Test
    void successTest() {
        // Arrange
        SearchInputData inputData = new SearchInputData("Toronto");

        // Create a success-case DAO that returns a specific location
        SearchDataAccessInterface successPresenterDAO = new SearchDataAccessInterface() {
            @Override
            public boolean existsByName(String locationName) {
                return true;
            }

            @Override
            public Location get(String locationName) {
                return new Location("Toronto", 43.6532, -79.3832);
            }

            @Override
            public void save(Location location) {}

            @Override
            public void setCurrentLocation(String locationName) {}

            @Override
            public String getCurrentLocationName() { return ""; }
        };

        // Create a presenter that asserts the success view is prepared correctly
        SearchOutputBoundary successPresenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                assertEquals("Toronto", outputData.getLocationName());
                assertEquals(43.6532, outputData.getLatitude());
                assertEquals(-79.3832, outputData.getLongitude());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        SearchInputBoundary interactor = new SearchInteractor(successPresenterDAO, successPresenter);

        // Act
        interactor.execute(inputData);
    }

    @Test
    void failureLocationDoesNotExistTest() {
        // Arrange
        SearchInputData inputData = new SearchInputData("Atlantis");

        // Create a DAO that simulates a missing location
        SearchDataAccessInterface failDAO = new SearchDataAccessInterface() {
            @Override
            public boolean existsByName(String locationName) {
                return false;
            }

            @Override
            public Location get(String locationName) {
                return null;
            }

            @Override
            public void save(Location location) {}
            @Override
            public void setCurrentLocation(String locationName) {}
            @Override
            public String getCurrentLocationName() { return ""; }
        };

        // Create a presenter that asserts the fail view is prepared correctly
        SearchOutputBoundary failPresenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Atlantis: Location does not exist.", error);
            }
        };

        SearchInputBoundary interactor = new SearchInteractor(failDAO, failPresenter);

        // Act
        interactor.execute(inputData);
    }

    @Test
    void failureNetworkErrorTest() {
        // Arrange
        SearchInputData inputData = new SearchInputData("Network Error City");

        // Create a DAO that throws an IOException to simulate network issues
        SearchDataAccessInterface exceptionDAO = new SearchDataAccessInterface() {
            @Override
            public boolean existsByName(String locationName) throws IOException {
                throw new IOException("API Unreachable");
            }

            @Override
            public Location get(String locationName) { return null; }
            @Override
            public void save(Location location) {}
            @Override
            public void setCurrentLocation(String locationName) {}
            @Override
            public String getCurrentLocationName() { return ""; }
        };

        // Create a presenter that asserts the correct error message is passed
        SearchOutputBoundary exceptionPresenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Network error while searching for location: API Unreachable", error);
            }
        };

        SearchInputBoundary interactor = new SearchInteractor(exceptionDAO, exceptionPresenter);

        // Act
        interactor.execute(inputData);
    }
}