package webserver.mappers;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import halleck.Course;
import halleck.OnlineCourse;

import javax.annotation.Nullable;

public class CourseMapper implements Function<FormVars, Course> {
    @Override
    public Course apply(@Nullable FormVars request) {
        return new OnlineCourse(
                request.get("id"),
                request.get("name"),
                request.get("description"),
                request.get("url"),
                toInt(request.get("max")));
    }

    private Integer toInt(String max) {
        if(Strings.isNullOrEmpty(max)){
            return null;
        }
        return Ints.tryParse(max);
    }

}
