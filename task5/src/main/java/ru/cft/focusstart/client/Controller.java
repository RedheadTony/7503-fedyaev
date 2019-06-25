package ru.cft.focusstart.client;

public class Controller {
    private final Model model;


    public Controller(Model model) {
        this.model = model;
    }

    public void sendMessage(String message) {
        model.sendMessage(message);
    }

    public void sendNickName() {
        model.sendNickName();
    }
}
