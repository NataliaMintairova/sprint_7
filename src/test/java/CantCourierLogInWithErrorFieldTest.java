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

import static org.hamcrest.core.IsEqual.equalTo;

public class CantCourierLogInWithErrorFieldTest {
    CourierData courier1;
    CourierData courier2;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void cleanUp() {
        CourierApi courierApi = new CourierApi();
        courierApi.cleanUp(courier1);
    }

    @Test
    @DisplayName("can't LogIn Courier With Error Login")
    @Description("Если логин неверный, запрос возвращает ошибку")
    public void cantLogInCourierWithErrorLoginTest() {
        courier1 = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier1);
        courier2 = new CourierData(courier1.getLogin() + "1", courier1.getPassword(), courier1.getFirstName());
        ValidatableResponse response = courierApi.getCourierIdResponse(courier2);
        response.statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("can't LogIn Courier With Error Password")
    @Description("Если пароль неверный, запрос возвращает ошибку")
    public void cantLogInCourierWithErrorPasswordTest() {
        courier1 = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier1);
        courier2 = new CourierData(courier1.getLogin(), courier1.getPassword() + "1", courier1.getFirstName());
        ValidatableResponse response = courierApi.getCourierIdResponse(courier2);
        response.statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}