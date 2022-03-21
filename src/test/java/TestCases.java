import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class TestCases {


    public static String id = "9223372000668935934";
    public static String url = "https://petstore.swagger.io/v2";
    public static Response response;
    public static RequestSpecification request;
    public static int statusCode;

    @Test(priority=0)
    public void readAllPets() {
        // Test 1: Read all available pets (GET)

        given()
                .when()
                .get(url+"/pet/findByStatus?status=available")
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test(priority=1)
    public void addNewPet() {

        RestAssured.baseURI = url;

        // Test 2: Add a new pet from .json source file  (POST)

        File file = new File("C:\\Users\\hp\\IdeaProjects\\RestAssured_Test\\src\\test\\java\\data.json");

        request = RestAssured.given();

        request
                .contentType("application/json")
                .accept("application/json")
                .body(file);

        System.out.println("I am here");
        response = request.post("/pet");

        int statusCode = response.getStatusCode();
        System.out.println(response.asString());
        Assert.assertEquals(statusCode, 200);

    }


    @Test(priority=2)
    public void sellNewPet() {

        //put comes


        JsonPath jsonPathEvaluator = response.jsonPath();
        long longid = jsonPathEvaluator.get("id");
        System.out.println("id :  " + jsonPathEvaluator.get("id"));
        id = String.valueOf(longid);
        System.out.println("id :  " + id);

        JSONParser parser = new JSONParser();
        try {
            Object object = parser
                    .parse(new FileReader("C:\\Users\\hp\\IdeaProjects\\RestAssured_Test\\src\\test\\java\\data.json"));

            //convert Object to JSONObject

            JSONObject jsonObject = (JSONObject)parser.parse(response.toString());

            jsonObject.put("status","sold");


            System.out.println("Structure to put : "+jsonObject.toString());
            request =RestAssured.given();


            request
                    .contentType("application/json")
                    .

                            accept("application/json")
                    .

                            body(jsonObject.toJSONString());
            System.out.println("A");
            response =request.put("/pet/"+id);
            System.out.println("B");
            statusCode =response.getStatusCode();
            System.out.println(response.asString());
            Assert.assertEquals(statusCode,200);


        }

        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test(priority=3)
    public void deleteSoldPet() {
        //Delete the sold Pet
        url = "https://petstore.swagger.io/v2/pet/"+id;
        given()
                .when()
                .delete(url)
                .then()
                .assertThat()
                .statusCode(200);

    }
}








