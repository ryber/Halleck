package halleck.webserver;

public class PageContext {

    private static ThreadLocal<Context> context = new ThreadLocal<>();

    public static void set(User user, String sitename){
        context.set(new Context(user, sitename));
    }

    public static Context get(){
        return context.get();
    }

    public static void clear(){
        context.remove();
    }

    public static class Context {
        private final User username;
        private final String sitename;

        public Context(User username, String sitename){
            this.username = username;
            this.sitename = sitename;
        }

        public User getUser() {
            return username;
        }

        public String getSitename() {
            return sitename;
        }
    }
}
