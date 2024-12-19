package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String ORDERS_URI = "/api/v1/orders";
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";

@Step("create Order")
    public Response createOrder(OrderData order) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(ORDERS_URI);
    }
@Step("clean Up Order")
    public void cleanUpOrder(Response response) {
       GetOrderTrack orderTrack =

        response.body().as(GetOrderTrack.class);

        given()
                .spec(requestSpecification())
                .and()
                .body(orderTrack)
                .when()
                .put(CANCEL_ORDER_URI)
                .then()
                .log().all();
    }
}
