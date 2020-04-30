package com.example.thread;

class WaitDemo {

    private String sharedString;

    void waitAndNotify() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initString();
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printSharedString();
            }
        };
        thread1.start();
        thread2.start();
    }

    private synchronized void initString() {
        sharedString = "shared string";
        notifyAll();
    }

    private synchronized void printSharedString() {
        while (sharedString == null) {  //注意：此处不能换成if，因为我们无法保证此线程是被谁唤醒的
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 当被notify唤醒后，从这里开始执行
        }
        System.out.println("sharedString = " + sharedString);
    }
}