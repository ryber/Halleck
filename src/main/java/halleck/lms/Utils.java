package halleck.lms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {
    public static String toSafeString(String url) {
        return url == null ? "" : url;
    }

    public static Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {
        String query = url.substring(url.indexOf('?') + 1);
        String[] pairs = query.split("&");
        Map<String, String> query_pairs = new LinkedHashMap<>();
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}