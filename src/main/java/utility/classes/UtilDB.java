package utility.classes;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class UtilDB implements AutoCloseable {
    private static Properties properties = new Properties();
    private static Statement statement;
    private static Connection connection;

    /**
     * UtilDB constructor
     *
     * @throws SQLException exception
     */
    public UtilDB() throws SQLException {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fileInputStream);
            connection = DriverManager.getConnection(properties.getProperty("db.urlDB"), properties.getProperty("db.usernameDB"), properties.getProperty("db.passwordDB"));


        } catch (IOException exception) {
            System.out.println("No such file or directory");
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        } else throw new SQLException("Connection in null");
    }

    public void deleteUserByEmail(final String email) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM users WHERE document->>'username' = '" + email + "'");
        statement.close();
    }
}