# SwingTripPlanner

SwingTripPlanner is a Java Swing desktop application for planning trips on an
interactive map. It follows Clean Architecture to keep use cases independent of
the UI and infrastructure layers while supporting the following five core user
stories:

1. **Search Location** – center the map on a city via the OpenStreetMap
   Nominatim API.
2. **Add Marker** – append a stop to the itinerary by coordinates.
3. **Remove Marker** – delete a stop from the itinerary by index.
4. **Reorder Itinerary** – move a stop up or down to change the visit order.
5. **Generate Route** – request an ordered route for all markers from the OSRM
   routing service.

The stop list shared by these use cases lives in the `SearchState` view model.
Controllers pass the current stop names and coordinates straight into the
interactors, keeping storage concerns out of the UI while avoiding an extra
gateway abstraction; earlier branches that referenced an
`InMemoryItineraryDataAccessObject` were simplified away during the latest
cleanup, so there is no separate itinerary DAO to merge.

For a visual overview of the Clean Architecture wiring and request flow, see the UML diagrams in [`docs/UML_DIAGRAM.md`](docs/UML_DIAGRAM.md).
