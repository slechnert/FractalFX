package slechnert;


import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerVisualizer implements Initializable {
    public double MANDELBROT_RE_MIN = -2.7;
    public double MANDELBROT_RE_MAX = 0.5;
    public double MANDELBROT_IM_MIN = -1.1;
    public double MANDELBROT_IM_MAX = 1.1;


    public double JULIA_RE_MIN = -1.5;
    public double JULIA_RE_MAX = 1.5;
    public double JULIA_IM_MIN = -1.5;
    public double JULIA_IM_MAX = 1.5;

    double factorR;
    double factorG;
    double factorB;
    public Mandelbrot brot;
    GraphicsContext gc;

    private User currentUser;
    public List<CustomRGB> allCustomRGBS;
    private final DAO dao;

    public ControllerVisualizer() {
        this.dao = new DAO();
    }

    public void fillAllRGB() {

    }

    @FXML
    private ChoiceBox customSetLoader;

    private void initializeCustomSetLoader() {
//        fillCustomSetStrings();
        customSetLoader.getItems().addAll(currentUser.customSetList);
//        customSetLoader.getSelectionModel().select(currentUser.customSetList(0));
//        customSetLoader.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> brot.setColorScheme((Mandelbrot.ColorScheme) newV)));
//        customSetLoader.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> System.out.println("dafuq")));
    }

//    public void fillCustomSetStrings(){
//           for(CustomSet cs : currentUser.customSetList){
//               customSetStrings.add(cs.getSet_name());
//           }
//    }

    @FXML
    public Button customSaveButton;

    public int getUniquefractalID() {
        int fractalID = 1;
        for (int i = 2; i <= 999; i++) {
            for (Mandelbrot mb : currentUser.customBrote) {
                if (mb.fractal_ID == i) {
                    fractalID++;
                }
            }
        }
        return fractalID;
    }

    //TODO null felder logik einbauen
    public boolean isUniqueRGB() {
        double testR;
        double testG;
        double testB;
        if ((customR.getText()).equals("")) {
            testR = brot.getR_factor();
        } else {
            testR = Double.parseDouble(customR.getText());
        }
        for (CustomRGB rgb : allCustomRGBS) {
            if ((rgb.getR_factor() != testR && rgb.getG_factor() != Double.parseDouble(customG.getText()) && rgb.getB_factor() != Double.parseDouble(customB.getText()))) {
                return true;
            }
        }
        return false;
    }

    public int getUniqueCustomRGBID() {
        int customRGB_ID = 1;
        for (int i = 2; i <= allCustomRGBS.size(); i++) {
            for (CustomRGB rgb : allCustomRGBS) {
                if (customRGB_ID == rgb.customRGB_ID) {
                    customRGB_ID++;
                }
            }
        }
        return customRGB_ID;
    }

    @FXML
    public void save() throws SQLException {
        if (!currentUser.customBrote.contains(brot)) {
            brot.setFractal_ID(getUniquefractalID());
        } else {
            return;
        }
        if (isUniqueRGB()) {
            brot.setCustomRGB_ID(getUniqueCustomRGBID());
        }
        dao.addCustomRGB(new CustomRGB(brot.customRGB_ID, Double.parseDouble(customR.getText()), Double.parseDouble(customR.getText()), Double.parseDouble(customR.getText())));
        allCustomRGBS = dao.getAllCustomRGB();
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
        paintSet(canVis.getGraphicsContext2D(), brot);
    }

    @FXML
    public ChoiceBox colorSchemePicker;

    private void initializeColorSchemePicker() {
        colorSchemePicker.getItems().addAll(Mandelbrot.ColorScheme.values());
        colorSchemePicker.getSelectionModel().select(brot.getColorScheme());
        colorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> brot.setColorScheme((Mandelbrot.ColorScheme) newV)));
        colorSchemePicker.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldV, newV) -> paintSet(gc, brot)));
    }

    @FXML
    public TextField zTF;

    @FXML
    public TextField ziTF;

    @FXML
    public void setZ() {
        double newZ = Double.parseDouble(zTF.getText());
        if (newZ <= 1 && newZ >= -1) {
            brot.setZ(newZ);
            paintSet(canVis.getGraphicsContext2D(), brot);
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
            paintSet(canVis.getGraphicsContext2D(), brot);
        } else {
            zTF.clear();
            ziTF.setText("NOPE");
        }
    }

    @FXML
    public TextField convTF;

    @FXML
    public void convRedraw() {
        hideZoomTangle();
        int newConv = 50;
        if (convTF.getText().equals("")) {
        } else {
            newConv = Integer.parseInt(convTF.getText());
        }
        brot.setConvergenceSteps(newConv);
        paintSet(canVis.getGraphicsContext2D(), brot);
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
                brot = new Mandelbrot(50, JULIA_RE_MIN, JULIA_RE_MAX, JULIA_IM_MIN, JULIA_IM_MAX, 0.3, -0.5);
            } else if (Integer.parseInt(convTF.getText()) >= 1 && Integer.parseInt(convTF.getText()) <= 1000) {
                brot = new Mandelbrot(Integer.parseInt(convTF.getText()), JULIA_RE_MIN, JULIA_RE_MAX, JULIA_IM_MIN, JULIA_IM_MAX, 0.3, -0.5);
            } else {
                brot = new Mandelbrot(50, JULIA_RE_MIN, JULIA_RE_MAX, JULIA_IM_MIN, JULIA_IM_MAX, 0.3, -0.5);
            }
            zTF.setDisable(false);
            ziTF.setDisable(false);
        } else {
            gc.clearRect(0, 0, canVis.getWidth(), canVis.getHeight());
            if (convTF.getText().equals("")) {
                brot = new Mandelbrot(50, brot.MANDELBROT_RE_MIN, brot.MANDELBROT_RE_MAX, brot.MANDELBROT_IM_MIN, brot.MANDELBROT_IM_MAX, 0, 0);
            } else if (Integer.parseInt(convTF.getText()) >= 1 && Integer.parseInt(convTF.getText()) <= 1000) {
                brot = new Mandelbrot(Integer.parseInt(convTF.getText()), brot.MANDELBROT_RE_MIN, brot.MANDELBROT_RE_MAX, brot.MANDELBROT_IM_MIN, brot.MANDELBROT_IM_MAX, 0, 0);
            } else {
                brot = new Mandelbrot(50, brot.MANDELBROT_RE_MIN, brot.MANDELBROT_RE_MAX, brot.MANDELBROT_IM_MIN, brot.MANDELBROT_IM_MAX, 0, 0);
            }
            zTF.setDisable(true);
            ziTF.setDisable(true);
        }
        brot.setConvergenceColor(colorPicker.getValue());
        paintSet(canVis.getGraphicsContext2D(), brot);
        updateStats();
    }

    //pixelwriter draw
    private void paintSet(GraphicsContext ctx, Mandelbrot brot) {
        double precision = Math.max((brot.MANDELBROT_RE_MAX - brot.MANDELBROT_RE_MIN) / canVis.getWidth(), (brot.MANDELBROT_IM_MAX - brot.MANDELBROT_IM_MIN) / canVis.getHeight());
        double convergenceValue;
        PixelWriter p = ctx.getPixelWriter();

        for (double c = brot.MANDELBROT_RE_MIN, xR = 0; xR < canVis.getWidth(); c += precision, xR++) {
            for (double ci = brot.MANDELBROT_IM_MIN, yR = 0; yR < canVis.getHeight(); ci += precision, yR++) {
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

        if ((customR.getText()).equals("")) {
            factorR = brot.getR_factor();
        } else {
            if (Double.parseDouble(customR.getText()) >= 0 && Double.parseDouble(customR.getText()) <= 10) {
                factorR = Double.parseDouble(customR.getText());
            }
        }
        if ((customG.getText()).equals("")) {
            factorG = brot.getG_factor();
        } else {
            if (Double.parseDouble(customG.getText()) >= 0 && Double.parseDouble(customG.getText()) <= 10) {
                factorG = Double.parseDouble(customG.getText());
            }
        }
        if ((customB.getText()).equals("")) {
            factorB = brot.getB_factor();
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
        paintSet(canVis.getGraphicsContext2D(), brot);
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


    //TODO for zoom get x/y and c/ci at that point to calc new RE / IM
    //TODO understand how old c/ci correlate with new drawing

    //ZoomDraw
    private void paintZoom(GraphicsContext ctx, Mandelbrot zoombrot, double precisionX, double precisionY) {
        PixelWriter p = ctx.getPixelWriter();
        double convergenceValue;
        double precision = Math.max(precisionX, precisionY);
        for (double c = zoombrot.MANDELBROT_RE_MIN, xR = 0; xR < canVis.getWidth(); c += precision, xR++) {
            for (double ci = zoombrot.MANDELBROT_IM_MIN, yR = 0; yR < canVis.getHeight(); ci += precision, yR++) {
                if (zoombrot.isMandelbrot()) {
                    convergenceValue = checkConvergence(ci, c, 0, 0, zoombrot.getConvergenceSteps());
                } else { //is Julia
                    convergenceValue = checkConvergence(zoombrot.getZi(), zoombrot.getZ(), ci, c, zoombrot.getConvergenceSteps());
                }
                double t1 = convergenceValue / zoombrot.getConvergenceSteps(); //(50.0 .. )
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);
                Color color;
                if (convergenceValue != zoombrot.getConvergenceSteps()) {
                    //Set colorScheme
                    color = getDistortedColorScheme(c1, c2);
                } else {
                    color = zoombrot.getConvergenceColor();
                }
                if (zoombrot.isMandelbrot()) {
                    p.setColor((int) xR, (int) yR, color);
                } else {
                    p.setColor((int) xR, (int) yR, color);
                }
            }
        }
        this.brot = zoombrot;
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
        int oldConvergenceSteps = brot.getConvergenceSteps();
        double oldZ = brot.getZ();
        double oldZi = brot.getZi();

        //convert pixel pos to number range
        double oldReRange;
        double oldImRange;
        //TODO remin too low, remax to low, shift towards -x

        oldReRange = oldReMax - oldReMin;
        oldImRange = oldImMax - oldImMin;

        double rePerPixel = oldReRange / canVis.getWidth();
        double imPerPixel = oldImRange / canVis.getHeight();
        double newReMin = ((zoomTangle.getLayoutX() - canVis.getLayoutX()) * rePerPixel) + oldReMin;
        double newImMin = ((zoomTangle.getLayoutY() - canVis.getLayoutY()) * imPerPixel) + oldImMin;
//        double newReMin = oldReMin+oldReRange/4;
//        double newImMin = oldImMin+oldImRange/4;
//        double newReMax = newReMin + oldReRange / 4;
//        double newImMax = newImMin + oldImRange / 4;
        double newReMax = newReMin + zoomTangle.getWidth() * rePerPixel;
        double newImMax = newImMin + zoomTangle.getHeight() * imPerPixel;


        brot = new Mandelbrot(oldConvergenceSteps, newReMin, newReMax, newImMin, newImMax, oldZ, oldZi);
//        double precisionX = (oldReRange / 4) / canVis.getWidth();
//        double precisionY = (oldImRange / 4) / canVis.getHeight();
        double precisionX = (newReMax - newReMin) / canVis.getWidth();
        double precisionY = (newImMax - newImMin) / canVis.getHeight();
        paintZoom(canVis.getGraphicsContext2D(), brot, precisionX, precisionY);
        hideZoomTangle();
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
            if (zoomTangle.isDisabled()) {
                drawStandardBrot();
            } else {
                zoom();
            }
        }
    }

    @FXML
    private void drawStandardBrot() {
        brot = new Mandelbrot(50, -2.5, .5, -1.1, 1.1, 0, 0);
        if (isJulia.isSelected()) {
            isJulia.setSelected(false);
        }
        paintSet(canVis.getGraphicsContext2D(), brot);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allCustomRGBS = dao.getAllCustomRGB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        currentUser = dao.getUser("Simon");
        brot = new Mandelbrot(50, MANDELBROT_RE_MIN, MANDELBROT_RE_MAX, MANDELBROT_IM_MIN, MANDELBROT_IM_MAX, 0, 0);
        gc = canVis.getGraphicsContext2D();
        initializeCustomSetLoader();
        initializeColorSchemePicker();
        paintSet(gc, brot);
        updateStats();
        try {
            dao.syncCustomSet(currentUser);
        } catch (SQLException throwables) {
            System.out.println("NONONO");
            throwables.printStackTrace();
        }
    }

    //TODO Fix custom color scheme boundaries
    //TODO number range + zoom

}


//Model view viewmodel architecture