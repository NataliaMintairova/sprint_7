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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierAutorizationTest {
    CourierData courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void cleanUp() {
        CourierApi courierApi = new CourierApi();
        courierApi.cleanUp(courier);
    }

    @Test
    @DisplayName("courier Can LogIn")
    @Description("курьер может авторизоваться по логину и паролю")
    public void courierCanLogInTest() {
        courier = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier);
        ValidatableResponse response = courierApi.getCourierIdResponse(courier);
        response.statusCode(200)
                .assertThat()
                .body("id", notNullValue());
    }


    @Test
    @DisplayName("courie Required Fields Can LogIn")
    @Description("для авторизации нужно передать все обязательные поля")
    public void courieRequiredFieldsCanLogInTest() {
        courier = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), null);
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier);
        ValidatableResponse response = courierApi.getCourierIdResponse(courier);
        response.statusCode(200)
                .assertThat()
                .body("id", notNullValue());

    }
}