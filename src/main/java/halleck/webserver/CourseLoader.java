package halleck.webserver;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import halleck.lms.Course;
import halleck.lms.Halleck;
import halleck.lms.Settings;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CourseLoader {

    private Settings settings;
    private Halleck hal;
    private ResourceLoader loader;

    @Inject
    public CourseLoader(Settings settings, Halleck hal, ResourceLoader loader) {
        this.settings = settings;
        this.hal = hal;
        this.loader = loader;
    }

    public void load() {
        String courseLoadLocation = settings.getCourseLoadLocation();

        Optional<String> content = loader.getContent(courseLoadLocation);
        content.ifPresent((s) -> {
            hal.createCourses(createCourseArray(s));
            System.out.println("Loaded Course Content from " + courseLoadLocation);
        });
    }

    Collection<Course> createCourseArray(String content) {
        Gson g = new Gson();
        return g.fromJson(content, new TypeToken<List<Course>>(){}.getType());
    }

}
