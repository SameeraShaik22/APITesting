import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.runners.Parameterized;
import org.testng.Assert;

import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class PostmanTest {
    private static String URL = "https://jsonplaceholder.typicode.com/todos/";

    @Test
    public void postManTesting() {
        Response response = given().when()
                .get(URL + "1");

        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        int id = jsonPath.getInt("id");
        Assert.assertEquals(id, 1);

    }
    private static String URL2 = "https://reqres.in/api/login";

    @Test
    public void postManTesting2() {
        Response response = given().when().body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}").
        contentType("application/json")
                .post(URL2 );


        System.out.println("Hello API Testing"+response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        String token = jsonPath.getString("token");
        Assert.assertNotNull(token);



    }
}

