package halleck.webserver.renderers;

import halleck.webserver.Renderer;

class StandardRenderer extends Renderer {

    @Override
    public String mustacheTemplate() {
        return "standardLinks.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return true;
    }
}
