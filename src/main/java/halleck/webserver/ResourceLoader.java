package halleck.webserver;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import halleck.lms.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ResourceLoader {
    public static final String RESOURCE_PREFIX = "resource://";

    public Optional<String> getContent(String location){
        if (!Strings.isNullOrEmpty(location)) {
            if(location.startsWith(RESOURCE_PREFIX)){
                return getCourseResourceContent(location);
            }else {
                return getCourseContent(new File(location));
            }
        }
        return Optional.empty();
    }

    private Optional<String> getCourseResourceContent(String courseLoadLocation) {
        try {
            URL url = Resources.getResource(courseLoadLocation.substring(RESOURCE_PREFIX.length()));
            return Optional.of(Resources.toString(url, Charsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Config load file not found: " + courseLoadLocation);
            return Optional.empty();
        }
    }

    private Optional<String> getCourseContent(File file) {
        if(file.exists()){
            return Utils.tryget(() -> Files.toString(file, Charsets.UTF_8));
        }
        System.out.println("Config load file not found: " + file.getAbsoluteFile());
        return Optional.empty();
    }
}
