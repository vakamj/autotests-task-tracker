package api.tests;


import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.classes.UtilDB;
import utility.classes.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * tests for login requests
 */
public class LoginTests extends Assert {

    /**
     * test properties
     */
    private Properties property = new Properties();
    private UtilDB database;
    private static String existingEmail;
    private static String password;

    @DataProvider
    public Object[][] fieldValidation() {
        return new Object[][]{
                {existingEmail, password, true},
                {"", "123456", false},
                {"exampletest@mail.ru", "", false},
                {"etoneya@mail.ru", "123456", false},
                {"exampletest@mail.ru", "654321", false}
        };
    }

    /**
     * method to perform before class
     *
     * @throws IOException throwable exception
     */
    @BeforeClass
    public void initialize() throws IOException, SQLException {
        RestAssured.config = RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().
                appendDefaultContentCharsetToContentTypeIfUndefined(false));
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
        }
        database = new UtilDB();
        existingEmail = "exampletest@mail.ru";
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
    public void loginFieldValidation(final String email, final String password,
                                     final boolean expectedResult) throws IOException, SQLException {
        assertEquals((Utils.login(email, password, property.getProperty("db.typeLogin")).cookies().containsKey("rJwt") &&
                Utils.login(email, password, property.getProperty("db.typeLogin")).cookies().containsKey("aJwt")), expectedResult);
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
                property.getProperty("db.typeLogin")).getStatusCode(), Integer.parseInt(property.getProperty("db.statusCode")));
    }
}
