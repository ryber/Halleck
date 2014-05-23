package halleck.lms.mongomappers;

import com.mongodb.DBObject;
import halleck.lms.Course;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static org.junit.Assert.*;

public class BaseCourseToMongoMapperTest {
    @Test
    public void canCreateADbObjectFromACourse() throws Exception {
        Course c = new Course("42", "Spice Harvesting");
        c.setMaxCapacity(123);
        c.setDecription("a description");
        c.addRegisteredUsers(newArrayList("Duncan", "Jessica"));
        c.setOwner("Paul");

        BaseCourseToMongoMapper mapper = new BaseCourseToMongoMapper();

        DBObject obj = mapper.apply(c);

        assertEquals("42", obj.get(BaseCourseToMongoMapper.ID));
        assertEquals("Spice Harvesting", obj.get(BaseCourseToMongoMapper.NAME));
        assertEquals(123, obj.get(BaseCourseToMongoMapper.MAX));
        Set users = (Set)obj.get(BaseCourseToMongoMapper.USERS);
        assertTrue(users.contains("Jessica"));
        assertTrue(users.contains("Duncan"));
        assertEquals("Paul", obj.get(BaseCourseToMongoMapper.OWNER));
    }
}