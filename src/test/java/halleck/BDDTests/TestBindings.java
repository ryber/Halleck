package halleck.BddTests;

import halleck.api.Settings;
import halleck.BddTests.mocks.MockSettings;
import halleck.appstart.BindingModule;
import halleck.lms.AppContext;

public class TestBindings extends BindingModule {
    @Override
    protected void bindRepo() {
        bind(Settings.class).to(MockSettings.class);
        bind(AppContext.class).to(StaticContext.class);
    }
}
