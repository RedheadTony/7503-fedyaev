package ru.cft.focusstart.server;

import com.google.gson.Gson;
import ru.cft.focusstart.common.Pack;
import ru.cft.focusstart.common.PackType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private List<Connect> connects = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;
    private final Gson gson = new Gson();

    public Server() throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties.load(propertiesStream);
        }
        serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
        createMessageListener();
        createClientListener();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                serverSocket.close();
                for (Connect connect : connects) {
                    connect.getClientSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData(String data) {
        for (Connect connect : connects) {
            PrintWriter writer = connect.getWriter();
            writer.println(data);
            writer.flush();
        }
    }

    private void removeConnection(Connect connect) {
        String nickName = connect.getNickName();
        try {
            connect.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connects.remove(connect);
        if (!nickName.equals("")) {
            Pack pack = new Pack(PackType.MESSAGE, nickName + " left the chat");
            String JSON = gson.toJson(pack);
            sendData(JSON);
            sendNicknames();
        }
    }

    private void sendNicknames() {
        StringBuilder nickNames = new StringBuilder();
        for (Connect connect : connects) {
            if (!connect.getNickName().equals("")) {
                nickNames.append(connect.getNickName()).append("\n");
            }
        }
        Pack pack = new Pack(PackType.NICKS_LIST, nickNames.toString());
        String JSON = gson.toJson(pack);
        sendData(JSON);
    }

    private void createClientListener() {
        Thread clientListenerThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    connects.add(new Connect(clientSocket, reader, writer));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientListenerThread.start();
    }

    private void createMessageListener() {
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    for (Connect connect : connects) {
                        BufferedReader reader = connect.getReader();
                        if (connect.getReader().ready()) {
                            String JSON = reader.readLine();
                            Pack pack = gson.fromJson(JSON, Pack.class);
                            switch (pack.getType()) {
                                case MESSAGE:
                                    sendData(JSON);
                                    break;
                                case NICK:
                                    saveNewNickName(pack.getContent(), connect);
                                    break;
                                case CLOSE_CONNECTION:
                                    removeConnection(connect);
                            }
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

    private void saveNewNickName(String nickName, Connect currentConnect) {
        boolean nickNameExist = false;
        for (Connect connect : connects) {
            if (connect.getNickName().equals(nickName)) {
                nickNameExist = true;
                break;
            }
        }
        Pack pack;
        String JSON;
        if (!nickNameExist) {
            currentConnect.setNickName(nickName);
            pack = new Pack(PackType.NICK_SAVED_SUCCESS, "");
            JSON = gson.toJson(pack);
            PrintWriter writer = currentConnect.getWriter();
            writer.println(JSON);
            writer.flush();

            String message = nickName + " join to chat";
            pack = new Pack(PackType.MESSAGE, message);
            JSON = gson.toJson(pack);
            sendData(JSON);
            sendNicknames();
        } else {
            pack = new Pack(PackType.NICK_SAVED_ERROR, "");
            JSON = gson.toJson(pack);
            PrintWriter writer = currentConnect.getWriter();
            writer.println(JSON);
            writer.flush();
        }
    }
}
