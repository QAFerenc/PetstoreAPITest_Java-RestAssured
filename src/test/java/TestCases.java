import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;

public class TestCases {


    @Test
    public void getAvailablePets()
    {

        String url = "https://petstore.swagger.io/v2/pet/findByStatus?status=available";
        given()
                .when()
                .get(url)
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void postNewAvailablePet()
    {

    }
    @Test
    public void updateLastPetStatus()
    {

        String url = "https://petstore.swagger.io/v2/pet/";
        RestAssured.baseURI =  "https://petstore.swagger.io/v2";
        /*JSONObject test = new JSONObject();
                test.put(
                        "Employee ID", "092789");
                +
                        "Employee Name: Helen Mirren," +
                        "Age: 27, " +
                        "Designation: Assistant Manager," +
                        "City: Florida," +
                        "Salary: 67000.00, " +
                        "Experience: 26 " +
                        "}"
        );*/

        JSONObject obj = new JSONObject();
                obj.put ("id","9223372000668885684");
        obj.put ("category","{id :0,name:string}");
        obj.put ("name","tigris");
        obj.put ("photoUrls","www.picture.com");
        obj.put ("tags","[{id:0,name:string}]");
        obj.put ("status","sold");

        RequestSpecification request = RestAssured.given();


        request
                .contentType("application/json" )
                .accept("application/json")
                .body(obj.toJSONString());

        Response response = request.put("/pet/"+"9223372000668885684");

        int statusCode = response.getStatusCode();
        System.out.println(response.asString());
        Assert.assertEquals(statusCode, 200);
    }
    @Test
    public void deleteLastPet()
    {
 
        String url = "https://petstore.swagger.io/v2/pet/9223372000668883762";
        given()
                .when()
                .delete(url)
                .then()
                .assertThat()
                .statusCode(200);

    }
}