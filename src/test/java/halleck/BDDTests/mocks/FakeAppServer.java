package halleck.BddTests.mocks;

import halleck.BddTests.fixtures.ApplicationFixture;
import halleck.webserver.mappers.FormVars;
import spark.route.HttpMethod;
import spark.route.RouteMatcherFactory;
import spark.webserver.MatcherFilter;

public class FakeAppServer {

    public static Result exec(HttpMethod method, String path){
        return exec(method, path, null);
    }

    public static Result exec(HttpMethod method, String path, FormVars form){
        MatcherFilter matcherFilter = new MatcherFilter(RouteMatcherFactory.get(), false, false);
        matcherFilter.init(null);

        MockRequest request = new MockRequest(method, path, ApplicationFixture.getContext(), form);


        MockResponse response = new MockResponse();
        try {
            matcherFilter.doFilter(request, response, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(response);
    }



}
