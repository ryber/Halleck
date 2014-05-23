package halleck.webserver.renderers;

class EmbeddedVideoLinkRenderer implements LinkRenderer {

    @Override
    public String mustacheTemplate() {
        return "embeddedVideo.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return url.toLowerCase().endsWith("mp4");
    }
}
