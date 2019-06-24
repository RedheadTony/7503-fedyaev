package ru.cft.focusstart.client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Model model = null;
        try {
            model = new Model();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = new Controller(model);
        View view = new View(controller, model);
        view.start();
        view.setVisible(true);
    }
}
