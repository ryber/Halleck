package halleck.lms;

import com.mongodb.DBObject;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MongoCourseRepositoryTest {

    @Test
    public void canMapBackAndForth() throws Exception {
        Course c = new Course("42", "Spice Harvesting");
        c.setMaxCapacity(123);
        c.setDecription("a description");
        c.addRegisteredUsers(newArrayList("Duncan", "Jessica"));
        c.setOwner("Paul");
        c.addChild("child1");
        c.addChild("child2");
        c.setContent("pictures of sandworms");

        MongoCourseRepository mapper = new MongoCourseRepository(null);

        Course obj = mapper.map(mapper.map(c));

        assertEquals("42", obj.getId());
        assertEquals("Spice Harvesting", obj.getName());
        assertEquals((Integer)123, obj.getMaxEnrollment());
        Set users = obj.getRegisteredUsers();
        assertTrue(users.contains("Jessica"));
        assertTrue(users.contains("Duncan"));
        assertEquals("Paul", obj.getOwner());
        List dojo = obj.getChildIds();
        assertTrue(dojo.contains("child1"));
        assertTrue(dojo.contains("child2"));
        assertEquals("pictures of sandworms", c.getContent());
    }

}