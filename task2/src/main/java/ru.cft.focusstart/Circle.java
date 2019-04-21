package ru.cft.focusstart;

import java.io.FileWriter;
import java.io.IOException;

public class Circle extends Shape {
    private float radius;

    public Circle(String name, String params) {
        super(name);
        try {
            radius = Float.valueOf(params.trim()).floatValue();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка в параметрах фигуры!");
        }
    }

    @Override
    protected double getPerimeter() {
        return 2 * Math.PI*radius;
    }

    @Override
    protected double getSquare() {
        return Math.PI*radius*radius;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.printf("Радиус:\t\t\t%.2f\n", radius);
        System.out.printf("Диаметр:\t\t%.2f\n", radius*radius);
    }

    @Override
    public void getInfo(String fileName) {
        super.getInfo(fileName);
        try {
            FileWriter file = new FileWriter(fileName, true);
            String tmp = String.format("Радиус:\t\t\t%.2f\n", radius);
            file.write(tmp);
            tmp = String.format("Диаметр:\t\t%.2f\n", radius*radius);
            file.write(tmp);
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
