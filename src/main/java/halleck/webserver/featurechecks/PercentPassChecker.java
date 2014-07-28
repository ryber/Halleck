package halleck.webserver.featurechecks;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import halleck.lms.AppContext;
import halleck.lms.CurrentUser;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class PercentPassChecker implements Predicate<AppContext> {

    private final Die d100;
    private final LoadingCache<String, Boolean> sessionCache;
    private final int percentPass;

    public PercentPassChecker(int percentPass){
        this(percentPass, new D100());
    }

    public PercentPassChecker(int percentPass, Die die){
        this.percentPass = percentPass;
        this.sessionCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.DAYS)
                .build(new RollForSession());
        this.d100 = die;
    }

    @Override
    public boolean test(AppContext context) {
        CurrentUser currentUser = context.currentUser();
        if(currentUser.isAuthenticated()) {
            return sessionCache.getUnchecked(currentUser.getSessionId());
        }
        return false;
    }


    private class RollForSession extends CacheLoader<String,Boolean>{
        @Override
        public Boolean load(String key) throws Exception {
             return percentPass > d100.roll();
        }
    }
}
