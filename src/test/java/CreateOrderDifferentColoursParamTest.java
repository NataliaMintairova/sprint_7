import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.OrderApi;
import org.example.OrderData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.CoreMatchers.notNullValue;
@RunWith(Parameterized.class)
public class CreateOrderDifferentColoursParamTest {

    private final String[] scooterColour;

    OrderData order;
    Response response;

    public CreateOrderDifferentColoursParamTest(String[] scooterColour) {
        this.scooterColour = scooterColour;
    }

    @Parameterized.Parameters()
    public static Object[][] getScooterColour() {
        String[] allColours = {"BLACK", "GREY"};
        String[] blackColour = {"BLACK"};
        String[] greyColour = {"GREY"};
        String[] noColour = {};

        return new Object[][] {
                {allColours},
                {greyColour},
                {blackColour},
                {noColour},
        };
    }

    @Before
    public void setUp()  {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("order Can Be Created With Any Colour")
    @Description("параметризированный тест - заказ создается с любым набором цветов самоката")
    public void orderCanBeCreateAnyColourTest() {
        order = new OrderData("Иванов", "Иван", "Иваново", "4", "123456789123", 3, "2024-12-12", "Saske, come back to Konoha", scooterColour);
        OrderApi orderApi = new OrderApi();
        response = orderApi.createOrder(order);

        response.then()
                .log().all()
                .statusCode(201)
                .assertThat()
                .body("track", notNullValue());
    }
}
