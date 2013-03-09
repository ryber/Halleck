package lms.learningobjects;

import halleck.Course;

public class Olt implements Course {
    private String name;

    public Olt(String id, String name, String descrription) {

        this.name = name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
