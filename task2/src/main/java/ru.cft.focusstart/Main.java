package ru.cft.focusstart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Отсутствуют аргументы!");
            return;
        }

        String input = args[0];
        String output = null;

        if(args.length > 1) {
            output = args[1];
        }

        ShapeData shapeData;
        try {
            shapeData = readInputFile(input);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        String shapeName = shapeData.getShapeName();
        String shapeParams = shapeData.getShapeParams();

        ShapeType type;
        try {
            type = ShapeType.valueOf(shapeName);
        } catch (Exception e) {
            System.err.println("Неизвестная фигура");
            return;
        }

        Shape shape;
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

    private static ShapeData readInputFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String name = reader.readLine();
            String params = reader.readLine();

            if(name == null || params == null) {
                throw new IOException("В файле не достаточно строк!");
            }

            return new ShapeData(name, params);

        } catch (FileNotFoundException e) {
            throw new IOException("Файл не найден!", e);
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла!", e);
        }
    }
}
