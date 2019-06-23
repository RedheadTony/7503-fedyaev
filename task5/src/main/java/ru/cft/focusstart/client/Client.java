package ru.cft.focusstart.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner userInputReader = new Scanner(System.in);
        String userName = userInputReader.nextLine();

        Socket socket = new Socket("localhost", 1111);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    if (reader.ready()) {
                        System.out.println(reader.readLine());
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

        new Thread(() -> {
            try {
                while (true) {
                    String userInput = userInputReader.nextLine();
                    if ("\\q".equals(userInput)) {
                        messageListenerThread.interrupt();
                        socket.close();
                        break;
                    }
                    String message = userName + ": " + userInput;
                    writer.println(message);
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
