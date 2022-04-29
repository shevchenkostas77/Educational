package multithreading;

/*
В старых версиях Java прерывали поток с помощью метода stop():

        имяПеременнойПотока.stop();

Этот метод устаревший и его не рекомендовано использовать. Он просто грубо прерывал
поток и некоторые объекты или процессы оставались в непонятном или незавершенном состоянии.

Теперь у программиста есть возможность послать сигнал потоку, что его хотят прервать - метод
interrupt(). И естественно, есть возможность в самом потоке проверить, хотят ли его прервать -
метод isInterrupted(). Что делать если данная проверка показала, что поток хотят прервать,
должен решать сам программист.

Прервать один поток из другого потока используя только метод interrupt() - невозможно, НО
поток сам может проверить хотят ли его прервать, для этого в методе необходимо указать
проверочное условие используя метод isInterrupted(). Этот метод вернет true, когда,
к примеру, поток main захочет прервать поток thread, как в примере ниже. В этом же примере,
вызывая метод interrupt() на потоке, который хотим прервать (thread.interrupt();) это
означает, что поток main будет пытаться прервать поток thread. Поток main, сам по себе,
не может прервать поток thread, но поток main может послать сигнал потоку thread, что его
хочет прервать. В свою очередь поток thread может проверить хотят ли его прервать
( if (isInterrupted()) ). Если поток thread видит, что его хотят прервать, то он может
реагировать на это совершенно по-разному. Как? Если программист хочет, то он может завершить
работу этого потока или даже зная, что поток хотят прервать можно продолжить его работу.
На сколько это будет правильным зависит от конкретно поставленных целей. Но дальнейшее
поведение программы решает сам программист.

При использовании методов sleep и join необходимо позаботиться о возможном выбрасывании
исключения InterruptedException. Исключение InterruptedException выбрасывается тогда, когда,
например, поток спит, а его хотят прервать. Когда идет проверка прерывания потока через метод
isInterrupted(), то эта проверка осуществляется естественно, когда поток работает, а не спит.
Представим, что поток InterruptedThread хотят прервать из потока main через 2 секунды после
того, как поток thread начал только спать и должен был еще проспать 7 секунд.
Если бы не было InterruptedException, т.е. если бы метод sleep не выбрасывал InterruptedException,
тогда узнать, что хотят прервать поток thread возможно было только через 10 секунд, т.е. после
того, как поток проснется:

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main starts");

        InterruptedThread thread = new InterruptedThread();
        thread.start(); // поток thread запущен

        Thread.sleep(2_000); // поток main засыпает на 2 сек., а в это время поток thread работает

        thread.interrupt(); // посылается сигнал потоку thread, что его хотят прервать

        thread.join();
        System.out.println("Main ends");
    }
}

class InterruptedThread extends Thread {
    double sqrtSum = 0;
    @Override
    public void run() {
        for (int i = 0; i <= 1_000_000_000; i++) {
            if (isInterrupted()) {
                System.out.println("Поток хотят прервать");
                System.out.println("Убедились, что состояние всех объектов нормальное и решили" +
                        "завершить работу потока");
                System.out.println(sqrtSum);
                return;
            }
            sqrtSum += Math.sqrt(i); //
            try {
                sleep(10_000); // поток thread засыпает на 10 сек
            } catch (InterruptedException e) {
                System.out.println("Поток хотят прервать во время сна. " +
                        "Завершена работу потока");
                System.out.println(sqrtSum);
                return;
            }
        }
        System.out.println(sqrtSum);
    }
}

Чтобы вот такой большой задержки не было методы sleep и join выбрасывают InterruptedException,
а именно после того, как сработал метод sleep или метод join, тем самым сразу видно, что
выбрасывается исключение и на это исключение можно как-то среагировать.
*/

public class InterruptionThreads {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main starts");

        InterruptedThread thread = new InterruptedThread();
        thread.start();
        Thread.sleep(2_000); // метод main засыпает на 2 секунды

        thread.interrupt(); // из потока main подается сигнал, что поток thread хотят прервать

        thread.join();
        System.out.println("Main ends");
    }
}

class InterruptedThread extends Thread {
    double sqrtSum = 0;
    @Override
    public void run() {
        for (int i = 0; i <= 1_000_000_000; i++) {
            if (isInterrupted()) { // проверка на прерывания потока
                System.out.println("Поток хотят прервать");
                System.out.println("Убедились, что состояние всех объектов нормальное и решили" +
                        "завершить работу потока");
                System.out.println(sqrtSum);
                return; // чтобы завершить работу потока
            } // если поток никто не хочет прерывать, то работа продолжается
        /*
        Теперь программа будет работать следующим образом, поток thread будет запущен, далее
        поток main заснет на 2 секунды, после чего будет послан сигнал потоку thread
        (InterruptedThread), что его хотят прервать (thread.interrupt();), thread поймает этот
        сигнал (if (isInterrupted()) {) он ведь каждый раз, при входе в тело цикла, проверяет
        хотят ли его прервать. После этого работа потока будет завершена.
        ВАЖНЫЙ момент!
        Программист сам решает, что делать, если поток хотят прервать. Все зависит от целей
        */
            sqrtSum += Math.sqrt(i); // sqrt (от англ. square root «квадратный корень»)
            try {
                sleep(10_000); // 10 сек
            } catch (InterruptedException e) {
                System.out.println("Поток хотят прервать во время сна. " +
                        "Завершена работу потока");
                System.out.println(sqrtSum);
                return; // т.к. решили завершить работу потока при возникновении исключения
            }
        }
        System.out.println(sqrtSum);
    }
}