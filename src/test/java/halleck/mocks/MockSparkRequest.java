package halleck.mocks;

import com.google.common.collect.Maps;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

public class MockSparkRequest extends Request {
    private final String path;
    private HashMap<String,String> cookies = Maps.newHashMap();
    private HashMap<String,Object> attributes = Maps.newHashMap();

    public MockSparkRequest(String path){
        this.path = path;
    }

    @Override
    public String pathInfo() {
        return path;
    }

    @Override
    public Map<String, String> cookies() {
        return cookies;
    }

    @Override
    public String cookie(String name) {
        return cookies.get(name);
    }

    @Override
    public void attribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    @Override
    public Object attribute(String attribute) {
        return attributes.get(attribute);
    }
}
