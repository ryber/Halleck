package halleck.webserver.routs;

import halleck.webserver.ModelMapView;
import spark.Request;
import spark.Response;

public interface RouteFunction {
    ModelMapView handle(Request request, Response response);
}
