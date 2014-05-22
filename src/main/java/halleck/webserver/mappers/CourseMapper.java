package halleck.webserver.mappers;


import halleck.lms.Course;

import javax.annotation.Nullable;
import java.util.function.Function;

import static halleck.lms.Utils.toInteger;

public class CourseMapper implements Function<FormVars, Course> {
    @Override
    public Course apply(@Nullable FormVars request) {
        Course c = new Course(request.get("id"), request.get("name"));
        c.setDecription(request.get("description"));
        c.setUrl(request.get("url"));
        c.setMaxCapacity(toInteger(request.get("max")));
        c.setContent(request.get("content"));
        return c;
    }


}
