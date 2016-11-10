package model;

import static org.junit.Assert.*;

import model.exception.DataException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * Created by XinZhang on 11/9/16.
 */
public class AuthenticateTestTest {
    MemoryDataSource msc;
    String useri;
    User a;
    User b;
    User c;
    User d;
    User e;
    User f;
    User g;
    User h;
    User i;
    boolean ifpass;
    boolean added = false;
    DataReceiver<User> rc = (User u)-> {useri = u.getUser();
        ifpass = true;
    };
    DataErrorReceiver erc = (DataException e)-> ifpass = false;

    DataSuccessReceiver src = ()-> added = true;
    @Before
    public void setup() throws Exception {
        msc = MemoryDataSource.getInstance();
        a = new User("aaabbbccc", "1234567890aa", AccountType.USER);
        b = new User("bbbcccddd", "11112222333zx", AccountType.USER);
        c = new User("cccccdddee","111133333aa", AccountType.USER);
        d = new User("dddddfffff","22441133zz", AccountType.WORKER);
        e = new User("ggggasdf","asdfasdfasfsa", AccountType.WORKER);
        f = new User("asdfsaff","fffffasda", AccountType.MANAGER);
        g = new User("asdgasdfagasdfa","aaaafff123", AccountType.MANAGER);
        h = new User("gggg123asd", "agsfasdfa3333", AccountType.ADMIN);
        i = new User("ggggaaaxxcvvv", "12312asdasda123", AccountType.ADMIN);

    }


    /**
     * Test when no user have stored in the dataSource
     */
    @Test
    public void testcase1(){
        msc.authenticate(a.getUser(),a.getPassword(),rc,erc);
        Assert.assertEquals("Failed for checking with non-exist User",false,ifpass);
        msc.authenticate(b.getUser(),b.getPassword(),rc,erc);
        Assert.assertEquals("Failed for checking with non-exist User",false,ifpass);
    }


    /**
     * Test on existing User
     */
    @Test
    public void testcase2(){
        msc.addUser(a,src,erc);
        msc.addUser(b,src,erc);
        msc.authenticate(a.getUser(),a.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking existing account", true,ifpass);
        ifpass = false;
        msc.authenticate(b.getUser(),b.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking existing account", true,ifpass);
        ifpass = false;
    }

    /**
     * Test on non-existing User
     */
    @Test
    public void testcase3(){
        msc.addUser(a,src,erc);
        msc.addUser(b,src,erc);
        msc.authenticate(c.getUser(),c.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking non-exist User", false,ifpass);
    }

    /**
     * Test on Worker Account
     */
    @Test
    public void testcase4(){
        msc.addUser(d,src,erc);
        msc.authenticate(d.getUser(),d.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking existing Worker", true, ifpass);
        msc.authenticate(e.getUser(),e.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking non-existing Worker", false, ifpass);
    }

    /**
     * Test on Manager
     */
    @Test
    public void testcase5(){
        msc.authenticate(f.getUser(),f.getPassword(),rc,erc);
        Assert.assertEquals("Fail to check non-exisiting Worker", false, ifpass);
        msc.addUser(f,src,erc);
        msc.authenticate(f.getUser(),f.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking existing Worker", true, ifpass);
        msc.authenticate(g.getUser(),g.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking non-existing Worker", false, ifpass);
    }

    /**
     * Test on Admin Account
     */
    @Test
    public void testcase6(){
        msc.authenticate(h.getUser(),h.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking existing Worker", false, ifpass);
        msc.addUser(g,src,erc);
        msc.authenticate(g.getUser(),g.getPassword(),rc,erc);
        Assert.assertEquals("Fail for checking non-existing Worker",true, ifpass);

    }

}
