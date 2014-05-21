package halleck.webserver.mappers;


import halleck.lms.Course;

import javax.annotation.Nullable;
import java.util.function.Function;

import static halleck.lms.Utils.toInteger;

public class CourseMapper implements Function<FormVars, Course> {
    @Override
    public Course apply(@Nullable FormVars request) {
        return new Course(request.get("id"), request.get("name"))
            {{
                setDecription(request.get("description"));
                setUrl(request.get("url"));
                setMaxCapacity(toInteger(request.get("max")));
                setContent(request.get("content"));
            }};
    }


}
