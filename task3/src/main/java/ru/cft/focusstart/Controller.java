package ru.cft.focusstart;

public class Controller {
    private Model model;
    private View view;

    public void startNewGame() {
        model.startGame();
        view.resetField();
    }

    public void onLeftClick(int row, int column) {
        if (model.getIsWin() || model.getIsLose()) {
            return;
        }
        ButtonCell cell = model.getButtonCell(row, column);
        String status = cell.getStatus();
        if (status.equals("opened")) {
            return;
        }
        cell.open();
        status = cell.getStatus();
        if (cell.getIsMined() && status.equals("opened")) {
            model.setIsLose(true);
        } else {
            model.openedCellsIncrement();
            model.checkIsWin();
        }
        view.syncWithModel(row, column);
        if (!cell.getIsMined() && cell.getNumber() == 0) {
            openCellsAround(row, column);
        }
    }

    public void openCellsAround(int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                try {
                    ButtonCell cell = model.getButtonCell(i, j);
                    if (!cell.getIsMined() && !cell.getStatus().equals("opened")) {
                        onLeftClick(i, j);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    System.err.println(e);
                }
            }
        }

    }

    public void onRightClick(int row, int column) {
        if (model.getIsWin() || model.getIsLose()) {
            return;
        }
        ButtonCell cell = model.getButtonCell(row, column);
        cell.nextMark();
        view.syncWithModel(row, column);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }
}
