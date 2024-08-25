package AugustTesting;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class NewAPITest {
    private static String URl="http://localhost:8085/employee";

    @Test
    public void employee(){
        Response response =given().when().body("{\n" +
                "    \"employeeId\": 170,\n" +
                "    \"employeeName\": \"Family\",\n" +
                "    \"employeeSalary\": 1000.0\n" +
                "}").contentType("application/json").post(URl);

        JsonPath jsonPath =new JsonPath(response.getBody().prettyPrint());
        System.out.println(jsonPath);

        String name=jsonPath.getString("employeeName");
        Assert.assertEquals(name,"Family");

        int getId = jsonPath.getInt("employeeId");
        Assert.assertNotNull(getId);
    }
    @Test
    public void checkThomas(){
//        Response response =given().when().body("{\n" +
//                "    \"employeeName\":\"Thomas\",\n" +
//                "    \"employeeSalary\":1000\n" +
//                "}").contentType("application/json").get(URl);
//
//        JsonPath jsonPath=new JsonPath(response.getBody().prettyPrint());
//        System.out.println(jsonPath);
//        Assert.assertNotNull(response.getStatusCode());

        Response myresponse=given().when().contentType("application/json").get(URl+"/"+173);

        JsonPath jsonPath1=new JsonPath(myresponse.getBody().prettyPrint());
        System.out.println(jsonPath1);
        String getEmployeeId=jsonPath1.getString("employeeId");
        Assert.assertNotNull(getEmployeeId);
        int getId=jsonPath1.getInt("employeeId");
        Assert.assertEquals(getId,173);
        String name=jsonPath1.getString("employeeName");
        Assert.assertEquals(name,"Thomas");
        int getStatuscode=myresponse.getStatusCode();
        Assert.assertNotNull(myresponse.getStatusCode());
        Assert.assertEquals(getStatuscode,200);
    }

}
//  String name=jsonPath.getString("employeeName");
//        Assert.assertEquals(name,"Thomas");
