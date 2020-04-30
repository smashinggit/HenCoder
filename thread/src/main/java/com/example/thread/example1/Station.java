package com.example.thread.example1;

/**
 * 三个售票窗口同时出售20张票
 * <p>
 * 程序分析：
 * 1.票数要使用同一个静态值
 * 2.为保证不会出现卖出同一个票数，要java多线程同步锁。
 * <p>
 * 设计思路：
 * 1.创建一个站台类Station，继承Thread，重写run方法，
 * 在run方法里面执行售票操作！售票要使用同步锁：即有一个站台卖这张票时，其他站台要等这张票卖完！
 * 2.创建主方法调用类
 */
public class Station extends Thread {
    public static int tickets = 20;

    public Station(String name) {
        super(name);
    }


    @Override
    public void run() {
        while (tickets > 0) {
            synchronized (Station.class) {

                if (tickets > 0) {
                    System.out.println(getName() + "卖出了第" + tickets + "张票");
                    tickets--;
                } else {
                    System.out.println("票卖完了");
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MainClass {

    public static void main(String[] args) {
        Station station1 = new Station("窗口一");
        Station station2 = new Station("窗口二");
        Station station3 = new Station("窗口三");

        station1.start();
        station2.start();
        station3.start();
    }

}
