package multithreading;

import java.util.concurrent.*;

/*
Задание пример.
Необходимо найти факториал числа используя собственный класс CallableAndFuture и ExecutorService.
Факториал натурального числа N определяется как произведение всех натуральных чисел от 1 до N включительно. Т.е.
(факториал обозначается знаком "!") 5! = 1 * 2 * 3 * 4 * 5 = 120.

public class CallableAndFuture {
    static int factorialResult;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Factorial factorial = new Factorial(5);
        executorService.execute(factorial);
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(factorialResult);

        Если не попросить поток main подождать с помощью метода awaitTermination, тогда поток main сразу будет выводить
        на экран значение переменной factorialResult. Т.е. поток main не дождется выполнения метода run и выведет
        значение переменной factorialResult - 0. Поток main ждет либо 10 секунд, либо окончание работы всех потоков
        ExecutorService (в данном случае одного потока).

        Запуск программы. Вывод:
        120
    }
}

class Factorial implements Runnable {
    int f;

    public Factorial(int f) {
        this.f = f;
    }

    @Override
    public void run() {
        if (f <= 0) {
            System.out.println("Вы ввели не верное число");
            return; // заканчивается метод, если число меньше или равно 0
        }
        // Если число введено больше 0
        int result = 1;
        for (int i = 1; i <= f; i++) {
            result *= i;
        }
        CallableAndFuture.factorialResult = result;
    }
}

Какие недостатки у этого кода, а именно чего не хватает в классе, который имплементирует Runnable?
Приходится использовать переменную factorialResult вне класса, который имплементирует функциональный интерфейс Runnable,
чтобы передать значение result. Потому, что тип возвращаемого значения метода run - void, он ничего не возвращает. Так
же невозможно в методе run выбросить какое-либо исключение. К примеру, если в примере выше кто-то введет неверное число
вместо System.out.println("Вы ввели не верное число"); выбросить исключение:
        public void run() {
        if (f <= 0) {
            System.out.println("Вы ввели не верное число");
            return; // заканчивается метод, если число меньше или равно 0
        }
Почему нет возможности выбросить исключение?
Если посмотреть на сигнатуру метода run:

        public abstract void run();

видно, что метод не выбрасывает никакое исключение. Следовательно, когда происходит переопределения метода run, то не
возможно выбросить в нем исключение. Это недостатки интерфейса Runnable. НО есть интерфейс, который называется
Callable, который очень похож на Runnable, но он ко всему прочему может в output-e возвращать значение и позволяет
выбрасывать исключение. Как выглядит функциональный интерфейс Callable:

        @FunctionalInterface
        public interface Callable<V> {
            V call() throws Exception;
        }

Функциональный интерфейс Callable имеет в себе один абстрактный метод (поэтому функциональный), как и Runnable, и
называется метод call (у Runnable - run, Callable - call). Это метод может выбрасывать Exception и имеет return type (V).
Тут используются Generics, т.е. когда происходит создание Callable, нужно указать с каким типом будет этот Callable
работать. Т.е. какой тип данных будет в output-e у метода call.
Функциональный интерфейс Callable, как и функциональный интерфейс Runnable представляет собой какое-то задание (task),
которое должно быть выполнено потоком.
 */

public class CallableAndFuture {
    static int factorialResult;


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Factorial factorial = new Factorial(5);
        /*
        Для Callable нельзя использовать метод execute, т.к. в параметр принимает только Runnable. Вместо метода execute
        нужно использовать метод submit (с англ. отправить). Метод submit, так же как и метод execute, добавляет задание
        (task) в Thread pool, но помимо этого он еще и возвращает результат выполненного задания. А результат задания,
        в данном случае, Callable будет Integer. Этот результат будет храниться в объекте типа Future. Future тоже
        работает c Generics.
         */
        Future<Integer> future = executorService.submit(factorial);
        /*
        Как получить результат задания из Future?
        С помощью метода get. У Future есть метод get. Этот метод выбрасывает исключение. Выбрасывает он исключение
        потому, что метод call это допускает:
                @Override
                public Integer call() throws Exception {
                    if (f <= 0) {
                        throw new Exception("Вы ввели не верное число");
                    }
                    int result = 1;
                    for (int i = 1; i <= f; i++) {
                        result *= i;
                    }
                    return result;
                }
        тут то выбрасывается исключение. Поэтому, когда происходит попытка получить результат задания при помощи метода
        get, может выброситься исключение и поэтому необходимо учесть этот момент. Выброситься может исключение
        ExecutionException и если это исключение будет поймано, то при помощи метода getCause можно узнать причину
        (вывести на экран) по которой был выброшен Exception. А что эта причина покажет вот это сообщение: "Вы ввели не
        верное число".
         */
        try {
            factorialResult = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getCause()); // выведет причину по которой было выброшено исключение
        } finally {
            executorService.shutdown();
            /*
            Вообще ExecutorService всегда хорошо закрывать в finally блоках
             */
        }
        System.out.println(factorialResult);

        /*
        Callable, так же как и Runnable, представляет собой определенное задание, которое выполняется потоком.
        В отличие от Runnable Callable:
        1. имеет return type не void;
        2. может выбрасывать Exception;

        Метод submit передает задание (task) в Thread pool, для выполнения его одним из потоков, и возвращает тип
        Future, в котором и хранится результат выполнения задания.

        Метод get позволяет получить результат выполнения задания из объекта Future.
         */

    }
}

class Factorial implements Callable<Integer> {
    int f;

    public Factorial(int f) {
        this.f = f;
    }

    @Override
    public Integer call() throws Exception {
        if (f <= 0) {
            throw new Exception("Вы ввели не верное число");
        }
        int result = 1;
        for (int i = 1; i <= f; i++) {
            result *= i;
        }
        return result;
    }
}