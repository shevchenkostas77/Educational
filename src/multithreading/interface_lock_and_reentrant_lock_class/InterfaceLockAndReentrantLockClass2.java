package multithreading.interface_lock_and_reentrant_lock_class;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Есть банкомат, которым пользуются люди для различных операций. Этим банкоматом может
пользоваться только один человек в одно и тоже время, а остальные будут ждать.
*/

public class InterfaceLockAndReentrantLockClass2 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Employee("Zaur", lock);
        new Employee("Oleg", lock);
        new Employee("Elena", lock);
        new Employee("Victor", lock);
        new Employee("Marina", lock);
    }
    /*
    Запуск программы. Вывод:

    Oleg ждет...
    Zaur ждет...
    Marina ждет...
    Elena ждет...
    Victor ждет...
    Oleg пользуется банкоматом
    Oleg завершил(а) свои дела
    Zaur пользуется банкоматом
    Zaur завершил(а) свои дела
    Elena пользуется банкоматом
    Elena завершил(а) свои дела
    Victor пользуется банкоматом
    Victor завершил(а) свои дела
    Marina пользуется банкоматом
    Marina завершил(а) свои дела
    */
}

class Employee extends Thread {
    String name;
    private Lock lock;
    public Employee(String name, Lock lock) {
        this.name = name;
        this.lock = lock;
        this.start(); // после создания объекта сразу запускается поток
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " ждет...");
            lock.lock();
            // когда поток заходит в код, это означет, что работник пользуется банкоматом
            System.out.println(name + " пользуется банкоматом");
            Thread.sleep(3_000); // имитация пользования банкоматом
            System.out.println(name + " завершил(а) свои дела");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}