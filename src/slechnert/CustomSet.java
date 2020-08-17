package slechnert;

public class CustomSet {
    private String user_name;
    private int fractal_ID;
    private String set_name;

    public CustomSet(String user_name, int fractal_ID, String set_name) {
        this.user_name = user_name;
        this.fractal_ID = fractal_ID;
        this.set_name = set_name;
    }

    public int getFractal_ID() {
        return fractal_ID;
    }

    public void setFractal_ID(int fractal_ID) {
        this.fractal_ID = fractal_ID;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }
}
