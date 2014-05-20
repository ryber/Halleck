package halleck.lms;

@FunctionalInterface
public interface FeatureChecker {
    boolean check(Feature feature);
}
