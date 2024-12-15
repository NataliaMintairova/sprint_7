import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CourierApi;
import org.example.CourierData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CantCreateIdenticalCouriersTest {
    CourierData courier1;
    CourierData courier2;
    @Before
    public void setUp()  {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void cleanUp() {
        CourierApi courierApi = new CourierApi();
        courierApi.cleanUp(courier1);
    }

    @Test
    @DisplayName("нельзя создать двух одинаковых курьеров")
    @Description("если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void IdentialCourierCantBeCreatedTest() {
         courier1 = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier1);

         courier2 = new CourierData(courier1.getLogin(), courier1.getPassword(), null);
        ValidatableResponse response = courierApi.createCourier(courier2);
        response
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется"));
    }
}
