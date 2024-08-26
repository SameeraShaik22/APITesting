package devtesting;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.context.annotation.Profile;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

@Profile("dev")
public class UserAPI {

    private static String URL = "https://jsonplaceholder.typicode.com/todos/";
    private static String URL2 = "https://reqres.in/api/login";
    private static String URL3 = "https://reqres.in/api/users";
    @DataProvider(name = "test1")
    public static Object[][] primeNumbers() {
        return new Object[][]{{"John", "Manager"}, {"Tom", "Testing"}};
    }

   @Test(dataProvider = "test1")
    public void employee(String name, String job) throws IOException, JSONException {
//In RequestBody we are getting json file from createRequestReqres.
        String requestBody = IOUtils.toString(getClass().getResourceAsStream("/createRequestReqres.json"),
                StandardCharsets.UTF_8);
        System.out.println(" Before Update"+requestBody+"***************");

//       Creating request Json using Parameters.
        JSONObject jsonObject=new JSONObject(requestBody);
        jsonObject.put("name",name);
        jsonObject.put("job",job);
       System.out.println(jsonObject);

//  Getting Response by sending Request.
       Response response=given().when().body(jsonObject.toString()).
               contentType("application/json").post(URL);

//  Creating a JsonPAth for response
       JsonPath jsonPath=new JsonPath(response.getBody().prettyPrint());

       //  Assertions for Name
       String name1=jsonPath.getString("name");
       Assert.assertEquals(name,name1);

       //  Assertions for Job
       String job1=jsonPath.getString("job");
       Assert.assertEquals(job,job1);



    }

        @Test
        public void logIn () {
            Response createResponse = given().when().body("{\n" +
                    "    \"name\": \"morpheus\",\n" +
                    "    \"job\": \"leader\"\n" +
                    "}").contentType("application/json").post(URL);
            JsonPath jsonPath = new JsonPath(createResponse.getBody().prettyPrint());
            System.out.println(createResponse.getBody().prettyPrint());
            // System.out.println(jsonPath);
            Assert.assertEquals(createResponse.getStatusCode(), 201);
            jsonPath.getInt("id");
            Assert.assertNotNull(jsonPath.getInt("id"));
            String name = jsonPath.getString("name");
            Assert.assertEquals(name, "morpheus");
            String job = jsonPath.getString("job");
            Assert.assertEquals(job, "leader");
        }
    }

