package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderApi {

    public static final String ORDERS_URI = "/api/v1/orders";
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";

@Step("create Order")
    public Response createOrder(OrderData order) {
        return given()
                .header("Content-type", "application/json")
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
                .header("Content-type", "application/json")
                .and()
                .body(orderTrack)
                .when()
                .put(CANCEL_ORDER_URI)
                .then()
                .log().all();
    }
}
