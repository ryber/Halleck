package halleck.mocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import spark.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MockSparkRequest extends Request {

    private HashMap<String,String> cookies = Maps.newHashMap();
    private HashMap<String,Object> attributes = Maps.newHashMap();
    private HashMap<String,String> params = Maps.newHashMap();
    private Map<String, String> headers = Maps.newHashMap();
    private MockRequest rawRequest;

    public MockSparkRequest(String path){
        this(new MockRequest(path));
    }

    public MockSparkRequest(MockRequest rawRequest) {
        this.rawRequest = rawRequest;
    }

    @Override
    public String queryParams(String queryParam) {
        return params.get(queryParam);
    }

    @Override
    public String pathInfo() {
        return rawRequest.getPathInfo();
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

    @Override
    public String headers(String header) {
        return headers.get(header);
    }

    @Override
    public HttpServletRequest raw() {
        return rawRequest;
    }

    public HashMap<String, String> getParams() {
        return params;
    }
}
