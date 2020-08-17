package slechnert;

import java.util.List;

public class User {
    private final String user_name;
    private final String password;
    private final String email;
    private List<CustomSet> customSetList;
    private List<Mandelbrot> customBrote;

    public User(String name, String password, String email) {
        this.user_name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<CustomSet> getCustomSetList() {
        return customSetList;
    }

    public void setCustomSetList(List<CustomSet> customSetList) {
        this.customSetList = customSetList;
    }

    public List<Mandelbrot> getCustomBrote() {
        return customBrote;
    }

    public void setCustomBrote(List<Mandelbrot> customBrote) {
        this.customBrote = customBrote;
    }
}
