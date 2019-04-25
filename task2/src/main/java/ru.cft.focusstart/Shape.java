package ru.cft.focusstart;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Shape {
    protected final String name;

    public Shape(String name) {
        this.name = name;
    }

    abstract protected double getPerimeter();

    abstract protected double getSquare();

    public StringBuffer buildOutString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Тип фигуры:\t\t").append(name).append("\n");
        sb.append("Периметр:\t\t").append(getPerimeter()).append("\n");
        sb.append("Площадь:\t\t").append(getSquare()).append("\n");
        return sb;
    }

    public void printInfo() {
        System.out.println(buildOutString().toString());
    }

    public void printInfo(String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(buildOutString().toString());
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
