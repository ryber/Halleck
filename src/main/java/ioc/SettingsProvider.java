package ioc;

import com.google.common.base.Charsets;
import com.google.inject.Provider;
import halleck.Settings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static com.google.common.io.Files.newReaderSupplier;
import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.newInputStreamSupplier;

public class SettingsProvider implements Provider<Settings> {

    private static File overrides;

    public static void setOverrides(String[] overrides) {
        SettingsProvider.overrides = getPath(overrides);
    }

    private static File getPath(String[] args) {

        if(args != null && args.length > 0){
            File prop = new File(args[0]);
            if(prop.exists()){
                return prop;
            }
        }

        return null;
    }


    private static Properties getProperties(Properties baseFile) {
        if(overrides == null){
            return baseFile;
        }

        Properties props = new Properties(baseFile);
        try {
            props.load(getOverridePropertyFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private static InputStreamReader getOverridePropertyFile() throws IOException {
        return newReaderSupplier(overrides, Charsets.UTF_8).getInput();
    }


    @Override
    public Settings get() {
        return new Settings(getProperties(getBaseFile()));
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
        return newInputStreamSupplier(getResource("settings/halleck.properties")).getInput();
    }
}
