package ru.cft.focusstart.client.ui;

import ru.cft.focusstart.client.model.Model;

public class Controller {
    private final Model model;

    public Controller(Model model) {
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
