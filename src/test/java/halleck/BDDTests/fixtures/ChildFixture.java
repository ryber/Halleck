package halleck.BDDTests.fixtures;

import halleck.lms.Course;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

public class ChildFixture {
    private List<Course> courseDojo;

    public ChildFixture(Stream<Course> courseDojo) {

        this.courseDojo = courseDojo.collect(toList());
    }

    public ChildFixture hasChild(String id, String name) {
        assertTrue(courseDojo.stream()
                .anyMatch(c -> c.getId().equals(id) && c.getName().equals(name)));
        return this;
    }
}
