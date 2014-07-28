package halleck.lms;

public interface Registration {
    Course getCourse();
    boolean isRegistered();

    boolean canRegister();
}
