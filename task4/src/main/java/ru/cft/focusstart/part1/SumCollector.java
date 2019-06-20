package ru.cft.focusstart.part1;

public class SumCollector implements SumResultListener {
    private double result = 0;
    private static final int NUMBER = 10_000_000;

    public void startCalc() {
        int cores = Runtime.getRuntime().availableProcessors();
        int step = (int) NUMBER / cores;
        for (int i = 0; i < cores; i++) {
            int startNumber = i * step;
            if (startNumber == 0) {
                startNumber = 1;
            }
            int endNumber = (i + 1) * step;
            if (i + 1 == cores) {
                endNumber = NUMBER;
            }
            Task task = new Task(startNumber, endNumber, this);
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    @Override
    public synchronized void changeSumResult(double result) {
        this.result += result;
        System.out.println(this.result);
    }
}
