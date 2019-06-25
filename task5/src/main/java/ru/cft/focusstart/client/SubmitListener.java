package ru.cft.focusstart.client;

public interface SubmitListener {
    void onSubmit(String host, String nick);
    void onFail(String errorMessage);
}
