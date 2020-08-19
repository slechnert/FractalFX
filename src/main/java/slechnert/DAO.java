package slechnert;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;

import static javax.swing.UIManager.getInt;

public class DAO {

    public DAO() {

//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Driver couldn't be loaded");
//            e.printStackTrace();
//        }
//        System.out.println("Driver was loaded");


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
        return true;
    }

    public boolean createUser(@NotNull User user) {
        try {
            Connection connection = openConnection();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO USER (user_name, password, email) " +
                            " VALUES (?, ?, ?)");
            insertStatement.setString(1, user.getName());
            insertStatement.setString(2, user.getPassword());
            insertStatement.setString(3, user.getEmail());
            insertStatement.execute();
            return true;
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

        String user_name = resultSet.getString("user_name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        return new User(user_name, password, email);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
            Connection connection = openConnection();
            PreparedStatement selectUsers = connection.prepareStatement("SELECT * FROM User");
            ResultSet resultSet = selectUsers.executeQuery();

            while (resultSet.next()) {
                User user = getUserFromResultset(resultSet);
                fillUserCustomSets(user);
                allUsers.add(user);
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
                User resultUser = getUserFromResultset(resultSet);
                fillUserCustomSets(resultUser);
                return resultUser;
            } else {
                return null;
            }

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
        List<CustomSet> customSets = new ArrayList<>();
        while (rs.next()) {
            customSets.add(new CustomSet(rs.getString("user_name"), rs.getInt("fractal_ID"), rs.getString("set_name")));
        }
        if (!rs.next()) {
            customSets.add((new CustomSet(user.getName(), 0, "default")));
        }
        user.setCustomSetList(customSets);
        closeConnection(connection);
//        syncCustomSet(user);
    }

    public List<CustomRGB> getAllCustomRGB() throws SQLException {
        Connection connection = openConnection();
        PreparedStatement getRGBs = connection.prepareStatement("SELECT * FROM customrgb");

        ResultSet rs = getRGBs.executeQuery();
        List<CustomRGB> allRGB = new ArrayList<>();
        while (rs.next()) {
            allRGB.add(new CustomRGB(rs.getInt("customRGB_ID"), rs.getDouble("r_factor"), rs.getDouble("g_factor"), rs.getDouble("b_factor")));
        }
        closeConnection(connection);
        return allRGB;
    }

    public void addCustomRGB(CustomRGB rgb) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement addcustomrbg = connection.prepareStatement("REPLACE INTO customrgb(customRGB_ID, r_factor, g_factor, b_factor) VALUES(?,?,?,?)");
        addcustomrbg.setInt(1, rgb.getCustomRGB_ID());
        addcustomrbg.setDouble(2, rgb.getR_factor());
        addcustomrbg.setDouble(3, rgb.getG_factor());
        addcustomrbg.setDouble(4, rgb.getB_factor());
        addcustomrbg.execute();
        closeConnection(connection);
    }
//    public void addMandelbrot(Mandelbrot brot) throws SQLException {
//        Connection connection = openConnection();
//        PreparedStatement addcustomrbg = connection.prepareStatement("REPLACE INTO customrgb(customRGB_ID, r_factor, g_factor, b_factor) VALUES(?,?,?,?)");
//        addcustomrbg.setInt(1,brot.getCustomRGB_ID());
//        addcustomrbg.setDouble(2,brot.getR_factor());
//        addcustomrbg.setDouble(3,brot.getG_factor());
//        addcustomrbg.setDouble(4,brot.getB_factor());
//    }

    public void syncCustomSet(User user) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement syncCustomSets = connection.prepareStatement("REPLACE INTO customset (user_name, fractal_ID, set_name) VALUES(?,?,?)");
        for (CustomSet cs : user.customSetList) {
            syncCustomSets.setString(1, user.getName());
            syncCustomSets.setInt(2, cs.getFractal_ID());
            syncCustomSets.setString(3, cs.getSet_name());
            syncCustomSets.execute();
        }
        closeConnection(connection);
    }

}
