package ru.cft.focusstart.client;

import ru.cft.focusstart.client.model.Model;
import ru.cft.focusstart.client.ui.ChatWindow;
import ru.cft.focusstart.client.ui.ConnectForm;
import ru.cft.focusstart.client.ui.Controller;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Application implements SubmitListener, SetNickNameListener {
    private Model model = new Model(this);
    private Controller controller = new Controller(model);
    private ConnectForm form = new ConnectForm(this, controller);
    public static void main(String[] args) {
          Application application = new Application();
    }

    public Application() {
        createConnectForm();
    }

    private void createConnectForm() {
        form.createForm();
        form.setVisible(true);
    }

    private void createChatWindow() {
        ChatWindow chatWindow = new ChatWindow(controller, model);
        chatWindow.start();
        chatWindow.setVisible(true);
    }

    @Override
    public void onSubmit(String host, Integer port, String nick) {
        try {
            model.connect(host, port, nick);
            model.sendNickName();
        } catch (IOException e) {
            e.printStackTrace();
            onFail("Не удалось подключиться к хосту " + host + ":" + port);
        }
    }

    @Override
    public void onFail(String errorMessage) {
        showMessageDialog(null, errorMessage);
    }

    @Override
    public void onSuccess() {
        createChatWindow();
        form.setVisible(false);
    }

    @Override
    public void onError() {
        onFail("Ник " + model.getNickName() + " уже занят");
    }
}
