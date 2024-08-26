package devtesting;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

@SpringBootTest
@Profile("dev")
public class NewAPITest {
    private static String URl="http://localhost:8085/employee";

    @DataProvider(name = "test1")
    public static Object[][] primeNumbers() {
        return new Object[][] {{"John",100.10},{"Tom",600.10}};
    }

    @Test(dataProvider="test1")
    public void employee(String employeeName,Double employeeSalary) throws IOException, JSONException {
        String requestBody= IOUtils.toString(getClass().getResourceAsStream("/createRequest.json"),
                StandardCharsets.UTF_8);
        System.out.println(" Before Update"+requestBody+"***************");
        JSONObject jsonObject=new JSONObject(requestBody);
        jsonObject.put("employeeName",employeeName);
        jsonObject.put("employeeSalary",employeeSalary);
//        System.out.println(" After Update"+jsonObject);


        Response response =given().when().body(jsonObject.toString()).contentType("application/json").post(URl);

        JsonPath jsonPath =new JsonPath(response.getBody().prettyPrint());
        System.out.println(jsonPath);

        String name=jsonPath.getString("employeeName");
        Double salary=jsonPath.getDouble("employeeSalary");
        Assert.assertEquals(name,employeeName);
        Assert.assertEquals(salary,employeeSalary);

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
