package lms;

import halleck.Course;
import lms.learningobjects.Olt;
import webserver.mappers.CourseInputImpl;

import java.util.List;

import static lms.learningobjects.LoUtils.newCourseList;

public class TestData {
    public static void hydrate(){
        CourseRepository.addCourse(getAllCourses());
    }

    static List<Course> getAllCourses() {
        return newCourseList(
                createCourse("CC1",
                             "Clean Code, Episode 1 - Clean Code",
                        "Get ready for something very different. This ain't no screen cast. This ain't no talkin' head lecture. This is an Uncle Bob Video!",
                        "E1-960x540.mp4"),


                createCourse("CC2",
                             "Clean Code, Episode 2 - Names++",
                        "So, you think this is just going to be like the second chapter of the Clean Code book? Think again! This is a completely different take on the topic. Oh the goal is the same, certainly. But there are things covered in this video that were never mentioned in the book. And even those things that are similar are covered in a very different way. So even if you've read Clean Code fifty times over, this episode will give you more to think about.",
                        "E1-960x540.mp4")
        );
    }

    private static Olt createCourse(String id, String name, String desc, String url) {
        return new Olt(new CourseInputImpl(id, name, desc, url));

    }
}
