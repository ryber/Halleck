package webserver.mappers;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import halleck.CourseInput;

import javax.annotation.Nullable;

public class CourseMapper implements Function<FormVars, CourseInput> {
    @Override
    public CourseInput apply(@Nullable FormVars request) {
        return new CourseInputImpl(
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
