package ru.cft.focusstart.client;

public class Controller {
    private final Model model;

    Controller(Model model) {
        this.model = model;
    }

    void sendMessage(String message) {
        model.sendMessage(message);
    }

    void disconnect() {
        model.disconnect();
    }

    public void sendNickName() {
        model.sendNickName();
    }
}
