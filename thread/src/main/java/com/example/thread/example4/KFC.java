package com.example.thread.example4;

import java.util.ArrayList;
import java.util.List;

/**
 * 在一个KFC内，服务员负责生产食物，消费者负责消费食物；
 * 当生产到一定数量可以休息一下，直到消费完食物，再马上生产，一直循环
 * <p>
 * 程序涉及到的内容：
 * 1.这设计到java模式思想：生产者消费者模式
 * 2.要保证操作对象的统一性，即消费者和服务者都是跟同一个KFC发生关系的，KFC只能new一次
 * 3.this.notifyAll();和 this.wait();一个是所有唤醒的意思，一个是让自己等待的意思
 * 生产者生产完毕后，先所有唤醒（包括消费者和生产者），再让所有自己（生产者）等待
 * 这时，消费者开始消费，直到食材不够，先所有唤醒（包括消费者和生产者），再让所有自己（消费者）等待
 * 一直执行上面的操作的循环
 * 4.生产者和消费者都要继承Thread，才能实现多线程的启动
 * <p>
 * <p>
 * 程序设计的步骤思路
 * 1.创建一个食物类Food，有存放/获取食物的名称的方法
 * 2. 创建一个KFC类，有生产食物和消费食物的方法
 * 3. 创建一个客户类Customer，继承Thread，重写run方法，在run方法里面进行消费食物操作
 * 4. 创建一个服务员类Waiter，继承Thread，重写run方法，在run方法里面进行生产食物的操作
 */
public class KFC {
    static String[] foodKinds = new String[]{"薯条", "烧板", "鸡翅", "可乐"};

    //生产的最大值，到达后可以休息
    static final int MAX = 20;
    List foods = new ArrayList();


    public synchronized void productFood(int size) {

        while (foods.size() >= MAX) {
            System.out.println("食材够了，通知消费者开始消费");
            notifyAll();  //这个唤醒是针对生产者和消费者，有all

            try {
                System.out.println(Thread.currentThread().getName() + "开始休息");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + "开始生产食物");
        for (int i = 0; i < size; i++) {
            Food food = new Food(foodKinds[(int) (Math.random() * 4)]);
            foods.add(food);
            System.out.println(Thread.currentThread().getName() + "生产了一个" + food.getName() + "，目前食物总量为 " + foods.size());
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void consume(int size) {

        while (foods.size() < size) {
            System.out.println("食材不够了,通知服务员开始生产食物");
            notifyAll();

            try {
                System.out.println(Thread.currentThread().getName() + "等待");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 足够消费
        System.out.println(Thread.currentThread().getName() + "开始消费");
        for (int i = 0; i < size; i++) {
            Food food = (Food) foods.remove(foods.size() - 1);
            System.out.println(Thread.currentThread().getName() + "消费了一个" + food.getName() + "，目前食材剩余" + foods.size());
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Customers extends Thread {

    KFC kfc;

    public Customers(KFC kfc, String name) {
        super(name);
        this.kfc = kfc;
    }

    @Override
    public void run() {
        int size = (int) (Math.random() * 5);//每次要消费的食物的数量
        while (true) {
            kfc.consume(size);
        }
    }
}

class Waiter extends Thread {

    KFC kfc;

    public Waiter(KFC kfc, String name) {
        super(name);
        this.kfc = kfc;
    }

    @Override
    public void run() {
        int size = (int) (Math.random() * 5);//每次要生产的食物的数量
        while (true) {
            kfc.productFood(size);
        }
    }
}

class Food {
    String name;

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class MainClass {
    public static void main(String[] args) {
        KFC kfc = new KFC();
        Waiter waiter1 = new Waiter(kfc, "服务员1");
        Waiter waiter2 = new Waiter(kfc, "服务员2");
        Waiter waiter3 = new Waiter(kfc, "服务员3");
        Customers customer1 = new Customers(kfc, "顾客1");
        Customers customer2 = new Customers(kfc, "顾客2");

        waiter1.start();
        waiter2.start();
        waiter3.start();
        customer1.start();
        customer2.start();
    }
}

