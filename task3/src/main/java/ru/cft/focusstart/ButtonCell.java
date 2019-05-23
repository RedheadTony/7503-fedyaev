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
        System.out.println("nextMark");
        if (status.equals("opened")) {
            return;
        }
        System.out.println(status.equals("closed"));
        if (status.equals("closed")) {
            status = "flagged";
            System.out.println("FLAGGED");
        } else {
            status = "closed";
            System.out.println("CLOSED");
        }
    }

    public String getStatus() {
        return status;
    }
}
