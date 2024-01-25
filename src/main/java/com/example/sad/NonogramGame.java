package com.example.sad;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class NonogramGame extends Application {
    private final ImageView blank = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("blank.png"))));
    private final ImageView delete = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("delete.png"))));
    private final ImageView fill = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("fill.png"))));
    private boolean stopTimer = true;
    private Text finishText;
    private Text timerText;
    private int seconds = 0;

    public int sizeCell = getSizeCell();
    private GridPane gridPane;
    private final int matrixSize = getmatrixSize();
    private final Nonogram nonogram = getNonogram();
    private final int[][] initialMatrix = new int[matrixSize][matrixSize];

    void initializeNonogramGame(Parent root) {
        displayMatrix(nonogram.matrix);

        gridPane = (GridPane) root.lookup("#gridPane");
        timerText = (Text) root.lookup("#timerCountdown");
        finishText = (Text) root.lookup("#finishText");
        finishText.setOpacity(0);

        Button resetButton = (Button) root.lookup("#resetButton");
        resetButton.setOnAction(this::resetTimer);

        populateGrid();
        createGrid(gridPane);
        startTimer();
    }

    @FXML
    private void resetTimer(ActionEvent event) {
        stopTimer = true;
        seconds = 0;
        finishText.setOpacity(0);
        initializeInitialMatrix();
        createGrid(gridPane);
        updateTimerText();
    }
    private String vectorToStringCol(Vector<Integer> vector) {
        StringBuilder result = new StringBuilder();

        if(vector.isEmpty())
        {
            return "0";
        }

        for (Integer value : vector) {
            result.append(value).append("  ");
        }
        return result.toString().trim();
    }
    private String vectorToStringRow(Vector<Integer> vector) {
        StringBuilder result = new StringBuilder();

        if(vector.isEmpty())
        {
            return "0";
        }

        for (Integer value : vector) {
            result.append(value).append(" ");
        }
        return result.toString().trim();
    }
    private void populateGrid() {
        int matrixSize = nonogram.matrixSize;

        for (int i = 0; i < matrixSize; i++) {
            Label label = (Label) gridPane.lookup("#labelr_" + i);
            label.setText(" " + vectorToStringRow(nonogram.row.get(i)));
        }

        for (int j = 0; j < matrixSize; j++) {
            Label label = (Label) gridPane.lookup("#labelc_" + j);
            label.setText(" " + vectorToStringCol(nonogram.col.get(j)));
        }
    }
    private void createGrid(GridPane gridPane) {
        initializeInitialMatrix();
        GridPane gridPane2 = (GridPane) gridPane.lookup("#gridPane2");
        for (int i = 0; i < matrixSize ; i++) {
            for (int j = 0; j < matrixSize; j++) {
                ImageView cell = createCell(i, j);
                gridPane2.add(cell, j, i);
            }
        }
    }
    private void initializeInitialMatrix() {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                initialMatrix[i][j] = 0;
            }
        }
    }
    private ImageView createCell(int row, int col) {

        ImageView cell = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("blank.png"))));

        cell.setFitWidth(sizeCell);
        cell.setFitHeight(sizeCell);

        cell.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handleLeftClick(cell, row, col);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                handleRightClick(cell, row, col);
            }
        });

        return cell;
    }
    private void handleLeftClick(ImageView cell, int row, int col) {
        if(initialMatrix[row][col] == 1)
        {
            initialMatrix[row][col] = 0;
            cell.setImage(blank.getImage());
            checkAndDisplayMatrix();
        }
        else
        {
            initialMatrix[row][col] = 1;
            cell.setImage(fill.getImage());
            checkAndDisplayMatrix();
        }
    }
    private void handleRightClick(ImageView cell, int row, int col) {
        if(delete.getImage() == cell.getImage())
        {
            initialMatrix[row][col] = 0;
            cell.setImage(blank.getImage());
            checkAndDisplayMatrix();
        }
        else
        {
            initialMatrix[row][col] = 0;
            cell.setImage(delete.getImage());
            checkAndDisplayMatrix();
        }
    }
    private void checkAndDisplayMatrix() {
        if (checkMatrix()) {
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect");
        }
        displayMatrix(initialMatrix);
    }
    private boolean checkMatrix() {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (initialMatrix[i][j] != nonogram.matrix[i][j]) {
                    return false;
                }
            }
        }
        finishText.setOpacity(1);
        stopTimer = !stopTimer;
        return true;
    }
    private void displayMatrix(int[][] matrix) {
        System.out.println("Matrix:");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    private void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(stopTimer)
            {
                seconds++;
                updateTimerText();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerText() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        String time = String.format("%02d:%02d", minutes, remainingSeconds);
        timerText.setText(time);
    }

    public void goToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menuNonogram.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(getFXMLPath()));
            Parent root = loader.load();

            initializeNonogramGame(root);

            Scene scene = new Scene(root, 600, 900);

            primaryStage.setTitle(getTitle());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getFXMLPath() {
        return "";
    }

    public String getTitle() {
        return "";
    }

    public Nonogram getNonogram()
    {
        return null;
    }

    public int getmatrixSize()
    {
        return 0;
    }

    public int getSizeCell()
    {
        return 0;
    }
}
