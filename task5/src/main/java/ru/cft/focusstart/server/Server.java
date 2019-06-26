package ru.cft.focusstart.server;

import com.google.gson.Gson;
import ru.cft.focusstart.common.Pack;
import ru.cft.focusstart.common.PackTypes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private List<Connect> connects = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;

    public Server() throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties.load(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
        createMessageListener();
        createClientListener();

    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
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
            Pack pack = new Pack(PackTypes.MESSAGE, nickName + " left the chat");
            Gson gson = new Gson();
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
        Gson gson = new Gson();
        Pack pack = new Pack(PackTypes.NICKS_LIST, nickNames.toString());
        String JSON = gson.toJson(pack);
        sendData(JSON);
    }

    private void createClientListener() {
        Thread clientListenerThread = new Thread(() -> {
            boolean interrupted = false;
            Gson gson = new Gson();
            while (!interrupted) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    connects.add(new Connect(clientSocket, reader, writer));
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
        clientListenerThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                clientListenerThread.interrupt();
                serverSocket.close();
                for (Connect connect : connects) {
                    connect.getClientSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private void createMessageListener() {
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            Gson gson = new Gson();
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
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                messageListenerThread.interrupt();
                serverSocket.close();
                for (Connect connect : connects) {
                    connect.getClientSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private void saveNewNickName(String nickName, Connect currentConnect) {
        boolean nickNameExist = false;
        for (Connect connect : connects) {
            if (connect.getNickName().equals(nickName)) {
                nickNameExist = true;
                break;
            }
        }
        Gson gson = new Gson();
        Pack pack;
        String JSON;
        if (!nickNameExist) {
            currentConnect.setNickName(nickName);
            pack = new Pack(PackTypes.NICK_SAVED_SUCCESS, "");
            JSON = gson.toJson(pack);
            PrintWriter writer = currentConnect.getWriter();
            writer.println(JSON);
            writer.flush();

            String message = nickName + " join to chat";
            pack = new Pack(PackTypes.MESSAGE, message);
            JSON = gson.toJson(pack);
            sendData(JSON);
            sendNicknames();
        } else {
            pack = new Pack(PackTypes.NICK_SAVED_ERROR, "");
            JSON = gson.toJson(pack);
            PrintWriter writer = currentConnect.getWriter();
            writer.println(JSON);
            writer.flush();
        }
    }
}
