package ru.cft.focusstart;

public class Controller {
    private Model model;

    public void startNewGame() {
        model.startGame();
    }

    public void onLeftClick(int row, int column) {
        model.openCell(row, column);
    }

    public void onRightClick(int row, int column) {
        model.markCell(row, column);
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
