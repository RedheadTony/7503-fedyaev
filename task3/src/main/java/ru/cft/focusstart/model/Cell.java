package ru.cft.focusstart.model;

public class Cell {
    private final int minesAroundCount;
    private final boolean isMined;

    private CellState status = CellState.CLOSED;

    Cell(int minesAroundCount, boolean isMined) {
        this.minesAroundCount = minesAroundCount;
        this.isMined = isMined;
    }

    public int getMinesAroundCount() {
        return minesAroundCount;
    }

    public boolean getIsMined() {
        return isMined;
    }

    void open() {
        if (status == CellState.FLAGGED) {
            return;
        }
        if (isMined) {
            status = CellState.EXPLODED;
            return;
        }
        status = CellState.OPENED;
    }

    void mark() {
        if (status == CellState.OPENED) {
            return;
        }
        if (status == CellState.CLOSED) {
            status = CellState.FLAGGED;
        } else {
            status = CellState.CLOSED;
        }
    }

    public CellState getStatus() {
        return status;
    }
}
