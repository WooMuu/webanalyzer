package com.zjb.resource_downloader.queue;

import java.util.LinkedList;

/**
 * Created by zjb on 2019/2/18.
 */
public class PageInfoQueue implements EventQueue<String> {

    private final LinkedList<String> eventQqueue = new LinkedList<>();
    private final int max;
    private static final int DEFAULT_MAX_EVENT = 10000;

    public PageInfoQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public PageInfoQueue(int max) {
        this.max = max;
    }

    public void offer(String event) throws InterruptedException {
        synchronized (eventQqueue) {
            if (this.eventQqueue.size() >= max) {
                console("The queue is full;");
                eventQqueue.wait();
            }
//            console("The new event is submmited;");
            this.eventQqueue.addLast(event);
            eventQqueue.notify();
        }
    }

    public synchronized String take() throws InterruptedException {
        synchronized (eventQqueue) {
            if (this.eventQqueue.isEmpty()) {
//                console("The queue is empty;");
                eventQqueue.wait();
            }
            String event = this.eventQqueue.removeFirst();
//            console("The event" + event + " is handled;");

            eventQqueue.notify();
            return event;
        }
    }

    private void console(String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }

}
