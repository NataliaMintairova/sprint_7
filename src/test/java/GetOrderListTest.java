import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.OrderApi;
import org.example.RestApi;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.OrderApi.ORDERS_URI;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest extends RestApi {

     @Test
     @DisplayName("get Not Null Order List")
     @Description("в тело ответа возвращается список заказов")
    public void getNotNullOrderListTest() {
        given()
                .spec(requestSpecification())
                .get(ORDERS_URI)
                .then()
                .statusCode(200)
                .assertThat()
                .body("orders", notNullValue());
     }
}
