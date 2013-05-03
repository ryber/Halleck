package halleck.webserver;

import java.util.HashMap;
import java.util.Map;

public class MapMaker {
    public static Map map(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
