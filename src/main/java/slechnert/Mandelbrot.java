package slechnert;

import javafx.scene.paint.Color;

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
    private int convergenceSteps;
    private Color convergenceColor;
    private ColorScheme colorScheme;
    double prePrecision;

    public int fractal_ID;
    public int customRGB_ID;
    public double r_factor;
    public double g_factor;
    public double b_factor;


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
}