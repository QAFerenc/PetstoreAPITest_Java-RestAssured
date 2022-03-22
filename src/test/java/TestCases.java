import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestCases {

    public static long id;
    public static String url = "https://petstore.swagger.io/v2";
    public static Response response;
    public static RequestSpecification request;
    public static int statusCode;
    public static JSONObject fileContent;

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
    public void addNewPet() throws IOException, ParseException {

        RestAssured.baseURI = url;

        // Test 2: Add a new pet from petToUpload.json source file  (POST)

        JSONParser jsonParser = new JSONParser();
        fileContent = (JSONObject) jsonParser.parse(new FileReader("petToUpload.json"));

        id = given().contentType("application/json")
                .accept("application/json")
                .body(fileContent)
                .when().post(url+"/pet").then()
                .assertThat()
                .statusCode(200).extract().path("id");

        System.out.println("id : "+id);
    }

    @Test(priority=2)
    public void sellNewPet() throws ParseException {

        // Test 3: Set status to sold at the newly uploaded pet  (PUT)

        // the original petToUpload.json file content for the uploaded pet can be modified at the id and the status

        fileContent.put("id",id);
        fileContent.put("status","sold");

        System.out.println(fileContent.toString());

        given().contentType("application/json")
                .accept("application/json")
                .body(fileContent)
                .when().put(url+"/pet").then()
                .assertThat()
                .statusCode(200);

    }

    @Test(priority=3)
    public void deleteSoldPet() {

        // Test 4 : Delete the newly uploaded pet (where status was changed to "sold")   (DELETE)

        given()
                .when()
                .delete(url+"/pet/"+id)
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test(priority=4)
    public void checkThatAlreadyPetCannotBeDeletedAgain() {

        // Test 5 : Try to delete already deleted pet, operation fails with Error code 404    (DELETE)

        //url = "https://petstore.swagger.io/v2/pet/"+id;
        given()
                .when()
                .delete(url+"/pet"+id)
                .then()
                .assertThat()
                .statusCode(404);

    }

    @Test(priority=5)
    public void checkThatAlreadyDeletedPetDoesNotExist() {

        // Test 6 : Already deleted pet does not exist    (GET)

        //url = "https://petstore.swagger.io/v2/pet/"+id;
        given()
                .when()
                .get(url+"/pet/"+id)
                .then()
                .assertThat()
                .statusCode(404);

    }



}








