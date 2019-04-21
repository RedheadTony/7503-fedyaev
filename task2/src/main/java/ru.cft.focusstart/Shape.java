package ru.cft.focusstart;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Shape {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    abstract protected double getPerimeter();

    abstract protected double getSquare();

    public void getInfo() {
        System.out.println("Тип фигуры:\t\t" + name);
        System.out.printf("Периметр:\t\t%.2f\n", getPerimeter());
        System.out.printf("Площадь:\t\t%.2f\n", getSquare());
    }

    public void getInfo(String fileName) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write("Тип фигуры:\t\t" + name + "\n");
            String tmp = String.format("Периметр:\t\t%.2f\n", getPerimeter());
            file.write(tmp);
            tmp = String.format("Площадь:\t\t%.2f\n", getSquare());
            file.write(tmp);
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
