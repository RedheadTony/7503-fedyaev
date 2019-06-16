package ru.cft.focusstart;

public class Settings {
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_MINE_COUNT = 10;

    private static final Settings INSTANCE = new Settings();

    private int size = DEFAULT_SIZE;
    private int mineCount = DEFAULT_MINE_COUNT;

    private Settings() {
    }

    public static Settings getInstance() {
        return INSTANCE;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }
}
