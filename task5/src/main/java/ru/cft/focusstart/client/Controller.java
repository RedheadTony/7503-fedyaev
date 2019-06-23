package ru.cft.focusstart.client;

public class Controller {
    private final Model model;


    public Controller(Model model) {
        this.model = model;
    }

    public void setMessage(String message) {
        model.setMessage(message);
    }

    public void sendMessage() {
        model.sendMessage();
    }
}
