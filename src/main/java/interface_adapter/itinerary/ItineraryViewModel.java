package interface_adapter.itinerary;

import interface_adapter.ViewModel;

public class ItineraryViewModel extends ViewModel<ItineraryState> {

    public ItineraryViewModel() {
        super("itinerary");
        setState(new ItineraryState());
    }
}
