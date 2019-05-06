package ru.cft.focusstart;

import javax.swing.*;

public class Controller {
    private Modal modal;
    private JButton [] buttons = new JButton [81];

    public Controller() {
        modal = new Modal();
        modal.generateMines();
    }

    private Icon getCurrentIcon(int index) {
        if (modal.getItem(index) == 1) {
            return new ImageIcon(Main.class.getResource("/icons/mine.png"));
        }

        int number = modal.getNumber(index);
        System.out.println("////////////////////");
        System.out.println(index);
        System.out.println(number);
        System.out.println("////////////////////");
        switch (number) {
            case 1:
                return new ImageIcon(Main.class.getResource("/icons/one.png"));
            case 2:
                return new ImageIcon(Main.class.getResource("/icons/two.png"));
            case 3:
                return new ImageIcon(Main.class.getResource("/icons/three.png"));
            case 4:
                return new ImageIcon(Main.class.getResource("/icons/four.png"));
            case 5:
                return new ImageIcon(Main.class.getResource("/icons/five.png"));
            case 6:
                return new ImageIcon(Main.class.getResource("/icons/six.png"));
            case 7:
                return new ImageIcon(Main.class.getResource("/icons/seven.png"));
            case 8:
                return new ImageIcon(Main.class.getResource("/icons/eight.png"));
            default:
                return new ImageIcon(Main.class.getResource("/icons/zero.png"));
        }
//        return icon;
    }

    public void setAllIcons() {
        for (int i = 0; i < 81; i++ ) {
            buttons[i].setIcon(getCurrentIcon(i));
        }
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
