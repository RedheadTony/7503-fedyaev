package ru.cft.focusstart;

public class Modal {
    private final int [] mines = {
        1,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,1,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,1
    };

    private boolean [] isOpenedItem = new boolean[81];

    private boolean [] isMarkeredItem = new boolean[81];

    private boolean gameOver = false;

    public int getItem(int index) {
        return mines[index];
    }

    public boolean getIsOpenedItem(int index) {
        return isOpenedItem[index];
    }

    public void setIsOpenedItem(int index) {
        isOpenedItem[index] = true;
    }

    public void toggleMarker(int index) {
        isMarkeredItem[index] = !isMarkeredItem[index];
    }

    public boolean getIsMarkered(int index) {
        return isMarkeredItem[index];
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
