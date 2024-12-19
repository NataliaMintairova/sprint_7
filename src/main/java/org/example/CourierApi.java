package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {
    public static final String COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";

@Step("create Courier")
    public ValidatableResponse createCourier(CourierData courier) {
        return
                given()
                        .spec(requestSpecification())
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER_URI)
                        .then()

                        .log().all();
    }
@Step("get Courier Id")
    public GetCourierId getCourierId(CourierData courier) {
        GetCourierId courierId = given()
                .spec(requestSpecification())
                .body(courier)
                .post(LOGIN_COURIER_URI)
                .body().as(GetCourierId.class);
        return courierId;
    }
@Step("clean Up Courier")
    public void cleanUpCourier(GetCourierId courierId) {
        given()
                .spec(requestSpecification())
                .when()
                .delete(COURIER_URI + "/"+courierId.getId().toString())
                .then()
                .log().all();
    }
@Step("clean Up Viod")
    public void cleanUp(CourierData courier) {
        GetCourierId courierId = getCourierId(courier);
        if (courierId.getId() != null) {
        cleanUpCourier(courierId);
        }
}
@Step("get Courier Id Response")
    public ValidatableResponse getCourierIdResponse(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URI)
                .then().log().all();
    }
}
