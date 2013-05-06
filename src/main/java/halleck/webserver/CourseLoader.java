package halleck.webserver;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.OnlineCourse;
import halleck.api.Settings;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CourseLoader {

    private Settings settings;
    private Halleck hal;


    @Inject
    public CourseLoader(Settings settings, Halleck hal) {
        this.settings = settings;
        this.hal = hal;
    }

    public void load() {
        String courseLoadLocation = settings.getCourseLoadLocation();

        if (!Strings.isNullOrEmpty(courseLoadLocation)) {
            String content = getCourseContent(courseLoadLocation);
            if(!Strings.isNullOrEmpty(content)){

                hal.createCourses(createCourseArray(content));

                System.out.println("Loaded Course Content ");
            }
        }
    }

    Iterable<Course> createCourseArray(String content) {
        Gson g = new Gson();
        return g.fromJson(content, new TypeToken<List<OnlineCourse>>(){}.getType());
    }


    private String getCourseContent(String courseLoadLocation) {

        return getCourseContent(new File(courseLoadLocation));
    }

    private String getCourseContent(File file) {
        if(file.exists()){
            try {
                return Files.toString(file, Charsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Course load file not found: " + file.getAbsoluteFile());
        return null;
    }
}