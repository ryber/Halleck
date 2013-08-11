package halleck.webserver.mappers;

import halleck.api.Course;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class CourseMapperTest {

    private CourseMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new CourseMapper();
    }

    @Test
    public void canMapRequestIntoCourse() throws Exception {
        FormVars form = new FormVars();
        form.put("id", 42);
        form.put("name", "Underwater Basketweaving");
        form.put("description", "fishy went wherever I did go");
        form.put("url", "http://foo/foo.mp4");
        form.put("max", "566");
        form.put("content", "something completely different");

        Course inputCourse = mapper.apply(form);

        assertEquals("42", inputCourse.getId());
        assertEquals("Underwater Basketweaving", inputCourse.getName());
        assertEquals("fishy went wherever I did go", inputCourse.getDescription());
        assertEquals("http://foo/foo.mp4", inputCourse.getUrl());
        assertEquals(566, inputCourse.getMaxEnrollment().intValue());
        assertEquals("something completely different", inputCourse.getContent());
    }

    @Test
    public void willNotBombIfPartsAreMissing() throws Exception {
        FormVars form = new FormVars();
        form.put("name", "Underwater Basketweaving");

        Course inputCourse = mapper.apply(form);

        assertEquals(null, inputCourse.getId());
        assertEquals("Underwater Basketweaving", inputCourse.getName());
    }

    @Test
    public void emptyMaxResultsInNUll(){
        FormVars form = new FormVars();
        form.put("max", "");

        Course inputCourse = mapper.apply(form);

        assertNull(inputCourse.getMaxEnrollment());
    }


}
