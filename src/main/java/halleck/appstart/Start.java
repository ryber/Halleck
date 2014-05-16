package halleck.appstart;

import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.webserver.HttpRouts;

public class Start {

    public static void main(String[] args) {

        SettingsProvider.setCustomConfigFile(args);
        startServer(new BindingModule());

    }



    public static void startServer(BindingModule bindingModule) {
        Injector injector = Guice.createInjector(bindingModule);
        HttpRouts routs = injector.getInstance(HttpRouts.class);

        routs.init();
    }
}
