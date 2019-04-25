package ru.cft.focusstart;

public class Rectangle extends Shape {
    private final float width;
    private final float length;

    Rectangle(String name, String params) throws Exception {
        super(name);
        String [] splitParams = params.split(" ");
        if(splitParams.length > 2) {
            Exception err = new Exception("Указаны лишние параметры!");
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
                return;
            }
            width = tmpLength;
            length = tmpWidth;
        } catch (NumberFormatException e) {
            Exception err = new Exception("Ошибка в параметрах фигуры!");
            throw err;
        } catch (ArrayIndexOutOfBoundsException e) {
            Exception ex = new Exception("Указаны не все стороны!", e);
            throw ex;
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
