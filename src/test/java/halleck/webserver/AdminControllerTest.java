package halleck.webserver;

import halleck.lms.Course;
import org.junit.Test;
import spark.ModelAndView;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AdminControllerTest {

    @Test
    public void featureAccessWillBeExposedThroughAMapOfFeaturesToLambdas() throws Exception {
        AdminController controller = new AdminController(null);

        ModelAndView view = controller.getModelAndView("foo.mustache", new Course("f"));

        assertTrue(((Map) view.getModel()).get("FEATURES") instanceof FeatureMap);
    }
}