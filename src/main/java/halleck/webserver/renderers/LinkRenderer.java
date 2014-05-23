package halleck.webserver.renderers;

import halleck.webserver.MapMaker;

import java.util.Map;

public abstract class LinkRenderer {
    public abstract String mustacheTemplate();
    public abstract boolean canRender(String url);
    protected String formatLink(String url){
        return url;
    }
}
