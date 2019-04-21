package ru.cft.focusstart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static String shapeName;
    private static String shapeParams;

    public static void main(String[] args) {
        String input = args[0];
        String output ="";
        if(args.length > 1) {
            output = args[1];
        }
        readInputFile(input);
        if(shapeName == null || shapeName == "" || shapeParams == null || shapeParams == "") {
            System.out.println("Ошибка в входном файле!");
            return;
        }

        Shape shape;
        ShapeType type = ShapeType.NOT_FOUND;
        try {
            type = ShapeType.valueOf(shapeName);
        } catch (Exception e) {
            System.out.println("Неизвестная фигура");
        }
        switch (type) {
            case CIRCLE:
                shape = new Circle(shapeName, shapeParams);
                break;
            case RECTANGLE:
                shape = new Rectangle(shapeName, shapeParams);
                break;
            case TRIANGLE:
                shape = new Triangle(shapeName, shapeParams);
                break;
            default:
                return;
        }
        if(output != null && !output.equals("")) {
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
