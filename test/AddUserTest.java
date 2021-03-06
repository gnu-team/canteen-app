import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.canteen_water.model.exception.DataException;
import org.canteen_water.model.DataReceiver;
import org.canteen_water.model.DataErrorReceiver;
import org.canteen_water.model.DataSuccessReceiver;
import org.canteen_water.model.MemoryDataSource;
import org.canteen_water.model.AccountType;
import org.canteen_water.model.User;

/**
 * Created by Hyungsuk Do on 11/9/16.
 */

public class AddUserTest {
    MemoryDataSource mds;
    boolean added;
    boolean ifFail;
    User b;
    User a;
    DataSuccessReceiver dsr;
    DataErrorReceiver der;
    @Before
    public void setUp() throws Exception {
        mds = MemoryDataSource.getInstance();
        dsr = ()-> added = true;
        der = (DataException e)-> ifFail = true;
        a = new User("hyungsuk505", "dlwi278", AccountType.USER);
        b = new User("jadsld5857", "dklfjdaod234", AccountType.USER);
    }

    @Test
    public void testAddSuccess() {
        mds.addUser(b, dsr, der);
        Assert.assertEquals("success to add", false, added);
    }

    @Test
    public void testAddFail() {
        mds.addUser(b, dsr, der);
        Assert.assertEquals("The account already exists", false, ifFail);
    }
}
