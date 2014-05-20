package halleck.lms;

public class FeatureContext {

    private static FeatureChecker checker = (f) -> false;

    public static boolean check(Feature feature) {
        return checker.check(feature);
    }

    public static synchronized void setChecker(FeatureChecker check) {
        checker = check;
    }
}
