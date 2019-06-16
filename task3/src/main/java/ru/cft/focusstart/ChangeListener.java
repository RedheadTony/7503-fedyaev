package ru.cft.focusstart;

public interface ChangeListener {
    void onChanged(int row, int column);
    void onWin();
    void onDefeat();
}
