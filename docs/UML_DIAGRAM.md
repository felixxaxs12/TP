# UML Diagram

The diagrams below summarize the high-level Clean Architecture wiring for SwingTripPlanner and how the five use cases collaborate with the view layer.

```mermaid
classDiagram
    direction LR
    class AppBuilder {
        +addSearchView()
        +addSearchUseCase()
        +build()
    }

    class SearchView {
        +setSearchController()
        +setRemoveMarkerController()
        +setReorderController()
        +setGenerateRouteController()
        +render(SearchState)
    }

    class SearchViewModel {
        -SearchState state
        +getState()
        +setState(SearchState)
    }

    class SearchController
    class SearchPresenter
    class SearchInteractor
    class OSMDataAccessObject

    class RemoveMarkerController
    class RemoveMarkerPresenter
    class RemoveMarkerInteractor

    class ReorderController
    class ReorderPresenter
    class ReorderInteractor
    class SearchStateReorderDataAccess

    class GenerateRouteController
    class GenerateRoutePresenter
    class GenerateRouteInteractor
    class RoutingDataAccessObject

    class AddMarkerInteractor

    class MapState
    class Location
    class Marker
    class SearchState

    AppBuilder --> SearchView
    AppBuilder --> SearchViewModel
    AppBuilder --> OSMDataAccessObject
    AppBuilder --> RoutingDataAccessObject

    SearchView --> SearchController
    SearchController --> SearchInteractor
    SearchInteractor --> OSMDataAccessObject
    SearchInteractor --> SearchPresenter
    SearchPresenter --> SearchViewModel

    SearchView --> RemoveMarkerController
    RemoveMarkerController --> RemoveMarkerInteractor
    RemoveMarkerInteractor --> RemoveMarkerPresenter
    RemoveMarkerPresenter --> SearchViewModel

    SearchView --> ReorderController
    ReorderController --> ReorderInteractor
    ReorderInteractor --> SearchStateReorderDataAccess
    SearchStateReorderDataAccess --> SearchViewModel
    ReorderInteractor --> ReorderPresenter
    ReorderPresenter --> SearchViewModel

    SearchView --> GenerateRouteController
    GenerateRouteController --> GenerateRouteInteractor
    GenerateRouteInteractor --> RoutingDataAccessObject
    GenerateRouteInteractor --> GenerateRoutePresenter
    GenerateRoutePresenter --> SearchViewModel

    SearchViewModel o-- SearchState
    SearchView --> MapState
    AddMarkerInteractor --> Marker
    SearchInteractor --> Location
```

```mermaid
sequenceDiagram
    participant User
    participant SearchView as View
    participant Controller
    participant Interactor
    participant Presenter
    participant ViewModel as SearchViewModel

    User->>SearchView: Click UI control
    SearchView->>Controller: Build InputData
    Controller->>Interactor: execute(input)
    Interactor->>Presenter: present(OutputData)
    Presenter->>ViewModel: update state
    ViewModel-->>SearchView: property change
    SearchView->>SearchView: repaint map/list
```

These diagrams highlight that each user-facing action flows through a controller into an interactor, then back through a presenter to update the shared `SearchViewModel`, which triggers the Swing view to refresh its components.
