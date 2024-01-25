package com.example.sad;

import javafx.scene.Parent;

public class NonogramGameHard extends NonogramGame{

    @Override
    public String getFXMLPath() {
        return "hardNonogram.fxml";
    }

    @Override
    public String getTitle() {
        return "Hard - Nonogram";
    }

    @Override
    public Nonogram getNonogram()
    {
        return new Nonogram(10);
    }
    @Override
    public int getmatrixSize()
    {
        return 10;
    }

    @Override
    public int getSizeCell()
    {
        return 30;
    }

    @Override
    void initializeNonogramGame(Parent root) {

        super.initializeNonogramGame(root);
    }
}
