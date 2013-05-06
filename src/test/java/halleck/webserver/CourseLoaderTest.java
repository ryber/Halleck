package halleck.webserver;


import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import halleck.api.Course;
import halleck.api.OnlineCourse;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.util.MyAsserts.assertEquals;

public class CourseLoaderTest {
    @Test
    public void testGSonStuff() throws Exception {
        CourseLoader l = new CourseLoader(null, null);

        Course source = new OnlineCourse("42","Underwater Basketweaving", "a long long description", "http://www.google.com",2);

        Iterable<Course> courses = l.createCourseArray(new Gson().toJson(newArrayList(source)));
        Course c = Iterables.get(courses,0);

        assertEquals(1, Iterables.size(courses));
        assertEquals("42", c.getId());
        assertEquals("Underwater Basketweaving", c.getName());
        assertEquals("a long long description", c.getDescription());
        assertEquals("http://www.google.com", c.getUrl());
        assertEquals((Integer)2, c.getMaxEnrollment());

    }
}
