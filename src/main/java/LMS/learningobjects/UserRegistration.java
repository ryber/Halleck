package lms.learningobjects;

import halleck.Course;
import halleck.Registration;

public class UserRegistration implements Registration {
    private final Course course;
    private final String userID;
    private final boolean isRegistered;

    public UserRegistration(Course course, String userID, boolean isRegistered) {
        this.course = course;
        this.userID = userID;
        this.isRegistered = isRegistered;

    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public Course getCourse() {
        return course;
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }
}
