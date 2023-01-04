import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class UserSignUpValidationTest {

    private final String email;
    private final String password;
    private final String name;

    public UserSignUpValidationTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"", UserGenerator.getUserPassword(), UserGenerator.getUserName()},
                {UserGenerator.getUserEmail(), "", UserGenerator.getUserName()},
                {UserGenerator.getUserEmail(), UserGenerator.getUserPassword(), ""},
                {null, UserGenerator.getUserPassword(), UserGenerator.getUserName()},
                {UserGenerator.getUserEmail(), null, UserGenerator.getUserName()},
                {UserGenerator.getUserEmail(), UserGenerator.getUserPassword(), null},
                {"", "", ""},
                {null, null, null}
        };
    }

    @Test
    @DisplayName("Check that a user cannot be created without email, password and name")
    public void checkSignUpWithInvalidRequestIsNotAllowed() {
        final int expectedStatus = 403;
        final String expectedMessage = "Email, password and name are required fields";

        User user = new User(email, password, name);
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.create(user);

        int actualStatus = response
                .extract()
                .statusCode();
        assertThat("Status code is not 403", actualStatus, equalTo(expectedStatus));

        boolean isUserCreated = response
                .extract()
                .path("success");
        assertFalse("User without mandatory fields was created", isUserCreated);

        String actualMessage = response
                .extract()
                .path("message");
        assertThat("Error message is incorrect", actualMessage, equalTo(expectedMessage));
    }
}

