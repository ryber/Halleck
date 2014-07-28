package halleck.lms;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void propogateSupplierTurnsCheckedExceptionsIntoRuntimes() throws Exception {
        Utils.ExceptionalSupplier ex = () -> {throw new SuperCheckedException();};

        try{
            Utils.propogate(ex);
            fail("Should Have Thrown");
        }catch (RuntimeException re){
            assertEquals(true, re.getCause() instanceof SuperCheckedException);
        }
    }

    @Test
    public void propogateRunnableTurnsCheckedExceptionsIntoRuntimes() throws Exception {
        Utils.ExceptionaRunnable ex = () -> {throw new SuperCheckedException();};

        try{
            Utils.propogate(ex);
            fail("Should Have Thrown");
        }catch (RuntimeException re){
            assertEquals(true, re.getCause() instanceof SuperCheckedException);
        }
    }

    private class SuperCheckedException extends Exception {
    }
}