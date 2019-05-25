package ru.cft.focusstart;

public class ButtonCell {
    private final int number;
    private final boolean isMined;

    private String status = "closed";

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
        if (status.equals("flagged")) {
            return;
        }
        status = "opened";
    }

    public void nextMark() {
        if (status.equals("opened")) {
            return;
        }
        if (status.equals("closed")) {
            status = "flagged";
        } else {
            status = "closed";
        }
    }

    public String getStatus() {
        return status;
    }
}
