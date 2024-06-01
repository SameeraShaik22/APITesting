import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CrudTest {

    private static String URL = "http://localhost:8085/employee";
    @Test
    public void createCrud() {
        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Sameera Begum Shaik\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
        contentType("application/json")

                .post(URL);

        System.out.println(response.getBody().prettyPrint());
        JsonPath jsonPath = new JsonPath(response.getBody().prettyPrint());
        int employeeId = jsonPath.getInt("employeeId");
        Assert.assertNotNull(employeeId);
    }
    @Test
    public void updateCrud() {
        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Ruqayya Adil Shaik\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")

                .post(URL);
        JsonPath jsonPath=new JsonPath(response.getBody().prettyPrint());
        System.out.println(jsonPath+"*************");

        int getID=jsonPath.getInt("employeeId");
        System.out.println(getID+"******GET ID******");

       // int getId=jsonPath.getInt("employeeId");
        Response updateResponse = given().when().body("{\n" +
                        "    \"employeeName\":\"Sumaiya Adil Shaik\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")

                .put(URL+"/"+95);
        System.out.println("***********"+updateResponse.getBody().prettyPrint()+"*****************");
        JsonPath jsonPath1 = new JsonPath(response.getBody().prettyPrint());
        String employeeName = jsonPath1.getString("employeeName");
        Assert.assertNotNull(employeeName,"Sumaiya Adil Shaik");
        int getUpdatedID=jsonPath.getInt("employeeId");
        System.out.println(getUpdatedID+"******GET ID******");
    }
    @Test
    public void createAnddeleteCrud() {

        Response response = given().when().body("{\n" +
                        "    \"employeeName\":\"Ruqayya Adil Shaik\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")

                .post(URL);

        JsonPath jsonPath=new JsonPath(response.getBody().prettyPrint());
        System.out.println(jsonPath+"*************");

        int getID=jsonPath.getInt("employeeId");
        System.out.println(getID+"******GET ID******");

        Response responseDelete = given()
                .delete(URL+"/"+getID);
        String responseDeleted= responseDelete.prettyPrint();
                Assert.assertEquals(responseDeleted,"Employee deleted successfully");

    }
    @Test
    public void afterRetrieveCrud() {
        Response beforeResponse = given().when()
                .get(URL);
        JsonPath jsonPath = new JsonPath(beforeResponse.getBody().prettyPrint());
        System.out.println(jsonPath+"*********EMPLOYEE*********");
        String employee = beforeResponse.prettyPrint();

        List afterEmployeeList=jsonPath.getList(".");
        int beforeSize=afterEmployeeList.size();
        System.out.println(beforeSize+"************Before ID*******");

        Response createResponse = given().when().body("{\n" +
                        "    \"employeeName\":\"Sameera Begum Shaik\",\n" +
                        "    \"employeeSalary\":1000\n" +
                        "}").
                contentType("application/json")
                .post(URL);

        Response afterResponse = given().when()
                .get(URL);
        JsonPath afterJsonPath = new JsonPath(afterResponse.getBody().prettyPrint());

        List employeeListRetrieve=afterJsonPath.getList(".");
        int afterSize=employeeListRetrieve.size();
        System.out.println(afterSize+"************After ID*******");

        Assert.assertEquals(beforeSize+1,afterSize);



    }



}
