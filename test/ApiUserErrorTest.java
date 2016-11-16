import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import model.api.ApiUserError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ApiUserErrorTest {
    Gson gson;

    @Before
    public void initialize() {
        gson = new Gson();
    }

    @Test
    public void testEmpty() {
        ApiUserError err = jsonToInstance("{}");
        String detail = err.getDetail();
        assertNull("{} should return detail == null.", detail);
    }

    @Test
    public void testNullDetail() {
        ApiUserError err = jsonToInstance("{\"detail\": null}");
        String detail = err.getDetail();
        assertNull("{\"detail\": null} should return detail == null.", detail);
    }

    @Test
    public void testOnlyDetail() {
        String expected = "abcdef    ";
        ApiUserError err = jsonToInstance("{\"detail\": \"" + expected  + "\"}");
        String detail = err.getDetail();
        assertEquals("{\"detail\": \"...\"} should return detail == the contents of the detail tag.",
                     expected, detail);
    }

    @Test
    public void testOnlyNullDetail() {
        ApiUserError err = jsonToInstance("{\"detail\": null}");
        String detail = err.getDetail();
        assertNull("{\"detail\": null} should return detail == null.", detail);
    }

    @Test
    public void testUsernameAndDetail() {
        ApiUserError err = jsonToInstance("{\"detail\":   \"abcdetail\","
                                         + "\"username\": [\"Austin J. Adams IV\"]}");
        String detail = err.getDetail();
        String expected = "abcdetail\nUser name: Austin J. Adams IV";
        assertEquals("{\"detail\": \"...\", \"username\": [...]} should return both detail and username.",
                     expected, detail);
    }

    @Test
    public void testEmptyFieldsAndDetail() {
        ApiUserError err = jsonToInstance("{\"detail\":   \"foo bar baz\","
                                         + "\"username\": [],"
                                         + "\"password\": [],"
                                         + "\"bio\": [],"
                                         + "\"group\": []}");
        String detail = err.getDetail();
        String expected = "foo bar baz";
        assertEquals("{\"detail\": \"...\", \"x\": [], \"y\": [], ...} should return only detail.",
                     expected, detail);
    }

    @Test
    public void testEmptyFields() {
        ApiUserError err = jsonToInstance("{\"username\": [],"
                                         + "\"password\": [],"
                                         + "\"bio\": [],"
                                         + "\"group\": []}");
        String detail = err.getDetail();
        assertNull("{\"x\": [], \"y\": [], ...} should return null.", detail);
    }

    @Test
    public void testBogusFields() {
        ApiUserError err = jsonToInstance("{\"asdkfjasdkfj\": [\"sdfjaj\",\"ksjsjj\"],"
                                         + "\"jsjsjsjskj\": [\"sdfjaj\",\"ksjsjj\",\"wew\"],"
                                         + "\"k34jk4jkj5kj\": [\"sdfjaj\"],"
                                         + "\"k3j5kj\": [],"
                                         + "\"     \": null}");
        String detail = err.getDetail();
        assertNull("{?: [...], ?: [...], ...} should return null.", detail);
    }

    @Test
    public void testBogusFieldsAndDetail() {
        ApiUserError err = jsonToInstance("{\"asdkfjasdkfj\": [\"sdfjaj\",\"ksjsjj\"],"
                                         + "\"jsjsjsjskj\": [\"sdfjaj\",\"ksjsjj\",\"wew\"],"
                                         + "\"k34jk4jkj5kj\": [\"sdfjaj\"],"
                                         + "\"k3j5kj\": [],"
                                         + "\"     \": null,"
                                         + "\"detail\": \"I'd just like to interject for a moment.\"}");
        String detail = err.getDetail();
        String expected = "I'd just like to interject for a moment.";
        assertEquals("{\"detail\": \"...\", ?: [...], ?: [...], ...} should return detail.",
                     expected, detail);
    }

    @Test
    public void testSomeFields() {
        ApiUserError err = jsonToInstance("{\"username\": [\"Invalid characters\", \"Too short\"],"
                                         + "\"password\": [\"Too long\"],"
                                         + "\"bio\": [\"Talk about your walks on the beach, dude\"],"
                                         + "\"group\": [\"Invalid\", \"This is personal\"]}");
        String detail = err.getDetail();
        String expected = "User name: Invalid characters; Too short\n" +
                          "Group name: Invalid; This is personal\n" +
                          "Password: Too long\n" +
                          "Bio: Talk about your walks on the beach, dude";
        assertEquals(expected, detail);
    }

    private ApiUserError jsonToInstance(String json) {
        return gson.fromJson(json, ApiUserError.class);
    }
}
