package halleck.appstart;

import halleck.lms.Settings;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsProviderTest {
    @After
    public void tearDown() {
        SettingsProvider.clear();
    }

    @Test
    public void willGetDefaultSettingsByDefault() {
        SettingsProvider.setCustomConfigFile(null);
        SettingsProvider.setCustomConfigFile(new String[]{});
        SettingsProvider provider = new SettingsProvider();
        Settings settings = provider.get();
        assertEquals("Halleck LMS", settings.getSiteName());
    }

    @Test
    public void ifProvidedDemoFlagThenWillReturnDemoConfig() {
        SettingsProvider.setCustomConfigFile(new String[]{"demo"});
        SettingsProvider provider = new SettingsProvider();
        Settings settings = provider.get();
        assertEquals("Halleck LMS Demo", settings.getSiteName());
    }

    @Test
    public void ifProvidedAlternateLocationThenWillReturnThatOne() {
        SettingsProvider.setCustomConfigFile(new String[]{"src/test/resources/test-settings.properties"});
        SettingsProvider provider = new SettingsProvider();
        Settings settings = provider.get();
        assertEquals("The Barron's Playroom", settings.getSiteName());
        assertEquals(4567, settings.getAppPort());
    }
}