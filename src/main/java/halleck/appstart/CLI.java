package halleck.appstart;

import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.webserver.HttpRouts;

public class CLI {

    public static void main(String[] args) {

        SettingsProvider.setOverrides(args);
        startServer(new BindingModule());

    }



    public static void startServer(BindingModule bindingModule) {
        Injector injector = Guice.createInjector(bindingModule);
        HttpRouts routs = injector.getInstance(HttpRouts.class);

        routs.init();
    }
}
