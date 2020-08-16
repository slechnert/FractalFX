package slechnert;

import java.util.List;

public class User {
    private final Integer UID;
    private final String name;
    private final String password;
    private final String email;
    private List<CustomSet> customSetList;

    public User(Integer UID, String name, String password, String email) {
        this.UID = UID;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Integer getUID() {
        return UID;
    }

    public String getName() {
        return name;
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
}
