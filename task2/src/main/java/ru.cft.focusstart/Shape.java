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
        System.out.print("Тип фигуры:\t\t\t");
        System.out.println(name);
        System.out.print("Периметр:\t\t\t");
        System.out.println(getPerimeter());
        System.out.print("Площадь:\t\t\t");
        System.out.println(getSquare());
    }

    public void getInfo(String fileName) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write("Тип фигуры:\t\t\t");
            file.write(name + "\n");
            file.write("Периметр:\t\t\t");
            file.write(getPerimeter() + "\n");
            file.write("Площадь:\t\t\t");
            file.write(getSquare() + "\n");
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
