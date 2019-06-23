package ru.cft.focusstart.part2;

import java.util.concurrent.*;

public class Application {
    private static final int STOCK_SIZE = 20;
    private static final int PRODUCER_COUNT = 10;
    private static final int PRODUCER_DELAY_TIME = 2500;
    private static final int CONSUMER_COUNT = 10;
    private static final int CONSUMER_DELAY_TIME = 1000;

    public static void main(String[] args) {
        BlockingQueue<Resource> stock = new ArrayBlockingQueue<Resource>(STOCK_SIZE);

        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Producer producer = new Producer(stock, PRODUCER_DELAY_TIME);
            Thread thread = new Thread(producer);
            thread.start();
        }

        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Consumer consumer = new Consumer(stock, CONSUMER_DELAY_TIME);
            Thread thread = new Thread(consumer);
            thread.start();
        }


    }
}