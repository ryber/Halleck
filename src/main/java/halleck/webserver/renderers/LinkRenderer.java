package halleck.webserver.renderers;

interface LinkRenderer {
    String mustacheTemplate();
    boolean canRender(String url);
    default String formatLink(String url){
        return url;
    }
}
