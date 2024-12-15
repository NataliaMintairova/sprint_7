import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CourierApi;
import org.example.CourierData;
import org.example.GetCourierId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CantCreateCourierWithoutRequiredFieldTest {
    CourierData courier;
    @Before
    public void setUp()  {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void cleanUp() {
        CourierApi courierApi = new CourierApi();
        courierApi.cleanUp(courier);
    }

    @Test
    @DisplayName("can't Create Courier Without Login")
    @Description("если логина нет, запрос возвращает ошибку")
    public void cantCreateCourierWithoutLoginTest() {
        courier = new CourierData(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courier);
        response
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("can't Create Courier Without Password")
    @Description("если пароля нет, запрос возвращает ошибку")
    public void cantCreateCourierWithoutPasswordTest() {
        courier = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), null, RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courier);
        response
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
