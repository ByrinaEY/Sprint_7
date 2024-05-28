import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.example.LogIn;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, курьера можно создать")
    public void createCourier() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD, LogIn.FIRST_NAME));
        createCourier.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void createCourierWithMustFields() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        createCourier.then().assertThat().statusCode(201).and().body("ok", is(true));
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, если одного из полей нет, запрос возвращает ошибку")
    public void checkEmptyLoginField() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier("", LogIn.PASSWORD));
        createCourier.then().assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, если одного из полей нет, запрос возвращает ошибку")
    public void checkEmptyPasswordField() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, ""));
        createCourier.then().assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void checkCreateDuplicateLoginCourier() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        Response createCourier2 = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD_2));
        createCourier2.then().assertThat().statusCode(409);
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, что запрос возвращает правильный код ответа")
    public void checkStatusCode() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        createCourier.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, что нельзя создать двух одинаковых курьеров")
    public void createDuplicateCourier() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        Response createCourier2 = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        createCourier2.then().assertThat().statusCode(409);

    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка, что успешный запрос возвращает ok: true")
    public void checkBodyAnswer() {
        Response createCourier = LogIn.getPostRequestCreateCourier(new Courier(LogIn.LOGIN, LogIn.PASSWORD));
        createCourier.then().assertThat().body("ok", is(true));
    }

    @After
    public void deleteCourier() {
        Courier courierId = LogIn.getPostRequestCourierLogin(new Courier(LogIn.LOGIN, LogIn.PASSWORD)).body().as(Courier.class);
        given().header("Content-type", "application/json")
                .delete("/api/v1/courier/" + courierId.getId());
    }

}
