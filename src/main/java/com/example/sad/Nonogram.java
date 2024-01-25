package com.example.sad;

import java.util.Random;
import java.util.Vector;

public class Nonogram{

    public int matrixSize = 10;
    public int[][] matrix = new int[matrixSize][matrixSize];
    public Vector<Vector<Integer>> col;
    public Vector<Vector<Integer>> row;
    public int[][] getMatrix(int matrixSize)
    {
        Random random = new Random();

        int[][] matrix = new int[matrixSize][matrixSize];

        for(int i = 0; i < matrixSize; i++)
        {
            for(int j = 0; j < matrixSize; j++)
            {
                int rand = random.nextInt(10);
                matrix[i][j] = rand % 2;
            }
        }

        return matrix;
    }
    private void getColumn(int matrixSize, int[][] matrix)
    {
        int cnt = 0;
        for(int i = 0; i < matrixSize; i++)
        {
            cnt = 0;
            Vector<Integer> auxiliar;
            auxiliar = new Vector<>();

            for(int j = 0; j < matrixSize; j++)
            {
                if(matrix[i][j] == 1)
                {
                    cnt++;
                }
                else
                {
                    if(cnt != 0)
                    {
                        auxiliar.add(cnt);
                    }
                    cnt = 0;
                }
            }
            if(cnt != 0)
            {
                auxiliar.add(cnt);
            }
            col.add(auxiliar);
        }
    }
    private void getRow(int matrixSize, int[][] matrix)
    {
        int cnt = 0;
        for(int i = 0; i < matrixSize; i++)
        {
            cnt = 0;
            Vector<Integer> auxiliar;
            auxiliar = new Vector<>();

            for(int j = 0; j < matrixSize; j++)
            {
                if(matrix[j][i] == 1)
                {
                    cnt++;
                }
                else
                {
                    if(cnt != 0)
                    {
                        auxiliar.add(cnt);
                    }
                    cnt = 0;
                }
            }
            if(cnt != 0)
            {
                auxiliar.add(cnt);
            }
            row.add(auxiliar);
        }
    }
    public void displayNonogram() {
        System.out.println("Matrix:");
        displayMatrix();
        System.out.println("\nColumns:");
        displayVector(col);
        System.out.println("\nRows:");
        displayVector(row);
    }
    private void displayMatrix() {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    private void displayVector(Vector<Vector<Integer>> vector) {
        for (Vector<Integer> row : vector) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    public Nonogram(int matrixSize) {
        this.matrixSize = matrixSize;
        col = new Vector<>();
        row = new Vector<>();
        this.matrix = getMatrix(matrixSize);
        getRow(matrixSize, matrix);
        getColumn(matrixSize, matrix);
    }

    public static void main(String[] args) {
        Nonogram nonogram = new Nonogram(10);
        nonogram.displayNonogram();
    }
}

