package ru.cft.focusstart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static String shapeName;
    private static String shapeParams;
    enum types { CIRCLE, RECTANGLE, TRIANGLE }; // как юзать это!!!

    public static void main(String[] args) {

//        System.out.println("Введите имя входного файла (обязательно)");
//        String input = new Scanner(System.in).next();
//        System.out.println("Введите имя выходного файла (не обязательно)");
//        String output = new Scanner(System.in).next();
        String input = "/home/antony/study/java/input3.txt";
        String output ="/home/antony/study/java/output.txt";
        readInputFile(input);
        if(shapeName == null || shapeName == "" || shapeParams == null || shapeParams == "") {
            System.out.println("Ошибка в входном файле!");
            return;
        }

        Shape shape;
        switch (shapeName) {
            case "CIRCLE":
                shape = new Circle(shapeName, shapeParams);
                break;
            case "RECTANGLE":
                shape = new Rectangle(shapeName, shapeParams);
                break;
            case "TRIANGLE":
                shape = new Triangle(shapeName, shapeParams);
                break;
            default:
                System.out.println("Неизвестная фигура");
                return;
        }
        if(output != "" && output != null) {
            shape.getInfo(output);
            return;
        }
        shape.getInfo();
    }

    private static void readInputFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            shapeName = reader.readLine();
            shapeParams = reader.readLine();

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка2!");
        }
    }
}
