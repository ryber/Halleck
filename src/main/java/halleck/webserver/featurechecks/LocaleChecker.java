package halleck.webserver.featurechecks;

import com.google.common.collect.ImmutableSet;
import halleck.lms.AppContext;

import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

public class LocaleChecker implements Predicate<AppContext> {

    private ImmutableSet<Locale> locales;

    public LocaleChecker(Set<Locale> locales){
        this.locales = ImmutableSet.copyOf(locales);
    }

    @Override
    public boolean test(AppContext context) {
        return locales.contains(context.currentUser().getLocale());
    }
}
