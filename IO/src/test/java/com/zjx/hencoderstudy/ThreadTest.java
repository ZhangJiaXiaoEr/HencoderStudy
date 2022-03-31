package com.zjx.hencoderstudy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest {

    /**
     * 线程的创建
     */
    public void threadCreate() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//            }
//        };
//        thread.start();

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        new Thread(runnable).start();

//        ThreadFactory threadFactory = new ThreadFactory() {
//            int count;
//            @Override
//            public Thread newThread(Runnable r) {
//                return null;
//            }
//        };
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        Runnable runnable1 = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        Thread thread = threadFactory.newThread(runnable);
//        Thread thread1 = threadFactory.newThread(runnable1);
//        thread.start();
//        thread1.start();

//        Callable<String> callable = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return null;
//            }
//        };
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        Future<String> future = executorService.submit(callable);
//        while (future.isDone()) {
//            try {
//                String result = future.get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    /**
     * 读写锁
     */
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    int count;

    private void count () {
        writeLock.lock();
        try{
            count ++;
        } finally {
            writeLock.unlock();
        }
    }

    private void print () {
        readLock.lock();
        try {
            for (int i = 0; i < count; i++) {
                System.out.println("count===" + count);
            }
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 线程终止
     */
    private void stopThread () {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isInterrupted()) {//这个不会改变线程interrupt的状态
                        //进行停止线程的收尾工作
                        return;
                    }
                    if (Thread.interrupted()) {//这个会改变线程interrupt的状态
                        //进行停止线程的收尾工作
                        return;
                    }

                    //将本线程的一小片时间分给其它线程用
                    yield();
                }
            }
        };
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //改变线程的一个状态，需要在线程中配合isInterrupted()或Thread.interrupted()来进行终止线程
        thread.interrupt();
        try {
            //将这个线程放到第一位，只有当前线程运行完成，其它线程才能运行
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生产者、消费者
     */
    int productCount = 0;
    private synchronized void produce () {
        if (productCount > 10) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        productCount++;
        notifyAll();
    }

    private synchronized void consum () {
        if (productCount < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        productCount --;
        notifyAll();
    }
}
