package halleck.lms;

public interface AppContext {
    String currentUser();
    void setCurrentUser(String username);
    void clear();
}
