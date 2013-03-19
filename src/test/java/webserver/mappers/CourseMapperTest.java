package webserver.mappers;

import halleck.CourseInput;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CourseMapperTest {


    @Test
    public void canMapRequestIntoCourse() throws Exception {
        FormVars form = new FormVars();
        form.put("id", 42);
        form.put("name", "Underwater Basketweaving");
        form.put("description", "fishy went wherever I did go");
        form.put("url", "http://foo/foo.mp4");

        CourseMapper mapper = new CourseMapper();

        CourseInput inputCourse = mapper.apply(form);

        assertEquals("42", inputCourse.getId());
        assertEquals("Underwater Basketweaving", inputCourse.getName());
        assertEquals("fishy went wherever I did go", inputCourse.getDescription());
        assertEquals("http://foo/foo.mp4", inputCourse.getUrl());
    }

    @Test
    public void willNotBombIfPartsAreMissing() throws Exception {
        FormVars form = new FormVars();
        form.put("name", "Underwater Basketweaving");

        CourseMapper mapper = new CourseMapper();

        CourseInput inputCourse = mapper.apply(form);

        assertEquals(null, inputCourse.getId());
        assertEquals("Underwater Basketweaving", inputCourse.getName());
    }
}
