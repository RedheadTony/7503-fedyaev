package ru.cft.focusstart.client;

public interface SubmitListener {
    void onSubmit(String host, Integer port, String nick);
    void onFail(String errorMessage);
}
