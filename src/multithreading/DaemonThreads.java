package multithreading;

/*
User threads - обычне потоки.

       main
        |
        | ------------ T1 (user thread)
        |
        | ------------ T1 (user thread)
        |
    Завершение работы потока Main


Создался поток main, с которого все начинается и идет его работа. Внутри потока main
создаются, к примеру, потоки T1 и Т2. Программа завершит свою работу только тогда,
когда все ее потоки завершат свою. Т.е. поток main мог уже завершить свою работу, но
если потки Т1 или Т2 или даже они оба продолжают еще свою работу, то программа
работает. Программа завершит свою работу только тогда, когда потоки Т1 и Т2 завершат
свою.

       main
        |
        | ------------ T1 (user thread)
        | ----------------------------------- Daemon thread
        | ------------ T1 (user thread)
        |
    Завершение работы потока Main

Если, к примеру, создается поток Daemon - DT1, помимо потоков Т1 и Т2. В этом случае
будет работать программа слудующим образом, к примеру, main поток заврешил свою работу,
но он ждет остальные потоки, далее, к примеру, завершил свою работу поток Т1, а после
уже завершает свою работу и поток Т2. На этом программа завершается. Никто не будет
ждать окончания работы потока Daemon. Если user threads завершили свою работу, то и вся
программа завершает свою работу.

Daemon threads (с англ. потоки демона 😈) - предназначены для выполнения фоновых задач и
оказания различных сервисов User потокам.
При завершении работы последнего User потока программа завершает своё выполнение, не
дожидась окончания работы Daemon потоков. Срабатывает простая логика, если самих User
потоков нет, то им оказывать никаких сервисов не нужно и поэтому можно не дожидать
окончания работы потока Daemon. Daemon потоки очень полезны для поддержания каких-то
background (фоновых) заданий. Например, большинство потоков JVM - Daemon потоки.
Они занимаются сборкой мусора - Garbage collector, они освобождают память, удаляя
ненужные объекты и тд.

Объявить поток Daemon достаточно просто, нужно перед запуском (обязательно перед запуском)
потока вызвать его метод setDeamon() и передать значение true, тем самым сообщая программе,
что этот поток будет Daemon. Метод setDaemon() нужно вызывать после того, как поток был
создан и перед тем, как он был запущен. Если создать поток, запустить его и уже после
вызвать метод setDaemon, то будет выброшен IllegalThreadStateException.

Проверить является ли поток Daemon потоком можно вызвав его метод isDaemon().
*/

public class DaemonThreads {
    public static void main(String[] args) {
        System.out.println("Main thread starts");

        UserThread userThread = new UserThread();
        userThread.setName("user_thread");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setName("daemon_thread");
        // daemonThread.setDaemon(true); // объявление потока Daemon

        userThread.start();
        daemonThread.start();

        daemonThread.setDaemon(true); // вызов метод setDaemon после запуска потока

        System.out.println("Main thread ends");

        /*
        Запуск программы. Вывод:

        Main thread starts    // main thread начал свою работу
        Main thread ends      // main thread сразу закончил свою работу
        user_thread is daemon: false
        daemon_thread is daemon: true
        1
        2
        A
        3
        4
        5
        B
        6
        7
        8
        C
        9
        10
        11
        D
        12
        13
        14
        E
        15
        16
        17
        F
        18
        19
        20
        G
        21
        22
        23
        H
        24
        25
        26
        I
        27
        28
        29
        J

        Main thread начал свою работу, запустил два потока (user_thread и daemon_thread) и
        закончил свою работу, но программа продолжает работать.
        Запускаются два потока user_thread и daemon_thread. Идет проверка is "daemon?".
        После происходит вывод на экран значений. Сначала вывел свои значения daemon_thread
        (1, 2), потом user_thread вывел свое значение (A) и тд. И когда user_thread выводи
        свое последнее значение, он выходит из loop (цикла), заканчивает свою работу, то и
        программа заканчивает свою работу несмотря на то, что daemon_thread вывел числа
        всего лишь до 29, а не до 1_000.

        Если вызвать метод setDaemon после запуска потока, выброситься Exception -
        IllegalThreadStateException и поток daemon_thread будет выводить свои значения
        до 1_000, даже после того, как поток user_thread закончит свою работу.
        Запуск программы. Вывод:

        1
        2
        A
        3
        4
        5
        B
        ...
        28
        29
        J
        30
        31
        32
        33
        ...
        1000
        */

    }
}

class UserThread extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                " is daemon: " + isDaemon());
        for (char i = 'A'; i <= 'J'; i++) {
            try {
                Thread.sleep(300);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class DaemonThread extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                " is daemon: " + isDaemon());
        for (int i = 1; i <= 1_000; i++) {
            try {
                Thread.sleep(100);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}