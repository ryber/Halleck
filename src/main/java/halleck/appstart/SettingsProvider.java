package halleck.appstart;

import com.google.common.base.Charsets;
import com.google.inject.Provider;
import halleck.api.Settings;
import halleck.webserver.AppSettings;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.function.Supplier;

import static com.google.common.io.Files.newReaderSupplier;
import static com.google.common.io.Resources.getResource;

public class SettingsProvider implements Provider<Settings> {

    public static final String DEFAULT_PROPERTIES = "settings/halleck.properties";
    public static final String DEMO_PROPERTIES = "test-config/demo.properties";
    private static Supplier<Reader> customConfigFile;
    private static final String DEMO_MODE = "demo";


    public static void setCustomConfigFile(String[] args) {
        if(args != null && args.length > 0){
            parseArguments(args[0]);
        } else {
            System.out.println("Loading with default properties");
        }
    }

    private static void parseArguments(String arg) {
        if(DEMO_MODE.equalsIgnoreCase(arg)){
            setDemoProperties();
        }else {
            setCustomProperties(arg);
        }
    }

    private static void setDemoProperties() {
        System.out.println("Loading with demo configuration");
        SettingsProvider.customConfigFile = () -> getResourceInputStream(DEMO_PROPERTIES);
    }

    private static void setCustomProperties(String arg) {
        File prop = new File(arg);
        if (prop.exists()) {
            System.out.println("Loading with properties: " + prop.getPath());
            SettingsProvider.customConfigFile = () -> getOverridePropertyFile(prop);
        }else {
            System.out.println("Supplied property file not found! " + prop.getAbsolutePath());
        }
    }


    @Override
    public Settings get() {
        return new AppSettings(getProperties(getBaseFile()));
    }

    private Properties getBaseFile() {
        Properties baseFile = new Properties();
        try {
            baseFile.load(getResourceInputStream(DEFAULT_PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseFile;
    }


    private static Properties getProperties(Properties baseFile) {
        if(customConfigFile == null){
            return baseFile;
        }

        Properties props = new Properties(baseFile);
        try {
            props.load(customConfigFile.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private static Reader getOverridePropertyFile(File customProp) {
        try {
            return newReaderSupplier(customProp, Charsets.UTF_8).getInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static Reader getResourceInputStream(String rezLocation) {
        try {
            return getOverridePropertyFile(new File(getResource(rezLocation).toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
