package ru.cft.focusstart.ui;

import ru.cft.focusstart.model.Model;

public class Controller {
    private Model model;

    void startNewGame() {
        model.startGame();
    }

    void onLeftClick(int row, int column) {
        model.openCell(row, column);
    }

    void onRightClick(int row, int column) {
        model.markCell(row, column);
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
