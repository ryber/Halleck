package halleck.webserver;


import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import halleck.lms.Course;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.util.MyAsserts.assertEquals;
import static halleck.lms.Utils.toInteger;

public class CourseLoaderTest {
    @Test
    public void testGSonStuff() throws Exception {
        CourseLoader l = new CourseLoader(null, null);

        Course source = new Course("42", "Underwater Basketweaving");
        source.setDecription("a long long description");
        source.setUrl("http://www.google.com");
        source.setMaxCapacity(2);
        source.setContent("fishy content");


        ArrayList<Course> sourceList = newArrayList(source);
        Collection<Course> courses = l.createCourseArray(new Gson().toJson(sourceList));
        Course c = courses.stream().findAny().get();

        assertEquals(1, courses.size());
        assertEquals("42", c.getId());
        assertEquals("Underwater Basketweaving", c.getName());
        assertEquals("a long long description", c.getDescription());
        assertEquals("http://www.google.com", c.getUrl());
        assertEquals((Integer)2, c.getMaxEnrollment());
        assertEquals("fishy content", c.getContent());

    }
}
