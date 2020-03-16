package api.tests;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.*;
import utility.classes.RequestBuilder;
import utility.classes.UtilDB;
import utility.classes.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Class for check add, delete task, logout user
 */
public class TaskTest {
    private static String cookie;
    private RequestBuilder requestBuilder;
    private Properties property = new Properties();
    private String getSphereID;
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
        getSphereID = requestBuilder.createSphere(property.getProperty("db.sphere"), cookie).jsonPath().getString("data.sphereId");
        database = new UtilDB();
    }

    /**
     * @return test data
     */
    @DataProvider
    public Object[][] taskData() {
        return new Object[][]{
                {"20.9.2019", getSphereID, "", "ппппппп", 60, 60, 0.8, "success"},
                {"22.8.2025", getSphereID, "", "tr", 120, 120, 0.8, "success"},
                {"22.8.2025", getSphereID, "", "ki", 150, 150, 0.24, "success"},
                {"22.8.2025", getSphereID, "", "io", 340, 340, 0.48, "success"},
                {"22.8.2025", getSphereID, "", "oi", 3, 3, 0.72, "success"},
                {"22.8.2025", getSphereID, "", "io", 3, 7, 0.94, "success"},
                {"22.8.2019", getSphereID, "", "", 8, 8, 0.0, "error"},
        };
    }

    /**
     * Test for checking create task
     */
    @Test(dataProvider = "taskData")
    public void createTask(final String deadline, final String spheresID, final String taskDescription, final String taskTitle, final int timeLeft, final int timer, final double weight, final String result) {
        assertEquals(result, requestBuilder.createTask(deadline, spheresID, taskDescription, taskTitle, timeLeft, timer, weight, cookie).jsonPath().getString("status"));
    }

    /**
     * Test for checking delete task
     */
    @Test
    public void deleteTask() {
        String newTask = requestBuilder.createTask("29.8.2025", requestBuilder.createSphere("er", cookie).jsonPath().getString("data.sphereId"), "", "tr", 120, 120, 0.8, cookie).jsonPath().getString("data.taskId");
        assertEquals("success", requestBuilder.deleteTask(newTask, cookie).jsonPath().getString("status"));
    }

    /**
     * Test for checking logout user
     */
    @Test
    public void logoutUser() {
        assertEquals("success", requestBuilder.postRequest("logout", cookie).jsonPath().getString("status"));
    }

    /**
     * method to perform after class
     */
    @AfterTest
    public void tearDown() throws SQLException {
        database.deleteUserByEmail(property.getProperty("db.testLogin"));
    }
}
