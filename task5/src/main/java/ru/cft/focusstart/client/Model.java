package ru.cft.focusstart.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Model {
    private StringBuffer chatContent = new StringBuffer();
    private ChangeListener changeListener;

    Socket socket = new Socket("localhost", 1111);
    PrintWriter writer = new PrintWriter(socket.getOutputStream());
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    public Model() throws IOException {
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    if (reader.ready()) {
//                        System.out.println(reader.readLine());
                        chatContent.append(reader.readLine() + "\n\n");
                        changeListener.onChatContentChange();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        });
        messageListenerThread.start();
    }

    public String getChatContent() {
        return chatContent.toString();
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void sendMessage(String message) {
        if(message.equals("")) {
            return;
        }
        System.out.println("was changed: " + message);
//        chatContent.append("my_nickname: ").append(message).append("\n\n");
//        changeListener.onChatContentChange();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy HH:mm");
        writer.println(dateFormat.format(currentDate).toString() + " nick: " + message);
        writer.flush();
        changeListener.resetInput();
        message = "";
    }
}
