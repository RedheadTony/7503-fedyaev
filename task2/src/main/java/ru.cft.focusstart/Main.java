package ru.cft.focusstart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String input;
        try {
            input = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Отсутствуют аргументы!");
            return;
        }

        String output = null;

        if(args.length > 1) {
            output = args[1];
        }

        ShapeData shapeData;
        String shapeName;
        String shapeParams;
        try {
            shapeData = readInputFile(input);
            shapeName = shapeData.getShapeName();
            shapeParams = shapeData.getShapeParams();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        Shape shape;
        ShapeType type;
        try {
            type = ShapeType.valueOf(shapeName);
        } catch (Exception e) {
            System.err.println("Неизвестная фигура");
            return;
        }

        try {
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
        } catch (Exception e) {
                System.err.println(e.getMessage());
            return;
        }

        if(output != null && !output.equals("")) {
            shape.printInfo(output);
        } else {
            shape.printInfo();
        }
    }

    private static ShapeData readInputFile(String fileName) throws Exception {
        String name;
        String params;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            name = reader.readLine();
            params = reader.readLine();

            if(name == null || params == null) {
                Exception ex = new Exception("В файле не достаточно строк!");
                throw ex;
            }

        } catch (FileNotFoundException e) {
            Exception ex = new Exception("Файл не найден!");
            throw ex;
        } catch (IOException e) {
            Exception ex = new Exception("Ошибка чтения файла!");
            throw ex;
        }
        ShapeData data = new ShapeData(name, params);
        return data;
    }
}
