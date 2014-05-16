package halleck.BDDTests.ui;

import halleck.BDDTests.TestBindings;
import halleck.BDDTests.fixtures.ApplicationFixture;
import halleck.appstart.Start;
import org.junit.After;
import org.junit.Before;

public class UITEstRunner {
    private static boolean serverStarted = false;

    @Before
    public void setUp() throws Exception {
        if(!serverStarted){
            Start.startServer(new TestBindings());
            serverStarted = true;
        }
    }

    @After
    public void tearDown(){
        ApplicationFixture.reset();
    }
}
