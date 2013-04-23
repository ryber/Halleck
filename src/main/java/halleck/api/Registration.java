package halleck.api;

public interface Registration {
    String getUserID();
    Course getCourse();
    boolean isRegistered();

    boolean canRegister();
}
