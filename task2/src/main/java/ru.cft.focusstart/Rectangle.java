package ru.cft.focusstart;

public class Rectangle extends Shape {
    private final float width;
    private final float length;

    public Rectangle(String name, String params) {
        super(name);
        String [] splitParams = params.split(" ");
        if(splitParams.length > 2) {
            throw new IllegalArgumentException("Указаны лишние параметры!");
        }
        if(splitParams.length < 2) {
            throw new IllegalArgumentException("Указаны не все стороны!");
        }
        float tmpWidth;
        float tmpLength;
        try {
            tmpWidth = Float.valueOf(splitParams[0].trim());
            tmpLength = Float.valueOf(splitParams[1].trim());
            if(tmpWidth < tmpLength) {
                width = tmpWidth;
                length = tmpLength;
            } else {
                width = tmpLength;
                length = tmpWidth;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка в параметрах фигуры!", e);
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
    public StringBuffer buildOutString() {
        StringBuffer sb = super.buildOutString();
        sb.append("Диагональ:\t\t").append(getDiagonal()).append("\n");
        sb.append("Длина:\t\t\t").append(length).append("\n");
        sb.append("Ширина:\t\t\t").append(width).append("\n");
        return sb;
    }
}
