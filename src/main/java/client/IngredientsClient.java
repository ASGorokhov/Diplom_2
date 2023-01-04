package client;

import io.qameta.allure.Step;
import model.IngredientsResponse;
import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {

    @Step("Send GET-request to /ingredients")
    public IngredientsResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/ingredients")
                .body()
                .as(IngredientsResponse.class);
    }
}