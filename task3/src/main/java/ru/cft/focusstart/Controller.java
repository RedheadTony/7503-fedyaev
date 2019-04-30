package ru.cft.focusstart;

import javax.swing.*;

public class Controller {
    private Modal modal;
    private JButton [] buttons = new JButton [81];

    public Controller() {
        modal = new Modal();
    }

    public void getIcon(int index) {
        if(modal.getGameOver()) return;
        if(modal.getIsOpenedItem(index)) return;
        if(modal.getIsMarkered(index)) return;
        modal.setIsOpenedItem(index);
        if(modal.getItem(index) == 1) {
            Icon icon = new ImageIcon(Main.class.getResource("/icons/mine.png"));
            modal.setGameOver(true);
            buttons[index].setIcon(icon);
        } else {
            Icon icon = new ImageIcon(Main.class.getResource("/icons/zero.png"));
            buttons[index].setIcon(icon);
        }
    }

    public void toggleMine(int index) {
        if(modal.getGameOver()) return;
        if(modal.getIsOpenedItem(index)) return;
        modal.toggleMarker(index);
        Icon icon;
        boolean isMarkered = modal.getIsMarkered(index);
        if(isMarkered) {
            icon = new ImageIcon(Main.class.getResource("/icons/flag.png"));
        } else {
            icon = new ImageIcon(Main.class.getResource("/icons/closed.png"));
        }
        buttons[index].setIcon(icon);
    }

    public void setButtonItem(int index, JButton button) {
        buttons[index] = button;
    }
}
