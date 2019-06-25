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
    private List<PrintWriter> writers = new CopyOnWriteArrayList<>();
    private List<Socket> clients = new CopyOnWriteArrayList<>();
    private List<BufferedReader> readers = new CopyOnWriteArrayList<>();
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
        for (PrintWriter writer : writers) {
            writer.println(data);
            writer.flush();
        }
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
                    clients.add(clientSocket);
                    readers.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
                    writers.add(new PrintWriter(clientSocket.getOutputStream()));
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
                for (Socket socket : clients) {
                    socket.close();
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
                    for (BufferedReader reader : readers) {
                        if (reader.ready()) {
                            String JSON = reader.readLine();
                            Pack pack = gson.fromJson(JSON, Pack.class);
                            System.out.println("get data");
                            System.out.println(JSON);
                            System.out.println(pack.getType());
                            if(pack.getType().equals("message")) {
                                message = pack.getContent();
                                System.out.println("message");
                                sendData(message);
                            } else if (pack.getType().equals("nick")) {
                                System.out.println("nick");
                                System.out.println(pack.getContent());
                                sendData(pack.getContent() + " join to chat");
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
                for (Socket socket : clients) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}