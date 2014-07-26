package halleck.webserver.featurechecks;


import com.google.common.collect.ImmutableSet;
import halleck.lms.AppContext;

import java.util.Set;
import java.util.function.Predicate;

public class UserChecker implements Predicate<AppContext> {

    private ImmutableSet<String> usernames;

    public UserChecker(Set<String> usernames){
        this.usernames = ImmutableSet.copyOf(usernames);
    }

    @Override
    public boolean test(AppContext appContext) {
        return usernames.contains(appContext.currentUser().getUserName());
    }
}
