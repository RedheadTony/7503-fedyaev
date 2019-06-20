package ru.cft.focusstart.model;

import ru.cft.focusstart.ChangeListener;
import ru.cft.focusstart.Settings;

import java.util.Random;

public class Model {

    private ChangeListener changeListener;

    public void setOnChangedListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    private final Settings settings = Settings.getInstance();

    private Cell[][] cells = new Cell[settings.getSize()][settings.getSize()];

    private boolean isWin = false;
    private boolean isLose = false;
    private int openedCellsCounter = 0;

    private void checkIsWin() {
        int size = settings.getSize();
        int mineCount = settings.getMineCount();
        if (openedCellsCounter == size * size - mineCount) {
            isWin = true;
            changeListener.onWin();
        }
    }

    public void startGame() {
        int size = settings.getSize();
        int mineCount = settings.getMineCount();
        isLose = false;
        isWin = false;
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

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int minesCountAround = getMinesCountAround(row, column, mines);
                boolean isMined = mines[row][column] == 1;
                cells[row][column] = new Cell(minesCountAround, isMined);
                changeListener.onChanged(row, column);
            }
        }
    }

    private int getMinesCountAround(int row, int column, int[][] mines) {
        int minesCounterAround = 0;
        int size = settings.getSize();

        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > size - 1) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j > size - 1) continue;
                minesCounterAround += mines[i][j];
            }
        }
        return minesCounterAround;
    }

    public Cell getButtonCell(int row, int column) throws ArrayIndexOutOfBoundsException {
        return cells[row][column];
    }

    public void openCell(int row, int column) {
        if (isWin || isLose) {
            return;
        }
        Cell cell = getButtonCell(row, column);
        CellState status = cell.getStatus();
        if (status == CellState.OPENED || status == CellState.FLAGGED) {
            return;
        }
        cell.open();
        status = cell.getStatus();
        if (status == CellState.EXPLODED) {
            isLose = true;
            changeListener.onChanged(row, column);
            changeListener.onDefeat();
        } else {
            openedCellsCounter++;
            checkIsWin();
        }
        changeListener.onChanged(row, column);
        if (cell.getMinesAroundCount() == 0) {
            openCellsAround(row, column);
        }
    }

    private void openCellsAround(int row, int column) {
        int size = settings.getSize();

        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > size - 1) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j > size - 1) continue;
                Cell cell = getButtonCell(i, j);
                if (!cell.getIsMined() && !(cell.getStatus() == CellState.OPENED || cell.getStatus() == CellState.FLAGGED)) {
                    openCell(i, j);
                }
            }
        }

    }

    public void markCell(int row, int column) {
        if (isWin || isLose) {
            return;
        }
        Cell cell = getButtonCell(row, column);
        cell.mark();
        changeListener.onChanged(row, column);
    }
}
