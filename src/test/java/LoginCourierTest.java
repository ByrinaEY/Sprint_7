import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.example.LogIn;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;

public class LoginCourierTest {
    @Before

    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    Courier courier = new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME);
    // Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD,LogIn.FIRST_NAME));

    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться")
    public void checkAutorization() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        courierLog.then().assertThat().statusCode(200);
    }

    //если какого-то поля нет, запрос возвращает ошибку;
    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, если нет поля логин, запрос возвращает ошибку")
    public void emptyLogin() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier("", LogIn.PASSWORD));
        courierLog.then().assertThat().statusCode(400);

    }

    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, если нет поля пароль, запрос возвращает ошибку")
    public void emptyPassword() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier(LogIn.LOGIN, ""));
        courierLog.then().assertThat().statusCode(400);
    }

    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void nonExistentUser() {
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier("I", "123654"));
        courierLog.then().assertThat().statusCode(404)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, если если неправильно указать логин система вернёт ошибку")
    public void incorrectLogin() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier(LogIn.INCORRECT_LOGIN, LogIn.PASSWORD));
        Courier courierIncorrectLogin = new Courier(LogIn.INCORRECT_LOGIN, LogIn.PASSWORD);
        courierLog.then().assertThat().statusCode(404);

    }

    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, если если неправильно указать пароль система вернёт ошибку")
    public void incorrectPassword() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier(LogIn.LOGIN, LogIn.PASSWORD_2));
        courierLog.then().assertThat().statusCode(404);
    }

    //успешный запрос возвращает id.
    @Test
    @DisplayName("авторизация курьера")
    @Description("Проверка, что успешный запрос возвращает id")
    public void checkIdInAnswer() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        Response courierLog = LogIn.getPostRequestCourierLogin(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        courierLog.then().assertThat().and().statusCode(200).and().body("id", Matchers.notNullValue());
    }

    @After
    public void deleteCourier() {
        Courier courierId = LogIn.getPostRequestCourierLogin(new Courier(LogIn.LOGIN, LogIn.PASSWORD)).body().as(Courier.class);
        given().header("Content-type", "application/json")
                .delete("/api/v1/courier/" + courierId.getId());
    }

}
