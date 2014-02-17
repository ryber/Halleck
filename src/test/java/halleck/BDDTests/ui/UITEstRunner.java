package halleck.BddTests.ui;

import halleck.BddTests.TestBindings;
import halleck.BddTests.fixtures.ApplicationFixture;
import halleck.appstart.CLI;
import org.junit.After;
import org.junit.Before;

public class UITEstRunner {
    private static boolean serverStarted = false;

    @Before
    public void setUp() throws Exception {
        if(!serverStarted){
            CLI.startServer(new TestBindings());
            serverStarted = true;
        }
    }

    @After
    public void tearDown(){
        ApplicationFixture.reset();
    }
}
