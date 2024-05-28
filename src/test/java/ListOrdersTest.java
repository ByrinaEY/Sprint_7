import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ListOrdersTest {
    @Before

    public void setUp()
    {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

@Test
@DisplayName("Список заказов")
@Description("Проверка, что тело ответа возвращается список заказов")
public void checkListOfOrders(){
    given()
            .header("Content-type", "application/json")
            .log().all()
            .get("/api/v1/orders").then().assertThat().statusCode(200);
}
}
