package ioc;

import com.google.common.io.Resources;
import com.google.inject.Provider;
import halleck.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class SettingsProvider implements Provider<Settings> {
    @Override
    public Settings get() {
        Properties properties = new Properties();
        try {
            properties.load(getDefault());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Settings(properties);
    }


    private InputStream getDefault() throws IOException {
        URL url = Resources.getResource("settings/halleck.properties");
        return Resources.newInputStreamSupplier(url).getInput();
    }
}
