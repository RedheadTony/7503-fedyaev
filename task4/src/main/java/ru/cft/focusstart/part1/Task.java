package ru.cft.focusstart.part1;

public class Task implements Runnable {
    private int startNumber;
    private int endNumber;
    private double result = 0;
    private SumResultListener sumResultListener;

    public Task(int startNumber, int endNumber, SumResultListener sumResultListener) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.sumResultListener = sumResultListener;
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
        sumResultListener.changeSumResult(result);
    }
}
