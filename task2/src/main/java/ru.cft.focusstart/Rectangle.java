package ru.cft.focusstart;

import java.io.FileWriter;
import java.io.IOException;

public class Rectangle extends Shape {
    private float width;
    private float length;

    public Rectangle(String name, String params) {
        super(name);
        String [] splitParams = params.split(" ");
        float tmpWidth;
        float tmpLength;
        try {
            tmpWidth = Float.valueOf(splitParams[0].trim()).floatValue();
            tmpLength = Float.valueOf(splitParams[1].trim()).floatValue();
            if(tmpWidth < tmpLength) {
                width = tmpWidth;
                length = tmpLength;
                return;
            }
            width = tmpLength;
            length = tmpWidth;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка в параметрах фигуры!");
        }
    }


    @Override
    protected double getPerimeter() {
        return (width + length) * 2;
    }

    @Override
    protected double getSquare() {
        return width * length;
    }

    private double getDiagonal() {
        return Math.sqrt(width * width + length * length);
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.printf("Диагональ:\t\t%.2f\n", getDiagonal());
        System.out.printf("Длина:\t\t\t%.2f\n", length);
        System.out.printf("Ширина:\t\t\t%.2f\n", width);
    }

    @Override
    public void getInfo(String fileName) {
        super.getInfo(fileName);
        try {
            FileWriter file = new FileWriter(fileName, true);
            String tmp = String.format("Диагональ:\t\t%.2f\n", getDiagonal());
            file.write(tmp);
            tmp = String.format("Длина:\t\t\t%.2f\n", length);
            file.write(tmp);
            tmp = String.format("Ширина:\t\t\t%.2f\n", width);
            file.write(tmp);
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
