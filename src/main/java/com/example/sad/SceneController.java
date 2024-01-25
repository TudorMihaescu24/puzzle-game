package com.example.sad;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController{

    private Stage stage;
    private Scene scene;
    public void switchToFrame1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuNonogram.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Difficulty Selector Nonogram");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFrame2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuSudoku.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Difficulty Selector Sudoku");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void quitFunction(ActionEvent event) {
        try {
            Platform.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
