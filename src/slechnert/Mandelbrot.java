package slechnert;

import javafx.scene.paint.Color;

public class Mandelbrot {

    public enum ColorScheme {
        DARK, LIGHT, BUNT
    }


    private double reMin;
    private double reMax;
    private double imMin;
    private double imMax;
    // z + zi for Julia set
    private double z;
    private double zi;
    private int convergenceSteps;
    private Color convergenceColor = Color.WHITE;
    private ColorScheme colorScheme = ColorScheme.DARK;

    public boolean isIsMandelbrot() {
        // if z is 0 then it is a Mandelbrot set
        return getZ() == 0 && getZi() == 0;
    }

    public Mandelbrot(int convergenceSteps, double reMin, double reMax, double imMin, double imMax, double z, double zi) {
        this.convergenceSteps = convergenceSteps;
        this.reMin = reMin;
        this.reMax = reMax;
        this.imMin = imMin;
        this.imMax = imMax;
        this.z = z;
        this.zi = zi;
    }

    public int getConvergenceSteps() {
        return convergenceSteps;
    }

    public double getReMin() {
        return reMin;
    }

    public double getReMax() {
        return reMax;
    }

    public double getImMin() {
        return imMin;
    }

    public double getImMax() {
        return imMax;
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
}