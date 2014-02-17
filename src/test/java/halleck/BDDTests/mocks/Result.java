package halleck.BddTests.mocks;

public class Result {
    private final int status;
    private final String content;
    private final String redirect;

    public Result(MockResponse response){

        this.status = response.getStatus();
        this.content = response.getOutputString();
        this.redirect = response.getRedirectLocation();
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }

    public String getRedirect() {
        return redirect;
    }
}
