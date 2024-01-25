package com.example.sad;

import javafx.scene.Parent;

public class SudokuGameEasy extends SudokuGame {

    @Override
    public String getTitle() {
        return "Easy - Sudoku";
    }
    @Override
    public String getFXMLPath() {
        return "easySudoku.fxml";
    }

    @Override
    public Sudoku getSudoku(){
        return new Sudoku(15);
    }

    @Override
    void initializeSudokuGame(Parent root) {
        sudoku = getSudoku();
        super.initializeSudokuGame(root);
    }
}
