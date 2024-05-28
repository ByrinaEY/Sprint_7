import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.Color;
import org.example.Order;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderTest {
   public final Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }
    @Before
    public void setUp()
    {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
       @Parameterized.Parameters
       public static Object[][] data() {
            return new Object[][]{
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{Color.COLOR_1})},
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{Color.COLOR_2})},
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{Color.COLOR_1, Color.COLOR_2})},
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{})}
            };
        }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
        given().log().all().header("Content-type", "application/json").body(order).when().post("/api/v1/orders").
       then().assertThat().and().statusCode(201).body("track", Matchers.notNullValue());
    }
}