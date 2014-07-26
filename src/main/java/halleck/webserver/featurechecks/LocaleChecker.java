package halleck.webserver.featurechecks;

import halleck.lms.AppContext;

import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

public class LocaleChecker implements Predicate<AppContext> {

    private Set<Locale> locales;

    public LocaleChecker(Set<Locale> locales){
        this.locales = locales;
    }

    @Override
    public boolean test(AppContext context) {
        return locales.contains(context.currentUser().getLocale());
    }
}
