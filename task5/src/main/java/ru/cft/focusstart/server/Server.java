package ru.cft.focusstart.server;

import com.google.gson.Gson;
import ru.cft.focusstart.common.Pack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
//    private List<PrintWriter> writers = new CopyOnWriteArrayList<>();
//    private List<Socket> clients = new CopyOnWriteArrayList<>();
//    private List<BufferedReader> readers = new CopyOnWriteArrayList<>();
    private  List<Connect> connects = new CopyOnWriteArrayList<>();
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
        System.out.println("send data " + data);
        for (Connect connect : connects) {
            PrintWriter writer = connect.getWriter();
            writer.println(data);
            writer.flush();
        }
    }

    private void removeConnection(Connect connect) {
        System.out.println("remove " + connect.getNickName());
        Pack pack = new Pack("message", connect.getNickName() + " left the chat");
        try {
            connect.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connects.remove(connect);
        Gson gson = new Gson();
        String JSON  = gson.toJson(pack);
        sendData(JSON);
        sendNicknames();
    }

    private void sendNicknames() {
        StringBuilder nickNames = new StringBuilder();
        for (Connect connect : connects) {
            System.out.println("nick " + connect.getNickName());
            if(!connect.getNickName().equals("")) {
                nickNames.append(connect.getNickName()).append("\n");
            }
        }
        Gson gson = new Gson();
        Pack pack = new Pack("nicks", nickNames.toString());
        String JSON  = gson.toJson(pack);
        sendData(JSON);
    }

    private void createClientListener() {
        Thread clientListenerThread = new Thread(() -> {
            boolean interrupted = false;
            Gson gson = new Gson();
            while (!interrupted) {
                try {
                    System.out.println("listen client");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println(clientSocket.getInetAddress().getHostName());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    connects.add(new Connect(clientSocket, reader, writer));
//                    clients.add(clientSocket);
//                    readers.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
//                    writers.add(new PrintWriter(clientSocket.getOutputStream()));
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
                    String message = null;
                    for (Connect connect : connects) {
                        BufferedReader reader = connect.getReader();
                        if (connect.getReader().ready()) {
                            String JSON = reader.readLine();
                            Pack pack = gson.fromJson(JSON, Pack.class);
                            System.out.println("get data");
                            System.out.println(JSON);
                            System.out.println(pack.getType());
                            if(pack.getType().equals("message")) {
                                message = pack.getContent();
                                sendData(JSON);
                            } else if (pack.getType().equals("nick")) {
                                System.out.println("nick");
                                String nickName = pack.getContent();
                                System.out.println(nickName);
                                saveNewNickName(nickName, connect);
//                                connect.setNickName(nickName);
//                                message = nickName + " join to chat";
//                                pack = new Pack("message", message);
//                                JSON  = gson.toJson(pack);
//                                sendData(JSON);
//                                sendNicknames();
                            } else if(pack.getType().equals("close")) {
                                removeConnection(connect);
                            }
                        }
                    }

//                    if (message != null) {
//                        System.out.println("new message received: " + message);
//                        for (PrintWriter writer : writers) {
//                            writer.println(message);
//                            writer.flush();
//                        }
//                    }
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

    private boolean saveNewNickName(String nickName, Connect currentConnect) {
        boolean nickNameExist = false;
        for( Connect connect : connects ) {
            if(connect.getNickName().equals(nickName)) {
                nickNameExist = true;
                break;
            }
        }
        Gson gson = new Gson();
        Pack pack;
        String JSON;
        if(!nickNameExist) {
            currentConnect.setNickName(nickName);
            System.out.println("saved nickname");
            pack = new Pack("saveNickNameResponse", "success");
            JSON  = gson.toJson(pack);
            PrintWriter writer = currentConnect.getWriter();
            writer.println(JSON);
            writer.flush();

            String message = nickName + " join to chat";
            pack = new Pack("message", message);
            JSON  = gson.toJson(pack);
            sendData(JSON);
            sendNicknames();
            return true;
        } else {
            System.out.println("no saved nickname");
            pack = new Pack("saveNickNameResponse", "error");
            JSON  = gson.toJson(pack);
            PrintWriter writer = currentConnect.getWriter();
            writer.println(JSON);
            writer.flush();
            return false;
        }
    }
}
