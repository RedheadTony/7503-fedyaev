package ru.cft.focusstart.client.model;

import com.google.gson.Gson;
import ru.cft.focusstart.client.ChangeListener;
import ru.cft.focusstart.client.SetNickNameListener;
import ru.cft.focusstart.common.Pack;
import ru.cft.focusstart.common.PackType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.cft.focusstart.common.PackType.MESSAGE;

public class Model {
    private StringBuffer chatContent = new StringBuffer();
    private ChangeListener changeListener;
    private String nicks = "";
    private String nickName;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Thread messageListenerThread;
    private SetNickNameListener nickNameListener;

    private final Gson gson = new Gson();

    public Model(SetNickNameListener nickNameListener) {
        this.nickNameListener = nickNameListener;
    }

    public void connect(String host, Integer port, String nick) throws IOException {
        if (messageListenerThread != null) {
            messageListenerThread.interrupt();
        }
        if (socket != null) {
            disconnect();
        }
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        nickName = nick; //.replaceAll("\\n|\\r\\n", "")
        startMessageListener();
    }

    public void disconnect() {
        if (writer != null) {
            Pack pack = new Pack(PackType.CLOSE_CONNECTION, "");
            String JSON = gson.toJson(pack);
            writer.println(JSON);
            writer.flush();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startMessageListener() {
        messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    if (reader.ready()) {
                        String JSON = reader.readLine();
                        Pack pack = gson.fromJson(JSON, Pack.class);
                        switch (pack.getType()) {
                            case MESSAGE:
                                chatContent.append(pack.getContent()).append("\n\n");
                                if (changeListener != null) {
                                    changeListener.onChatContentChange();
                                }
                                break;
                            case NICKS_LIST:
                                nicks = pack.getContent();
                                if (changeListener != null) {
                                    changeListener.onClientListChange();
                                }
                                break;
                            case NICK_SAVED_SUCCESS:
                                nickNameListener.onSuccess();
                                break;
                            case NICK_SAVED_ERROR:
                                nickNameListener.onError();
                                break;
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
        Pack pack = new Pack(PackType.NICK, nickName);
        String JSON = gson.toJson(pack);
        writer.println(JSON);
        writer.flush();
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void sendMessage(String message) {
        if (message.equals("")) {
            return;
        }
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy HH:mm");
        Pack pack = new Pack(MESSAGE,
                dateFormat.format(currentDate) +
                        " " + nickName + ": " + message);
        String JSON = gson.toJson(pack);
        writer.println(JSON);
        writer.flush();
        changeListener.resetInput();
    }

    public String getNicks() {
        return nicks;
    }

    public String getNickName() {
        return nickName;
    }
}
