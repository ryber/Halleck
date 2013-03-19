package integrationTests.mocks;

import integrationTests.SetupFixtures;
import spark.route.HttpMethod;
import spark.route.RouteMatcherFactory;
import spark.webserver.MatcherFilter;

public class FakeAppServer {

    public static Result exec(HttpMethod method, String path){
        MatcherFilter matcherFilter = new MatcherFilter(RouteMatcherFactory.get(), false);
        matcherFilter.init(null);

        MockRequest request = new MockRequest(method, path, SetupFixtures.getCurrentUser());
        MockResponse response = new MockResponse();
        try {
            matcherFilter.doFilter(request, response, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(response.getStatus(), response.getOutputString());
    }

    public static class Result {
        private final int status;
        private final String content;

        public Result(int status, String content){

            this.status = status;
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public int getStatus() {
            return status;
        }
    }

}
