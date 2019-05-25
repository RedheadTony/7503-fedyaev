package ru.cft.focusstart;

public class ButtonCell {
    private final int number;
    private final boolean isMined;

    private ButtonCellStates status = ButtonCellStates.CLOSED;

    public ButtonCell(int number, boolean isMined) {
        this.number = number;
        this.isMined = isMined;
    }

    public int getNumber() {
        return number;
    }

    public boolean getIsMined() {
        return isMined;
    }

    public void open() {
        if (status == ButtonCellStates.FLAGGED) {
            return;
        }
        status = ButtonCellStates.OPENED;
    }

    public void nextMark() {
        if (status == ButtonCellStates.OPENED) {
            return;
        }
        if (status == ButtonCellStates.CLOSED) {
            status = ButtonCellStates.FLAGGED;
        } else {
            status = ButtonCellStates.CLOSED;
        }
    }

    public ButtonCellStates getStatus() {
        return status;
    }
}
