package ru.cft.focusstart.client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

public class View extends JFrame implements ChangeListener {
    private JTextArea chatContent;
    private JTextField input;
    private Model model;

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
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World\n\n");
//        jtaTextAreaMessage.append("Goodbye Cruel World2\n\n");


        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);

        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);

        JTextArea clientsList = new JTextArea();

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

//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3\n\n");
//
//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3\n\n");
//
//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3\n\n");
//
//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3\n\n");
//
//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3\n\n");
//
//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3\n\n");
//
//        clientsList.append("Client1 gsdf gsdg dfgdfg sdfgdsfg sdfgsdfg sdfgsdfgdf gsfdg\n\n");
//        clientsList.append("Client2\n\n");
//        clientsList.append("Client3 sfddfdfsf\n\n");

//        clientsList.setText("");

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
                    controller.sendMessage();
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
                controller.sendMessage();
            }
        };

        jtfMessage.addActionListener( action );

        DocumentListener listener = new DocumentListener() {

            public void removeUpdate(DocumentEvent event) {
//                System.out.println("removeUpdate");
//                System.out.println(jtfMessage.getText());
                controller.setMessage(jtfMessage.getText());
            }

            public void insertUpdate(DocumentEvent event) {
//                System.out.println("insertUpdate");
//                System.out.println(jtfMessage.getText());
                controller.setMessage(jtfMessage.getText());
            }

            public void changedUpdate(DocumentEvent event) {
//                System.out.println("changedUpdate");
//                System.out.println(jtfMessage.getText());
                controller.setMessage(jtfMessage.getText());
            }
        };

        jtfMessage.getDocument().addDocumentListener(listener);

//        jtfMessage.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                String text = jtfMessage.getText();
//                if(text.equals("Введите ваше сообщение: ")) {
//                    jtfMessage.setText("");
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                jtfMessage.setText("Введите ваше сообщение: ");
//            }
//        });

        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
//        JTextField jtfName = new JTextField("Введите ваше имя: ");
//        bottomPanel.add(jtfName, BorderLayout.WEST);

//        Scanner userInputReader = new Scanner(jtfMessage.getText());
//        new Thread(() -> {
//            while (true) {
//                String userInput = userInputReader.nextLine();
////                if (userInput()) {
////
////                }
//                if ("\\q".equals(userInput)) {
////                        messageListenerThread.interrupt();
////                        socket.close();
//                    break;
//                }
//                String message = userInput;
//                System.out.println(message);
////                    writer.println(message);
////                    writer.flush();
//            }
//        }).start();

    }

    @Override
    public void onChatContentChange() {
        chatContent.setText(model.getChatContent());
    }

    @Override
    public void resetInput() {
        input.setText("");
    }
}
