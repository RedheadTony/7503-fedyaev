package ru.cft.focusstart;

import java.util.Random;

public class Model {

    interface OnChangedListener {
        void onChanged(int row, int column);
    }

    private OnChangedListener onChangedListener;

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    public void syncView(int row, int column) {
        onChangedListener.onChanged(row, column);
    }

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
                syncView(row, column);
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

    public void openCell(int row, int column) {
        if (getIsWin() || getIsLose()) {
            return;
        }
        ButtonCell cell = getButtonCell(row, column);
        ButtonCellStates status = cell.getStatus();
        if (status == ButtonCellStates.OPENED) {
            return;
        }
        cell.open();
        status = cell.getStatus();
        if (cell.getIsMined() && status == ButtonCellStates.OPENED) {
            setIsLose(true);
        } else {
            openedCellsIncrement();
            checkIsWin();
        }
        syncView(row, column);
        if (!cell.getIsMined() && cell.getNumber() == 0) {
            openCellsAround(row, column);
        }
    }

    public void openCellsAround(int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                try {
                    ButtonCell cell = getButtonCell(i, j);
                    if (!cell.getIsMined() && !(cell.getStatus() == ButtonCellStates.OPENED)) {
                        openCell(i, j);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    System.err.println(e);
                }
            }
        }

    }

    public void markCell(int row, int column) {
        if (getIsWin() || getIsLose()) {
            return;
        }
        ButtonCell cell = getButtonCell(row, column);
        cell.nextMark();
        syncView(row, column);
    }
}
