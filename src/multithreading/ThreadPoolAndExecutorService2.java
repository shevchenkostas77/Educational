package multithreading;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
Когда необходимо запускать поток или несколько потоков из Thread pool по какому-то расписанию, то необходимо
использовать ScheduledExecutorService (scheduled с англ. расписание).
Данный pool создается, используя factory (с англ. фабрика) метод класса Executors:
Executors.newScheduledThreadPool(int count) - создаст pool с count (с конкретным значением) потоков
 */

public class ThreadPoolAndExecutorService2 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        for (int i = 0; i < 10; i++) {
//            scheduledExecutorService.execute(new RunnableImplementation200());
//        }
//        scheduledExecutorService.schedule(new RunnableImplementation200(), 3, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleAtFixedRate(new RunnableImplementation200(),
//                3, 1, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(new RunnableImplementation200(),
                3,1,TimeUnit.SECONDS);
        Thread.sleep(20_000);
        scheduledExecutorService.shutdown();

        //Кешированный Thread pool
        ExecutorService executeService = Executors.newCachedThreadPool();
    }
}

class RunnableImplementation200 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begins work");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ends work");

        /*
        public class ThreadPoolAndExecutorService2 {
            public static void main(String[] args) {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1); // 1 поток
                for (int i = 0; i < 10; i++) {
                    scheduledExecutorService.execute(new RunnableImplementation200());
                }
            }
        }

        class RunnableImplementation200 implements Runnable {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " begins work");
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ends work");
            }
        }

        Запуск программы. Вывод:
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        (программа продолжает свою работу)

        Тут один поток выполняет всю описанную работу в RunnableImplementation200. Метод execute (с англ. выполнять)
        работает точно так же как и в обычном ExecutorService. Т.е. если нужен обычный execute метод, то нет смысла
        использовать ScheduledExecutorService. ScheduledExecutorService нужно использовать тогда, когда необходимо
        установить какое-либо расписание запуска потоков.

        Метод schedule.
        scheduledExecutorService.schedule(new RunnableImplementation200(), 3, TimeUnit.SECONDS);
        Что означает это написание?
        Метод schedule выполнит задания (task) через определенный период времени. Т.е. "говорим" ScheduledExecutorService
        выполни вот это задание new RunnableImplementation200() через три (3) секунды (TimeUnit.SECONDS). Вот так это
        читается программой.

        public class ThreadPoolAndExecutorService2 {
            public static void main(String[] args) {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(new RunnableImplementation200(), 3, TimeUnit.SECONDS);
                scheduledExecutorService.shutdown();
            }
        }

        class RunnableImplementation200 implements Runnable {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " begins work");
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ends work");
            }
        }

        Запуск программы. Вывод:
        (пройдет 3 секунды и начнется выполнение задания)
        pool-1-thread-1 begins work
        (через 5 секунд программа завершит выполнение задания)
        pool-1-thread-1 ends work

        Метод scheduleAtFixedRate.
        scheduledExecutorService.scheduleAtFixedRate(new RunnableImplementation200(), 3, 1, TimeUnit.SECONDS);
        Что означает это написание?
        Метод scheduleAtFixedRate планирует задачу для периодического выполнения, вот эту задачу (в данном случае):
        new RunnableImplementation200();. Впервые эта задача задача обработается через три секунды после запуска
        метода scheduleAtFixedRate и далее будет выполняться с периодичностью в 1 секунду.
        Вызывая метод shutdown сразу после метода scheduleAtFixedRate задание new RunnableImplementation200()
        выполнится всего один раз. Потому, что метод scheduleAtFixedRate позволяет задаче периодически выполняться, в
        данном случае каждую секунду, а из-за этого периода метод shutdown сработает и "скажет", что задачи больше не
        принимаются. Поэтому этот момент нужно отсрочить.

        public class ThreadPoolAndExecutorService2 {
            public static void main(String[] args) throws InterruptedException {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(new RunnableImplementation200(),
                        3, 1, TimeUnit.SECONDS);
                Thread.sleep(20_000);
                scheduledExecutorService.shutdown();
            }
        }

        class RunnableImplementation200 implements Runnable {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " begins work");
                System.out.println(Thread.currentThread().getName() + " ends work");

        Запуск программы. Вывод:
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        ...
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        (до тех пор будет работать, пока не пройдет 20 секунд, т.к. будет вызван метод shutdown)
        Период, который задается в параметре метода scheduleAtFixedRate (третий по счету параметр) это период между
        НАЧАЛОМ первого задания и НАЧАЛОМ последующего задания. Именно началом последующего задания, а не между началом
        и концом выполнения задания или же концом предыдущего задания и началом следующего.

        Если период указан в параметре метода scheduleAtFixedRate меньше, чем требуется времени на выполнение задания
        (к примеру, в параметре метода указана 1 секунда, а на выполнения задания необходимо 2 секунды)?
        Задание начинает выполняться, проходит 2 секунды и задание выполнилось, а период в параметре метода
        scheduleAtFixedRate указан 1 секунда, тогда ScheduledExecutorService ничего не ждет после завершения выполнения
        задания, а принимается сразу выполнять следующее задание, т.е. ScheduledExecutorService не ждет 1 секунду до
        начала следующего задания после окончания предыдущего. Если следующее задание тоже длится больше, чем период
        указанный в параметре метода, то ScheduledExecutorService так же не ждет, а сразу продолжает выполнять следующее
        задание после окончания предыдущего.

        public class ThreadPoolAndExecutorService2 {
            public static void main(String[] args) throws InterruptedException {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(new RunnableImplementation200(),
                        3, 1, TimeUnit.SECONDS);
                Thread.sleep(20_000);
                scheduledExecutorService.shutdown();
            }
        }

        class RunnableImplementation200 implements Runnable {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " begins work");
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ends work");

        Запуск программы. Вывод:
        (пройдет 3 секунды и начнется выполнение задания)
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work           // как только задание закончило выполняться
        pool-1-thread-1 begins work         // начинает сразу выполняться следующее задание и тд. пока не пройдет 20 сек
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work

        Метод scheduleWithFixedDelay.
        scheduledExecuteService.scheduleWithFixedDelay(new RunnableImplementation200(), 3, 1, TimeUnit.SECONDS);
        Что означает это написание?
        Он принимает такие же параметры, что и scheduleAtFixedRate, но отличие в том, что период, который указывается
        в параметре метода scheduleWithFixedDelay это задержка по времени между окончанием предыдущего задания и началом
        следующего.
        Этот метод выполнит впервые задание (new RunnableImplementation200()) через 3 секунды. Через 3 секунды (в методе
        run - Thread.sleep(3_000);) закончится его выполнение и через 1 секунду (период заданный в параметре метода
        scheduleWithFixedDelay) начнется выполнение следующего задания. Т.е. задание будет выполняться с той
        периодичностью, которое указано в параметре метода, не смотря на продолжительность выполнения самого задания.

        public class ThreadPoolAndExecutorService2 {
            public static void main(String[] args) throws InterruptedException {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleWithFixedDelay(new RunnableImplementation200(),
                        3,1,TimeUnit.SECONDS);
                Thread.sleep(20_000);
                scheduledExecutorService.shutdown();
            }
        }

        class RunnableImplementation200 implements Runnable {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " begins work");
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ends work");
        Запуск программы. Вывод:
        (пройдет 3 секунды и начнется выполнение задания)
        pool-1-thread-1 begins work     // 3 сек будет выполняться задание
        pool-1-thread-1 ends work       // через 3 сек закончится выполняться задание и через 1 сек начнется следующее
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work
        pool-1-thread-1 begins work
        pool-1-thread-1 ends work

       Кешированный Thread pool.
       ExecuteService executeService = Executors.newCachedThreadPool();
       Метод newCachedThreadPool создаст Thread pool, который будет создавать в себе новые потоки по надобности.
       Приходит первое задание и кешированный Thread pool создал внутри себя поток Т1. Поток Т1 выполняет первое задание.
       Тем временем приходит второе задание и в кешированном Thread pool создается поток Т2, который берет на себя это
       задание. Приходит третье задание и если какой-либо поток освободился, то освободившийся поток будет
       переиспользован. К примеру, третьем заданием займется поток Т1, т.к. он выполнил первое задание. Приходит
       четвертое задание и если оба, ранее созданных, потока заняты, то создастся новый поток (Т3) для выполнения
       четвертого задания. Т.е. когда ранее созданные потоки становятся свободными для выполнения задания, то они могут
       быть переиспользованы. Если потоков не хватает для выполнения заданий, то кешированный Thread pool создаст
       необходимое количество потоков.
       Наступает момент, когда задания не приходят и если через 60 секунд, после того как был использован последний
       поток, к примеру это Т3, к потоку Т3 через 60 секунд не пришло новое задание,то кешированный Thread pool удалит
       его, т.к. посчитает, что кешированному Thread pool-у этот поток уже не нужен.
         */
    }
}