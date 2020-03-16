package api.tests;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utility.classes.RequestBuilder;
import utility.classes.UtilDB;
import utility.classes.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Class for check add, delete and update spheres
 */
public class Sphere {
    private static String cookie;
    private RequestBuilder requestBuilder;
    private Properties property = new Properties();
    private UtilDB database;

    /**
     * method to perform before class
     *
     * @throws IOException throwable exception
     */
    @BeforeClass
    public void initialize() throws IOException, SQLException {
        requestBuilder = new RequestBuilder();

        RestAssured.config = RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false));
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
        }
        Utils.login(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"), property.getProperty("db.typeRegistration"));
        cookie = Utils.login(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"),
                property.getProperty("db.typeLogin")).cookies().get("aJwt");
        database = new UtilDB();
    }

    /**
     * Test for checking create sphere
     */
    @Test
    public void createSphere() {
        Response createSphere = requestBuilder.createSphere(property.getProperty("db.sphere"), cookie);
        assertEquals(createSphere.jsonPath().getString("status"), "success");
        requestBuilder.deleteSphere(createSphere.jsonPath().getString("data.sphereId"), cookie);
    }

    /**
     * Test for checking delete sphere
     */
    @Test
    public void deleteSphere() {
        Response createSphere = requestBuilder.createSphere(property.getProperty("db.sphere"), cookie);
        assertEquals((requestBuilder.deleteSphere(createSphere.jsonPath().getString("data.sphereId"), cookie)).jsonPath().getString("status"), "success");
    }

    /**
     * Test for checking edit sphere
     */
    @Test
    public void editSphere() {
        Response createSphere = requestBuilder.createSphere(property.getProperty("db.sphere"), cookie);
        assertEquals(requestBuilder.updateSphere(createSphere.jsonPath().getString("data.sphereId"), "newSphere", cookie).jsonPath().getString("status"), "success");
        requestBuilder.deleteSphere(createSphere.jsonPath().getString("data.sphereId"), cookie);
    }
    /**
     * method to perform after class
     */
    @AfterTest
    public void tearDown() throws SQLException {
        database.deleteUserByEmail(property.getProperty("db.testLogin"));
    }
}
