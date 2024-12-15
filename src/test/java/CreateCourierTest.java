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

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateCourierTest {
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
    @DisplayName("courier Can Be Created")
    @Description("курьера можно создать")
    public void courierCanBeCreatedTest() {
        courier = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courier);
        response.statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("courier Required Fields Can Be Created")
    @Description("чтобы создать курьера, нужно передать в ручку обязательные поля: login и password")
    public void courierRequiredFieldsCanBeCreatedTest() {
        courier = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5),null);
        CourierApi courierApi = new CourierApi();
        ValidatableResponse response = courierApi.createCourier(courier);
       response
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));

    }
}
