package slechnert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    public DAO() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver couldn't be loaded");
            e.printStackTrace();
        }
        System.out.println("Driver was loaded");


    }

    private Connection openConnection() throws SQLException {
        String database = "fractalFX";
        String username = "root";
        String password = "";
        String connectionURL = "jdbc:mysql://localhost:3306/"
                + database + "?user="
                + username + "&password=" + password;
        return DriverManager.getConnection(connectionURL);
    }

    private boolean closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Connection closed");
        return true;
    }

    public boolean createUser(User user) {
        try {
            Connection connection = openConnection();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO USER (name, password, email) " +
                            " VALUES (?, ?, ?)");
            insertStatement.setString(1, user.getName());
            insertStatement.setString(2, user.getPassword());
            insertStatement.setString(3, user.getEmail());

            insertStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String user_name) {
        try {
            Connection connection = openConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM User WHERE user_name = ?");
            deleteStatement.setString(1, user_name);

            deleteStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User getUserFromResultset(ResultSet resultSet) throws SQLException {

        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        return new User(name, password, email);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
            Connection connection = openConnection();
            PreparedStatement selectUsers = connection.prepareStatement("SELECT * FROM User");
            ResultSet resultSet = selectUsers.executeQuery();

            while (resultSet.next()) {
                User User = getUserFromResultset(resultSet);
                allUsers.add(User);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't connect to Database");
            e.printStackTrace();

        }
        return allUsers;
    }


    public User getUser(String name) {

        try {
            Connection connection = openConnection();
            PreparedStatement selectOne = connection.prepareStatement("SELECT * FROM User WHERE user_name = ?");
            selectOne.setString(1, name);

            ResultSet resultSet = selectOne.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultset(resultSet);
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fillUserCustomSets(User user) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement getCustomSets = connection.prepareStatement("SELECT * FROM CustomSet WHERE user_name = ?");
        getCustomSets.setString(1, user.getName());

        ResultSet rs = getCustomSets.executeQuery();
        List<CustomSet> customSets = user.getCustomSetList();
        while (rs.next()) {
            customSets.add(new CustomSet(rs.getString("user_name"), rs.getInt("fractal_ID"), rs.getString("set_name")));
        }
        user.setCustomSetList(customSets);
    }


}
