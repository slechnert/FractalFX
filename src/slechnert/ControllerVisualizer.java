package slechnert;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    private final User currentUser;
    private final DAO dao;

    public ControllerVisualizer(User currentUser) {
        this.currentUser = currentUser;
        this.dao = new DAO();
    }

    @FXML
    public BorderPane momma;

    @FXML
    public Button close;

    @FXML
    public void stop() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
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
            zTF.setText("NOPE");
        }
    }

    @FXML
    public void setZi() {
        double newZi = Double.parseDouble(ziTF.getText());
        if (newZi <= 1 && newZi >= -1) {
            brot.setZi(newZi);
            paintSet(gc, brot);
        } else {
            zTF.clear();
            ziTF.setText("NOPE");
        }
    }

    @FXML
    public TextField convTF;

    @FXML
    public void convRedraw() {
        int newConv = 50;
        if (convTF.getText().equals("")) {
        } else {
            newConv = Integer.parseInt(convTF.getText());
        }
        brot.setConvergenceSteps(newConv);
        paintSet(gc, brot);
    }

    //swap & redraw
    @FXML
    public CheckBox isJulia;


    @FXML
    private void drawJulia() {
        boolean checkBoxSelected = isJulia.isSelected();
        if (checkBoxSelected) {
            gc.clearRect(0, 0, canVis.getWidth(), canVis.getHeight());
            if (convTF.getText().equals("")) {
                brot = new Mandelbrot(1, brot.JULIA_RE_MIN, brot.JULIA_RE_MAX, brot.JULIA_IM_MIN, brot.JULIA_IM_MAX, 0.3, -0.5);
            } else if (Integer.parseInt(convTF.getText()) >= 1 && Integer.parseInt(convTF.getText()) <= 1000) {
                brot = new Mandelbrot(Integer.parseInt(convTF.getText()), brot.JULIA_RE_MIN, brot.JULIA_RE_MAX, brot.JULIA_IM_MIN, brot.JULIA_IM_MAX, 0.3, -0.5);
            } else {
                brot = new Mandelbrot(1, brot.JULIA_RE_MIN, brot.JULIA_RE_MAX, brot.JULIA_IM_MIN, brot.JULIA_IM_MAX, 0.3, -0.5);
            }
            zTF.setDisable(false);
            ziTF.setDisable(false);
        } else {
            gc.clearRect(0, 0, canVis.getWidth(), canVis.getHeight());
            if (convTF.getText().equals("")) {
                brot = new Mandelbrot(1, brot.MANDELBROT_RE_MIN, brot.MANDELBROT_RE_MAX, brot.MANDELBROT_IM_MIN, brot.MANDELBROT_IM_MAX, 0, 0);
            } else if (Integer.parseInt(convTF.getText()) >= 1 && Integer.parseInt(convTF.getText()) <= 1000) {
                brot = new Mandelbrot(Integer.parseInt(convTF.getText()), brot.MANDELBROT_RE_MIN, brot.MANDELBROT_RE_MAX, brot.MANDELBROT_IM_MIN, brot.MANDELBROT_IM_MAX, 0, 0);
            } else {
                brot = new Mandelbrot(1, brot.MANDELBROT_RE_MIN, brot.MANDELBROT_RE_MAX, brot.MANDELBROT_IM_MIN, brot.MANDELBROT_IM_MAX, 0, 0);
            }
            zTF.setDisable(true);
            ziTF.setDisable(true);
        }
        brot.setConvergenceColor(colorPicker.getValue());
        updateStats();
        paintSet(gc, brot);
    }

    //pixelwriter draw
    private void paintSet(GraphicsContext ctx, Mandelbrot brot) {
        double precision = Math.max((MANDELBROT_RE_MAX - MANDELBROT_RE_MIN) / canVis.getWidth(), (MANDELBROT_IM_MAX - MANDELBROT_IM_MIN) / canVis.getHeight());
        double convergenceValue;
        PixelWriter p = ctx.getPixelWriter();

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
                Color color = Color.WHITE;
                if (convergenceValue != brot.getConvergenceSteps()) {
                    //Set colorScheme
                    color = getDistortedColorScheme(c1, c2);
                } else {
                    color = brot.getConvergenceColor();
                }
                if (brot.isMandelbrot()) {
                    p.setColor((int) xR + (int) (canVis.getWidth() / 12), (int) yR, color);
                } else {
                    p.setColor((int) xR + (int) (canVis.getWidth() / 5), (int) yR, color);
                }
            }
        }
        updateStats();
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

    //getColorScheme + distortion
    @FXML
    public TextField customR;
    @FXML
    public TextField customG;
    @FXML
    public TextField customB;

    private Color getDistortedColorScheme(double c1, double c2) {
        Mandelbrot.ColorScheme colorScheme = brot.getColorScheme();
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
                return Color.color((c2 * factorR) / 255.0, (c2 * factorG) / 255.0, (c1 * factorB) / 255.0);
            case GREEN:
                return Color.color((c2 * factorR) / 255.0, (c1 * factorG) / 255.0, (c2 * factorB) / 255.0);
            case CYAN:
                return Color.color((c2 * factorR) / 255.0, (c1 * factorG) / 255.0, (c1 * factorB) / 255.0);
            case BLACK:
                return Color.color((c2 * factorR) / 255.0, (c2 * factorG) / 255.0, (c2 * factorB) / 255.0);
            case WHITE:
                return Color.color((c1 * factorR) / 255.0, (c1 * factorG) / 255.0, (c1 * factorB) / 255.0);
            default:
                return Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0);
        }
    }

    @FXML
    public void keepRGBActual() {
        paintSet(gc, brot);
    }

    //Number range display
    @FXML
    private Label reMinStat;
    @FXML
    private Label reMaxStat;
    @FXML
    private Label imMinStat;
    @FXML
    private Label imMaxStat;

    @FXML
    private void updateStats() {
        reMinStat.setText(String.valueOf(brot.getMANDELBROT_RE_MIN()));
        reMaxStat.setText(String.valueOf(brot.getMANDELBROT_RE_MAX()));
        imMinStat.setText(String.valueOf(brot.getMANDELBROT_IM_MIN()));
        imMaxStat.setText(String.valueOf(brot.getMANDELBROT_IM_MAX()));
    }


    private void initializeColorSchemePicker() {
        colorSchemePicker.getItems().addAll(Mandelbrot.ColorScheme.values());
        colorSchemePicker.getSelectionModel().select(brot.getColorScheme());
        colorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> brot.setColorScheme((Mandelbrot.ColorScheme) newV)));
        colorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> paintSet(gc, brot)));
    }

    //TODO for zoom get x/y and c/ci at that point to calc new RE / IM
    //TODO understand how old c/ci correlate with new drawing

    //ZoomDraw
    private void paintZoom(GraphicsContext ctx, Mandelbrot brot, double reRange, double imRange, double c, double ci) {
        double precision = Math.max(reRange, imRange);
        double convergenceValue;
        PixelWriter p = ctx.getPixelWriter();


        for (double xR = 0; xR < canVis.getWidth(); c += precision, xR++) {
            for (double yR = 0; yR < canVis.getHeight(); ci += precision, yR++) {
                if (brot.isMandelbrot()) {
                    convergenceValue = checkConvergence(ci, c, 0, 0, brot.getConvergenceSteps());
                } else { //is Julia
                    convergenceValue = checkConvergence(brot.getZi(), brot.getZ(), ci, c, brot.getConvergenceSteps());
                }
                double t1 = convergenceValue / brot.getConvergenceSteps(); //(50.0 .. )
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);
                Color color;
                if (convergenceValue != brot.getConvergenceSteps()) {
                    //Set colorScheme
                    color = getDistortedColorScheme(c1, c2);
                } else {
                    color = brot.getConvergenceColor();
                }
                if (brot.isMandelbrot()) {
                    p.setColor((int) xR, (int) yR, color);
                } else {
                    p.setColor((int) xR, (int) yR, color);
                }
            }
        }
        updateStats();
        System.out.println("Fractal was drawn!");
    }

    @FXML
    private Rectangle zoomTangle;

    private void showZoomTangle() {
        zoomTangle.setDisable(false);
        zoomTangle.setStroke(Color.WHITE);
    }

    @FXML
    private void hideZoomTangle() {
        zoomTangle.setDisable(true);
        zoomTangle.setStroke(new Color(0, 0, 0, 0));
    }


    public void drawZoomBrot() {
        double oldReMin = brot.getMANDELBROT_RE_MIN();
        double oldReMax = brot.getMANDELBROT_RE_MAX();
        double oldImMin = brot.getMANDELBROT_IM_MIN();
        double oldImMax = brot.getMANDELBROT_IM_MAX();

        //convert pixel pos to number range
        double oldReRange;
        double oldImRange;
        if (oldReMin < 0 && oldReMax > 0) {
            oldReRange = (-1 * oldReMin + oldReMax);
        } else if (oldReMin < 0 && oldReMax < 0) {
            oldReRange = (oldReMin - oldReMax) * -1;
        } else {
            oldReRange = (oldReMax - oldReMax);
        }

        if (oldImMin < 0 && oldImMax > 0) {
            oldImRange = (-1 * oldImMin + oldImMax);
        } else if (oldImMin < 0 && oldImMax < 0) {
            oldImRange = (oldImMin - oldImMax) * -1;
        } else {
            oldImRange = (oldImMax - oldImMax);
        }
        double rePerPixel = oldReRange / canVis.getWidth();
        double imPerPixel = oldImRange / canVis.getHeight();
        double reStart = zoomTangle.getLayoutX() * rePerPixel;
        double imStart = zoomTangle.getLayoutY() * imPerPixel;

        //get old c & ci
        double precision = Math.max(oldReRange, oldImRange);
        double cCarry = 0;
        double ciCarry = 0;
        for (double oldC = brot.getMANDELBROT_RE_MIN(), xR = 0; xR < canVis.getWidth(); oldC += precision, xR++) {
            for (double oldCi = brot.getMANDELBROT_IM_MIN(), yR = 0; yR < canVis.getHeight(); oldCi += precision, yR++) {
                if (brot.isMandelbrot()) {
                    if (yR - 1 < imStart && yR + 1 > imStart) {
                        ciCarry = oldCi;
                    }
                    if (xR - 1 < reStart && xR + 1 > reStart) {
                        cCarry = oldC;
                    }
                }
            }
        }
        paintZoom(gc, new Mandelbrot(brot.getConvergenceSteps(), reStart, zoomTangle.getWidth() * rePerPixel, imStart, zoomTangle.getHeight() * imPerPixel, brot.getZ(), brot.getZi()), oldReRange, oldImRange, cCarry, ciCarry);
    }

    @FXML
    private void zoom() {
        drawZoomBrot();
    }

    @FXML
    private void setZoomCenter(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            showZoomTangle();
            zoomTangle.setLayoutX((mouseEvent.getSceneX() + canVis.getLayoutX()) - zoomTangle.getWidth() / 2);
            zoomTangle.setLayoutY((mouseEvent.getSceneY() + canVis.getLayoutY()) - zoomTangle.getHeight() / 2);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            zoom();
        }
    }

    @FXML
    Button reset;

    @FXML
    private void drawStandardBrot() {
        paintSet(gc, new Mandelbrot(50, -2, 1, -1, 1, 0, 0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            dao.fillUserCustomSets(currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        brot = new Mandelbrot(50, MANDELBROT_RE_MIN, MANDELBROT_RE_MAX, MANDELBROT_IM_MIN, MANDELBROT_IM_MAX, 0, 0);
        Mandelbrot initialBrot = brot;
        updateStats();
        gc = canVis.getGraphicsContext2D();
        initializeColorSchemePicker();
        paintSet(gc, brot);
    }

    //TODO Fix custom color scheme boundaries
    //TODO number range + zoom


}


//Model view viewmodel architecture