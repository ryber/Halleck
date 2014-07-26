package halleck.webserver.featurechecks;

import java.util.Random;

public class D100 implements Die {
    public int roll(){
        Random rand = new Random();
        return rand.nextInt((100 - 0) + 0);
    }
}
