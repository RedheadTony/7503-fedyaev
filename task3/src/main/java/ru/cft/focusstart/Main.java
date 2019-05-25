package ru.cft.focusstart;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();

        Controller controller = new Controller();

        view.setController(controller);
        view.setModel(model);

        controller.setModel(model);
        controller.setView(view);

        view.renderBoard();
        model.startGame();
    }
}
