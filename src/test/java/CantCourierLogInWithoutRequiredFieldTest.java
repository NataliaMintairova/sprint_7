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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CantCourierLogInWithoutRequiredFieldTest {
    CourierData courier1;
    CourierData courier2;

    @After
    public void cleanUp() {
        CourierApi courierApi = new CourierApi();
        courierApi.cleanUp(courier1);
    }

    @Test
    @DisplayName("can't LogIn Courier Without Login")
    @Description("если логина нет, запрос возвращает ошибку")
    public void cantLogInCourierWithoutLoginTest() {
        courier1 = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier1);
        courier2 = new CourierData(null, courier1.getPassword(), courier1.getFirstName());
        ValidatableResponse response = courierApi.getCourierIdResponse(courier2);
        response.statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("can't LogIn Courier Without Password")
    @Description("если пароля нет, запрос возвращает ошибку")
    public void cantLogInCourierWithoutPasswordTest() {
        courier1 = new CourierData("Rick" + RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        CourierApi courierApi = new CourierApi();
        courierApi.createCourier(courier1);
        courier2 = new CourierData(courier1.getLogin(), null, courier1.getFirstName());
        ValidatableResponse response = courierApi.getCourierIdResponse(courier2);
        response.statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

}
