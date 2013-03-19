package ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lms.TestData;
import webserver.HttpRouts;

public class CLI {

    public static void main(String[] args) {

        TestData.hydrate();

        startServer();

    }

    public static void startServer() {
        Injector injector = Guice.createInjector(new BindingModule());

        HttpRouts routs = injector.getInstance(HttpRouts.class);
        routs.init();
    }
}
