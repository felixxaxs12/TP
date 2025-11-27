package use_case.search;

public interface SearchOutputBoundary {
    void prepareSuccessView(SearchOutputData outputData);

    void prepareFailView(String errorMessage);

    void presentSuggestions(SearchSuggestionsOutputData outputData);

    void presentReverseLookup(ReverseLookupOutputData outputData);
}
