package webserver.mappers;

import halleck.CourseInput;

public class CourseInputImpl implements CourseInput {
    private final String id;
    private final String name;
    private final String description;
    private final String url;

    public CourseInputImpl(String id, String name, String description, String url) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
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
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
