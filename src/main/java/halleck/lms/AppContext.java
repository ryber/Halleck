package halleck.lms;

public interface AppContext {
    CurrentUser currentUser();
    void setCurrentUser(CurrentUser username);
    void clear();
}
