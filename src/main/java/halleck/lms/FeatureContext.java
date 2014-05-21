package halleck.lms;

import java.util.function.Predicate;

public class FeatureContext {

    private static Predicate<Feature> checker;

    static {
        clear();
    }

    public static boolean check(Feature feature) {
        return checker.test(feature);
    }

    public static synchronized void setChecker(Predicate<Feature> check) {
        checker = check;
    }

    public static void clear() {
        checker = (f) -> false;
    }
}
