package use_case.search;

import java.util.List;

public class SearchSuggestionsOutputData {
    private final List<SearchSuggestionDto> suggestions;

    public SearchSuggestionsOutputData(List<SearchSuggestionDto> suggestions) {
        this.suggestions = suggestions;
    }

    public List<SearchSuggestionDto> getSuggestions() {
        return suggestions;
    }
}
