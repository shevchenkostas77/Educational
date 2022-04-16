package multithreading;

public class ThreadNameAndThreadPriority implements Runnable {
    @Override
    public void run() {
        System.out.println("Method run. Thread name of = " +
                Thread.currentThread().getName());
        /*
        Статический метод currentThread класса Thread показывает текущий поток, т.е. если внутри метода run
        запустить метод currentThread, то этот метод покажет в каком потоке сейчас находимся:

                Thread.currentThread().getName()

        можно узнать имя текущего потока
         */
    }
    public static void main(String[] args) {
        /*
        Самостоятельно методы run не стоит запускать. Нужно оставить эту работу JVM, т.е. нужно запускать поток
        используя метод start (JVM сам, за кулисами, будет запускать метод run).
         */
        Thread thread = new Thread(new ThreadNameAndThreadPriority());
        thread.start();
        System.out.println("Method main. Thread name of = " +
                Thread.currentThread().getName());
//        Вывод:
//        Method run. Thread name of = Thread-0
//        Method main. Thread name of = main
        /*
        В методе main работа происходит с потоком, который называется main (его default имя). В методе run
        выводится информация о потоке, который называется Thread-0. Т.е. создался поток thread и он был
        успешно запущен. При запуске, с помощью метода start, вызывается метод run автоматически, а в методе
        run прописан код, который выводит информацию о текущем потоке используя метод currentThread. И после
        запуска программы Java показывает, что этот поток называется Thread-0.
        Если же попытаться запустить поток с помощью метода run, то на экране можно увидеть:

                Method run. Thread name of = main
                Method main. Thread name of = main

        В методе main поток называется main. В методе run так же поток называется main. Дело в том, что
        используя метод run не получилось запустить поток thread. Когда используется напрямую метод run
        (что делать нельзя!), то метод run выполняется как часть потока main. Никакой новый поток не
        запускается с помощью метода run, работа все так же продолжается в потоке main и этот метод run
        вызывается как какой-то обычный метод. Весь код:

                Thread thread = new Thread(new ThreadNameAndThreadPriority());
                thread.run();
                System.out.println("Method main. Thread name of = " +
                        Thread.currentThread().getName());

        обработан в потоке main. Поэтому метод run напрямую использовать нельзя.

         */

        MyThread myThread = new MyThread();
        System.out.println("Name of myThread = " + myThread.getName() + "\n" +
                "Priority of myThread = " + myThread.getPriority());
//        Вывод:
//        Name of myThread = Thread-0
//        Priority of myThread = 5

        System.out.println("-----------------------------------");

        MyThread myThread2 = new MyThread();
        System.out.println("Name of myThread2 = " + myThread2.getName() + "\n" +
                "Priority of myThread2 = " + myThread2.getPriority());
//        Вывод:
//        Name of myThread2 = Thread-1
//        Priority of myThread2 = 5


        /*
        Если самостоятельное не назвать поток (Thread) у него будет default имя и это имя выглядит
        так:
        Thread-0 (Thread тире и его очередность 0, 1, 2 и тд. Это не означает в какой последовательности
        они будут запускаться. Просто имя.)
        Что касается приоритета, если его не задать, то default приоритет это 5. Приоритетная шкала от 1 до 10,
        где 10 означает наивысший приоритет. Приоритет, как и имя, можно изменить, но никакой гарантии нет, что
        поток с высшим приоритетом запустится быстрее, чем поток с более низким приоритетом.
        Изменить имя потока можно при помощи метода setName и в параметрах метода указать в двойных кавычках
        имя потока:

                MyThread thread3 = new MyThread();
                thread3.setName("moy_potok");

         При помощи метода setPriority можно изменить приоритетность потока, но, еще раз, приоритетная шкала
         от 1 до 10, где 10 это наивысший приоритет. Нет никакой гарантии, что поток с высшим приоритетом
         запустится быстрее, чем поток с более низким приоритетом. Нет гарантии означает, что это должно работать,
         но в какой-то момент может не сработать. Т.е. если желательно, чтобы приоритетность какого-то потока была
         выше, но это условие не обязательное, то можно использовать данную функциональность.
         */
        System.out.println("-----------------------------------");

        MyThread thread3 = new MyThread();
        thread3.setName("moy_potok");
        thread3.setPriority(9);
        System.out.println("Name of thread3 = " + thread3.getName() + "\n" +
                "Priority of thread3 = " + thread3.getPriority());
//        Вывод:
//        Name of thread3 = moy_potok
//        Priority of thread3 = 9
        /*
        В примере выше переопределено имя и приоритет потока. Когда задается приоритет потока можно использовать
        числа в параметрах метода setPriority или использовать уже готовые варианты:
        - Thread.MIN_PRIORITY (приоритет равен 1);
        - Thread.NORM_PRIORITY (приоритет равен 5, что и default значение);
        - Thread.MAX_PRIORITY (приоритет равен 10);
        Используя готовые варианты читабельность кода улучшается.
         */
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Privet!");
    }
}