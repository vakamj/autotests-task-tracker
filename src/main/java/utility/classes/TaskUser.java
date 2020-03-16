package utility.classes;

/**
 * User class, to generate user data
 */
public class TaskUser {
    private String username;
    private String password;
    private String messageMapId;

    public TaskUser(final String messageMapId, final String username, final String password) {
        this.messageMapId = messageMapId;
        this.username = username;
        this.password = password;
    }

}
