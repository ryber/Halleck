package halleck.webserver.mappers;

import spark.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class FormVars extends HashMap<String, String> {

    public FormVars() {
    }

    public FormVars(Request request) {
        HttpServletRequest raw = request.raw();

        for (Object key : raw.getParameterMap().keySet()) {
            put(key.toString(), raw.getParameter(key.toString()));
        }

    }


    public void put(String id, Object i) {
        put(id, i.toString());
    }
}
