package halleck.webserver.routs;

import com.google.common.collect.Lists;
import halleck.webserver.FullPage;
import halleck.webserver.ModelMapView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public abstract class SparkRoutCollector {
    private List<Route> gets = Lists.newArrayList();
    private List<Route> puts = Lists.newArrayList();
    private List<Route> posts = Lists.newArrayList();

    public void get(Route get){
        gets.add(get);
    }

    public void put(Route put){
        puts.add(put);
    }

    public void post(Route post) {
        posts.add(post);
    }

    public void get(final String path, final RouteFunction get){
        gets.add(new FullPage(path) {
            @Override
            public ModelMapView action(Request request, Response response) {
                return get.handle(request, response);
            }
        });
    }

    public void post(final String path, final RouteFunction get){
        posts.add(new FullPage(path) {
            @Override
            public ModelMapView action(Request request, Response response) {
                return get.handle(request, response);
            }
        });
    }

    public Iterable<Route> getGets() {
        return gets;
    }

    public Iterable<Route> getPuts() {
        return puts;
    }

    public Iterable<Route> getPosts() {
        return posts;
    }

    public abstract void init();
}
