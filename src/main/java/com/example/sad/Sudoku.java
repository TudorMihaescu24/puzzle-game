package com.example.sad;

import java.util.Random;

public class Sudoku {

    private int matrixFillRate;
    public int[][] matrix;
    public int[][] activeMatrix;
    public int[][]  deleteElementMatrix;
    public int matrixSize;
    private int length = 9;

    Sudoku(int matrixFillRate) {
        this.matrixSize = 9;
        this.matrixFillRate = matrixFillRate;
        matrix = new int[matrixSize][matrixSize];
        activeMatrix = new int[matrixSize][matrixSize];
        deleteElementMatrix = new int[matrixSize][matrixSize];
        initMatrix(matrixSize);
        fillSquare(matrix, 0, 0);
        fillSquare(matrix, 1, 1);
        fillSquare(matrix, 2, 2);
       if(finishMatrix(0,3))
           System.out.println("Matrix succesful build !");
       copyMatrix();
       deleteFromMatrix(matrixFillRate);
       generateActiveMatrix();

    }


    private void initMatrix(int matrixSize) {
        for (int i = 0; i < this.matrixSize; i++) {
            for (int j = 0; j < this.matrixSize; j++) {
                matrix[i][j] = 0;
            }
        }
    }
    private int[] RandomiseArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }

        return array;
    }



    private void fillSquare(int[][] matrix, int x, int y) {
        int[] array = RandomiseArray();
        int cnt = 0;

        x = x * 3;
        y = y * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i + x][j + y] = array[cnt];
                cnt++;
            }
        }
    }
    private boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < matrixSize; i++) {
            if (matrix[row][i] == num) {
                return false;
            }
        }

        for (int i = 0; i < matrixSize; i++) {
            if (matrix[i][col] == num) {
                return false;
            }
        }

        int subgridSize = (int) Math.sqrt(matrixSize);
        int subgridRow = row / subgridSize;
        int subgridCol = col / subgridSize;
        int subgridStartRow = subgridRow * subgridSize;
        int subgridStartCol = subgridCol * subgridSize;

        for (int i = subgridStartRow; i < subgridStartRow + subgridSize; i++) {
            for (int j = subgridStartCol; j < subgridStartCol + subgridSize; j++) {
                if (matrix[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean finishMatrix(int i, int j) {
        if (i == matrixSize) {
            return true;
        }

        int nextI = (j == matrixSize - 1) ? i + 1 : i;
        int nextJ = (j == matrixSize - 1) ? 0 : j + 1;

        if (matrix[i][j] == 0) {
            int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

            for (int num : array) {
                if (isValidMove(i, j, num)) {
                    matrix[i][j] = num;

                    if (finishMatrix(nextI, nextJ)) {
                        return true;
                    }

                    matrix[i][j] = 0;
                }
            }
        } else {
            if (finishMatrix(nextI, nextJ)) {
                return true;
            }
        }
        return false;
    }

    private void copyMatrix()
    {
        for(int i = 0; i < matrixSize; i++)
        {
            for(int j = 0; j < matrixSize; j++)
            {
                deleteElementMatrix[i][j] = matrix[i][j];
            }
        }
    }
    private void deleteFromMatrix(int noOfElements)
    {
        for(int i = 0; i < noOfElements; i++)
        {
            Random rand1 = new Random();
            Random rand2 = new Random();

            int col = rand1.nextInt(9);
            int row = rand2.nextInt(9);


            while (deleteElementMatrix[row][col] == 0)
            {
                col = rand1.nextInt(9);
                row = rand2.nextInt(9);
            }

            deleteElementMatrix[row][col] = 0;
        }
    }
      private void generateActiveMatrix()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(deleteElementMatrix[i][j] == 0)
                {
                    activeMatrix[i][j] = 1;
                }
                else {
                    activeMatrix[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku(10);
    }
}
