package ru.cft.focusstart.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectForm extends JFrame implements ActionListener {
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField host;
    JTextField nick;
    JButton submit, cancel;
    SubmitListener submitListener;
    public ConnectForm(SubmitListener submitListener) {
        this.submitListener = submitListener;
    }

    public void createForm() {
        user_label = new JLabel();
        user_label.setText("Host:");
        host = new JTextField();

        // Password

        password_label = new JLabel();
        password_label.setText("Nick name:");
        nick = new JTextField();

        // Submit

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

        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("gg");
        submitListener.onSubmit(host.getText(), nick.getText());
    }
}
