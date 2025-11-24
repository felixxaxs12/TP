package use_case.search;

public interface  SearchOutputBoundary {
    void prepareSuccessView(SearchOutputData outputData);

    void prepareFailView(String errorMessage);
}
