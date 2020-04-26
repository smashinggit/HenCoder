# 线程

## 线程池的大小和cpu的关系

根据cup的核数创建线程
比如 cpu是4核，那就创建4个线程
         8核，创建8个线程
         
> 注意： 这样写并不能让每个核都执行一个线程，提高程序的效率。
因为一个机器中会有许许多多的线程，我们并不能保证每个核刚好执行我们程序中的线程
但是这样写是有道理的，它能根据不同的cup性能，创建不同数量的线程，保证程序在不同机器上都能有较高的效率

## 线程安全