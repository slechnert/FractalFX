package slechnert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ControllerVisualizer implements Initializable {
    final double MANDELBROT_RE_MIN = -2;
    final double MANDELBROT_RE_MAX = 1;
    final double MANDELBROT_IM_MIN = -1;
    final double MANDELBROT_IM_MAX = 1;

    final private double JULIA_RE_MIN = -1.5;
    final private double JULIA_RE_MAX = 1.5;
    final private double JULIA_IM_MIN = -1.5;
    final private double JULIA_IM_MAX = 1.5;
    public Mandelbrot brot;
    GraphicsContext gc;

    public ControllerVisualizer() {
    }

    @FXML
    public Canvas canVis;

    @FXML
    public ColorPicker colorPicker;

    @FXML
    void pickColor() {
        brot.setConvergenceColor((colorPicker.getValue()));
        paintSet(gc, brot);
    }

    @FXML
    public ChoiceBox colorSchemePicker;

    @FXML
    public TextField zTF;

    @FXML
    public TextField ziTF;

    @FXML
    public void setZ() {
        double newZ = Double.parseDouble(zTF.getText());
        if (newZ <= 1 && newZ >= -1) {
            brot.setZ(newZ);
            paintSet(gc, brot);
        } else {
            zTF.clear();
            zTF.setText("Invalid Value");
        }
    }

    @FXML
    public void setZi() {
        double newZi = Double.parseDouble(ziTF.getText());
        if (newZi <= 1 && newZi >= -1) {
            brot.setZ(newZi);
            paintSet(gc, brot);
        } else {
            zTF.clear();
            ziTF.setText("Invalid Value");
        }
    }

    @FXML
    public TextField convTF;

    @FXML
    public void convRedraw() {
        int newConv = Integer.parseInt(convTF.getText());
        brot.setConvergenceSteps(newConv);
        paintSet(gc, brot);
    }

    @FXML
    public CheckBox isJulia;

    @FXML
    private void drawJulia() {
        boolean checkBoxSelected = isJulia.isSelected();
        if (checkBoxSelected) {
            gc.clearRect(0, 0, canVis.getWidth(), canVis.getHeight());
            if (Integer.parseInt(convTF.getText()) >= 1 && Integer.parseInt(convTF.getText()) <= 1000) {
                brot = new Mandelbrot(Integer.parseInt(convTF.getText()), JULIA_RE_MIN, JULIA_RE_MAX, JULIA_IM_MIN, JULIA_IM_MAX, 0.3, -0.5);
            } else {
                brot = new Mandelbrot(15, JULIA_RE_MIN, JULIA_RE_MAX, JULIA_IM_MIN, JULIA_IM_MAX, 0.3, -0.5);
            }
            zTF.setDisable(false);
            ziTF.setDisable(false);
        } else {
            gc.clearRect(0, 0, canVis.getWidth(), canVis.getHeight());
            if (Integer.parseInt(convTF.getText()) >= 1 && Integer.parseInt(convTF.getText()) <= 1000) {
                brot = new Mandelbrot(Integer.parseInt(convTF.getText()), MANDELBROT_RE_MIN, MANDELBROT_RE_MAX, MANDELBROT_IM_MIN, MANDELBROT_IM_MAX, 0, 0);
            } else {
                brot = new Mandelbrot(15, MANDELBROT_RE_MIN, MANDELBROT_RE_MAX, MANDELBROT_IM_MIN, MANDELBROT_IM_MAX, 0, 0);
            }
            zTF.setDisable(true);
            ziTF.setDisable(true);
        }
        brot.setConvergenceColor(colorPicker.getValue());
        paintSet(gc, brot);
    }

    @FXML
    public TextField customR;
    @FXML
    public TextField customG;
    @FXML
    public TextField customB;

    private void paintSet(GraphicsContext ctx, Mandelbrot brot) {
        double precision = Math.max((brot.getMANDELBROT_RE_MAX() - brot.getMANDELBROT_RE_MIN()) / canVis.getWidth(), (brot.getMANDELBROT_IM_MAX() - brot.getMANDELBROT_IM_MIN()) / canVis.getHeight());
        double convergenceValue;
        Set<Color> colors = new HashSet<>();
        for (double c = brot.getMANDELBROT_RE_MIN(), xR = 0; xR < canVis.getWidth(); c += precision, xR++) {
            for (double ci = brot.getMANDELBROT_IM_MIN(), yR = 0; yR < canVis.getHeight(); ci += precision, yR++) {
                if (brot.isMandelbrot()) {
                    convergenceValue = checkConvergence(ci, c, 0, 0, brot.getConvergenceSteps());
                } else { //is Julia
                    convergenceValue = checkConvergence(brot.getZi(), brot.getZ(), ci, c, brot.getConvergenceSteps());
                }
                double t1 = convergenceValue / brot.getConvergenceSteps(); //(50.0 .. )
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);

                if (convergenceValue != brot.getConvergenceSteps()) {
                    //Set colorScheme
                    Color color = getDistortedColorScheme(c1, c2);
                    colors.add(color);
                    ctx.setFill(color);
                } else {
                    ctx.setFill(brot.getConvergenceColor());

                }
                if (brot.isMandelbrot()) {
                    ctx.fillRect(xR + 50, yR, 1, 1);
                } else {
                    ctx.fillRect(xR + 250, yR, 1, 1);
                }
            }
        }
        System.out.println("Fractal was drawn!");
    }

    private int checkConvergence(double ci, double c, double z, double zi, int convergenceSteps) {
        for (int i = 0; i < convergenceSteps; i++) {
            double ziT = 2 * (z * zi);
            double zT = z * z - zi * zi;

            z = zT + c;
            zi = ziT + ci;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }

    //getColoScheme + distortion
    private Color getDistortedColorScheme(double c1, double c2) {
        Mandelbrot.ColorScheme colorScheme = brot.getColorScheme();
        //TODO fix RGB distortion
        double factorR = 1;
        double factorG = 1;
        double factorB = 1;

        if ((customR.getText()).equals("")) {
        } else {
            if (Double.parseDouble(customR.getText()) >= 0 && Double.parseDouble(customR.getText()) <= 10) {
                factorR = Double.parseDouble(customR.getText());
            }
        }
        if ((customG.getText()).equals("")) {
        } else {
            if (Double.parseDouble(customG.getText()) >= 0 && Double.parseDouble(customG.getText()) <= 10) {
                factorG = Double.parseDouble(customG.getText());
            }
        }
        if ((customB.getText()).equals("")) {
        } else {
            if (Double.parseDouble(customB.getText()) >= 0 && Double.parseDouble(customB.getText()) <= 10) {
                factorB = Double.parseDouble(customB.getText());
            }
        }
        switch (colorScheme) {
            case RED:
                return Color.color((c1 * factorR) / 255.0, (c2 * factorG) / 255.0, (c2 * factorB) / 255.0);
            case YELLOW:
                return Color.color((c1 * factorR) / 255.0, (c1 * factorG) / 255.0, (c2 * factorB) / 255.0);
            case MAGENTA:
                return Color.color((c1 * factorR) / 255.0, (c2 * factorG) / 255.0, (c1 * factorB) / 255.0);
            case BLUE:
                return Color.color((c2 * factorR) / 255.0, (c1 * factorG) / 255.0, (c1 * factorB) / 255.0);
            case GREEN:
                return Color.color((c2 * factorR) / 255.0, (c1 * factorG) / 255.0, (c2 * factorB) / 255.0);
            case CYAN:
                return Color.color((c2 * factorR) / 255.0, (c1 * factorG) / 255.0, (c1 * factorB) / 255.0);
            default:
                return Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0);
        }
    }

    //Checks which color scheme should be activated
    private Color getColorScheme(double c1, double c2) {
        Mandelbrot.ColorScheme colorScheme = brot.getColorScheme();
        switch (colorScheme) {
            case RED:
                return Color.color(c1 / 255.0, c2 / 255.0, c2 / 255.0);
            case YELLOW:
                return Color.color(c1 / 255.0, c1 / 255.0, c2 / 255.0);
            case MAGENTA:
                return Color.color(c1 / 255.0, c2 / 255.0, c1 / 255.0);
            case BLUE:
                return Color.color(c2 / 255.0, c1 / 255.0, c1 / 255.0);
            case GREEN:
                return Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0);
            case CYAN:
                return Color.color(c2 / 255.0, c1 / 255.0, c1 / 255.0);
            default:
                return Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0);
        }
    }

    @FXML
    public void keepRGBActual() {
        paintSet(gc, brot);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brot = new Mandelbrot(15, JULIA_RE_MIN, JULIA_RE_MAX, JULIA_IM_MIN, JULIA_IM_MAX, 0.3, -0.5);
        gc = canVis.getGraphicsContext2D();
        colorSchemePicker.getItems().addAll(Mandelbrot.ColorScheme.values());
        colorSchemePicker.getSelectionModel().select(brot.getColorScheme());
        colorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> brot.setColorScheme((Mandelbrot.ColorScheme) newV)));
        colorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> paintSet(gc, brot)));
        paintSet(gc, brot);
        System.out.println("Controller loaded");
    }
}

//Model view viewmodel architecture