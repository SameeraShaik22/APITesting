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
    private static String CRUDURL = "http://localhost:8085/employee";


    @Test
    public void Create() {
        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Sumaya MAriyam\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")
                .post(CRUDURL);
        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());

        String employeeName = jsonPath.getString("employeeName");
        Assert.assertEquals(employeeName, "Sumaya MAriyam");

        double employeeSalary = jsonPath.getDouble("employeeSalary");
        Assert.assertEquals(employeeSalary, 1000);

        int status = response.getStatusCode();
        Assert.assertEquals(status, 200);
        System.out.println(CRUDURL);
    }

    public void deleteEmployee() {
        Response response = given()
                .contentType("application/json")
                .delete(CRUDURL+"/"+ 3);
        System.out.println(response.getBody().prettyPrint()+"Delete ");
        Assert.assertEquals(response.getBody().prettyPrint(),"Employee deleted successfully");
    }
    @Test
    public void CreateAndDelete() {
        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Sameera Shaik Begum\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")
                .post(CRUDURL);
        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        String employeeName = jsonPath.getString("employeeName");
        Assert.assertEquals(employeeName, "Sameera Shaik Begum");
        int getID = jsonPath.getInt("employeeId");
        System.out.println(getID+" Get ID");

        Response deleteResponse = given()
                .contentType("application/json")
                .delete(CRUDURL+"/"+ getID);
        System.out.println(deleteResponse.getBody().prettyPrint()+"Delete ");
        Assert.assertEquals(deleteResponse.getBody().prettyPrint(),"Employee deleted successfully");

    }
    @Test
    public void CreateAndUpdate() {
        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Sameera Shaik Begum\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")
                .post(CRUDURL);
        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        String employeeName = jsonPath.getString("employeeName");
        Assert.assertEquals(employeeName, "Sameera Shaik Begum");
        int getID = jsonPath.getInt("employeeId");
        System.out.println(getID+" Get ID");

        Response deleteResponse = given().body("{\n" +
                        "    \"employeeName\":\"Sumaiya Shaik Begum\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}")
                .contentType("application/json")
                .put(CRUDURL+"/"+ getID);
        System.out.println(deleteResponse.getBody().prettyPrint()+"Delete ");
        JsonPath jsonPathforUpdate = new JsonPath(deleteResponse.getBody().prettyPrint());
        jsonPathforUpdate.getString("employeeName");
        String getemployeeName=jsonPathforUpdate.getString("employeeName");
        Assert.assertEquals(getemployeeName, "Sumaiya Shaik Begum");

        System.out.println(jsonPathforUpdate);


    }
    @Test
    public void retriveTest() {
        Response response = given().when().get(CRUDURL);

        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        List values = jsonPath.getList(".");
        int beforesize= values.size();
        System.out.println(beforesize+"*****Before*****Size");

        int status = response.getStatusCode();
        Assert.assertEquals(status, 200);
        System.out.println(status+ "********Response Code");

        Response response2 = given().when().body("{\n" +
                        "    \"employeeName\":\"Sumaya MAriyam\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")
                .post(CRUDURL);

        Response afterResponse = given().when().get(CRUDURL);
        JsonPath afterJsonPath = new JsonPath(afterResponse.getBody().prettyPrint());

        List afterValues = afterJsonPath.getList(".");
        int afterSize= afterValues.size();
        System.out.println(afterSize+"******After****Size***********");


        Assert.assertEquals(beforesize+1,afterSize);




    }



    }

