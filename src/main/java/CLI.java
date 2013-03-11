import com.google.inject.Guice;
import com.google.inject.Injector;
import ioc.BindingModule;
import lms.TestData;
import webserver.HttpRouts;

public class CLI {

    public static void main(String[] args) {

        TestData.hydrate();



        Injector injector = Guice.createInjector(new BindingModule());

        HttpRouts routs = injector.getInstance(HttpRouts.class);
        routs.registerRouts();

    }
}
