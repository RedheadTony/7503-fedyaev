package ru.cft.focusstart;

import ru.cft.focusstart.model.Model;
import ru.cft.focusstart.ui.Controller;
import ru.cft.focusstart.ui.View;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();

        Controller controller = new Controller();

        view.setController(controller);
        view.setModel(model);

        controller.setModel(model);

        view.renderBoard();
        model.startGame();
    }
}
