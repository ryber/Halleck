package halleck.webserver.featurechecks;

import java.util.Random;

public class D100 implements Die {
    public int roll(){
        Random rand = new Random();
        return rand.nextInt((0 - 100) + 1) + 0;
    }
}
