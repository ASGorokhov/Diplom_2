import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserUpdateWithAuthTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient
                .create(user)
                .extract()
                .path("accessToken")
                .toString()
                .substring(7);
    }

    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check that user email can be updated")
    public void checkUserEmailCanBeUpdated() {
        ValidatableResponse response = userClient.update(user.setEmail(UserGenerator.getUserEmail()), accessToken);

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 200", statusCode, equalTo(200));

        boolean isUserUpdated = response
                .extract()
                .path("success");
        assertTrue("User is not updated", isUserUpdated);

        String expectedUserEmail = user.getEmail();
        String actualUserEmail = response
                .extract()
                .path("user.email");
        assertEquals("Email is not the same", expectedUserEmail.toLowerCase(), actualUserEmail.toLowerCase());
    }

    @Test
    @DisplayName("Check that user password can be updated")
    public void checkUserPasswordCanBeUpdated() {
        ValidatableResponse response = userClient.update(user.setPassword(UserGenerator.getUserPassword()), accessToken);

        int statusCode = response.extract().statusCode();
        assertThat("Status code is not 200", statusCode, equalTo(200));

        boolean isUserUpdated = response.extract().path("success");
        assertTrue("User is not updated", isUserUpdated);
    }

    @Test
    @DisplayName("Check that user name can be updated")
    public void checkUserNameCanBeUpdated() {
        ValidatableResponse response = userClient.update(user.setName(UserGenerator.getUserName()), accessToken);

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 200", statusCode, equalTo(200));

        boolean isUserUpdated = response.extract().path("success");
        assertTrue("User is not updated", isUserUpdated);

        String expectedUserName = user.getName();
        String actualUserName = response
                .extract()
                .path("user.name");
        assertEquals("User name is not the same", expectedUserName, actualUserName);
    }
}