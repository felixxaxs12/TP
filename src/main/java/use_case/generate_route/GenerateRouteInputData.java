package use_case.generate_route;

public class GenerateRouteInputData {
    private final String profile;

    public GenerateRouteInputData(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }
}
