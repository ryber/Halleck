package webserver.mappers;

import com.google.common.base.Function;
import halleck.CourseInput;

import javax.annotation.Nullable;

public class CourseMapper implements Function<FormVars, CourseInput> {
    @Override
    public CourseInput apply(@Nullable FormVars request) {
        return new CourseInputImpl(
                request.get("id"),
                request.get("name"),
                request.get("description"),
                request.get("url")
        );
    }

}
