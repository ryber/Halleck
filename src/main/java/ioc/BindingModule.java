package ioc;

import com.google.inject.AbstractModule;
import halleck.Halleck;
import lms.Gurney;

public class BindingModule  extends AbstractModule {
    @Override
    protected void configure() {
         bind(Halleck.class).to(Gurney.class);

    }
}
