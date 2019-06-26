package ru.cft.focusstart.client.ui;

import ru.cft.focusstart.client.SubmitListener;
import ru.cft.focusstart.client.ui.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class ConnectForm extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel host_label, nick_label, port_label;
    private JTextField host;
    private JTextField port;
    private JTextField nick;
    private JButton submit, cancel;
    private SubmitListener submitListener;
    private Controller controller;

    public ConnectForm(SubmitListener submitListener, Controller controller) {
        this.submitListener = submitListener;
        this.controller = controller;
    }

    public void createForm() {
        host_label = new JLabel();
        host_label.setText("Host:");
        host = new JTextField();

        port_label = new JLabel();
        port_label.setText("Port:");
        port = new JTextField();

        nick_label = new JLabel();
        nick_label.setText("Nick name:");
        nick = new JTextField();

        submit = new JButton("Join to chat");

        panel = new JPanel(new GridLayout(4, 1));

        panel.add(host_label);
        panel.add(host);
        panel.add(port_label);
        panel.add(port);
        panel.add(nick_label);
        panel.add(nick);
        panel.add(new Label()); // чтобы кнопка была справа
        panel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(300, 150);
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
            showMessageDialog(null, "Поле Host обязательно для заполнения");
            return;
        }
        if(port.getText().equals("")) {
            showMessageDialog(null, "Поле Port обязательно для заполнения");
            return;
        }
        if(nick.getText().equals("")) {
            showMessageDialog(null, "Поле Nick  обязательно для заполнения");
            return;
        }
        int portNumber;
        try {
            portNumber = Integer.parseInt(port.getText());
        } catch (java.lang.NumberFormatException e) {
            showMessageDialog(null, "Поле Port должно быть числом");
            return;
        }
        submitListener.onSubmit(host.getText(), portNumber, nick.getText());
    }
}
