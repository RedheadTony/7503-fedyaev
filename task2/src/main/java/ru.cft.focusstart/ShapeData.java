package ru.cft.focusstart;

public class ShapeData {
    private final String shapeName;
    private final String shapeParams;

    public ShapeData(String shapeName, String shapeParams) {
        this.shapeName = shapeName;
        this.shapeParams = shapeParams;
    }

    public String getShapeParams() {
        return shapeParams;
    }

    public String getShapeName() {
        return shapeName;
    }
}
