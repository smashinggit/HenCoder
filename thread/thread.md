# 线程

## 线程池的大小和cpu的关系

根据cup的核数创建线程
比如 cpu是4核，那就创建4个线程
         8核，创建8个线程
         
> 注意： 这样写并不能让每个核都执行一个线程，提高程序的效率。
因为一个机器中会有许许多多的线程，我们并不能保证每个核刚好执行我们程序中的线程
但是这样写是有道理的，它能根据不同的cup性能，创建不同数量的线程，保证程序在不同机器上都能有较高的效率

## 线程安全

### synchronized 
本质：
- 保证⽅法内部或代码块内部资源（数据）的互斥访问。
即同⼀时间、由同⼀个Monitor 监视的代码，最多只能有⼀个线程在访问

-保证线程之间对监视资源的数据同步。
即，任何线程在获取到 Monitor 后的第⼀时间，会先将共享内存中的数据复制到⾃⼰的缓存中；
任何线程在释放 Monitor 的第⼀时间，会先将缓存中的数据复制到共享内存中

### volatile
- 保证加了 volatile 关键字的字段的操作具有原⼦性和同步性，其中原⼦性相当于实现了针对单⼀字段的线程间互斥访问。
 因此 volatile 可以看做是简化版的 synchronized
 
- volatile 只对基本类型 (byte、char、short、int、long、ﬂoat、double、boolean) 
的赋值操作和对象的引⽤赋值操作有效。

### java.util.concurrent.atomic 包
- 下⾯有 AtomicInteger AtomicBoolean 等类，作⽤和 volatile 基本⼀致，可以看做是
通⽤版的 volatile。

### Lock / ReentrantReadWriteLock
- ⼀般并不会只是使⽤ Lock ，⽽是会使⽤更复杂的锁，例如 ReadWriteLock ：

```
ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
Lock readLock = lock.readLock();
Lock writeLock = lock.writeLock();
private int x = 0;
private void count() {  //写数据的锁，保证同时只有一个线程进行写数据操作
 writeLock.lock();  
 try {
 x++;
 } finally {
 writeLock.unlock();
 }
}
private void print(int time) {
 readLock.lock();         //读数据的锁，没有线程限制，可以多线程同时读取
 try {
 for (int i = 0; i < time; i++) {
 System.out.print(x + " ");
 }
 System.out.println();
 } finally {
 readLock.unlock();
 }
}
```

### 线程安全问题的本质
在多个线程访问共同的资源时，在某⼀个线程对资源进⾏写操作的中途（写⼊已经开始，但还没
结束），其他线程对这个写了⼀半的资源进⾏了读操作，或者基于这个写了⼀半的资源进⾏了写
操作，导致出现数据错误

### 锁机制的本质
通过对共享资源进⾏访问限制，让同⼀时间只有⼀个线程可以访问资源，保证了数据的准确性。
不论是线程安全问题，还是针对线程安全问题所衍⽣出的锁机制，
它们的核⼼都在于共享的资源，⽽不是某个⽅法或者某⼏⾏代码。


## 线程间交互

### 中断一个线程
- stop()
- intercept() 配合 isInterrupted()

### wait()和 notify()

- 这两个方法都必须在 synchronize方法或者代码块中调用
- 这连个方法是Object类的方法，并不是线程的。

### join() 和  yield()

## ThreadLocal


# 协程


