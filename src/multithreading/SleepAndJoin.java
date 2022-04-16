package multithreading;

/*
При помощи метода sleep (с англ. спать) можно "усыпить" программу на определенное время.
Метод sleep является статическим методам класса Thread.
 */

public class SleepAndJoin extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + i);
//            currentThread (c англ. текущий поток)
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*
        Это пример не связан с многопоточностью. Пример продемонстрирует, что можно "усыпить"
        поток main. Используется только этот поток.

        for (int i = 5; i > 0; i--) {
            System.out.println(i);
//            После каждого вывода числа на экран программа будет спать 1 секунду
            Thread.sleep(1_000);
//            В параметр метода sleep передаются миллисекунды (1 сек = 1_000 миллисекунд)
//            Метод sleep выбрасывает исключение InterruptedException, поэтому нужно либо
              обернуть в try-catch, либо указать throws InterruptedException.
        }
        System.out.println("Go!");
//        Вывод:
//        5
//        (тут задержка 1 сек)
//        4
//        (тут задержка 1 сек)
//        3
//        (тут задержка 1 сек)
//        2
//        (тут задержка 1 сек)
//        1
//        (тут задержка 1 сек)
//        Go!
//        Метод sleep просто заставляет поток поспать на определенное время, которое указывает сам программист.
         */

        /*
        Пример с методом sleep, где больше одного потока.
         */
        SleepAndJoin thread1 = new SleepAndJoin();
        thread1.setName("thread1");
        thread1.start();

        Thread thread2 = new Thread(new MyRunnable());
        thread2.setName("thread2");
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("End");
//        Вывод (без использования метода join):
//        End
//        thread1 1
//        thread2 1
//        (тут задержка 1 сек)
//        thread2 2
//        thread1 2
//        (тут задержка 1 сек)
//        thread2 3
//        thread1 3
//        (тут задержка 1 сек)
//        thread2 4
//        thread1 4
//        (тут задержка 1 сек)
//        thread1 5
//        thread2 5
        /*
        Запускаются два потока thread1 и thread2 в методе main, работа обоих потоков одинакова, они выводят
        числа от 1 до 5 и перед этим указывают название потоков, в котором происходит действие. Это происходит
        с интервалом в одну секунду. И в потоке main выводится на экран слово End.
        Почему слово End вывелось в первую очередь?
        Потому, что автоматически срабатывает первым поток main и запускает новые потоки (thread1 и thread2), далее
        эти потоки отпачковуются от него, работают они абсолютно независимо от потока main и поток main продолжает
        свою работу. Поток main не спит, поэтому он сразу вывел на экран слово End и через одну секунду начинает
        появляться информацию на экран об обоих потоках - thread1 и thread2.
        ВАЖНЫЙ нюанс!
        Несмотря на то, что поток main очень быстро закончил свою работу, программа не завершает свою работу.
        В потоке main были созданы другие потоки, которые продолжают работать, следовательно, пока эти потоки
        не прекратят работу,  выполнение программы не завершится.

        InterruptedException (interrupted с англ. прервать или перебивать) поток может прервать другой поток, т.е.
        попросить его остановиться. Если какой-то поток2 просит поток1 остановиться, а в этот момент поток1
        находится в спячке, то будет выброшен InterruptedException, который будет означать, что пока поток1
        спал другой поток, попросил его остановиться.

        Метод sleep можно использовать с двумя параметрами:
        - первый параметр - миллисекунды (1 сек = 1_000 миллисекунд);
        - второй параметр - наносекунды (1 сек = 10 в 9 степени);
         */

        /*
        Метод join.

                public static void main(String[] args) {
                    SleepAndJoin thread1 = new SleepAndJoin();
                    thread1.start();

                    Thread thread2 = new Thread(new MyRunnable());
                    thread2.start();

                    System.out.println("End");

        К примеру, если необходимо, чтобы вывод на экран слова End произошло после того, как оба потока (thread1 и
        thread2) завершат свою работу, то нужно как-то объяснить потоку main, что он должен дождаться окончания потоков
        thread1 и thread2 и только потом продолжить свою работу, т.е. выполнить свой код после:

                SleepAndJoin thread1 = new SleepAndJoin();
                thread1.start();

                Thread thread2 = new Thread(new MyRunnable());
                thread2.start();

          в этом поможет метод join.
                public static void main(String[] args) throws InterruptedException {
                    SleepAndJoin thread1 = new SleepAndJoin();
                    thread1.start();

                    Thread thread2 = new Thread(new MyRunnable());
                    thread2.start();

                    thread1.join();
                    thread2.join();
                    System.out.println("End");
                }
          Метод join тоже выбрасывает InterruptedException, поэтому в методе main нужно указать
          throws InterruptedException или обернуть в try-catch.
          Поток в котором вызывается метод join (не НА котором), в данном случае в потоке main, т.е.

                public static void main(String[] args) throws InterruptedException {
                    SleepAndJoin thread1 = new SleepAndJoin();
                    thread1.start();

                    Thread thread2 = new Thread(new MyRunnable());
                    thread2.start();

                    thread1.join(); метод join вызывается внутри потока main
                    thread2.join(); метод join вызывается внутри потока main
                    System.out.println("End");

           поток main будет ждать окончание работы того потока, НА котором вызван метод join.
           Эти два statements

                thread1.join();
                thread2.join();

          означают, что поток main будет ожидать окончание работы thread1 и thread2 и только после
          этого продолжит свою работу. А продолжением его работы, в данном случае, будет вывод на экран
          слова End.
          Вывод:
          thread1 1
          thread2 1
          (тут задержка 1 сек)
          thread2 2
          thread1 2
          (тут задержка 1 сек)
          thread2 3
          thread1 3
          (тут задержка 1 сек)
          thread2 4
          thread1 4
          (тут задержка 1 сек)
          thread1 5
          thread2 5
          End
         */
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run()  {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
            Т.к. метод sleep может выбросить исключение, то нужно обернуть в try-catch. В этом примере нельзя
            написать, что метод run throws InterruptedException. Все потому, что в сигнатуре метода run,
            нельзя написать, что он выбрасывает исключение, т.к. происходит переопределение метода, а у
            функционального интерфейса Runnable метод run не выбрасывает никакое исключение:

                    @FunctionalInterface
                    public interface Runnable {
                        public abstract void run();
                    }

             Следовательно, при переопределении метода run нельзя написать, что метод run может выбросить какой-то
             Exception. Поэтому, необходимо ловить возможный выброс исключения.
             */
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}