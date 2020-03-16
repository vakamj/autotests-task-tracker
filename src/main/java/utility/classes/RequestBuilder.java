package utility.classes;


import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * class for building requests to the interface
 */
public class RequestBuilder {
    private String baseURL;

    public RequestBuilder() throws IOException {
        Properties property = new Properties();
        try (
                FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
        }
        this.baseURL = property.getProperty("baseUrl");
    }

    /**
     * @param messageMapId  messageMapId
     * @param token    authorisation
     * @return server response
     */
    public Response postRequest(final String messageMapId, final String token) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("messageMapId", messageMapId);
        return given().contentType("application/json")
                .body(jsonBody.toString())
                .cookie("aJwt", token)
                .when()
                .post(baseURL);
    }

    /**
     * @param sphereId id of the sphere to be deleted
     * @param token    authorisation
     * @return server response
     */
    public Response deleteSphere(final String sphereId, final String token) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("messageMapId", "delete-sphere");
        jsonBody.put("sphereId", sphereId);
        return given().contentType("application/json")
                .body(jsonBody.toString())
                .cookie("aJwt", token)
                .when()
                .post(baseURL);
    }

    /**
     * @param sphereTitle new sphere title
     * @param token       authorisation
     * @return server response
     */
    public Response createSphere(final String sphereTitle, final String token) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("messageMapId", "create-sphere");
        jsonBody.put("sphereTitle", sphereTitle);
        return given().contentType("application/json")
                .body(jsonBody.toString())
                .cookie("aJwt", token)
                .when()
                .post(baseURL);
    }

    /**
     * @param sphereId new sphere title
     * @param token    authorisation
     * @return server response
     */
    public Response updateSphere(final String sphereId, final String newSphereTitle, final String token) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("messageMapId", "update-sphere");
        jsonBody.put("spheresID", sphereId);
        JSONObject newSphere = new JSONObject();
        newSphere.put("sphereTitle", newSphereTitle);
        jsonBody.put("newSphere", newSphere);
        return given().contentType("application/json")
                .body(jsonBody.toString())
                .cookie("aJwt", token)
                .when()
                .post(baseURL);
    }

    /**
     * @param token authorisation
     * @return server response
     */
    public Response createTask(final String deadline, final String spheresID, final String taskDescription, final String taskTitle, final int timeLeft, final int timer, final double weight, final String token) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("messageMapId", "create-task");
        jsonBody.put("deadline", deadline);
        jsonBody.put("spheresID", spheresID);
        jsonBody.put("taskDescription", taskDescription);
        jsonBody.put("taskTitle", taskTitle);
        jsonBody.put("timeLeft", timeLeft);
        jsonBody.put("timer", timer);
        jsonBody.put("weight", weight);
        return given().contentType("application/json")
                .body(jsonBody.toString())
                .cookie("aJwt", token)
                .when()
                .post(baseURL);
    }
    /**
     * @param token authorisation
     * @return server response
     */
    public Response deleteTask(final String taskId, final String token) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("messageMapId", "delete-task");
        jsonBody.put("taskId", taskId);
        return given().contentType("application/json")
                .body(jsonBody.toString())
                .cookie("aJwt", token)
                .when()
                .post(baseURL);
    }
}
