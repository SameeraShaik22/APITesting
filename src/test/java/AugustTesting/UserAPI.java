package AugustTesting;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserAPI {

    private static String URL = "https://jsonplaceholder.typicode.com/todos/";
    private static String URL2 = "https://reqres.in/api/login";
    private static String URL3 = "https://reqres.in/api/users";

    @Test
    public void  logIn(){
        Response createResponse = given().when().body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}").
                contentType("application/json")
                .post(URL);
        JsonPath jsonPath=new JsonPath(createResponse.getBody().prettyPrint());
        System.out.println(createResponse.getBody().prettyPrint());
        System.out.println(jsonPath);


        Assert.assertEquals(createResponse.getStatusCode(),201);
        jsonPath.getInt("id");
        Assert.assertNotNull(jsonPath.getInt("id"));

    }
}
