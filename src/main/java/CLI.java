import com.google.inject.Guice;
import com.google.inject.Injector;
import ioc.BindingModule;
import webserver.HttpRouts;

public class CLI {

    public static void main(String[] args) {


        Injector injector = Guice.createInjector(new BindingModule());

        HttpRouts routs = injector.getInstance(HttpRouts.class);
        routs.registerRouts();
    }

}
