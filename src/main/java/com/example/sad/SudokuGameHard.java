package com.example.sad;

import javafx.scene.Parent;

public class SudokuGameHard extends SudokuGame {

    @Override
    public String getTitle() {
        return "Hard - Sudoku";
    }
    @Override
    public String getFXMLPath() {
        return "hardSudoku.fxml";
    }

    @Override
    public Sudoku getSudoku(){
        return new Sudoku(30);
    }

    @Override
    void initializeSudokuGame(Parent root) {
        sudoku = getSudoku();
        super.initializeSudokuGame(root);

    }
}
