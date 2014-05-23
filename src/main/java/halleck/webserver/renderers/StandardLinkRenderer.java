package halleck.webserver.renderers;

class StandardLinkRenderer extends LinkRenderer {

    @Override
    public String mustacheTemplate() {
        return "standardLinks.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return true;
    }
}
