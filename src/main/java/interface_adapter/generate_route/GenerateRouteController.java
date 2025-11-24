package interface_adapter.generate_route;

import use_case.generate_route.GenerateRouteInputBoundary;
import use_case.generate_route.GenerateRouteInputData;

public class GenerateRouteController {
    private final GenerateRouteInputBoundary generateRouteInputBoundary;

    public GenerateRouteController(GenerateRouteInputBoundary generateRouteInputBoundary) {
        this.generateRouteInputBoundary = generateRouteInputBoundary;
    }

    public void generate(String profile) {
        generateRouteInputBoundary.execute(new GenerateRouteInputData(profile));
    }
}
