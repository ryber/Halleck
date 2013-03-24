package lms.learningobjects;

import halleck.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoUtils {
    public static <T extends Course>  List<Course> newCourseList(T... courses){
        List<Course> result = new ArrayList<Course>();
        Collections.addAll(result, courses);
        return result;
    }
}
