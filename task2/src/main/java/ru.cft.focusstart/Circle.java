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
        System.out.print("Радиус:\t\t\t\t");
        System.out.println(radius);
        System.out.print("Диаметр:\t\t\t");
        System.out.println(radius*radius);
    }

    @Override
    public void getInfo(String fileName) {
        super.getInfo(fileName);
        try {
            FileWriter file = new FileWriter(fileName, true);
            file.write("Радиус:\t\t\t\t");
            file.write(radius + "\n");
            file.write("Диаметр:\t\t\t");
            file.write(radius*radius + "\n");
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
