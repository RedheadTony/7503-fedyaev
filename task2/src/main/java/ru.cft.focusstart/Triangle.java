package ru.cft.focusstart;

public class Triangle extends Shape {
    private final float a;
    private final float b;
    private final float c;

    private final double angleA;
    private final double angleB;
    private final double angleC;

    public Triangle(String name, String params) throws IllegalArgumentException {
        super(name);
        String [] splitParams = params.split(" ");
        if(splitParams.length > 3) {
            IllegalArgumentException err = new IllegalArgumentException("Указаны лишние параметры!");
            throw err;
        }
        if(splitParams.length < 3) {
            IllegalArgumentException err = new IllegalArgumentException("Указаны не все стороны!");
            throw err;
        }
        try {
            a = Float.valueOf(splitParams[0].trim());
            b = Float.valueOf(splitParams[1].trim());
            c = Float.valueOf(splitParams[2].trim());
            if(!checkExistenceOfTriangle()) {
                IllegalArgumentException ex = new IllegalArgumentException("Неверные длины треугольника!");
                throw ex;
            }
            angleA = Math.toDegrees(Math.acos((b*b + c*c - a*a) / (2*c*b)));
            angleB = Math.toDegrees(Math.acos((a*a + c*c - b*b) / (2*a*c)));
            angleC = Math.toDegrees(Math.acos((a*a + b*b - c*c) / (2*a*b)));
        } catch (NumberFormatException e) {
            IllegalArgumentException ex = new IllegalArgumentException("Ошибка в параметрах фигуры!", e);
            throw ex;
        }
    }

    private boolean checkExistenceOfTriangle() {
        if(a + b < c) return false;
        if(a + c < b) return false;
        if(b + c < a) return false;
        return true;
    }

    @Override
    protected double getPerimeter() {
        return a + b + c;
    }

    @Override
    protected double getSquare() {
        double halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }

    @Override
    public StringBuffer buildOutString() {
        StringBuffer sb = super.buildOutString();
        sb.append("Сторона a:\t\t").append(a).append("\n");
        sb.append("Угол напротив a:\t").append(angleA).append("\n");
        sb.append("Сторона b:\t\t").append(b).append("\n");
        sb.append("Угол напротив b:\t").append(angleB).append("\n");
        sb.append("Сторона c:\t\t").append(c).append("\n");
        sb.append("Угол напротив c:\t").append(angleC).append("\n");
        return sb;
    }
}
