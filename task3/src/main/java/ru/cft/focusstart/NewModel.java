package ru.cft.focusstart;

import java.util.Random;

public class NewModel {
    public void main(String[] args) {
        startGame();
    }

    private static final int size = 10;

    private static final int mineCount = 10;

    private ButtonCell[][] cells = new ButtonCell[size][size];

    private boolean isWin = false;
    private boolean isLose = false;

    public void startGame() {
        int minesCounter = 0;
        int[][] mines = new int[size][size];
        Random random = new Random();
        while (minesCounter < mineCount) {
            int row = random.nextInt(size);
            int column = random.nextInt(size);
            if (mines[row][column] == 0) {
                mines[row][column] = 1;
                minesCounter++;
            }
        }

        int[][] numbers = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                numbers[row][column] = getMinesCountAround(row, column, mines);
            }
        }

//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                System.out.print(mines[i][j]);
//            }
//            System.out.println();
//        }
//
//        System.out.println("//////////////////////////////////////////////");
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                System.out.print(numbers[i][j]);
//            }
//            System.out.println();
//        }

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int number = numbers[row][column];
                boolean isMined = mines[row][column] == 1;
                cells[row][column] = new ButtonCell(number, isMined);
            }
        }
    }

    private int getMinesCountAround(int row, int column, int[][] mines) {
        int minesCounterAround = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                try {
                    minesCounterAround += mines[i][j];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("row = " + row + "; column = " + column);
                }
            }
        }
        return minesCounterAround;
    }

    public ButtonCell getButtonCell (int row, int column) throws ArrayIndexOutOfBoundsException {
        return cells[row][column];
    }
}
