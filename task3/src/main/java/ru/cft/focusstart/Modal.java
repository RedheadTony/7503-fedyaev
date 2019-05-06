package ru.cft.focusstart;

import java.util.Random;

public class Modal {
    private int [] mines = {
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,0
    };

    private int [] numbers = new int[81];

    private boolean [] isOpenedItem = new boolean[81];

    private boolean [] isMarkeredItem = new boolean[81];

    private boolean gameOver = false;

    public void generateMines() {
        int minesCounter = 0;
        Random random = new Random();
        while(minesCounter < 10) {
            int mineIndex = random.nextInt(81);
            if (mines[mineIndex] == 0) {
                mines[mineIndex] = 1;
                minesCounter++;
            }
        }
        for (int i = 0; i < 81; i++) {
            if ( i == 0 ) {
                numbers[i] = mines[i + 1] + mines[i + 9] + mines[i + 10];
                continue;
            }
            if ( i == 8 ) {
                numbers[i] = mines[i - 1] + mines[i + 8] + mines[i + 9];
                continue;
            }
            if ( i < 9) {
                numbers[i] = mines[i - 1] + mines[i + 1] + mines[i + 8] + mines[i + 9] + mines[i + 10];
                continue;
            }
            if ( i >= 9 && i < 72 && (i + 1) % 9 == 0 ) {
                numbers[i] = mines[i - 1] + mines[i - 9] + mines[i - 10] + mines[i + 8] + mines[i + 9];
                continue;
            }
            if ( i >= 9 && i < 72 && (i + 1) % 9 == 1 ) {
                numbers[i] = mines[i + 1] + mines[i - 9] + mines[i - 8] + mines[i + 9] + mines[i + 10];
                continue;
            }
            if ( i >= 9 && i < 72 ) {
                numbers[i] = mines[i + 1] + mines[i - 1] +
                        mines[i - 9] + mines[i - 8] + mines[i - 10] +
                        mines[i + 9] + mines[i + 8] + mines[i + 10];
                continue;
            }
            if ( i == 72 ) {
                numbers[i] = mines[i + 1] + mines[i - 9] + mines[i - 8];
                continue;
            }
            if ( i == 80 ) {
                numbers[i] = mines[i - 1] + mines[i - 9] + mines[i - 10];
                continue;
            }
            if ( i >= 72 ) {
                numbers[i] = mines[i + 1] + mines[i - 1] +
                        mines[i - 9] + mines[i - 8] + mines[i - 10];
            }
        }

        System.out.println("mines");
        for (int i = 0; i < 81; i++ ) {
            System.out.print(mines[i] + ", ");
            if ( (i + 1) % 9 == 0 && i > 0) {
                System.out.println();
            }
        }

        System.out.println("numbers");
        for (int i = 0; i < 81; i++ ) {
            System.out.print(numbers[i] + ", ");
            if ( (i + 1) % 9 == 0 && i > 0) {
                System.out.println();
            }
        }
    }

    public int getItem(int index) {
        return mines[index];
    }

    public int getNumber(int index) {
        return numbers[index];
    }

    public boolean getIsOpenedItem(int index) {
        return isOpenedItem[index];
    }

    public void setIsOpenedItem(int index) {
        isOpenedItem[index] = true;
    }

    public void toggleMarker(int index) {
        isMarkeredItem[index] = !isMarkeredItem[index];
    }

    public boolean getIsMarkered(int index) {
        return isMarkeredItem[index];
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
