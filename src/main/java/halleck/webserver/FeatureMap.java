package halleck.webserver;



import halleck.lms.Feature;

import java.util.Arrays;
import java.util.HashMap;

public class FeatureMap extends HashMap<String, Boolean> {
    public FeatureMap(){
        Arrays.asList(Feature.values()).forEach(
                f -> put(f.name(), f.isActive())
        );
    }
}
