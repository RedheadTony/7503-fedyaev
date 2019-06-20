package ru.cft.focusstart.part2;

class Resource {
    private final int id;
    private static int count = 0;
    private static final Object lock = new Object();

    Resource() {
        synchronized (lock) {
            this.id = count++;
        }
    }

    int getId() {
        return id;
    }
}
