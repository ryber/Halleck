package halleck.webserver;

import java.util.Map;

public abstract class Renderer {
    public static final String URL = "URL";

    public abstract String mustacheTemplate();
    public abstract boolean canRender(String url);
    public Map<String, Object> formatUrl(String url){
        return MapMaker.map(URL, formatLink(url));
    }

    protected String formatLink(String url){
        return url;
    }
}
