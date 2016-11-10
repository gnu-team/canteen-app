package model.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by jitaekim on 11/9/16.
 */
public class ApiErrorTest {
    /**
     * The test fixture
     */
    ApiError api;
    StringBuilder sb;
    String[] array;
    public ApiErrorTest() throws Exception {
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is run before each test
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        api = new ApiError();
        sb = new StringBuilder();
    }

    /**
     * Test initial length
     */
    @Test
    public void testInitialLength() {
        api.listReasons(sb, null, array);
        Assert.assertEquals("length wrong", 0, sb.length());
    }

    /**
     * Test initial length and length after appending Stringbuilder
     */
    @Test
    public void testAfterLength() {
        Assert.assertEquals("Initial Count Wrong", 0, sb.length());
        try {
            array = new String[4];
            array[0] = "test1";
            array[1] = "test2";
            array[2] = "test3";
            array[3] = "test4";
            api.listReasons(sb, "test", array);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail("Should not have thrown exception here");
        }
        Assert.assertEquals("Count wrong length after appending", 32, sb.length());
    }

    /**
     * Test whether it appended correctly or not
     */
    @Test
    public void testAppedning() {
        try {
            array = new String[3];
            array[0] = "test1";
            array[1] = "test2";
            array[2] = "test3";
            String rv = api.listReasons(sb, "test", array).toString();
            Assert.assertEquals("content is wrong", "test: test1; test2; test3", rv);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail("Pop should not have thrown exception here");
        }
    }
}
