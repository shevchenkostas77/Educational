package multithreading.interfacelockandreentrantlockclass;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Lock - интерфейс, который имплементируется классом ReentrantLock.

ReentrantLock в переводе с англ. "блокировка повторного входа".
Так же как ключевое слово synchronized, Lock нужен для достижения синхронизации между
потоками.
*/

public class InterfaceLockAndReentrantLockClass1 {
    public static void main(String[] args) {
        Call call = new Call();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                call.mobileCall();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                call.skypeCall();
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                call.whatsappCall();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        /*
        Когда любой из этих потоков: thread1, thread2, thread3, начнет обрабатывать
        какой-то метод, допустим skypeCall, то другие потоки в этот же момент не
        смогут обработать mobileCall и whatsappCall. Они будут ждать окончания
        skype звонка.
        */

        /*
        Запуск программы. Вывод:

        Mobile call starts
        (тут задержка 3 сек)
        Mobile call ends
        Skype call starts
        (тут задержка 5 сек)
        Skype call ends
        WhatsApp call starts
        (тут задержка 7 сек)
        WhatsApp call ends

        В этом примере можно вызовом метода lock() заменить на synchronized блок и
        программа будет работать так же. Цель ReentrantLock и synchronized конструкции
        одинакова - с их помощью добиваемся, чтобы один поток работал в один и тот же
        момент времени, а остальные потоки ждали.

        Какой плюс есть у synchronized конструкции по отношению к Lock?
        В synchronized конструкциях не нужно заботиться о том, чтобы открыть lock -
        lock.unlock();, а это происходит автоматически. А если использовать класс
        ReentrantLock, то нужно всегда помнить об открытии замка - lock.unlock();
        Если завыть сделать unlock, к примеру, mobileCall, то:
        Запуск программы. Вывод:

        Mobile call starts <- мобильный звонок начался
        (тут задержка 3 сек)
        Mobile call ends <- мобильный звонок закончился
        (выводится информирование)

        Но из-за того, что lock не открылся (не освободили), lock продолжает висеть,
        т.е. ни один другой поток не может обработать свой код, они ждут открытия
        lock.
        */
    }
}

class Call {
    private Lock lock = new ReentrantLock();
    /*
    Сам Lock - это интерфейс;
    ReentrantLock - это класс, который имплементирует интерфейс Lock, т.е. lock -
    это объект этого класса и он будет использован для достижения синхронизации.
    */

    /*
    Методы интерфейса Lock:
    - lock();
    - unlock();
    Когда вызывается метод lock(), то происходит активация объекта класса ReentrantLock
    или класса имплементирующего интерфейс lock и тогда кодом, который идет после
    метода lock, в одно и то же время может воспользоваться только один поток. После
    того, как был написан код, который будут по очереди обрабатывать потоки, нужно на
    объекте, в данном случае, lock вызвать метод unlock.
    */

    void mobileCall() {

        lock.lock(); // вызывается метод lock() на объекте lock

        try {
            // начало кода
            System.out.println("Mobile call starts"); // начало мобильного звонка
            Thread.sleep(3_000); // имитация мобильного звонка  (3 сек)
            System.out.println("Mobile call ends"); // завершение мобильного звонка
            // конец кода

            /*
            Код от начала и до конца может выполняться только одним потоком в одно и
            то же время, после выполнения этого кода нужно снять замок с объекта lock.
            Это делается при помощи метода unlock. Метод unlock всегда нужно писать в
            блоке finally для того, если вдруг выбросится какой-то Exception, то
            замок будет снят с объекта в любом случае.
            */

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock(); // вызывается метод unlock() на объекте lock

        }
    }

    void skypeCall() {

        lock.lock(); // вызывается метод lock() на объекте lock

        try {
            // начало кода
            System.out.println("Skype call starts"); // начало мобильного звонка
            Thread.sleep(5_000); // имитация мобильного звонка (5 сек)
            System.out.println("Skype call ends"); // завершение мобильного звонка
            // конец кода

            /*
            Код от начала и до конца может выполняться только одним потоком в одно и
            то же время, после выполнения этого кода нужно снять замок с объекта lock.
            Это делается при помощи метода unlock. Метод unlock всегда нужно писать в
            блоке finally для того, если вдруг выбросится какой-то Exception, то
            замок будет снят с объекта в любом случае.
            */

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock(); // вызывается метод unlock() на объекте lock

        }
    }

    void whatsappCall() {

        lock.lock(); // вызывается метод lock() на объекте lock

        try {
            // начало кода
            System.out.println("WhatsApp call starts"); // начало мобильного звонка
            Thread.sleep(7_000); // имитация мобильного звонка (7 сек)
            System.out.println("WhatsApp call ends"); // завершение мобильного звонка
            // конец кода

            /*
            Код от начала и до конца может выполняться только одним потоком в одно и
            то же время, после выполнения этого кода нужно снять замок с объекта lock.
            Это делается при помощи метода unlock. Метод unlock всегда нужно писать в
            блоке finally для того, если вдруг выбросится какой-то Exception, то
            замок будет снят с объекта в любом случае.
            */

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock(); // вызывается метод unlock() на объекте lock

        }
    }

    /*
    Если будет несколько потоков и какой-то один из них вызовет один из этих методов:
    mobileCall, skypeCall, whatsappCall, то другой поток вызвав любой из этих методов
    увидит, что замок закрыт - lock.lock(); и этот замок будет закрыт до тех пор, пока
    первый поток не откроет этот замок - lock.unlock();. Т.е. откроется этот замок в
    самом конце исполнения метода.
    */
}