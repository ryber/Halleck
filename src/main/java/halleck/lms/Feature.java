package halleck.lms;


public enum Feature {

    DOJOS;

    public boolean isActive() {
        //return FeatureContext.get().isActive(this);
        return false;
    }
}
