package api.tests;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.response.Response;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import utility.classes.UtilDB;
import utility.classes.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * tests for registration requests
 */

public class RegistrationTest extends Assert {

    /**
     * test properties
     */
    private Properties property = new Properties();
    private UtilDB database;
    private static String existingEmail;
    private static String password;

    @DataProvider
    public static Object[][] fieldValidation() {
        return new Object[][]{
                {"datalls@mail.ru", "123456", "success"},
                {"dtrt@ddd", "passwordissolongsoitcantfitinform", "error"},
                {"", "", "error"},
                {"datye@mail.com", "1456", "error"},
                {existingEmail, password, "error"},
        };
    }

    /**
     * method to perform before class
     *
     * @throws IOException throwable exception
     */
    @BeforeClass
    public void initialize() throws IOException, SQLException {
        RestAssured.config = RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false));
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
        }
        database = new UtilDB();
        existingEmail = "datasddye@mail.ru";
        password = "123456";
        Utils.login(existingEmail, password, property.getProperty("db.typeRegistration"));
    }

    /**
     * Validating field test
     *
     * @param email          user email
     * @param password       user password
     * @param expectedResult expected result with such data
     * @throws IOException input/output exception
     */
    @Test(dataProvider = "fieldValidation")
    public void fieldValidationTest(final String email, final String password,
                                    final String expectedResult) throws IOException, SQLException {
        assertEquals((Utils.login(email, password, property.getProperty("db.typeRegistration")).jsonPath().getString("status")),
                expectedResult);
        database.deleteUserByEmail(email);
    }

    /**
     * Test for checking status code message
     *
     * @throws IOException throwable exception
     */
    @Test
    public void statusCodeCheck() throws IOException {
        assertEquals(Utils.login(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"),
                property.getProperty("db.typeRegistration")).getStatusCode(), Integer.parseInt(property.getProperty("db.statusCode")));
    }
}
