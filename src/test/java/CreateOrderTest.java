import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.OrderGenerator;
import model.User;
import model.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class CreateOrderTest {

    private OrderClient orderClient;
    private Order order;
    private String accessToken;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        User user = UserGenerator.getRandomUser();
        orderClient = new OrderClient();
        accessToken = userClient
                .create(user)
                .extract()
                .path("accessToken")
                .toString()
                .substring(7);
    }

    @Test
    @DisplayName("Check that an order can be created by an authorized user")
    public void checkOrderCreationWithAuth() {
        order = OrderGenerator.generateOrder();
        ValidatableResponse response = orderClient.create(order, accessToken);

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 200", statusCode, equalTo(200));

        boolean isOrderCreated = response
                .extract()
                .path("success");
        assertTrue("An order has not been created", isOrderCreated);

        int orderNumber = response
                .extract()
                .path("order.number");
        assertThat("Order number is 0", orderNumber, is(not(0)));

        String userName = response
                .extract()
                .path("order.owner.name");
        assertNotNull("User name is null", userName);

        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check that an order cannot be created by an unauthorized user")
    public void checkOrderCreationWithoutAuthCannotBeDone() {
        order = OrderGenerator.generateOrder();
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 400", statusCode, equalTo(400));

        boolean isOrderCreated = response
                .extract()
                .path("success");
        assertFalse("An order has been created", isOrderCreated);
    }

    @Test
    @DisplayName("Check that an order cannot be created without ingredients")
    public void checkOrderWithoutIngredientsCannotBeCreated() {
        order = new Order();
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 400", statusCode, equalTo(400));

        boolean isOrderCreated = response
                .extract()
                .path("success");
        assertFalse("An order has been created", isOrderCreated);

        String message = response
                .extract()
                .path("message");
        assertThat("Ingredient ids are not provided", message, equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Check that an order cannot be created with incorrect ingredients' ids")
    public void checkOrderWithInvalidIngredientsCannotBeCreated() {
        List<String> ids = new ArrayList<>();
        ids.add("invalid_ingredient");
        ids.add("");
        order = new Order(ids);
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response
                .extract()
                .statusCode();
        assertThat("Status code is not 500", statusCode, equalTo(500));
    }
}