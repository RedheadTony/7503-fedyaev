package ru.cft.focusstart.client;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Application implements SubmitListener, SetNickNameListener {
    private Model model = new Model(this);
    Controller controller = new Controller(model);
    private ConnectForm form = new ConnectForm(this, controller);
    public static void main(String[] args) {
//        Model model = null;
//        try {
//            model = new Model();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Controller controller = new Controller(model);
//        View view = new View(controller, model);
//        view.start();
//        view.setVisible(true);
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
        View view = new View(controller, model);
        view.start();
        view.setVisible(true);
    }

    @Override
    public void onSubmit(String host, String nick) {
        try {
            model.connect(host, nick);
            model.sendNickName();
        } catch (IOException e) {
            e.printStackTrace();
            onFail("Не удалось подключиться к хосту " + host);
        }
//        System.out.println(host);
//        System.out.println(nick);
//        createChatWindow(host, nick);
//        form.setVisible(false);
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
        onFail("Выбранный ник уже занят");
    }
}
