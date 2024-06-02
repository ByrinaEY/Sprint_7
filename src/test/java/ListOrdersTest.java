import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.LogIn;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ListOrdersTest extends AbstractBeforeTest {

@Test
@DisplayName("Список заказов")
@Description("Проверка, что тело ответа возвращается список заказов")
public void checkListOfOrders(){
    Response listOfOrders  = LogIn.getListOfOrders();
    listOfOrders.then().assertThat().statusCode(200).and().body("orders", Matchers.notNullValue());

}
}
