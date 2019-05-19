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

        if (args.length > 1) {
            output = args[1];
        }

        Shape shape;

        try {
            shape = getShape(input);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        if (output != null && !output.equals("")) {
            shape.printInfo(output);
        } else {
            shape.printInfo();
        }
    }

    private static Shape getShape(String input) throws Exception {
        ShapeData shapeData;

        shapeData = readInputFile(input);

        String shapeName = shapeData.getShapeName();
        String shapeParams = shapeData.getShapeParams();

        ShapeType type;
        try {
            type = ShapeType.valueOf(shapeName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неизвестная фигура", e);
        }

        switch (type) {
            case CIRCLE:
                return new Circle(shapeName, shapeParams);
            case RECTANGLE:
                return new Rectangle(shapeName, shapeParams);
            case TRIANGLE:
                return new Triangle(shapeName, shapeParams);
            default:
                throw new IllegalArgumentException("Неизвестная фигура");
        }
    }

    private static ShapeData readInputFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String name = reader.readLine();
            String params = reader.readLine();

            if (name == null || params == null) {
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
