package slechnert;

public class CustomRGB {
    public int customRGB_ID;
    public double r_factor;
    public double g_factor;
    public double b_factor;

    public CustomRGB(double r_factor, double g_factor, double b_factor) {
        this.r_factor = r_factor;
        this.g_factor = g_factor;
        this.b_factor = b_factor;
    }

    public CustomRGB(int customRGB_ID, double r_factor, double g_factor, double b_factor) {
        this.customRGB_ID = customRGB_ID;
        this.r_factor = r_factor;
        this.g_factor = g_factor;
        this.b_factor = b_factor;
    }

    public int getCustomRGB_ID() {
        return customRGB_ID;
    }

    public void setCustomRGB_ID(int customRGB_ID) {
        this.customRGB_ID = customRGB_ID;
    }

    public double getR_factor() {
        return r_factor;
    }

    public void setR_factor(double r_factor) {
        this.r_factor = r_factor;
    }

    public double getG_factor() {
        return g_factor;
    }

    public void setG_factor(double g_factor) {
        this.g_factor = g_factor;
    }

    public double getB_factor() {
        return b_factor;
    }

    public void setB_factor(double b_factor) {
        this.b_factor = b_factor;
    }
}
