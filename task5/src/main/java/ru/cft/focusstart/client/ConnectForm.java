package ru.cft.focusstart.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class ConnectForm extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel user_label, password_label, message;
    private JTextField host;
    private JTextField nick;
    private JButton submit, cancel;
    private SubmitListener submitListener;
    private Controller controller;

    ConnectForm(SubmitListener submitListener, Controller controller) {
        this.submitListener = submitListener;
        this.controller = controller;
    }

    void createForm() {
        user_label = new JLabel();
        user_label.setText("Host:");
        host = new JTextField();

        password_label = new JLabel();
        password_label.setText("Nick name:");
        nick = new JTextField();

        submit = new JButton("Join to chat");

        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(host);
        panel.add(password_label);
        panel.add(nick);

        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(300, 100);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                controller.disconnect();
                e.getWindow().dispose();
            }
        });
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(host.getText().equals("")) {
            showMessageDialog(null, "Поле host обязательно для заполнения");
            return;
        }
        if(nick.getText().equals("")) {
            showMessageDialog(null, "Поле nick  обязательно для заполнения");
            return;
        }
        submitListener.onSubmit(host.getText(), nick.getText());
    }
}
