package ru.cft.focusstart.client;

import com.google.gson.Gson;
import ru.cft.focusstart.common.Pack;

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
    private String nicks = "";
    private String nickName;

    Socket socket;// = new Socket("localhost", 1111);
    PrintWriter writer;// = new PrintWriter(socket.getOutputStream());
    BufferedReader reader;// = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    public Model(String host, String nick) throws IOException {
        socket = new Socket(host, 1111);
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        nickName = nick;
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    if (reader.ready()) {
//                        System.out.println(reader.readLine());
                        Gson gson = new Gson();
                        String JSON = reader.readLine();
                        Pack pack = gson.fromJson(JSON, Pack.class);
                        if(pack.getType().equals("message")) {
                            chatContent.append(pack.getContent()).append("\n\n");
                        } else if(pack.getType().equals("nicks")) {
                            nicks = pack.getContent();
                            changeListener.onClientListChange();
                        } else if(pack.getType().equals("saveNickNameResponse")) {
                            if(pack.getContent().equals("success")) {
                                System.out.println("Client saveNickNameResponse success");
                            } else {
                                System.out.println("Client saveNickNameResponse error");
                            }
                        }
                        if (changeListener != null) {
                            changeListener.onChatContentChange();
                        }
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

    public void sendNickName() {
        Gson gson = new Gson();
        Pack pack = new Pack("nick", nickName);
        String JSON  = gson.toJson(pack);
        System.out.println(JSON);
        writer.println(JSON);
        writer.flush();
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void sendMessage(String message) {
        if(message.equals("")) {
            return;
        }
        System.out.println("was changed: " + message);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy HH:mm");
        Gson gson = new Gson();
        Pack pack = new Pack("message", dateFormat.format(currentDate).toString() + " " + nickName + ": " + message);
        String JSON  = gson.toJson(pack);
        System.out.println(JSON);
        writer.println(JSON);
        writer.flush();
        changeListener.resetInput();
    }

    public String getNicks() {
        return nicks;
    }
}
