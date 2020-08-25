package slechnert;

import com.mysql.cj.protocol.Resultset;
import javafx.scene.paint.Color;

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

    public boolean createUser(User user) {
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

    public List<Integer> getColorIds() throws SQLException {
        Connection connection = openConnection();
        PreparedStatement getColorIds = connection.prepareStatement("SELECT color_ID FROM color");

        ResultSet rs = getColorIds.executeQuery();
        List<Integer> allColorIds = new ArrayList<>();
        while (rs.next()) {
            allColorIds.add(rs.getInt("color_ID"));
        }
        return allColorIds;
    }

    public Color getColor(int color_id) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement getColor = connection.prepareStatement("SELECT * FROM color WHERE color_id = ?");
        getColor.setInt(1, color_id);
        ResultSet rs = getColor.executeQuery();
        while (rs.next()) {
            double red = rs.getInt("red");
            double green = rs.getInt("green");
            double blue = rs.getInt("blue");
            return (Color.color(red / 255, green / 255, blue / 255));
        }
        return null;
    }

    public int getSpecificColorId(Color color) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement selectOne = connection.prepareStatement("SELECT color_ID FROM color WHERE red = ? AND green = ? AND blue = ?");
        selectOne.setInt(1, ((int) (color.getRed() * 255)));
        selectOne.setInt(2, ((int) (color.getGreen() * 255)));
        selectOne.setInt(3, ((int) (color.getBlue() * 255)));
        ResultSet rs = selectOne.executeQuery();
        while (rs.next()) {
            return rs.getInt("color_ID");
        }
        return 0;
    }


    public List<Color> getAllColors() throws SQLException {
        Connection connection = openConnection();
        PreparedStatement getAllColors = connection.prepareStatement("SELECT * FROM color");

        ResultSet rs = getAllColors.executeQuery();
        List<Color> allColors = new ArrayList<>();
        while (rs.next()) {
            double red = rs.getInt("red");
            double green = rs.getInt("green");
            double blue = rs.getInt("blue");
            allColors.add(Color.color(red / 255, green / 255, blue / 255));
        }
        return allColors;

    }


    public void addColor(Color color) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement addColor = connection.prepareStatement("INSERT INTO color(red, blue, green) VALUES(?,?,?)");
        addColor.setInt(1, ((int) color.getRed() * 255));
        addColor.setInt(2, ((int) color.getGreen() * 255));
        addColor.setInt(3, ((int) color.getBlue() * 255));
        addColor.execute();
        closeConnection(connection);
    }

    public void addCustomRGB(CustomRGB rgb) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement addcustomrbg = connection.prepareStatement("INSERT INTO customrgb(r_factor, g_factor, b_factor) VALUES(?,?,?)");
        addcustomrbg.setDouble(1, rgb.getR_factor());
        addcustomrbg.setDouble(2, rgb.getG_factor());
        addcustomrbg.setDouble(3, rgb.getB_factor());
        addcustomrbg.execute();
        closeConnection(connection);
    }

    public int getLastID() throws SQLException {
        Connection connection = openConnection();
        PreparedStatement lastID = connection.prepareStatement("SELECT * FROM fractal ORDER BY fractal.fractal_ID DESC LIMIT 1");
        ResultSet rs = lastID.executeQuery();
        while(rs.next()){
            return rs.getInt("fractal_ID");
        }
        return rs.getInt("fractal_ID");
    }

    public void addMandelbrot(Mandelbrot brot) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement addcustombrot = connection.prepareStatement("INSERT INTO fractal(customRGB_ID, base_color, color_scheme, convergence_steps, re_min, re_max, im_min, im_max, z, zi) VALUES(?,?,?,?,?,?,?,?,?,?)");
        addcustombrot.setInt(1, brot.getCustomRGB_ID());
        addcustombrot.setInt(2, brot.getColor_ID());
        addcustombrot.setString(3, brot.getColorScheme().toString());
        addcustombrot.setInt(4, brot.getConvergenceSteps());
        addcustombrot.setDouble(5, brot.getReMin());
        addcustombrot.setDouble(6, brot.getReMax());
        addcustombrot.setDouble(7, brot.getImMin());
        addcustombrot.setDouble(8, brot.getImMax());
        addcustombrot.setDouble(9, brot.getZ());
        addcustombrot.setDouble(10, brot.getZi());
        addcustombrot.execute();
        closeConnection(connection);
    }

    public List<Mandelbrot> getAllBrote() throws SQLException {
        Connection connection = openConnection();
        PreparedStatement getAllBrote = connection.prepareStatement("SELECT * FROM fractal");
        ResultSet rs = getAllBrote.executeQuery();
        List<Mandelbrot> allBrote = new ArrayList<>();
        while (rs.next()) {
            allBrote.add(new Mandelbrot(
                    rs.getDouble("re_min"),
                    rs.getDouble("re_max"),
                    rs.getDouble("im_min"),
                    rs.getDouble("im_max"),
                    rs.getDouble("z"),
                    rs.getDouble("zi"),
                    rs.getInt("convergence_steps"),
                    rs.getInt("fractal_ID"),
                    rs.getInt("customRGB_ID"),
                    rs.getInt("base_color"),
                    Mandelbrot.getColorScheme(rs.getString("color_scheme"))));
        }
        return allBrote;
    }

    public void addCustomSet(CustomSet customSet) throws SQLException {
        Connection connection = openConnection();
        PreparedStatement addCustomSet = connection.prepareStatement("INSERT INTO customset(user_name, fractal_ID, set_name) VALUES(?,?,?)");
        addCustomSet.setString(1, customSet.getUser_name());
        addCustomSet.setInt(2, customSet.getFractal_ID());
        addCustomSet.setString(3, customSet.getSet_name());
        addCustomSet.execute();
        closeConnection(connection);
    }


}
