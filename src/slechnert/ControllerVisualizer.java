package slechnert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerVisualizer implements Initializable {
    final double MANDELBROT_RE_MIN = -2;
    final double MANDELBROT_RE_MAX = 1;
    final double MANDELBROT_IM_MIN = -1;
    final double MANDELBROT_IM_MAX = 1;
    private Mandelbrot brot;
//    ColorPicker convergenceColorPicker;

    @FXML //fx:id needs this annotation
    public Canvas canVis;

    public ControllerVisualizer() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brot = new Mandelbrot(50, MANDELBROT_RE_MIN, MANDELBROT_RE_MAX, MANDELBROT_IM_MIN, MANDELBROT_IM_MAX, 0, 0);
        System.out.println(canVis);
        System.out.println(canVis.getGraphicsContext2D());
        paintSet(canVis.getGraphicsContext2D(), brot);
        System.out.println("Controller loaded");
    }

    private void paintSet(GraphicsContext ctx, Mandelbrot brot) {
        double precision = Math.max((brot.getReMax() - brot.getReMin()) / canVis.getWidth(), (brot.getImMax() - brot.getImMin()) / canVis.getHeight()); // 0.004;
        double convergenceValue;

        for (double c = brot.getReMin(), xR = 0; xR < canVis.getWidth(); c = c + precision, xR++) {
            for (double ci = brot.getImMin(), yR = 0; yR < canVis.getHeight(); ci = ci + precision, yR++) {
                if (brot.isIsMandelbrot()) {
                    convergenceValue = checkConvergence(ci, c, 0, 0, brot.getConvergenceSteps());
                } else {
                    convergenceValue = checkConvergence(brot.getZi(), brot.getZ(), ci, c, brot.getConvergenceSteps());
                }
                double t1 = convergenceValue / brot.getConvergenceSteps(); //(50.0 .. )
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);

//                if (convergenceValue != brot.getConvergenceSteps()) {
//                    //Set color
//                    ctx.setFill(getColorScheme(c1, c2));
//                } else {
                ctx.setFill(brot.getConvergenceColor());
//                }
                ctx.fillRect(xR, yR, 1, 1);
            }
        }
    }

    private int checkConvergence(double ci, double c, double z, double zi, int convergenceSteps) {
        for (int i = 0; i < convergenceSteps; i++) {
            double ziT = 2 * (z * zi);
            double zT = z * z - (zi * zi);

            z = zT + c;
            zi = ziT + ci;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }


//    private Color getColorScheme(double c1, double c2) {
//        Mandelbrot.ColorScheme colorScheme = brot.getColorScheme();
//        switch (colorScheme) {
//            case DARK:
//                return Color.color(c1 / 255.0, c2 / 255.0, c2 / 255.0);
//            case LIGHT:
//                return Color.color(c1 / 255.0, c1 / 255.0, c2 / 255.0);
//            case BUNT:
//                return Color.color(c1 / 255.0, c2 / 255.0, c1 / 255.0);
//            default:
//                return Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0);
//        }
//    }
}


//Model view viewmodel architecture