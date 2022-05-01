package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/*
Thread pool - это множество потоков, каждый из которых предназначен для выполнения той или иной задачи.
Thread pool, помимо того, что он удобен для использования, он более эффективен с точки зрения различных процессов,
которые происходят за кулисами, когда происходит работа с множеством потоков.

                task1                           Thread pool
                ------------->
                                                 Thread T1
                task2
                ------------->
                                                 Thread T2
                ...

                                                 Thread T3
                taskN
                ------------->

Thread pool - это множество потоков и допустим, что в thread pool находятся три потока: Т1, Т2, Т3. Программа поставляет
thread pool разные задания: task1, task2, ... , taskN. Уже внутри себя thread pool сам регулирует какой поток будет
выполнять какое задание. Допустим, за task1 может взяться поток T1, за task2 может взяться поток Т3. Когда поток Т1
заканчивает выполнение task1, он "говорит": "я свободен и могу выполнить следующее задание". Когда какой-либо поток
освобождается и если есть ожидающие задания, то освободившийся thread берет ожидающее одно задание и начинает его
выполнять. Вот и вся концепция Thread pool. В Java происходит работа с Thread pool по средствам объектов типа Executor
(c англ. исполнитель). Executor это интерфейс.
В Java c thread pool-ами удобнее всего работать посредством ExecutorService.
 */

public class ThreadPoolAndExecutorService {
    public static void main(String[] args) {
        /*
        C thread pool-ами удобнее всего работать посредством объектов типа ExecutorService.
        Вариант создания самого ThreadPoolExecutor:
        ExecutorService executorService = new ThreadPoolExecutor(); и в скобках нужно вписать определенные параметры
        Но напрямую Thread pool практически никогда не создают. Для того, что бы создать Thread pool нужно использовать
        один из factory (с англ. фабрика) методов класса Executors.
         */
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        /*
        Thread pool удобнее всего создавать, используя factory методы класса Executors:

        Executors.newFixedThreadPool(int count) - создаст pool с count (с конкретным значением) потоков
        newFixedThreadPool - новый фиксированный пул потоков;

        Executors.SingleThreadExecutor() - создаст pool с одним потоком

        Для работы с Thread pool используется ExecutorService, а создавать Thread pool лучше всего с помощью класса
        Executors. В примере выше Thread pool создан не при помощи конструктора, а с помощью метода newFixedThreadPool(5);
        Поэтому он и называется factory - фабрика по производству объектов. В данном случае, по производству Thread pool.
        Этот метод возвращает ThreadPoolExecutor.
         */
        for (int i = 0; i < 10; i++) {
            executorService.execute(new RunnableImplementation100()); // execute c англ. выполнять
        }
        executorService.shutdown();
        System.out.println("Thread main ends");
        /*
        Запуск программы. Вывод:
        Thread main ends
        pool-1-thread-1
        pool-1-thread-4
        pool-1-thread-3
        pool-1-thread-5
        pool-1-thread-2
        pool-1-thread-1
        pool-1-thread-4
        pool-1-thread-3
        pool-1-thread-5
        pool-1-thread-2
        (программа продолжает свою работу)

        Внутри потока main был создан Thread pool из 5-ти потоков:
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Далее при помощи цикла и метода execute 10 раз было передано задание, а именно new RunnableImplementation100 в
        Thread pool и поток main (он сработал быстрее всех) вывел на экран "Thread main ends".

        Как происходит работа pool-a?
        Если посмотреть на вывод выше и посчитать, то каждый поток выполнил по два задания, т.е. 5 потоков по 2 задания =
        10 заданий. И время исполнения каждого задания всего полсекунды.
        Thread pool-ом получено первое задание и первый поток начал выполнять это задание. Далее снова поступило задание
        и тут происходит проверка: "Есть ли свободный поток в tread pool-е?" Да, есть и четвертый поток взялся за
        выполнение второго задания. Поступило еще задание и снова проверка: "Есть ли свободный поток в tread pool-е?"
        Да, есть и третий поток взялся за выполнение третьего задания. Далее по аналогии до шестого задания.
        Цикл срабатывает быстрее и задания поступают быстрее, чем полсекунды и когда все потоки заняты, а новое задание
        поступило, тогда Thread pool "говорит": "Подождите, пока что свободных потоков нет, как только какой-либо поток
        освободится, сразу назначу ему шестое задание". Освобождается первый поток и назначается ему шестое задание и тд.
        Т.е. пять потоков постоянно работают пока есть задания. Освободился поток, взял задание и начал снова работать.
        Если в коде убрать sleep:
                class RunnableImplementation100 implements Runnable {

                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        т.е. выполнение этого задания будет практически моментальным.
        Запуск программы. Вывод:
        Thread main ends
        pool-1-thread-2
        pool-1-thread-2
        pool-1-thread-2
        pool-1-thread-2
        pool-1-thread-2
        pool-1-thread-2
        pool-1-thread-5
        pool-1-thread-1
        pool-1-thread-4
        pool-1-thread-3
        (программа продолжает свою работу)

        Выходит, что некоторые потоки сделают работу больше остальных и это нормальное явление. В выводе выше первые
        шесть заданий выполнил второй поток.

        Как это происходит?
        Второй поток взял первое задание и пока второе задание еще не поступило данному Thread pool-у, второй поток уже
        выполнил первое задание. Поступает второе задание и спрашивается: "Кто возьмет второе задание?". Второй поток
        берет это задание и второе задание второй поток снова выполняет моментально, еще до того, как третье задание
        поступило Thread pool-у и тд. Шесть раз второй поток выполнял свою работу, становился свободным потоком и брал
        новое задание. Если задание выполняется быстрее, чем поступает в Thread pool, то не нужно удивляться, что один
        какой-то поток выполняет больше работы, чем остальные.

        В примерах выводов выше было в конце добавлена фраза в скобках "программа продолжает свою работу". Все потому,
        что ExecutorService ждет новых заданий, что бы и их выполнить. Поэтому программа не заканчивает свою работу.
        Если нет намерения давать новые задания, то обязательно нужно заканчивать работу ExecutorService добавив метод
        shutdown() (с англ. выключение):
                public class ThreadPoolAndExecutorService {
                    public static void main(String[] args) {
                        for (int i = 0; i < 10; i++) {
                            executorService.execute(new RunnableImplementation100());
                        }
                        executorService.shutdown();
                        System.out.println("Thread main ends");
                    }
                }

        Запуск программы. Вывод:
        pool-1-thread-1
        pool-1-thread-2
        pool-1-thread-3
        pool-1-thread-4
        pool-1-thread-5
        pool-1-thread-1
        pool-1-thread-2
        pool-1-thread-3
        pool-1-thread-5
        pool-1-thread-4
        Thread main ends

        ExecutorService выполнил все задания и после программа закончилась. Вызвав метод shutdown, даем понять программе,
        что задач больше не будет и программа завершится после того, как все уже полученные задания выполнятся.

         */
    }
}

class RunnableImplementation100 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
