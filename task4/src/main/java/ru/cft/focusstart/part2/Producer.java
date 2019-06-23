package ru.cft.focusstart.part2;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

class Producer implements Runnable {
    private BlockingQueue<Resource> stock;
    private static int count = 0;
    private final int id;
    private final int delay;
    private Logger log = getLogger("Producer");

    Producer(final BlockingQueue<Resource> stock, final int delay) {
        id = count++;
        this.delay = delay;
        this.stock = stock;
    }

    @Override
    public void run() {
        String ConsumerInfo = "Производитель(id:" + id + "): ";
        while (!Thread.interrupted()) {
            Resource resource = new Resource();
            log.info(ConsumerInfo + "Ресурс(id:" + resource.getId() + ") произведен.");
            try {
                stock.put(resource);
                log.info(ConsumerInfo + "Ресурс(id:" + resource.getId() + ") помещен на склад.");
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                log.warning(ConsumerInfo + "Error InterruptedException in Producer");
            }
        }
    }
}
