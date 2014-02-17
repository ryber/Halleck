package halleck.webserver;


import com.google.common.collect.Maps;
import spark.ModelAndView;

import java.util.Map;

public class ModelMapView extends ModelAndView{

    public ModelMapView(Map model, String viewName) {
        super(model, viewName);
    }

    @Override
    public Map getModel() {
        return super.getModel() == null ? Maps.newHashMap() : (Map)super.getModel();
    }
}
