import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.Color;
import org.example.LogIn;
import org.example.Order;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreateOrderTest extends AbstractBeforeTest{
   public final Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }
       @Parameterized.Parameters
       public static Object[][] data() {
            return new Object[][]{
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{Color.COLOR_ONE})},
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{Color.COLOR_TWO})},
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{Color.COLOR_ONE, Color.COLOR_TWO})},
                    {new Order("Иван","Иванов", "ул.Победы 35", "Теплый стан", "+79123456789", 5, "2024-08-13","комментарий", new String[]{})}
            };
        }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
       Response createOrder = LogIn.createOrder(order);
       createOrder.then().assertThat().and().statusCode(201).body("track", Matchers.notNullValue());
    }
}