package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.ArrayList;

/*
Почему интерфейс Future называется Future?
Когда вызывается метод submit, то метод возвращает Future, но результат работы задания во Future пока что нет:

        Future<Integer> future = executorService.submit(factorial);

Он будет в будущем, когда задание полностью выполнится. Поэтому так и называется Future (с англ. будущее). И когда
происходит попытка вытащить из объекта типа Future результат выполненного задания (используя метод get), а это
задание не выполнилось, поток, который вызвал метод get, т.е. поток main, в данном случае, будет заблокирован до
тех пор, пока задание не выполнится полностью. Т.е. в данном случае, пока факториал числа не будет найден Future
не вернет результат.
Чуть более подробно (еще раз).

Код программы:

        public class Main {
            static int factorialResult;

            public static void main(String[] args) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Factorial factorial = new Factorial(5);

                Future<Integer> future = executorService.submit(factorial);

                try {
                    System.out.println("Хотим получить результат");
                    factorialResult = future.get();
                    System.out.println("Получили результат");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    System.out.println(e.getCause());
                } finally {
                    executorService.shutdown();
                }
                System.out.println(factorialResult);
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
                for (int i = 0; i <= f; i++) {
                    result *= i;
                    Thread.sleep(1_000);
                }
                return result;
            }
        }

В этой строчке кода:

        Future<Integer> future = executorService.submit(factorial);

отправляется (submit с англ. отправить) задание (factorial) и сразу "на выходе" получается объект типа Future. Но
на самом деле в этот момент задание еще не выполнилось, Thread pool не закончил свою работу. И в этой строчке кода:

        factorialResult = future.get();

при помощи метода get сразу же хотим узнать результат задания (грубо говоря, сразу хотим получить результат после
того, как только передали задание в Thread pool, а на выполнение задание нужно время). Метод get заблокирует поток,
в котором он был вызван, в данном случае поток main, до тех пор, пока задание не выполнится полностью и Future
(factorialResult = future.get();) сможет вернуть результат работы этого задания (task-a).
При нахождении факториала в каждой итерации был добавлен сон в одну секунду:

        Thread.sleep(1_000);

Далее, после запуска программы, на экране сначала выведется "Хотим получить результат" и пока все задание (factorial)
полностью не будет выполнено, поток main блокируется на следующей строчке кода:

        factorialResult = future.get();

будет ждать до тех пор, пока не закончится работа Thread pool (выполнится задание полностью).

Запуск программы. Вывод:

        Хотим получить результат // после этой фразы некоторое время результат не выводится, а именно 5 сек.
        Получили результат
        120

Почему в данном примере не нужно использовать метод awaitTermination?
Когда программа доходит до строки кода (последней):

        System.out.println(factorialResult);

переменная factorialResult будет хранить значение, которое передается в следующей строке кода:

        factorialResult = future.get();

поэтому в данном случае метод awaitTermination использовать нет смысла, т.к. метод awaitTermination блокирует поток в
котором он вызывается, а метод get уже несет данный функционал (блокирует поток в котором он вызывается).

Пример, когда выбрасывается Exception.
Для примера необходимо при создании объекта типа Factorial указать любое отрицательное число:

        Factorial factorial = new Factorial(-6);

Все остальное без изменений.

Запуск программы. Вывод:

        Хотим получить результат
        java.lang.Exception: Вы ввели не верное число
        0

Когда дело доходит до метода get (factorialResult = future.get();) выбрасывается Exception и при помощи метода
getCause выводится текст этого Exception - "Вы ввели не верное число", а текст был указан в переопределенном
методе call.

С помощью Future можно проверить завершено ли задание (task). Это проверяется при помощи метода isDone (возвращает true
или false):

        public static void main(String[] args) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Factorial factorial = new Factorial(5);

            Future<Integer> future = executorService.submit(factorial);

            try {
                System.out.println("The task been completed? " + future.isDone());

                System.out.println("Хотим получить результат");
                factorialResult = future.get();
                System.out.println("Получили результат");

                System.out.println("The task been completed? " + future.isDone());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e.getCause());
            }  finally {
                executorService.shutdown();
            }
            System.out.println(factorialResult);
        }

Запуск программы. Вывод:

        The task been completed? false
        Хотим получить результат
        Получили результат
        The task been completed? true
        120

Объект Future возвращается когда используется метод submit. Метод submit можно использовать когда работа происходит
с Runnable тоже:

        Future future = executorService.submit(factorial);

Тут уже нельзя использовать Generics потому, что метод run не возвращает какой-либо результат, поэтому Future
используется без Generics. И метод get, который будет вызван на объекте типа Future всегда будет возвращать
null, ведь метод run ничего не возвращает. И какой тогда смысл использовать метода submit с Runnable?
Т.к. метод submit возвращает объекта типа Future, то при помощью Future можно сделать cancel (с англ. отмена)
задания или узнать закончилась ли работа при помощи метода isDone.

Runnable можно использовать как с ExecutorService, так и при отдельном создании Thread:

        1. ExecutorService executorService = Executors.newFixedThreadPool(int count);
           executorService.execute(new ClassImplementsRunnable());

        2. Thread thread = new Thread(new ClassImplementsRunnable());

Callable можно использовать только с ExecutorService:

        1. ExecutorService executorService = Executors.newFixedThreadPool(int count);
           executorService.submit(new ClassImplementsCallable);

        2. ClassImplementsCallable nameClass = new ClassImplementsCallable;
           Thread thread = new Thread(nameClass);   <- так делать нельзя, компилятор не разрешит.

Задание (task) НЕ ВОЗВРАЩАЛО результат, то нужно использовать Runnable.
Задание (task) ВОЗВРАЩАЛО результат, то нужно использовать Callable.
*/

