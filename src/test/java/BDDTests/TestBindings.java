package BDDTests;

import halleck.api.Settings;
import BDDTests.mocks.MockSettings;
import halleck.appstart.BindingModule;

public class TestBindings extends BindingModule {
    @Override
    protected void bindRepo() {
        bind(Settings.class).to(MockSettings.class);
    }
}