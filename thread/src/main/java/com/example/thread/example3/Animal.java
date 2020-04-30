package com.example.thread.example3;

/**
 * 龟兔赛跑问题
 * <p>
 * 要求：
 * 1.兔子每次0.5米的速度，每跑2米休息10秒，
 * 2.乌龟每次跑0.1米，不休息
 * 3.其中一个跑到终点后另一个不跑了！
 * <p>
 * 程序设计思路:
 * 1.创建一个Animal动物类，继承Thread，编写一个running抽象方法，
 * 重写run方法，把running方法在run方法里面调用。
 * 2. 创建Rabbit兔子类和Tortoise乌龟类，继承动物类
 * 3. 两个子类重写running方法
 * 4. 本题的第3个要求涉及到线程回调。需要在动物类创建一个回调接口，创建一个回调对象
 */
public abstract class Animal extends Thread {
    public static int LENGTH = 20; //赛道长度
    protected float currentLength = 0f; //当前跑了多远

    public Animal(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (currentLength < LENGTH) {
            running();
        }
    }

    abstract void running();

    interface WinCllBack {
        void win();
    }

}


class Rabbit extends Animal {

    public Rabbit() {
        super("兔子");
    }

    @Override
    void running() {
        currentLength += 0.5f;  //每次跑0.5米
        if (currentLength >= LENGTH) {
            System.out.println("兔子获得了胜利");
        }
        System.out.println("兔子跑了" + currentLength + "米，距离终点" + (LENGTH - currentLength) + "米");


        if (currentLength % 2 == 0) {  //跑了2米，休息1秒
            System.out.println("兔子跑了2米，休息1s");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Tortoise extends Animal {

    public Tortoise() {
        super("乌龟");
    }

    @Override
    void running() {
        currentLength += 0.2f;
        if (currentLength >= LENGTH) {
            System.out.println("乌龟获得了胜利");
        }
        System.out.println("乌龟跑了" + currentLength + "米，距离终点" + (LENGTH - currentLength) + "米");
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}


class MainClass {


    public static void main(String[] args) {

        Rabbit rabbit = new Rabbit();
        Tortoise tortoise = new Tortoise();
        tortoise.run();
        rabbit.run();
    }

}