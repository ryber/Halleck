package halleck.lms;

import java.util.function.Predicate;

public class FeatureContext {

    private static Predicate<Feature> checker = (f) -> false;

    public static boolean check(Feature feature) {
        return checker.test(feature);
    }

    public static synchronized void setChecker(Predicate<Feature> check) {
        checker = check;
    }
}
