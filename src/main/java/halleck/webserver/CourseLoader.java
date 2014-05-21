package halleck.webserver;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import halleck.lms.Course;
import halleck.lms.Halleck;
import halleck.lms.Settings;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public class CourseLoader {

    public static final String RESOURCE_PREFIX = "resource://";
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

                System.out.println("Loaded Course Content from " + courseLoadLocation);
            }
        }
    }

    Collection<Course> createCourseArray(String content) {
        Gson g = new Gson();
        return g.fromJson(content, new TypeToken<List<Course>>(){}.getType());
    }


    private String getCourseContent(String courseLoadLocation) {
        if(courseLoadLocation.startsWith(RESOURCE_PREFIX)){
            return getCourseResourceContent(courseLoadLocation);
        }else {
            return getCourseContent(new File(courseLoadLocation));
        }
    }

    private String getCourseResourceContent(String courseLoadLocation) {
        try {
            URL url = Resources.getResource(courseLoadLocation.substring(RESOURCE_PREFIX.length()));
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Course load file not found: " + courseLoadLocation);
        return null;
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
