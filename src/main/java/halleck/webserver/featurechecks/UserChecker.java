package halleck.webserver.featurechecks;


import halleck.lms.AppContext;

import java.util.Set;
import java.util.function.Predicate;

public class UserChecker implements Predicate<AppContext> {

    private Set<String> usernames;

    public UserChecker(Set<String> usernames){
        this.usernames = usernames;
    }

    @Override
    public boolean test(AppContext appContext) {
        return usernames.contains(appContext.currentUser());
    }
}
