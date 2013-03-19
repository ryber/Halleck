package webserver.mappers;

import java.util.HashMap;

public class FormVars extends HashMap<String, String> {

    public void put(String id, Object i) {
        put(id, i.toString());
    }
}
