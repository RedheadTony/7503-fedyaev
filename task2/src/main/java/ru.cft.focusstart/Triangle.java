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
        System.out.print("Сторона a:\t\t\t");
        System.out.println(a);
        System.out.print("Угол напротив a:\t");
        System.out.println(angleA);
        System.out.print("Сторона b:\t\t\t");
        System.out.println(b);
        System.out.print("Угол напротив b:\t");
        System.out.println(angleB);
        System.out.print("Сторона c:\t\t\t");
        System.out.println(c);
        System.out.print("Угол напротив c:\t");
        System.out.println(angleC);
    }

    @Override
    public void getInfo(String fileName) {
        super.getInfo(fileName);
        try {
            FileWriter file = new FileWriter(fileName, true);
            file.write("Сторона a:\t\t\t");
            file.write(a + "\n");
            file.write("Угол напротив a:\t");
            file.write(angleA + "\n");
            file.write("Сторона b:\t\t\t");
            file.write(b + "\n");
            file.write("Угол напротив b:\t");
            file.write(angleB + "\n");
            file.write("Сторона c:\t\t\t");
            file.write(c + "\n");
            file.write("Угол напротив c:\t");
            file.write(angleC + "\n");
            file.close();
        } catch (IOException e) {
            System.out.println("Не удалось создать файл!");
        }
    }
}
