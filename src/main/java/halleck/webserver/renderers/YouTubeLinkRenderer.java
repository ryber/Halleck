package halleck.webserver.renderers;

import halleck.lms.Utils;

class YouTubeLinkRenderer implements LinkRenderer {
    @Override
    public String mustacheTemplate() {
        return "youTubeRendering.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return url.toLowerCase().contains("youtube.com");
    }

    @Override
    public String formatLink(String url) {
        return String.format("//www.youtube.com/embed/%s", getYouTubeId(url));
    }

    private String getYouTubeId(String url) {
        if(url.contains("watch")) {
            return extractTokenFromWatchLink(url);
        }
        return extractTokenFromEmbed(url);
    }

    private String extractTokenFromEmbed(String url) {
        return url.substring(url.indexOf("embed/")).substring(6);
    }

    private String extractTokenFromWatchLink(String url) {
        try {
            return Utils.splitQuery(url).get("v");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
