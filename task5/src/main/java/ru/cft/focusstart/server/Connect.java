package ru.cft.focusstart.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Connect {
    private PrintWriter writer;
    private Socket clientSocket;
    private BufferedReader reader;
    private String nickName = "";

    public Connect(Socket clientSocket, BufferedReader reader, PrintWriter writer) {
        this.clientSocket = clientSocket;
        this.reader = reader;
        this.writer = writer;
    }


    public PrintWriter getWriter() {
        return writer;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
