package org.example;

import io.restassured.response.Response;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.given;

public class LogIn {

    public static final String LOGIN = "byrinaE";
    public static final String PASSWORD = "12345";
    public static final String PASSWORD_2 = "54321";
    public static final String INCORRECT_LOGIN = "E_Byrina";
    public static final String FIRST_NAME = "ekaterina";
    private static final String CREATE_COURIER = "api/v1/courier";
    private static final String CREATE_COURIER_LOGIN = "/api/v1/courier/login";

    @Step("Создание курьера, проверка кода ответа")
    public static Response getPostRequestCreateCourier(Courier courier) {
        return given().log().all().filter(new AllureRestAssured()).header("Content-type", "application/json").body(courier).when().post(CREATE_COURIER);
    }
    @Step("Авторизация курьера в системе, получение id")
    public static Response getPostRequestCourierLogin(Courier courier) {
        return given().log().all().header("Content-type", "application/json").body(courier).when().post(CREATE_COURIER_LOGIN);
    }
}
