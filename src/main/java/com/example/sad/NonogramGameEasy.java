package com.example.sad;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Objects;

public class NonogramGameEasy extends NonogramGame {
    @Override
    public String getFXMLPath() {
        return "easyNonogram.fxml";
    }

    @Override
    public String getTitle() {
        return "Easy - Nonogram";
    }

    @Override
    public Nonogram getNonogram()
    {
        return new Nonogram(5);
    }
        @Override
        public int getmatrixSize()
        {
            return 5;
        }
    @Override
    public int getSizeCell()
    {
        return 58;
    }

    @Override
    void initializeNonogramGame(Parent root) {

        super.initializeNonogramGame(root);
    }

}
