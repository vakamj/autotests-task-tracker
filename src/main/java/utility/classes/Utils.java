package utility.classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * class with utility methods
 */
final public class Utils {
    private Utils() {

    }

    /**
     * login method
     *
     * @param email    user email
     * @param password user password
     * @param type     login type
     * @return server response
     * @throws IOException throwing input/output exception
     */
    public static Response login(final String email, final String password, final String type) throws IOException {

        Properties property = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
        }
        TaskUser user = new TaskUser(type, email, password);
        Gson jsonBody = new GsonBuilder().create();
        String registrationURL = property.getProperty("baseUrl");
        return given().contentType("application/json")
                .body(jsonBody.toJson(user))
                .when()
                .post(registrationURL);
    }
}
