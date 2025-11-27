# Code Overview

This document explains the main layers and classes in the SwingTripPlanner project after the recent refactor that split the five core use cases into dedicated Clean Architecture components.

## Framework/Drivers (outer layer)
- **`view/SearchView`** – Swing UI that renders the search field, stop list, map, and routing controls. It delegates user actions to controllers and updates visuals when view models change.
- **`view/ViewManager`** – Simple card-based view switcher used by the application shell.
- **`data_access/OSMDataAccessObject`** – Calls the OpenStreetMap Nominatim API for searching places and reverse geocoding.
- **`data_access/RoutingDataAccessObject`** – Calls the OSRM routing API to fetch polyline coordinates between consecutive stops.
- The stop list is held in the `SearchState` view model and passed directly into the remove, reorder, and generate-route use cases.

## Interface Adapters (controllers, presenters, view models)
- **Controllers**: thin entry points invoked by the UI.
  - `interface_adapter/search/SearchController` executes city lookups.
  - `interface_adapter/remove_marker/RemoveMarkerController` deletes a stop by index.
  - `interface_adapter/reorder/ReorderController` moves a stop from one index to another.
  - `interface_adapter/generate_route/GenerateRouteController` requests a fresh route for the current ordered stops.
- **Presenters**: translate interactor responses into view model updates.
  - `interface_adapter/search/SearchPresenter` updates map center and error messages for searches.
  - `interface_adapter/remove_marker/RemoveMarkerPresenter` removes the matching stop name and list entry while clearing errors.
  - `interface_adapter/reorder/ReorderPresenter` reorders stop names and positions in the itinerary state.
  - `interface_adapter/generate_route/GenerateRoutePresenter` stores computed route segments or failure messages.
- **View models**:
  - `interface_adapter/search/SearchViewModel` holds the active search query, coordinates, stop names/positions, route segments, and any error messages surfaced by the use cases.

## Use Cases (business rules)
- **File layout per use case (6 files)**: each package contains `*InputBoundary`, `*OutputBoundary`, `*InputData`, `*OutputData`, `*DataAccessInterface`, and `*Interactor` to mirror the Clean Architecture flow from incoming request to data gateway and presenter response.
- **Search**: `use_case/search/SearchInteractor` validates and geocodes a query via `OSMDataAccessObject`, then reports coordinates through the presenter.
- **Add Marker**: `use_case/add_marker/AddMarkerInteractor` appends a new stop to the itinerary unless a duplicate coordinate already exists.
- **Remove Marker**: `use_case/remove_marker/RemoveMarkerInteractor` validates the selected index, drops that stop from the provided lists, and emits the updated stops.
- **Reorder Itinerary**: `use_case/reorder/ReorderInteractor` moves a stop to a new position inside the provided list and returns the reordered data.
- **Generate Route**: `use_case/generate_route/GenerateRouteInteractor` walks consecutive stops passed in by the controller, calls the routing DAO for each leg, and aggregates the resulting geometry; falls back to straight lines on errors.

## Dependency Injection
- **`app/AppBuilder`** wires the object graph: it instantiates DAOs, the shared search view model, interactors, and their controllers/presenters, then injects them into `SearchView`. The stop list now lives in the view model itself and is passed to the business rules when needed; there is no standalone itinerary DAO (older branches referenced `InMemoryItineraryDataAccessObject`, which has been removed).

## UI Flow at Runtime
1. User enters a city and clicks **Search** → `SearchController` → `SearchInteractor` → `SearchPresenter` updates the search view model → `SearchView` centers the map and adds the stop.
2. User adds stops via map clicks or suggestions → `SearchView` stores them in `SearchState` and updates the view model.
3. User removes or reorders stops → `RemoveMarkerController` / `ReorderController` → corresponding interactors → presenters update the search view model → `SearchView` refreshes the list, map pins, and triggers routing if enough stops exist.
4. User clicks **Route** or auto-reroute triggers → `GenerateRouteController` → `GenerateRouteInteractor` → `GenerateRoutePresenter` stores route segments → `SearchView` paints the path on the map or shows an error dialog.
