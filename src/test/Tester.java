import model.AccountType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import model.User;

/**
 * Created by Ph3ncyclidine on 11/15/16.
 */
public class Tester {
    User claude = new User("Claude", "passpass", AccountType.USER);
    User phony = new User("phony", "password", AccountType.ADMIN);

    @Test
    public void authenticateTest1() {
        Assert.assertTrue(claude.authenticate("Claude", "passpass"));
    }

    @Test
    public void authenticateTest2() {
        Assert.assertFalse(phony.authenticate("Claude", "passpass"));
    }

    @Test
    public void authenticateMismatch() {
        Assert.assertFalse(claude.authenticate("Claude", "turtles"));
    }

    @Test
    public void authenticateMismatch2() {
        Assert.assertFalse(claude.authenticate("Patrick", "passpass"));
    }

    @Test
    public void authenticateCaseMismatch() {
        Assert.assertFalse(claude.authenticate("claude", "Passpass"));
    }
}
