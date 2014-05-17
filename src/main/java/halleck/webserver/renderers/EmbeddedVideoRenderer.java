package halleck.webserver.renderers;

import halleck.webserver.Renderer;

class EmbeddedVideoRenderer extends Renderer {

    @Override
    public String mustacheTemplate() {
        return "embeddedVideo.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return url.toLowerCase().endsWith("mp4");
    }
}
