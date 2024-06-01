import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CrudTest {
    private static String URL = "http://localhost:8085/employee";


    @Test
    public void Create() {
        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Sumaya MAriyam\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")
                .post(URL);
        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());

        String employeeName = jsonPath.getString("employeeName");
        Assert.assertEquals(employeeName, "Sumaya MAriyam");

        double employeeSalary = jsonPath.getDouble("employeeSalary");
        Assert.assertEquals(employeeSalary, 1000);

        int status = response.getStatusCode();
        Assert.assertEquals(status, 200);
        System.out.println(URL);
    }
    @Test
    public void deleteEmployee() {
        Response response = given()
                .contentType("application/json")
                .delete(URL+"/"+ 3);
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
                .post(URL);
        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        String employeeName = jsonPath.getString("employeeName");
        Assert.assertEquals(employeeName, "Sameera Shaik Begum");
        int getID = jsonPath.getInt("employeeId");
        System.out.println(getID+" Get ID");

        Response deleteResponse = given()
                .contentType("application/json")
                .delete(URL+"/"+ getID);
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
                .post(URL);
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
                .put(URL+"/"+ getID);
        System.out.println(deleteResponse.getBody().prettyPrint()+"Delete ");
        JsonPath jsonPathforUpdate = new JsonPath(deleteResponse.getBody().prettyPrint());
        jsonPathforUpdate.getString("employeeName");
        String getemployeeName=jsonPathforUpdate.getString("employeeName");
        Assert.assertEquals(getemployeeName, "Sumaiya Shaik Begum");


        System.out.println(jsonPathforUpdate);


    }
    @Test
    public void retriveTest() {
        Response response = given().when().get(URL);

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
                .post(URL);

        Response afterResponse = given().when().get(URL);
        JsonPath afterJsonPath = new JsonPath(afterResponse.getBody().prettyPrint());

        List afterValues = afterJsonPath.getList(".");
        int afterSize= afterValues.size();
        System.out.println(afterSize+"******After****Size***********");


        Assert.assertEquals(beforesize+1,afterSize);




    }
    }

