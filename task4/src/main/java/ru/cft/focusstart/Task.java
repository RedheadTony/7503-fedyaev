package ru.cft.focusstart;

public class Task implements Runnable {
    private int startNumber;
    private int endNumber;
    private double result = 0;
    private Summable summable;

    public Task(int startNumber, int endNumber, Summable summable) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.summable = summable;
    }

    public void calcResult() {
        for (int i = startNumber; i < endNumber; i++) {
            result += function(i);
        }
    }

    private double function(int x) {
        double temp = 0;
        for (int i = 0; i < 500; i++) {
            temp += Math.sqrt((Math.log10(x) * Math.pow(x, 2)) / 5) * 0.5 * x + 150;
        }
        return temp;
    }

    @Override
    public void run() {
        calcResult();
        summable.sendResult(result);
    }
}
