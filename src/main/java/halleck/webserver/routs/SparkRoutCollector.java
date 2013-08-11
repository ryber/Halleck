package halleck.webserver.routs;

import com.google.common.collect.Lists;
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
