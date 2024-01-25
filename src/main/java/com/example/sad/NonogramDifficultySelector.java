package com.example.sad;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NonogramDifficultySelector {

    private Stage stage;
    private Scene scene;
    public void switchToEasy(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("easyNonogram.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Easy - Nonogram");
        stage.setScene(scene);
        stage.show();

        NonogramGameEasy controller = loader.getController();
        controller.initializeNonogramGame(root);
    }

    public void switchToHard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hardNonogram.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Hard - Nonogram");
        stage.setScene(scene);
        stage.show();
        NonogramGameHard controller = loader.getController();
        controller.initializeNonogramGame(root);
    }

    public void quitFunction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
