package ru.cft.focusstart.part2;

import java.util.concurrent.BlockingQueue;

import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

class Consumer implements Runnable {
    private BlockingQueue<Resource> stock;
    private static int count = 0;
    private Logger log = getLogger("Consumer");
    private final int id;
    private final int delay;

    Consumer(final BlockingQueue<Resource> stock, final int delay) {
        id = count++;
        this.delay = delay;
        this.stock = stock;
    }

    @Override
    public void run() {
        String ConsumerInfo = "Потребитель(id:" + id + "): ";
        while (!Thread.interrupted()) {
            try {
                Resource resource = stock.take();
                log.info(ConsumerInfo + "Ресурс(id: " + resource.getId() + ") забран со склада.");
                Thread.sleep(delay);
                log.info(ConsumerInfo + "Ресурс(id:" + resource.getId() + ") потреблен.");
            } catch (InterruptedException e) {
                log.warning(ConsumerInfo + "Error InterruptedException in Consumer");
            }
        }
    }
}
