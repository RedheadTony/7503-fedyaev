package ru.cft.focusstart;

public class Circle extends Shape {
    private final float radius;

    public Circle(String name, String params) {
        super(name);
        try {
            radius = Float.valueOf(params.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка в параметрах фигуры!", e);
        }
    }

    @Override
    protected double getPerimeter() {
        return 2 * Math.PI*radius;
    }

    @Override
    protected double getSquare() {
        return Math.PI*radius*radius;
    }

    @Override
    public StringBuffer buildOutString() {
        StringBuffer sb = super.buildOutString();
        sb.append("Радиус:\t\t\t").append(radius).append("\n");
        sb.append("Диаметр:\t\t").append(radius*radius).append("\n");
        return sb;
    }
}
