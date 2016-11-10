package model;

import model.exception.DataException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by Hyungsuk Do on 11/9/16.
 */


public class addUserTest {
    MemoryDataSource mds;
    boolean added = false;
    boolean ifFail = true;
    User a;
    User b;
    User c;
    DataSuccessReceiver dsr = ()-> added = true;
    DataErrorReceiver der = (DataException e)-> ifFail = false;
    @Before
    public void setUp() throws Exception {
        mds = MemoryDataSource.getInstance();
        b = new User("jadsld5857", "dklfjdopsfhaod234", AccountType.USER);
    }

    @Test
    public void testAddFail() {
        mds.addUser(b, dsr, der);
        Assert.assertEquals("success to add", true, ifFail);
        mds.addUser(b, dsr, der);
        Assert.assertEquals("The account already exists", false, ifFail);
    }
}
