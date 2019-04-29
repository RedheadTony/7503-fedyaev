package ru.cft.focusstart;

public class Rectangle extends Shape {
    private final float width;
    private final float length;

    Rectangle(String name, String params) throws Exception {
        super(name);
        String [] splitParams = params.split(" ");
        if(splitParams.length > 2) {
            IllegalArgumentException err = new IllegalArgumentException("Указаны лишние параметры!");
            throw err;
        }
        if(splitParams.length < 2) {
            IllegalArgumentException err = new IllegalArgumentException("Указаны не все стороны!");
            throw err;
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
            IllegalArgumentException err = new IllegalArgumentException("Ошибка в параметрах фигуры!");
            throw err;
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
