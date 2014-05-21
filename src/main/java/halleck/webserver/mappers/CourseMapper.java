package halleck.webserver.mappers;


import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import halleck.lms.Course;

import javax.annotation.Nullable;
import java.util.function.Function;

public class CourseMapper implements Function<FormVars, Course> {
    @Override
    public Course apply(@Nullable FormVars request) {
        return new Course(
                request.get("id"),
                request.get("name"),
                request.get("description"),
                request.get("url"),
                toInt(request.get("max")),
                request.get("content")
                );
    }

    private Integer toInt(String max) {
        if(Strings.isNullOrEmpty(max)){
            return null;
        }
        return Ints.tryParse(max);
    }

}
