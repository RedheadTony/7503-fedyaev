package ru.cft.focusstart;

import java.util.Random;

public class Model {
//    public void main(String[] args) {
//        startGame();
//    }

    private static final int size = 10;

    private static final int mineCount = 10;

    private ButtonCell[][] cells = new ButtonCell[size][size];

    private boolean isWin = false;
    private boolean isLose = false;
    private int openedCellsCounter = 0;

    public boolean getIsWin() {
        return isWin;
    }

    public boolean getIsLose() {
        return isLose;
    }

    public void setIsWin(boolean isWin) {
        this.isWin = isWin;
    }

    public void setIsLose(boolean isLose) {
        this.isLose = isLose;
    }

    public void openedCellsIncrement() {
        openedCellsCounter++;
        if (openedCellsCounter == size * size - mineCount) {
            setIsWin(true);
        }
    }

    public void startGame() {
        setIsLose(false);
        setIsWin(false);
        openedCellsCounter = 0;
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

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int number = numbers[row][column];
                boolean isMined = mines[row][column] == 1;
                cells[row][column] = new ButtonCell(number, isMined);
                System.out.println("//////////////////////////");
                System.out.println(row);
                System.out.println(column);
                System.out.println(cells[row][column].getStatus());
                System.out.println("//////////////////////////");
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
