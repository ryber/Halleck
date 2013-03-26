package ioc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.inject.Provider;
import halleck.Settings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class SettingsProvider implements Provider<Settings> {

    private static File overrides;

    public static void setOverrides(String[] overrides) {
        SettingsProvider.overrides = getPath(overrides);
    }

    private static File getPath(String[] args) {
        if(args == null){
            return null;
        }
        File prop = new File(args[0]);
        if(prop.exists()){
            return prop;
        }
        return null;
    }


    private static Properties getProperties(Properties baseFile) {
        if(overrides == null){
            return baseFile;
        }

        Properties props = new Properties(baseFile);
        try {
            props.load(Files.newReaderSupplier(overrides, Charsets.UTF_8).getInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }


    @Override
    public Settings get() {

        Properties baseFile = getBaseFile();

        return new Settings(getProperties(baseFile));
    }

    private Properties getBaseFile() {
        Properties baseFile = new Properties();
        try {
            baseFile.load(getDefault());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseFile;
    }


    private InputStream getDefault() throws IOException {
        URL url = Resources.getResource("settings/halleck.properties");
        return Resources.newInputStreamSupplier(url).getInput();
    }
}
