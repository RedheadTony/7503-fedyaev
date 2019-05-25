package ru.cft.focusstart;

import java.util.Random;

public class Model {

    private static final Settings settings = new Settings();

    private ButtonCell[][] cells = new ButtonCell[settings.getSize()][settings.getSize()];

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

    public void checkIsWin() {
        int size = settings.getSize();
        int mineCount = settings.getMineCount();
        if (openedCellsCounter == size * size - mineCount) {
            setIsWin(true);
        }
    }

    public void openedCellsIncrement() {
        openedCellsCounter++;
    }

    public void startGame() {
        int size = settings.getSize();
        int mineCount = settings.getMineCount();
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
//                    System.err.println("row = " + row + "; column = " + column);
                }
            }
        }
        return minesCounterAround;
    }

    public ButtonCell getButtonCell(int row, int column) throws ArrayIndexOutOfBoundsException {
        return cells[row][column];
    }
}
