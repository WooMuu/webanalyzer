package com.zjb.resource_downloader.queue;

/**
 * Created by zjb on 2019/3/21.
 */
public interface EventQueue<T> {

    void offer(T event) throws InterruptedException;

    T take() throws InterruptedException;


}
