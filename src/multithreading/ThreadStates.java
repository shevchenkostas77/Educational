package multithreading;

/*
Thread states (с англ. состояния потока).
У потока может быть несколько состояний:
1. состояние, когда поток NEW - это после создания, до вызова метода start;
2. состояние RUNNABLE - когда вызывается метод start, т.е. состояние выполнения;
3. состояние TERMINATED - когда работа потока завершена;

В свою очередь состояние RUNNABLE делится на два состояния:
1. состояние ready - готовность;
2. состояние running - когда поток запущен и на самом деле выполняется;

Когда вызывается метод start поток после этого находится в состоянии ready. Он ждет операционную систему,
которая должна запустить его. Ждет он потому, что в очереди может быть очень много потоков, которые ожидают
своего выполнения и тогда поток сразу не начнет работу, он будет стоять в какой-то очереди. После того как
операционная система запустила поток его состояние становится running.
 */

public class ThreadStates {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Method main begins");

        Thread thread = new Thread(new Worker2());
//        При помощи метода getState можно получить информацию о состоянии потока
        System.out.println(thread.getState());

//        Запускается поток и проверяется следующей строкой его состояние с выводом на экран
        thread.start();
        System.out.println(thread.getState());


        thread.join();
        /*
        Тут заканчивает свою работу поток и выводится его состояние на экран.
        Почему поток thread заканчивает свою работу именно здесь?
        На этом этапе поток main останавливает свою работу до тех пор, пока поток thread не закончит
        свою работу. Все, что идет после этой строки thread.join(); будет работать только тогда,
        когда завершиться работы потока thread.
         */
        System.out.println(thread.getState());

        System.out.println("Method main is done");
//        Вывод:
//        Method main begins
//        NEW
//        RUNNABLE
//        Work begins
//        Work is done
//        TERMINATED
//        Method main is done

        /*
        Нужно быть очень осторожным с выводом состояний потоков. Ведь пока эта информация выводится на
        экран, если поток очень быстрый, то эта информация может быть уже не актуальной, т.к. поток
        может уже завершить свою работу. В примере выше все гладко сработало по причине, что поток thread
        довольно-таки долго работает (2.5 сек), поэтому в терминале отобразились его состояния:
        NEW, RUNNABLE, TERMINATED. А если бы поток очень быстро работал, то состояние RUNNABLE могло
        и не отобразиться, т.е. пока выводится информация на экран, состояние потока может быть уже
        TERMINATED.
         */

    }
}

class Worker2 implements Runnable {
    @Override
    public void run() {
        System.out.println("Work begins");
        try {
            Thread.sleep(2_500);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work is done");
    }
}