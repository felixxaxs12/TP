package use_case.search;

public interface SearchInputBoundary {

    void execute(SearchInputData searchInputData);

    void executeSuggestions(SearchSuggestionsInputData inputData);

    void reverseLookup(ReverseLookupInputData inputData);
}
