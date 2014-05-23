package halleck.webserver.renderers;

import halleck.webserver.MapMaker;

import java.util.Map;

interface LinkRenderer {
    String mustacheTemplate();
    boolean canRender(String url);
    default String formatLink(String url){
        return url;
    }
}
