package halleck.lms;

public interface Registration {
    String getUserID();
    Course getCourse();
    boolean isRegistered();

    boolean canRegister();
}
