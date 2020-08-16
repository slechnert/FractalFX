package slechnert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {

    private Connection openConnection() throws SQLException {
        String database = "school";
        String username = "root";
        String password = "";
        String connectionURL = "jdbc:mysql://localhost:3306/"
                + database + "?user="
                + username + "&password=" + password;
        return DriverManager.getConnection(connectionURL);
    }

    public boolean createUser(User user) {

        try {
            Connection connection = openConnection();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO USER (user_ID, name, password, email) " +
                            " VALUES (?, ?, ?, ?)");
            insertStatement.setString(2, user.getName());
            insertStatement.setString(3, user.getPassword());
            insertStatement.setString(4, user.getEmail());

            insertStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
