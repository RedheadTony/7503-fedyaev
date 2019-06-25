package ru.cft.focusstart.client;

public interface ChangeListener {
    void onChatContentChange();
    void onClientListChange();
    void resetInput();
}
