package lms.learningobjects;

import halleck.Course;
import halleck.Registration;

public class UserRegistration implements Registration {
    private final Course course;
    private final String userID;


    public UserRegistration(Course course, String userID) {
        this.course = course;
        this.userID = userID;
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
        return course.getRegisteredUsers().contains(userID);
    }

    public boolean canRegister() {
        return course.getFreeSeats() > 0 && !isRegistered();
    }
}
