package slechnert;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Mandelbrot {


    public enum ColorScheme {
        GREEN, RED, YELLOW, BLUE, CYAN, MAGENTA, BLACK, WHITE
    }


    public double MANDELBROT_RE_MIN = -2.5;
    public double MANDELBROT_RE_MAX = .5;
    public double MANDELBROT_IM_MIN = -1;
    public double MANDELBROT_IM_MAX = 1;

    public double JULIA_RE_MIN = -1.5;
    public double JULIA_RE_MAX = 1.5;
    public double JULIA_IM_MIN = -1.5;
    public double JULIA_IM_MAX = 1.5;
    // z + zi for Julia set
    private double z;
    private double zi;
    public int convergenceSteps;
    public Color convergenceColor;
    public ColorScheme colorScheme;
//    public String schemeString;

    public int fractal_ID;
    public int customRGB_ID;
    public int color_ID;
    public double r_factor;
    public double g_factor;
    public double b_factor;
    private String customSetName;

    public boolean isMandelbrot() {
        // if z is 0 then it is a Mandelbrot set
        return getZ() == 0 && getZi() == 0;
    }

    public Mandelbrot(int convergenceSteps, double MANDELBROT_RE_MIN, double MANDELBROT_RE_MAX, double MANDELBROT_IM_MIN, double MANDELBROT_IM_MAX, double z, double zi) {
        this.MANDELBROT_RE_MIN = MANDELBROT_RE_MIN;
        this.MANDELBROT_RE_MAX = MANDELBROT_RE_MAX;
        this.MANDELBROT_IM_MIN = MANDELBROT_IM_MIN;
        this.MANDELBROT_IM_MAX = MANDELBROT_IM_MAX;
        this.z = z;
        this.zi = zi;
        this.convergenceSteps = convergenceSteps;
        this.convergenceColor = Color.WHITE;
        this.colorScheme = ColorScheme.WHITE;
        this.customSetName = "default";
        this.fractal_ID = 0;
        this.customRGB_ID = 0;
        this.color_ID = 1000;
        this.r_factor = 1;
        this.g_factor = 1;
        this.b_factor = 1;
    }

    public Mandelbrot(double MANDELBROT_RE_MIN, double MANDELBROT_RE_MAX, double MANDELBROT_IM_MIN, double MANDELBROT_IM_MAX, double z, double zi, int convergenceSteps, int fractal_ID, int customRGB_ID, int color_ID, ColorScheme colorScheme) {
        this.MANDELBROT_RE_MIN = MANDELBROT_RE_MIN;
        this.MANDELBROT_RE_MAX = MANDELBROT_RE_MAX;
        this.MANDELBROT_IM_MIN = MANDELBROT_IM_MIN;
        this.MANDELBROT_IM_MAX = MANDELBROT_IM_MAX;
        this.z = z;
        this.zi = zi;
        this.convergenceSteps = convergenceSteps;
        this.fractal_ID = fractal_ID;
        this.customRGB_ID = customRGB_ID;
        this.color_ID = color_ID;
        this.colorScheme = colorScheme;
    }

    public int getConvergenceSteps() {
        return convergenceSteps;
    }

    public double getReMin() {
        return MANDELBROT_RE_MIN;
    }

    public double getReMax() {
        return MANDELBROT_RE_MAX;
    }

    public double getImMin() {
        return MANDELBROT_IM_MIN;
    }

    public double getImMax() {
        return MANDELBROT_IM_MAX;
    }

    public double getZ() {
        return z;
    }

    public double getZi() {
        return zi;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setZi(double zi) {
        this.zi = zi;
    }

    public Color getConvergenceColor() {
        return convergenceColor;
    }

    public void setConvergenceColor(Color convergenceColor) {
        this.convergenceColor = convergenceColor;
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    public static ColorScheme getColorScheme(String string) {
        switch (string) {
            case "red":
                return ColorScheme.RED;
            case "yellow":
                return ColorScheme.YELLOW;
            case "magenta":
                return ColorScheme.MAGENTA;
            case "blue":
                return ColorScheme.BLUE;
            case "green":
                return ColorScheme.GREEN;
            case "cyan":
                return ColorScheme.CYAN;
            case "black":
                return ColorScheme.BLACK;
            case "white":
                return ColorScheme.WHITE;
            default:
                return ColorScheme.BLACK;
        }
    }

    public void setColorScheme(ColorScheme colorSchema) {
        this.colorScheme = colorSchema;
    }

    public double getMANDELBROT_RE_MIN() {
        return MANDELBROT_RE_MIN;
    }

    public double getMANDELBROT_RE_MAX() {
        return MANDELBROT_RE_MAX;
    }

    public double getMANDELBROT_IM_MIN() {
        return MANDELBROT_IM_MIN;
    }

    public double getMANDELBROT_IM_MAX() {
        return MANDELBROT_IM_MAX;
    }

    public double getJULIA_RE_MIN() {
        return JULIA_RE_MIN;
    }

    public double getJULIA_RE_MAX() {
        return JULIA_RE_MAX;
    }

    public double getJULIA_IM_MIN() {
        return JULIA_IM_MIN;
    }

    public double getJULIA_IM_MAX() {
        return JULIA_IM_MAX;
    }

    public void setConvergenceSteps(int convergenceSteps) {
        this.convergenceSteps = convergenceSteps;
    }

    public String getCustomSetName() {
        if (customSetName.equals("default")) {
            return "";
        }
        return customSetName;
    }

    public int getFractal_ID() {
        return fractal_ID;
    }

    public int getCustomRGB_ID() {
        return customRGB_ID;
    }

    public double getR_factor() {
        return r_factor;
    }

    public double getG_factor() {
        return g_factor;
    }

    public double getB_factor() {
        return b_factor;
    }

    public void setMANDELBROT_RE_MIN(double MANDELBROT_RE_MIN) {
        this.MANDELBROT_RE_MIN = MANDELBROT_RE_MIN;
    }

    public void setMANDELBROT_RE_MAX(double MANDELBROT_RE_MAX) {
        this.MANDELBROT_RE_MAX = MANDELBROT_RE_MAX;
    }

    public void setMANDELBROT_IM_MIN(double MANDELBROT_IM_MIN) {
        this.MANDELBROT_IM_MIN = MANDELBROT_IM_MIN;
    }

    public void setMANDELBROT_IM_MAX(double MANDELBROT_IM_MAX) {
        this.MANDELBROT_IM_MAX = MANDELBROT_IM_MAX;
    }

    public void setJULIA_RE_MIN(double JULIA_RE_MIN) {
        this.JULIA_RE_MIN = JULIA_RE_MIN;
    }

    public void setJULIA_RE_MAX(double JULIA_RE_MAX) {
        this.JULIA_RE_MAX = JULIA_RE_MAX;
    }

    public void setJULIA_IM_MIN(double JULIA_IM_MIN) {
        this.JULIA_IM_MIN = JULIA_IM_MIN;
    }

    public void setJULIA_IM_MAX(double JULIA_IM_MAX) {
        this.JULIA_IM_MAX = JULIA_IM_MAX;
    }

    public void setCustomSetName(String customSetName) {
        this.customSetName = customSetName;
    }

    public void setFractal_ID(int fractal_ID) {
        this.fractal_ID = fractal_ID;
    }

    public void setCustomRGB_ID(int customRGB_ID) {
        this.customRGB_ID = customRGB_ID;
    }

    public void setR_factor(double r_factor) {
        this.r_factor = r_factor;
    }

    public void setG_factor(double g_factor) {
        this.g_factor = g_factor;
    }

    public void setB_factor(double b_factor) {
        this.b_factor = b_factor;
    }

    public int getColor_ID() {
        return color_ID;
    }

    public void setColor_ID(int color_ID) {
        this.color_ID = color_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mandelbrot that = (Mandelbrot) o;
        return Double.compare(that.MANDELBROT_RE_MIN, MANDELBROT_RE_MIN) == 0 &&
                Double.compare(that.MANDELBROT_RE_MAX, MANDELBROT_RE_MAX) == 0 &&
                Double.compare(that.MANDELBROT_IM_MIN, MANDELBROT_IM_MIN) == 0 &&
                Double.compare(that.MANDELBROT_IM_MAX, MANDELBROT_IM_MAX) == 0 &&
                Double.compare(that.z, z) == 0 &&
                Double.compare(that.zi, zi) == 0 &&
                convergenceSteps == that.convergenceSteps &&
                customRGB_ID == that.customRGB_ID &&
                color_ID == that.color_ID &&
                Double.compare(that.r_factor, r_factor) == 0 &&
                Double.compare(that.g_factor, g_factor) == 0 &&
                Double.compare(that.b_factor, b_factor) == 0 &&
                Objects.equals(convergenceColor, that.convergenceColor) &&
                colorScheme == that.colorScheme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(MANDELBROT_RE_MIN, MANDELBROT_RE_MAX, MANDELBROT_IM_MIN, MANDELBROT_IM_MAX, z, zi, convergenceSteps, colorScheme, customRGB_ID, color_ID, r_factor, g_factor, b_factor);
    }
}