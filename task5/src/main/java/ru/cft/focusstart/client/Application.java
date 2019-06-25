package ru.cft.focusstart.client;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Application implements SubmitListener {
    private ConnectForm form = new ConnectForm(this);
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

    private void createChatWindow(String host, String nick) throws IOException {
        Model model = new Model(host, nick);
        Controller controller = new Controller(model);
        View view = new View(controller, model);
        view.start();
        view.setVisible(true);
    }

    @Override
    public void onSubmit(String host, String name) {
        System.out.println(host);
        System.out.println(name);
        try {
            createChatWindow(host, name);
            form.setVisible(false);
        } catch (java.net.ConnectException e) {
            System.out.println("error connection");
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
            onFail("Something went wrong");
        }
    }

    @Override
    public void onFail(String errorMessage) {
        showMessageDialog(null, errorMessage);
    }
}
