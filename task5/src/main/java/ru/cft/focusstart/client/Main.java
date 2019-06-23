package ru.cft.focusstart.client;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller, model);
        view.start();
        view.setVisible(true);
    }
}
