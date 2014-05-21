package halleck.BDDTests;

import halleck.lms.Settings;
import halleck.BDDTests.mocks.MockSettings;
import halleck.appstart.BindingModule;
import halleck.lms.AppContext;

public class TestBindings extends BindingModule {
    @Override
    protected void bindRepo() {
        bind(Settings.class).to(MockSettings.class);
        bind(AppContext.class).to(StaticContext.class);
    }
}
