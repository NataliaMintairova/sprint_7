import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    @Before
    public void setUp()  {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

     @Test
     @DisplayName("get Not Null Order List")
     @Description("в тело ответа возвращается список заказов")
    public void getNotNullOrderListTest() {
        given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .assertThat()
                .body("orders", notNullValue());
     }
}
