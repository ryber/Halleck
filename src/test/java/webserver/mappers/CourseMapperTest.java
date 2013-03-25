package webserver.mappers;

import halleck.Course;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class CourseMapperTest {

    @Test
    public void canMapRequestIntoCourse() throws Exception {
        FormVars form = new FormVars();
        form.put("id", 42);
        form.put("name", "Underwater Basketweaving");
        form.put("description", "fishy went wherever I did go");
        form.put("url", "http://foo/foo.mp4");
        form.put("max", "566");

        CourseMapper mapper = new CourseMapper();

        Course inputCourse = mapper.apply(form);

        assertEquals("42", inputCourse.getId());
        assertEquals("Underwater Basketweaving", inputCourse.getName());
        assertEquals("fishy went wherever I did go", inputCourse.getDescription());
        assertEquals("http://foo/foo.mp4", inputCourse.getUrl());
        assertEquals(566, inputCourse.getMaxEnrollment().intValue());
    }

    @Test
    public void willNotBombIfPartsAreMissing() throws Exception {
        FormVars form = new FormVars();
        form.put("name", "Underwater Basketweaving");

        CourseMapper mapper = new CourseMapper();

        Course inputCourse = mapper.apply(form);

        assertEquals(null, inputCourse.getId());
        assertEquals("Underwater Basketweaving", inputCourse.getName());
    }

    @Test
    public void emptyMaxResultsInNUll(){
        FormVars form = new FormVars();
        form.put("max", "");

        CourseMapper mapper = new CourseMapper();

        Course inputCourse = mapper.apply(form);

        assertNull(inputCourse.getMaxEnrollment());
    }


}
