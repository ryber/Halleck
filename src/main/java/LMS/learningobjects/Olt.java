package lms.learningobjects;

import halleck.Course;

public class Olt implements Course {
    private final String id;
    private String name;
    private final String descrription;

    public Olt(String id, String name, String descrription) {
        this.id = id;
        this.name = name;
        this.descrription = descrription;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return descrription;
    }

    @Override
    public String getDescriptionShort() {
        return descrription.substring(0, 100);
    }

    @Override
    public String getVideoName() {
        return "E1-960x540.mp4";
    }
}
