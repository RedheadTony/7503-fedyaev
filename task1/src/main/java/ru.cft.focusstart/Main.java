package ru.cft.focusstart;

import java.util.Scanner;

public class Main {
    final private static int MAX_INPUT_NUMBER = 32;
    final private static int MIN_INPUT_NUMBER = 1;

    public static void main(String[] args) {
        int number = readSize();
        if(number == 0) return;
        renderTable(number);
    }

    private static int readSize() {
        int number;
        try {
            number = Integer.parseInt(new Scanner(System.in).next());
        } catch (NumberFormatException e) {
            System.out.println("Необходимо ввести целое число");
            return 0;
        }
        if (number > MAX_INPUT_NUMBER) {
            System.out.println("Число не должно быть больше 32");
            return 0;
        }
        if (number < MIN_INPUT_NUMBER) {
            System.out.println("Число не должно быть меньше 1");
            return 0;
        }
        return number;
    }

    private static void renderTable(int size) {
        int columnWidth = (size*size + "").length();
        String format = "| %" + columnWidth + "d ";
        renderSeparatorLine(columnWidth, size);
        for (int i = 0; i < size; i++) {
            renderLine((i + 1), size, format);
            renderSeparatorLine(columnWidth , size);
        }
    }

    private static void renderLine(int factor, int size, String format) {
        for (int i = 0; i < size; i++ ) {
            System.out.printf(format, (i + 1) * (factor));
        }
        System.out.println("|");
    }

    private static void renderSeparatorLine(int columnWidth, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print("+");
            for (int j = 0; j < columnWidth + 2; j++) {
                System.out.print("-");
            }
        }
        System.out.print("+");
        System.out.println();
    }
}