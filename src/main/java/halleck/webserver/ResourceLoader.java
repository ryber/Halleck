package halleck.webserver;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ResourceLoader {
    public static final String RESOURCE_PREFIX = "resource://";

    public String getContent(String location){
        if (!Strings.isNullOrEmpty(location)) {
            if(location.startsWith(RESOURCE_PREFIX)){
                return getCourseResourceContent(location);
            }else {
                return getCourseContent(new File(location));
            }
        }
        return "";
    }

    private String getCourseResourceContent(String courseLoadLocation) {
        try {
            URL url = Resources.getResource(courseLoadLocation.substring(RESOURCE_PREFIX.length()));
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Config load file not found: " + courseLoadLocation);
        return null;
    }

    private String getCourseContent(File file) {
        if(file.exists()){
            try {
                return Files.toString(file, Charsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Config load file not found: " + file.getAbsoluteFile());
        return null;
    }
}
