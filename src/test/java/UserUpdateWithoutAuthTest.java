import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserUpdateWithoutAuthTest {

    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Check that user email cannot be updated without authorization")
    public void checkUserEmailCannotBeUpdated() {
        ValidatableResponse response = userClient.update(user.setEmail(UserGenerator.getUserEmail()));

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 401", statusCode, equalTo(401));

        String errorMessage = response
                .extract()
                .path("message");
        assertThat("Error message is incorrect", errorMessage, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check that user password cannot be updated without authorization")
    public void checkUserPasswordCannotBeUpdated() {
        ValidatableResponse response = userClient.update(user.setPassword(UserGenerator.getUserPassword()));

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 401",
                statusCode, equalTo(401));

        String errorMessage = response
                .extract()
                .path("message");
        assertThat("Error message is incorrect", errorMessage, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check that user name cannot be updated without authorization")
    public void checkUserNameCannotBeUpdated() {
        ValidatableResponse response = userClient.update(user.setName(UserGenerator.getUserName()));

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 401", statusCode, equalTo(401));

        String errorMessage = response
                .extract()
                .path("message");
        assertThat("Error message is incorrect", errorMessage, equalTo("You should be authorised"));
    }
}