// public class Main {
//     static int factorialResult;

//     public static void main(String[] args) {
//         ExecutorService executorService = Executors.newSingleThreadExecutor();
//         Factorial factorial = new Factorial(5);

//         Future<Integer> future = executorService.submit(factorial);

//         try {
//             System.out.println("The task been completed? " + future.isDone());

//             System.out.println("Хотим получить результат");
//             factorialResult = future.get();
//             System.out.println("Получили результат");

//             System.out.println("The task been completed? " + future.isDone());
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         } catch (ExecutionException e) {
//             System.out.println(e.getCause());
//         } finally {
//             executorService.shutdown();
//         }
//         System.out.println(factorialResult);
//     }

// }

// class Factorial implements Callable<Integer> {
//     int f;

//     public Factorial(int f) {
//         this.f = f;
//     }

//     @Override
//     public Integer call() throws Exception {
//         if (f <= 0) {
//             throw new Exception("Вы ввели не верное число");
//         }
//         int result = 1;
//         for (int i = 1; i <= f; i++) {
//             result *= i;
//             Thread.sleep(1_000);
//         }
//         return result;
//     }
// }

/*
Задание-пример на использования Callable.
Нужно найти сумму чисел от 1 до 1_000_000_000, не используя какие-либо формулы. Сделать нужно это не одним потоком, а,
к примеру, 10-ю:
    - первый поток: от 1 до 100_000_000;
    - второй поток: от 100_000_001 до 200_000_000;
    ...
    - десятый поток: от 900_000_001 до 1_000_000_000;
*/

public class CallableAndFuture2 {
    private static long value = 1_000_000_000L;
    private static long sum = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Long>> futureResults = new ArrayList<>();
        /*
        Чтобы в цикле for нормально "разбросать" сложение чисел от 1 до 1_000_000_000 на 10 tasks нужна дополнительная
        переменная
        */
        long valueDividedBy10 = value / 10;
        for (int i = 0; i < 10; i++) { // создаются 10 tasks для ExecutorService
            long from = valueDividedBy10 * i + 1;
            long to = valueDividedBy10 * (i + 1);

            PartialSum task = new PartialSum(from, to);
            Future<Long> futurePartSum = executorService.submit(task);
            futureResults.add(futurePartSum);
        }
        for (Future<Long> result : futureResults) {
            sum += result.get();
        }
        executorService.shutdown();
        // тут метод awaitTermination не нужен
        System.out.println("Total sum = " + sum);

        /*
        Запуск программы. Вывод:
        Sum from 600000001 to 700000000 = 65000000050000000
        Sum from 300000001 to 400000000 = 35000000050000000
        Sum from 700000001 to 800000000 = 75000000050000000
        Sum from 200000001 to 300000000 = 25000000050000000
        Sum from 900000001 to 1000000000 = 95000000050000000
        Sum from 100000001 to 200000000 = 15000000050000000
        Sum from 500000001 to 600000000 = 55000000050000000
        Sum from 800000001 to 900000000 = 85000000050000000
        Sum from 400000001 to 500000000 = 45000000050000000
        Sum from 1 to 100000000 = 5000000050000000
        Total sum = 500000000500000000
        */
    }
}

class PartialSum implements Callable <Long> { // Partial с англ. частичный
    // Чтобы разделить по разным потокам эту задачу, нужно потокам сообщить с какого числа начинать считать и до какого.
    long from;
    long to;
    long localSum; // значение сумму для конкретного потока будет записано в эту переменную

    public PartialSum(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public Long call() { // выбрасывать Exception в данном случае не нужно
        for (long i = from; i <= to; i++) {
            localSum += i;
        }
        System.out.println("Sum from " + from + " to " + to + " = " + localSum);
        return localSum;
    }
}