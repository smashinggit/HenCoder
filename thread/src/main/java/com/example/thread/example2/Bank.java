package com.example.thread.example2;

/**
 * 两个人AB通过一个账户同时取钱,A在柜台取钱和B在ATM机取钱！
 * <p>
 * 程序分析：钱的数量要设置成一个静态的变量。两个人要取的同一个对象值
 */
public class Bank {

    public static int MONEY = 1000;


    public synchronized void counter(String user, int money) {
        if (MONEY < money) {
            System.out.println("余额不足");
        } else {
            MONEY -= money;
            System.out.println(user + "在银行柜台取走了" + money + "元，剩余金额为" + MONEY + "元");
        }
    }

    public synchronized void ATM(String user, int money) {
        if (MONEY < money) {
            System.out.println("余额不足");
        } else {
            MONEY -= money;
            System.out.println(user + "在ATM取走了" + money + "元，剩余金额为" + MONEY + "元");
        }
    }

}

class Person extends Thread {
    Bank bank;

    public Person(String name, Bank bank) {
        super(name);
        this.bank = bank;
    }

    @Override
    public void run() {

        while (Bank.MONEY > 100) {

            if (getName().equals("用户A")) {
                bank.counter(getName(), 150);
            } else {
                bank.ATM(getName(), 120);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class MainClass {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Person personA = new Person("用户A", bank);
        Person personB = new Person("用户B", bank);

        personA.start();
        personB.start();
    }

}
