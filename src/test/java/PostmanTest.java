import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.runners.Parameterized;
import org.testng.Assert;

import org.testng.annotations.Test;


import java.util.List;

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
    private static String URL3 = "https://reqres.in/api/users";

    @Test
    public void postManTesting3() {
        Response response = given().when().body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}").
                contentType("application/json")
                .post(URL3 );


        System.out.println("Hello API Testing"+response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());

        String name = jsonPath.getString("name");
        Assert.assertEquals(name,"morpheus");

        String job = jsonPath.getString("job");
        Assert.assertEquals(job,"leader");

        String id = jsonPath.getString("id");
        Assert.assertNotNull(id);

        String createdAt = jsonPath.getString("createdAt");
        Assert.assertNotNull(createdAt);
    }

    }

