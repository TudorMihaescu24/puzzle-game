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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SudokuGame extends Application {

    public Text timerText;
    public Text finishText;
    private int seconds = 0;
    private boolean stopTimer = true;
    private ImageView empty = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Empty.png"))));
    private ImageView selected = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Selected.png"))));
    private ImageView none = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Static.png"))));

    private final int[][] initialMatrix = new int[9][9];
    public int selectedRow;
    public int selectedCol;
    private StackPane selectedCell;
    public GridPane gridPane;

    public Sudoku sudoku = getSudoku();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(getFXMLPath()));
            Parent root = loader.load();

            initializeSudokuGame(root);
            printMatrix(sudoku.activeMatrix, 9);
            System.out.println();
            printMatrix(initialMatrix, 9);

            Scene scene = new Scene(root, 600, 900);

            primaryStage.setTitle(getTitle());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initializeSudokuGame(Parent root) {
        printMatrix(sudoku.matrix, 9);
        gridPane = (GridPane) root.lookup("#gridPane2");
        timerText = (Text) root.lookup("#timerCountdown");
        finishText = (Text) root.lookup("#finishText");
        finishText.setOpacity(0);
        copyMatrix();
        Button resetButton = (Button) root.lookup("#resetButton");
        resetButton.setOnAction(this::resetTimer);

        createGrid(gridPane);
        startTimer();
    }


    public void startTimer() {
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

    @FXML
    public void resetTimer(ActionEvent event) {
        stopTimer = true;
        seconds = 0;
        finishText.setOpacity(0);
        copyMatrix();
        createGrid(gridPane);
        updateTimerText();

    }

    public void createGrid(GridPane gridPane) {
        for (int i = 0; i < sudoku.matrixSize; i++) {
            for (int j = 0; j < sudoku.matrixSize; j++) {
                StackPane cell = createCell(i, j);
                gridPane.add(cell, j, i);
            }
        }
    }

    private StackPane createCell(int row, int col) {
        ImageView imageView = new ImageView();

        if (sudoku.deleteElementMatrix[row][col] == 0) {
            imageView.setImage(empty.getImage());
        } else {
            imageView.setImage(none.getImage());
        }

        imageView.setFitWidth(53);
        imageView.setFitHeight(53);

        Text text = new Text();
        if (initialMatrix[row][col] != 0) {
            text.setText(String.valueOf(initialMatrix[row][col]));
        } else {
            text.setText(" ");
        }

        text.setStyle("-fx-font-size: 25; -fx-font-family: 'Poppins SemiBold'; -fx-text-fill: '#364861';");

        StackPane cell = new StackPane();
        cell.getChildren().addAll(imageView, text);

        cell.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handleLeftClick(cell, row, col);

            }
        });

        return cell;
    }

    private void handleLeftClick(StackPane cell, int row, int col) {

        if (sudoku.deleteElementMatrix[row][col] == 0) {

            resetSelectedCell();

            ImageView imageView = (ImageView) cell.getChildren().get(0);

            if (imageView.getImage().equals(selected.getImage())) {
                resetSelectedCell();
            } else {
                System.out.println("DA");
                imageView.setImage(selected.getImage());
                selectedRow = row;
                selectedCol = col;

                selectedCell = cell;

                cell.setOnKeyPressed(this::handleKeyPress);
                cell.requestFocus();

            }
        }
    }

    private void resetSelectedCell() {
        if (selectedCell != null) {
            ImageView imageView = (ImageView) selectedCell.getChildren().get(0);
            imageView.setImage(empty.getImage());

            selectedRow = -1;
            selectedCol = -1;
            selectedCell = null;
        }
    }

    private void handleKeyPress(KeyEvent event) {

        if (event.getText().matches("[1-9]")) {
            int digit = Integer.parseInt(event.getText());
            Text text = findTextChild(selectedCell);
            text.setText(String.valueOf(digit));
            initialMatrix[selectedRow][selectedCol] = Integer.valueOf(digit);
            text.setStyle("-fx-font-size: 25; -fx-font-family: 'Poppins SemiBold'; -fx-text-fill: '#364861';");
            resetSelectedCell();
            checkMatrix(initialMatrix, sudoku.matrix, 9);
        }
    }

    private Text findTextChild(StackPane cell) {
        for (Node node : cell.getChildren()) {
            if (node instanceof Text) {
                return (Text) node;
            }
        }
        return null;
    }

    private void printMatrix(int[][] matrix, int matrixSize) {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    void launchSudokuGame(String[] args) {
        launch(args);
    }
    public void copyMatrix() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                initialMatrix[i][j] = sudoku.deleteElementMatrix[i][j];
            }
            System.out.println();
        }
    }

    private void checkMatrix(int[][] matrix1, int[][] matrix2, int matrixSize) {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return;
                }
            }
        }

        finishText.setOpacity(1);
        stopTimer = !stopTimer;
    }

    public void goToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menuSudoku.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return "";
    }

    public String getFXMLPath() {
        return "";
    }

    public Sudoku getSudoku()
    {
        return new Sudoku(30);
    }

    public SudokuGame() {
        this.empty = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Empty.png"))));
        this.selected = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Selected.png"))));
        this.none = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Static.png"))));
    }

}
