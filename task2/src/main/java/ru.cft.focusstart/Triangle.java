package ru.cft.focusstart;

import java.io.FileWriter;
import java.io.IOException;

public class Triangle extends Shape {
    private float a;
    private float b;
    private float c;

    private double angleA;
    private double angleB;
    private double angleC;

    public Triangle(String name, String params) {
        super(name);
        String [] splitParams = params.split(" ");
        try {
            a = Float.valueOf(splitParams[0].trim()).floatValue();
            b = Float.valueOf(splitParams[1].trim()).floatValue();
            c = Float.valueOf(splitParams[2].trim()).floatValue();
            setAngles();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка в параметрах фигуры!");
        }
    }

    @Override
    protected double getPerimeter() {
        return a + b + c;
    }

    private void setAngles() {
        angleA = Math.toDegrees(Math.acos((b*b + c*c - a*a) / (2*c*b)));
        angleB = Math.toDegrees(Math.acos((a*a + c*c - b*b) / (2*a*c)));
        angleC = Math.toDegrees(Math.acos((a*a + b*b - c*c) / (2*a*b)));
    }

    @Override
    protected double getSquare() {
        double halfP = getPerimeter() / 2;
        return Math.sqrt(halfP * (halfP - a) * (halfP - b) * (halfP - c));
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.printf("Сторона a:\t\t%.2f\n", a);
        System.out.printf("Угол напротив a:\t%.2f\n", angleA);
        System.out.printf("Сторона b:\t\t%.2f\n", b);
        System.out.printf("Угол напротив b:\t%.2f\n", angleB);
        System.out.printf("Сторона c:\t\t%.2f\n", c);
        System.out.printf("Угол напротив c:\t%.2f\n", angleC);
    }

    @Override
    public void getInfo(String fileName) {
        super.getInfo(fileName);
        try {
            FileWriter file = new FileWriter(fileName, true);
            String tmp = String.format("Сторона a:\t\t%.2f\n", a);
            file.write(tmp);
            tmp = String.format("Угол напротив a:\t%.2f\n", angleA);
            file.write(tmp);
            tmp = String.format("Сторона b:\t\t%.2f\n", b);
            file.write(tmp);
            tmp = String.format("Угол напротив b:\t%.2f\n", angleB);
            file.write(tmp);
            tmp = String.format("Сторона c:\t\t%.2f\n", c);
            file.write(tmp);
            tmp = String.format("Угол напротив c:\t%.2f\n", angleC);
            file.write(tmp);
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
