package org.example;

import io.restassured.response.Response;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class LogIn {
    public static final String URL = "https://qa-scooter.praktikum-services.ru/";
    public static final String LOGIN = RandomStringUtils.randomAlphabetic(8); //"byrinaE";
    public static final String PASSWORD = RandomStringUtils.randomAlphabetic(8);//"12345";
    public static final String PASSWORD_TWO = RandomStringUtils.randomAlphabetic(8);//"54321";
    public static final String INCORRECT_LOGIN = RandomStringUtils.randomAlphabetic(8);//"E_Byrina";
    public static final String FIRST_NAME = RandomStringUtils.randomAlphabetic(8);//"ekaterina";
    private static final String CREATE_COURIER = "api/v1/courier";
    private static final String CREATE_COURIER_LOGIN = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/";
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String LIST_ORDERS = "/api/v1/orders";

    @Step("Создание курьера, проверка кода ответа")
    public static Response getPostRequestCreateCourier(Courier courier) {
        return given().log().all().filter(new AllureRestAssured()).header("Content-type", "application/json").body(courier).when().post(CREATE_COURIER);
    }
    @Step("Авторизация курьера в системе, получение id")
    public static Response getPostRequestCourierLogin(Courier courier) {
        return given().log().all().header("Content-type", "application/json").body(courier).when().post(CREATE_COURIER_LOGIN);
    }
    @Step("Удаление курьера")
    public static Response deleteCourier(String id){
        return given().header("Content-type", "application/json")
                .delete(DELETE_COURIER + id);
    }
    @Step("Создание заказа")
    public static Response createOrder(Order order){
       return given().log().all().header("Content-type", "application/json")
               .body(order).when().post(CREATE_ORDER);
    }
    @Step("Получение списка заказов")
    public static Response getListOfOrders()
    {
       return given()
                .header("Content-type", "application/json")
                .log().all()
                .get(LIST_ORDERS);
    }
}
