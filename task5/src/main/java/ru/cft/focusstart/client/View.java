package ru.cft.focusstart.client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;

public class View extends JFrame implements ChangeListener {
    private JTextArea chatContent;
    private JTextField input;
    private Model model;
    private JTextArea clientsList;

    private final Controller controller;
    public View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        model.setChangeListener(this);
    }

    public void start() {
        setBounds(200, 100, 900, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTextArea jtaTextAreaMessage = new JTextArea();
        chatContent = jtaTextAreaMessage;
        jtaTextAreaMessage.setBorder(
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createTitledBorder(
                                null, null,
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Verdana", 1, 11)
                        ),
                        javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );


        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);

        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);

        JTextArea clientsList = new JTextArea();
        this.clientsList = clientsList;

//        add(clientsList, "West");
//        clientsList.setPreferredSize(new Dimension(250, 400));

        clientsList.setBorder(
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createTitledBorder(
                                null, "Список активных пользователей:",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Verdana", 1, 11)
                        ),
                        javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );

        clientsList.setEditable(false);
        clientsList.setLineWrap(true);


        add(clientsList, "West");
        System.out.println(clientsList.getHeight());
        clientsList.setPreferredSize(new Dimension(250, clientsList.getHeight()));
        JScrollPane jsp2 = new JScrollPane(clientsList);
        add(jsp2, BorderLayout.LINE_END);

//        JScrollBar vertical = jsp.getVerticalScrollBar();
//        vertical.setValue( jtaTextAreaMessage.getDocument().getLength() );

//        JLabel jlNumberOfClients = new JLabel("Количество клиентов в чате: ");
//        add(jlNumberOfClients, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSendMessage = new JButton("Отправить");

        jbSendMessage.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    controller.sendMessage(input.getText());
                }
            }
        });

        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        JTextField jtfMessage = new JTextField();//"Введите ваше сообщение: "
        input = jtfMessage;

        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.sendMessage(input.getText());
            }
        };

        jtfMessage.addActionListener( action );

        bottomPanel.add(jtfMessage, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                controller.disconnect();
                e.getWindow().dispose();
            }
        });
    }

    @Override
    public void onChatContentChange() {
        System.out.println("onChatContentChange");
        chatContent.setText(model.getChatContent());
        chatContent.setCaretPosition(chatContent.getDocument().getLength());
    }

    @Override
    public void onClientListChange() {
        clientsList.setText(model.getNicks());
    }

    @Override
    public void resetInput() {
        input.setText("");
    }
}
