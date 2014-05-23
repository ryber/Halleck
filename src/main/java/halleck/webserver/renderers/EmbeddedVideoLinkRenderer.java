package halleck.webserver.renderers;

class EmbeddedVideoLinkRenderer extends LinkRenderer {

    @Override
    public String mustacheTemplate() {
        return "embeddedVideo.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return url.toLowerCase().endsWith("mp4");
    }
}
