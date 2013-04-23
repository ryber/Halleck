package halleck.webserver;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class MapMaker {
    public static Map map(String key, Object value) {
        return ImmutableMap.of(key, value);
    }
}
